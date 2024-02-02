package gov.tech.util;

import gov.tech.model.UserForm;
import gov.tech.service.AppServiceDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ControllerutilTest {

    @Test
    public void testTransform(){
        UserForm form = new UserForm();
        form.setResult("result");
        form.setName("name");
        form.setRestaurant("restaurant");
        form.setSessionId("sessionid");

        AppServiceDto result = ControllerUtil.transform(form);
        Assertions.assertEquals(form.getName(), result.getName());
        Assertions.assertEquals(form.getRestaurant(), result.getRestaurant());
        Assertions.assertEquals(form.getResult(), result.getResult());
        Assertions.assertEquals(form.getSessionId(), result.getSessionId());

    }
}
