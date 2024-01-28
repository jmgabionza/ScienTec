package gov.tech.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class UserForm {
    private String name;
    private String sessionId;
    private String restaurant;
    private boolean isSessionActive;
    private List<UserForm> userForms;

}
