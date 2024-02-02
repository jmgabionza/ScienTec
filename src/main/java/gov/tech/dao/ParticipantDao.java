package gov.tech.dao;

import gov.tech.domain.Participant;

import java.util.List;

public interface ParticipantDao extends GenericDao<Participant, String> {
    List<Participant> getUsersByActiveSessionId(String sessionId);
    List<Participant> getUsersBySessionId(String sessionId);
    Participant getUserByName(String name);

}
