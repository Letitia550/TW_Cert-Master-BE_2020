package com.tw.certmaster.services;

import com.tw.certmaster.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class UsersServices {
    public static boolean userExists(long id) {
        Long count;

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        String hql = "SELECT COUNT(*) FROM User WHERE id = :id";

        Query<Long> query = session.createQuery(hql);
        query.setParameter("id", id);

        count = query.uniqueResult();

        if( count == 0 ) {
            return false;
        } else {
            return true;
        }
    }

    public static User createUser(User user) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        long insertedUserId = (long) session.save(user);

        session.getTransaction().commit();
        session.close();

        return UsersServices.findUser(insertedUserId);
    }

    public static User findUser(Long id) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        Query query = session.createQuery("FROM User U WHERE U.id = :id");
        query.setParameter("id", id);
        User user = (User) query.uniqueResult();

        session.getTransaction().commit();
        session.close();

        return user;
    }
}
