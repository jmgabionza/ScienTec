package gov.tech.dao.impl;

import gov.tech.dao.SessionDao;
import gov.tech.domain.LunchSession;
import jakarta.persistence.Query;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class SessionDaoImpl implements SessionDao {
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
    public void save(LunchSession domain) {
        try (org.hibernate.Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(domain);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(LunchSession domain) {
        try (org.hibernate.Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(domain);
            session.getTransaction().commit();
        }
    }

    @Override
    public LunchSession getById(String id) {
        try (org.hibernate.Session session = sessionFactory.openSession()) {
            return session.get(LunchSession.class, id);
        }
    }

    @Override
    public List<LunchSession> getAll() {
        try (org.hibernate.Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Session", LunchSession.class)
                    .list();
        }
    }
}
