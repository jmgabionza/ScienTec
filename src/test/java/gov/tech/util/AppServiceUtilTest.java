package gov.tech.util;

import gov.tech.domain.LunchSession;
import gov.tech.domain.Participant;
import gov.tech.model.UserForm;
import gov.tech.service.AppServiceDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AppServiceUtilTest {

    @Test
    public void testTransformDtoToModel(){
        AppServiceDto dto = initializeTestDto();
        UserForm result = AppServiceUtil.transformDtoToModel(dto);
        Assertions.assertEquals(dto.getResult(), result.getResult());
        Assertions.assertEquals(dto.getRestaurant(), result.getRestaurant());
        Assertions.assertEquals(dto.getName(), result.getName());
        Assertions.assertEquals(dto.getSessionId(), result.getSessionId());
        Assertions.assertNull(dto.getLunchSession());
    }

    @Test
    public void testTransformDtoToDomain(){
        AppServiceDto dto = initializeTestDto();
        Participant result = AppServiceUtil.transformDtoToDomain(dto);
        Assertions.assertEquals(dto.getName(), result.getName());
        Assertions.assertEquals(dto.getRestaurant(), result.getRestaurant());
    }

    @Test
    public void testTransformUserToDto(){
        Participant participant = new Participant("name","restaurant", new LunchSession());
        AppServiceDto result = AppServiceUtil.transformUserToDto(participant);
        Assertions.assertEquals(participant.getName(), result.getName());
        Assertions.assertEquals(participant.getRestaurant(), result.getRestaurant());

    }

    @Test
    public void testTransformUserListToModel(){
        List<Participant> participants = new ArrayList<>();
        Participant participant = new Participant("name","restaurant", new LunchSession());
        participants.add(participant);
        List<UserForm> result = AppServiceUtil.transformUserListToModel(participants);
        Assertions.assertEquals(participants.size(), result.size());
    }

    @Test
    public void testMapLunchSessionToDto(){
        LunchSession session = new LunchSession();
        session.setSessionId("sessionId");
        session.setActive(true);
        session.setResult("result");
        AppServiceDto result = AppServiceUtil.mapLunchSessionToDto(session);
        Assertions.assertEquals(session.getSessionId(), result.getSessionId());
        Assertions.assertEquals(session.getResult(), result.getResult());
        Assertions.assertTrue(result.getLunchSession().isActive());
    }

    private AppServiceDto initializeTestDto(){
        AppServiceDto dto = new AppServiceDto();
        dto.setResult("result");
        dto.setRestaurant("restaurant");
        dto.setName("name");
        dto.setSessionId("sessionId");
        dto.setLunchSession(null);
        return dto;
    }
}
