package gov.tech.service;

import gov.tech.model.UserForm;

import java.util.List;

public interface ParticipantService {
    String submitChoice(AppServiceDto dto);
    List<UserForm> getDemographics(String sessionId);

}
