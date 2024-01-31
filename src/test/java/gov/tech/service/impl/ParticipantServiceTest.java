package gov.tech.service.impl;

import gov.tech.dao.ParticipantDao;
import gov.tech.dao.SessionDao;
import gov.tech.domain.LunchSession;
import gov.tech.model.UserForm;
import gov.tech.service.AppServiceDto;
import gov.tech.service.ParticipantService;
import gov.tech.service.impl.ParticipantServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ParticipantServiceTest {
    @Mock
    private ParticipantDao participantDao;

    @Mock
    private SessionDao sessionDao;

    @InjectMocks
    ParticipantService service = new ParticipantServiceImpl();

    @Test
    public void testGetDemographics(){
        String sessionId = "sessionId";
        List<UserForm> result = service.getDemographics(sessionId);
        Mockito.verify(participantDao).getUsersBySessionId(sessionId);
        Assertions.assertNotNull(result);
    }

    @Test
    public void testSubmitChoice(){
        AppServiceDto dto = Mockito.mock(AppServiceDto.class);
        LunchSession session = new LunchSession();
        session.setSessionId("sessionID");
        session.setActive(true);

        Mockito.when(sessionDao.getById(dto.getSessionId())).thenReturn(session);

        // checking for duplicates; return null for unique
        Mockito.when(participantDao.getUserByName(dto.getName())).thenReturn(null);

        String result = service.submitChoice(dto);

        Assertions.assertEquals("Your choice has been submitted.", result);
        Mockito.verify(sessionDao).getById(dto.getSessionId());
        Mockito.verify(participantDao).getUserByName(dto.getName());
        Mockito.verify(participantDao).save(Mockito.any());
    }
}
