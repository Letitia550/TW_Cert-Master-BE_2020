package com.ibm.certmaster.services;

import com.ibm.certmaster.models.Category;
import com.ibm.certmaster.models.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class RolesServices {
    public static Role createRole(Role role) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        long insertedRoleId = (long) session.save(role);

        session.getTransaction().commit();
        session.close();

        return RolesServices.findRole(insertedRoleId);
    }

    public static Role findRole(Long id) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        Query query = session.createQuery("FROM Role R WHERE R.id = :id");
        query.setParameter("id", id);
        Role role = (Role) query.uniqueResult();

        session.getTransaction().commit();
        session.close();

        return role;
    }

    public static boolean exists(String name) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        Query query = session.createQuery("SELECT COUNT(R) FROM Role R WHERE R.name = :name");
        query.setParameter("name", name);
        int roleCount = (int) query.getFirstResult();

        session.getTransaction().commit();
        session.close();

        if( roleCount == 0 ) {
            return false;
        } else {
            return true;
        }
    }
}
