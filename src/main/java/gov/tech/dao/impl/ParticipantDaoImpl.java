package gov.tech.dao.impl;

import gov.tech.dao.ParticipantDao;
import gov.tech.domain.Participant;
import jakarta.persistence.LockModeType;
import jakarta.persistence.NoResultException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ParticipantDaoImpl implements ParticipantDao {
    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();

        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void save(Participant participant) {
        try (org.hibernate.Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(participant);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Participant participant) {
        try (org.hibernate.Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(participant);
            session.getTransaction().commit();
        }
    }

    @Override
    public Participant getById(String userid) {
        try (org.hibernate.Session session = sessionFactory.openSession()) {
            return session.get(Participant.class, userid);
        }
    }

    @Override
    public List<Participant> getAll() {
        try (org.hibernate.Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Participant", Participant.class)
                    .list();
        }
    }

    @Override
    public List<Participant> getUsersByActiveSessionId(String sessionId) {
        try (org.hibernate.Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Participant u WHERE u.lunchSession.isActive = true AND u.lunchSession.sessionId = :sessionId", Participant.class)
                    .setParameter("sessionId", sessionId)
                    .list();
        }
    }

    @Override
    public List<Participant> getUsersBySessionId(String sessionId) {
        try (org.hibernate.Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Participant u WHERE u.lunchSession.sessionId = :sessionId", Participant.class)
                    .setParameter("sessionId", sessionId)
                    .list();
        }
    }

    @Override
    public Participant getUserByName(String name) {
        try (org.hibernate.Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Participant WHERE name = :name", Participant.class)
                    .setParameter("name", name)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }
}
