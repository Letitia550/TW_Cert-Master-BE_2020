package com.tw.certmaster.services;

import com.tw.certmaster.models.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class CategoriesService {
    public static Category createCategory(Category category) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        long insertedCategoryId = (long) session.save(category);

        session.getTransaction().commit();
        session.close();

        return CategoriesService.findCategory(insertedCategoryId);
    }

    public static Category findCategory(Long id) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        Query query = session.createQuery("FROM Category C WHERE C.id = :id");
        query.setParameter("id", id);
        Category category = (Category) query.uniqueResult();

        session.getTransaction().commit();
        session.close();

        return category;
    }
}
