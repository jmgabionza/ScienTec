package gov.tech.service;

import gov.tech.exception.AppServiceException;
import gov.tech.model.UserForm;

public interface SessionService {
    String createSession(String sessionId);
    String closeSession(String sessionId) throws AppServiceException;
    UserForm getSession(String sessionId);

}
