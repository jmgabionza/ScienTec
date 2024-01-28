package gov.tech.service;

import gov.tech.domain.LunchSession;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class AppServiceDto {
    private String name;
    private String sessionId;
    private String restaurant;
    private LunchSession lunchSession;
}
