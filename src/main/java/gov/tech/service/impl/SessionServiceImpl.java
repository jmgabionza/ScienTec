package gov.tech.service.impl;

import gov.tech.dao.ParticipantDao;
import gov.tech.dao.SessionDao;
import gov.tech.domain.LunchSession;
import gov.tech.domain.Participant;
import gov.tech.exception.AppServiceException;
import gov.tech.model.UserForm;
import gov.tech.service.AppServiceDto;
import gov.tech.service.LunchObserver;
import gov.tech.service.SessionService;
import gov.tech.util.AppServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {
    private ParticipantDao participantDao;
    private SessionDao sessionDao;
    private LunchObserver observer;

    @Autowired
    public SessionServiceImpl(ParticipantDao participantDao, SessionDao sessionDao, LunchObserver observer) {
        this.participantDao = participantDao;
        this.sessionDao = sessionDao;
        this.observer = observer;
    }

    @Override
    public String createSession(String sessionId) {
        LunchSession lunchSession = new LunchSession();
        lunchSession.setSessionId(sessionId);
        lunchSession.setActive(true);

        // check if session already exists
        LunchSession existingSession = sessionDao.getById(sessionId);

        if (existingSession == null){
            sessionDao.save(lunchSession);
            //observer will notify participants of the new session
            observer.inviteParticipants();
            return "Your Session is created successfully.";
        }

        return "Unable to create Session. The ID already exists.";
    }

    @Override
    public String closeSession(String sessionId) throws AppServiceException {
        LunchSession lunchSession = sessionDao.getById(sessionId);
        List<Participant> participants = participantDao.getUsersByActiveSessionId(sessionId);
        if (lunchSession == null){
            String response = "Session Id " + sessionId + " does not exist!";
            System.out.println(response);
            throw new AppServiceException(response);
        }

        if (!lunchSession.isActive()){
            String response = "Session Id " + sessionId + " is already closed!";
            System.out.println(response);
            throw new AppServiceException(response);
        }

        lunchSession.setActive(false);
        lunchSession.setParticipants(participants);
        sessionDao.update(lunchSession);

        // when session is closed, the observer will perform below actions
        String chosenRestaurant =  observer.chooseARestaurant(participants);
        System.out.println("The chosen restaurant is..... " + chosenRestaurant + "!!!");
        observer.updateResult(lunchSession, chosenRestaurant);
        return chosenRestaurant;
    }

    @Override
    public UserForm getSession(String sessionId) {
        LunchSession lunchSession = sessionDao.getById(sessionId);
        if (lunchSession != null){
            AppServiceDto dto =  AppServiceUtil.mapLunchSessionToDto(lunchSession);
            return AppServiceUtil.transformDtoToModel(dto);
        }
        return null;
    }
}
