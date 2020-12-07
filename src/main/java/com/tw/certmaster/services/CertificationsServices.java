package com.tw.certmaster.services;

import com.tw.certmaster.models.Certification;
import com.tw.certmaster.results.CertificationResult;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class CertificationsServices {
    public static ArrayList<CertificationResult> getCertifications() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        ArrayList<CertificationResult> certifications =
                (ArrayList<CertificationResult>) session.createQuery("SELECT new com.tw.certmaster.results.CertificationResult(C.id, C.title, C.quarter, Ca.id, Ca.name, C.price)\n" +
                        "FROM Certification C\n" +
                            "INNER JOIN Category Ca ON C.category_id = Ca.id")
                                                  .list();

        session.getTransaction().commit();
        session.close();

        return certifications;
    }

    public static Certification createCertification(Certification certification) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        long insertedCertificationId = (long) session.save(certification);

        session.getTransaction().commit();
        session.close();

        return CertificationsServices.findCertification(insertedCertificationId);
    }

    public static Certification findCertification(long certificationId) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        String hql = "FROM Certification WHERE id = :id";

        Query query = session.createQuery(hql);
        query.setParameter("id", certificationId);

        Certification certification = (Certification) query.uniqueResult();

        session.getTransaction().commit();
        session.close();

        return certification;
    }
}
