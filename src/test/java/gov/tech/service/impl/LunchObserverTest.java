package gov.tech.service.impl;

import gov.tech.dao.ParticipantDao;
import gov.tech.dao.SessionDao;
import gov.tech.domain.LunchSession;
import gov.tech.domain.Participant;
import gov.tech.service.LunchObserver;
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
public class LunchObserverTest {

    @Mock
    private ParticipantDao participantDao;

    @Mock
    private SessionDao sessionDao;

    @InjectMocks
    private LunchObserver observer = new LunchObserverImpl(sessionDao, participantDao);

    @Test
    public void testChooseARestaurant(){

        List<Participant> participants = new ArrayList<>();
        Participant participant = new Participant();
        LunchSession session = new LunchSession();

        participant.setRestaurant("result1");
        participant.setName("name1");
        session.setSessionId("1234");
        session.setActive(true);

        participant.setLunchSession(session);
        participants.add(participant);

        String result = observer.chooseARestaurant(participants);
        Assertions.assertEquals(participant.getRestaurant(), result);

    }

    @Test
    public void testInviteParticipants(){
        observer.inviteParticipants();
        Mockito.verify(participantDao).getAll();
    }

    @Test
    public void testUpdateResult(){
        LunchSession session =  Mockito.mock(LunchSession.class);
        observer.updateResult(session, Mockito.anyString());
        Mockito.verify(sessionDao).update(Mockito.any());
    }

    @Test
    public void testPublishResult(){
        LunchSession session =  Mockito.mock(LunchSession.class);
        observer.publishResult(session);
        Mockito.verify(participantDao).getUsersByActiveSessionId(session.getSessionId());
    }
}
