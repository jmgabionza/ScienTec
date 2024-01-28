package gov.tech.dao;

import gov.tech.domain.Participant;

import java.util.List;

public interface UserDao extends GenericDao<Participant, String> {
    List<Participant> getUsersByActiveSessionId(String sessionId);
}
