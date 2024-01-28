package gov.tech.service.impl;

import gov.tech.dao.SessionDao;
import gov.tech.dao.UserDao;
import gov.tech.domain.LunchSession;
import gov.tech.domain.Participant;
import gov.tech.service.LunchObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class LunchObserverImpl implements LunchObserver {
    @Autowired
    SessionDao sessionDao;
    @Autowired
    UserDao userDao;
    @Override
    public List<Participant> publishResult(LunchSession lunchSession) {
        return userDao.getUsersByActiveSessionId(lunchSession.getSessionId());
    }

    @Override
    public void inviteParticipants() {
        List<Participant> participants =  userDao.getAll();
        // TODO: invite participants e.g. via email (out of scope)
    }

    @Override
    public String chooseARestaurant(List<Participant> participants) {
        System.out.println("Choosing a restaurant...");
        String result = "";
        if (participants == null || participants.isEmpty()){
            System.err.println("The list of participants is empty!");
            return result;
        }
        Random random = new Random();
        int randomIndex = random.nextInt(participants.size());
        return participants.get(randomIndex).getRestaurant();
    }

    @Override
    public void updateResult(LunchSession session, String result) {
        session.setResult(result);
        sessionDao.update(session);
    }


}
