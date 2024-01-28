package gov.tech.util;

import gov.tech.model.UserForm;
import gov.tech.service.AppServiceDto;
import org.springframework.stereotype.Component;

/**
 * This class is created to house the data transformation from Front-end Model (MVC) to Data Transformation Objects (DTO)
 * Used by the service layer, and any other functions the Controller might need
 */
@Component
public class ControllerUtil {

    public static AppServiceDto transform(UserForm userForm){
        AppServiceDto dto = new AppServiceDto();
        dto.setName(userForm.getName());
        dto.setRestaurant(userForm.getRestaurant());
        dto.setSessionId(userForm.getSessionId());
        return  dto;
    }

}
