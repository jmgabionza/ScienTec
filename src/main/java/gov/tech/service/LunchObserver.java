package gov.tech.service;

import gov.tech.domain.LunchSession;
import gov.tech.domain.Participant;

import java.util.List;

public interface LunchObserver {

     List<Participant> publishResult(LunchSession lunchSession);
     void inviteParticipants();

     String chooseARestaurant(List<Participant> participants);

     void updateResult(LunchSession session, String result);
}
