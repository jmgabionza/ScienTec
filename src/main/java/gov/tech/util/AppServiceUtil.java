package gov.tech.util;

import gov.tech.domain.Participant;
import gov.tech.model.UserForm;
import gov.tech.service.AppServiceDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppServiceUtil {

    public static UserForm transformDtoToModel(AppServiceDto dto){
        UserForm userForm = new UserForm();
        userForm.setName(dto.getName());
        userForm.setRestaurant(dto.getRestaurant());
        userForm.setSessionId(dto.getSessionId());
        userForm.setSessionActive(null != dto.getLunchSession() && dto.getLunchSession().isActive());
        return userForm;
    }

    public static Participant transformDtoToDomain(AppServiceDto dto){
        return new Participant(dto.getName(), dto.getRestaurant(), dto.getLunchSession());

    }

    public static AppServiceDto transformUserToDto(Participant participant){
        AppServiceDto dto = new AppServiceDto();
        dto.setLunchSession(participant.getLunchSession());
        dto.setName(participant.getName());
        dto.setRestaurant(participant.getRestaurant());
        dto.setSessionId(participant.getLunchSession().getSessionId());
        return dto;
    }

    public static List<UserForm> transformUserListToModel(List<Participant> participantList){
        List<UserForm> userForms = new ArrayList<>();
        for(Participant participant : participantList){
            AppServiceDto dto = transformUserToDto(participant);
            UserForm userForm = transformDtoToModel(dto);
            userForms.add(userForm);
        }
        return userForms;
    }
}
