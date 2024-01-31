package gov.tech.service.impl;

import gov.tech.dao.ParticipantDao;
import gov.tech.dao.SessionDao;
import gov.tech.domain.LunchSession;
import gov.tech.domain.Participant;
import gov.tech.model.UserForm;
import gov.tech.service.AppServiceDto;
import gov.tech.service.ParticipantService;
import gov.tech.util.AppServiceUtil;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantServiceImpl implements ParticipantService {
    private final ParticipantDao participantDao;
    private final SessionDao sessionDao;

    @Autowired
    public ParticipantServiceImpl(ParticipantDao participantDao, SessionDao sessionDao) {
        this.participantDao = participantDao;
        this.sessionDao = sessionDao;
    }

    @Transactional
    @Override
    public String submitChoice(AppServiceDto dto) {
        String response = "";
        LunchSession lunchSession = sessionDao.getById(dto.getSessionId());
        boolean isDuplicate = isDuplicate(dto);

        if (lunchSession == null || !lunchSession.isActive()){
            response = "Session ID is either not found or no longer active. ";
            System.err.println(response);
            return response;
        }

        if (isDuplicate){
            response = "Your choice has been previously saved.";
            System.err.println(response);
            return response;
        }

        System.out.println("Session ID found and active.");
        dto.setLunchSession(lunchSession);
        Participant participant = AppServiceUtil.transformDtoToDomain(dto);
        participantDao.save(participant);
        response = "Your choice has been submitted.";
        return response;
    }

    @Transactional
    @Override
    public List<UserForm> getDemographics(String sessionId) {
        List<Participant> participants = participantDao.getUsersBySessionId(sessionId);
        return AppServiceUtil.transformUserListToModel(participants);
    }

    private boolean isDuplicate(AppServiceDto dto) {
        boolean isDuplicate = false;
        Participant existingParticipant = participantDao.getUserByName(dto.getName());
        if (existingParticipant != null){
            isDuplicate = true;
        }
        return isDuplicate;
    }
}
