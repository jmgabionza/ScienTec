package gov.tech.service.impl;

import gov.tech.dao.ParticipantDao;
import gov.tech.dao.SessionDao;
import gov.tech.domain.LunchSession;
import gov.tech.domain.Participant;
import gov.tech.exception.AppServiceException;
import gov.tech.model.UserForm;
import gov.tech.service.LunchObserver;
import gov.tech.service.SessionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {
    @Mock
    private ParticipantDao participantDao;

    @Mock
    private SessionDao sessionDao;

    @Mock
    private LunchObserver lunchObserver;

    @InjectMocks
    SessionService service = new SessionServiceImpl(participantDao,sessionDao,lunchObserver);

    @Test
    public void testCreateSession(){
        Mockito.when(sessionDao.getById("sessionId")).thenReturn(null);
        String result = service.createSession("sessionId");
        Assertions.assertEquals("Your Session is created successfully.", result);

        // existing session
        Mockito.when(sessionDao.getById("sessionId")).thenReturn(new LunchSession());
        result = service.createSession("sessionId");
        Assertions.assertEquals("Unable to create Session. The ID already exists.", result);
    }

    @Test
    public void testCloseSession() throws AppServiceException {

        LunchSession session = new LunchSession();
        session.setSessionId("sessionId");
        session.setActive(true);
        Mockito.when(sessionDao.getById("sessionId")).thenReturn(session);

        Participant participant = new Participant();
        participant.setRestaurant("restaurant");
        participant.setName("name");
        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        Mockito.when(participantDao.getUsersByActiveSessionId("sessionId")).thenReturn(participants);
        Mockito.when(lunchObserver.chooseARestaurant(participants)).thenReturn("ANSWER");
        String result = service.closeSession("sessionId");

        Assertions.assertEquals("ANSWER", result);
    }

    @Test
    public void testGetSession(){
        LunchSession lunchSession = new LunchSession();
        lunchSession.setActive(true);
        lunchSession.setSessionId("sessionId");
        Mockito.when(sessionDao.getById("sessionId")).thenReturn(lunchSession);
        UserForm result = service.getSession("sessionId");
        Assertions.assertEquals("sessionId", result.getSessionId());
    }

}
