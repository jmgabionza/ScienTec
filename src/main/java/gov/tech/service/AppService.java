package gov.tech.service;

import gov.tech.dao.SessionDao;
import gov.tech.dao.UserDao;
import gov.tech.domain.LunchSession;
import gov.tech.domain.Participant;
import gov.tech.model.UserForm;
import gov.tech.util.AppServiceUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppService {
    @Autowired
    private SessionDao sessionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private LunchObserver observer;

    @Transactional
    public void submitChoice(AppServiceDto dto){
        LunchSession lunchSession = sessionDao.getById(dto.getSessionId());
        if (lunchSession == null){
            System.err.println("Session ID is either not found or not active. ");
        }
        System.out.println("Session ID found and active.");
        dto.setLunchSession(lunchSession);
        Participant participant = AppServiceUtil.transformDtoToDomain(dto);
        userDao.save(participant);
    }

    @Transactional
    public void createSession(String sessionId){
        LunchSession lunchSession = new LunchSession();
        lunchSession.setSessionId(sessionId);
        lunchSession.setActive(true);
        sessionDao.save(lunchSession);

        //observer will notify participants of the new session
        observer.inviteParticipants();
    }
    @Transactional
    public String closeSession(String sessionId){
        LunchSession lunchSession = sessionDao.getById(sessionId);
        List<Participant> participants = userDao.getUsersByActiveSessionId(sessionId);
        if (null != lunchSession && lunchSession.isActive()){
            lunchSession.setActive(false);
            lunchSession.setParticipants(participants);
            sessionDao.update(lunchSession);
        }

        // when session is closed, the observer will perform below actions
        String chosenRestaurant =  observer.chooseARestaurant(participants);
        System.out.println("The chosen restaurant is....... " + chosenRestaurant + "!!!");
        observer.updateResult(lunchSession, chosenRestaurant);
        return chosenRestaurant;
    }

    public List<UserForm> getDemographics(String sessionId){
        List<Participant> participants = userDao.getUsersByActiveSessionId(sessionId);
        return AppServiceUtil.transformUserListToModel(participants);
    }



}
