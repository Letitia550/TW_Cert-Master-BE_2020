package com.ibm.certmaster.services;

import com.ibm.certmaster.models.Request;
import com.ibm.certmaster.results.RequestResult;
import com.ibm.certmaster.results.RequestResultExcel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.ArrayList;

public class RequestsServices {
    public static ArrayList<RequestResult> getRequestsOfUser(long userId) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        String hql = "SELECT new com.ibm.certmaster.results.RequestResult(R.id, U.id, U.name, C.title, Ca.id, C.quarter, Ca.name, R.status, C.price, R.business_justification)\n" +
                "FROM Request R\n" +
                    "INNER JOIN Certification C ON R.certification_id = C.id\n" +
                    "INNER JOIN User U ON R.user_id = U.id\n" +
                    "INNER JOIN Category Ca ON C.category_id = Ca.id\n" +
                "WHERE R.user_id = :userId\n";

        Query<RequestResult> query = session.createQuery(hql);
        query.setParameter("userId", userId);
        ArrayList<RequestResult> requests = (ArrayList<RequestResult>) query.list();

        session.getTransaction().commit();
        session.close();

        return requests;
    }

    public static RequestResult findRequestResult(long requestId) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        String hql = "SELECT new com.ibm.certmaster.results.RequestResult(R.id, U.id, U.name, C.title, Ca.id, C.quarter, Ca.name, R.status, C.price, R.business_justification)\n" +
                "FROM Request R\n" +
                    "INNER JOIN Certification C ON R.certification_id = C.id\n" +
                    "INNER JOIN User U ON R.user_id = U.id\n" +
                    "INNER JOIN Category Ca ON C.category_id = Ca.id\n" +
                "WHERE R.id = :requestId\n";

        Query query = session.createQuery(hql);
        query.setParameter("requestId", requestId);

        RequestResult request;

        try {
            request = (RequestResult) query.getSingleResult();
        } catch (NoResultException ex) {
            session.getTransaction().rollback();
            session.close();

            return null;
        }

        session.getTransaction().commit();
        session.close();

        return request;
    }

    public static Request findRequest(long requestId) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        String hql = "FROM Request WHERE id = :id";

        Query query = session.createQuery(hql);
        query.setParameter("id", requestId);

        Request request = (Request) query.uniqueResult();

        session.getTransaction().commit();
        session.close();

        return request;
    }

    public static Request createRequest(Request request) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        request.setStatus("Pending");
        long insertedRequestId = (long) session.save(request);

        session.getTransaction().commit();
        session.close();

        return RequestsServices.findRequest(insertedRequestId);
    }

    public static void deleteRequest(Long requestId) {
        System.out.println("Ok");
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        String hql = "DELETE FROM Request R WHERE R.id = :requestId";
        Query query = session.createQuery(hql);
        query.setParameter("requestId", requestId);
        query.executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    public static ArrayList<RequestResult> getAllRequest(Integer quarter, String status) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        String hql = "";
        ArrayList<RequestResult> requests = new ArrayList<>();

        if( quarter == null && status != null ) {
            hql = "SELECT new com.ibm.certmaster.results.RequestResult(R.id, U.id, U.name, C.title, Ca.id, C.quarter, Ca.name, R.status, C.price, R.business_justification)\n" +
                "FROM Request R\n" +
                "INNER JOIN Certification C ON R.certification_id = C.id\n" +
                "INNER JOIN User U ON R.user_id = U.id\n" +
                "INNER JOIN Category Ca ON C.category_id = Ca.id\n" +
                "WHERE R.status = :status ";

            Query query = session.createQuery(hql);
            query.setParameter("status", status);
            requests = (ArrayList<RequestResult>)  query.list();
        }

        if( quarter != null && status == null ) {
            hql = "SELECT new com.ibm.certmaster.results.RequestResult(R.id, U.id, U.name, C.title, Ca.id, C.quarter, Ca.name, R.status, C.price, R.business_justification)\n" +
                "FROM Request R\n" +
                "INNER JOIN Certification C ON R.certification_id = C.id\n" +
                "INNER JOIN User U ON R.user_id = U.id\n" +
                "INNER JOIN Category Ca ON C.category_id = Ca.id\n" +
                "WHERE C.quarter = :quarter";

            Query query = session.createQuery(hql);
            query.setParameter("quarter", quarter);
            requests = (ArrayList<RequestResult>)  query.list();
        }

        if( quarter == null && status == null ) {
            hql = "SELECT new com.ibm.certmaster.results.RequestResult(R.id, U.id, U.name, C.title, Ca.id, C.quarter, Ca.name, R.status, C.price, R.business_justification)\n" +
                "FROM Request R\n" +
                "INNER JOIN Certification C ON R.certification_id = C.id\n" +
                "INNER JOIN User U ON R.user_id = U.id\n" +
                "INNER JOIN Category Ca ON C.category_id = Ca.id";

            Query query = session.createQuery(hql);
            requests = (ArrayList<RequestResult>)  query.list();
        }

        if( quarter != null && status != null ) {
            hql = "SELECT new com.ibm.certmaster.results.RequestResult(R.id, U.id, U.name, C.title, Ca.id, C.quarter, Ca.name, R.status, C.price, R.business_justification)\n" +
                "FROM Request R\n" +
                "INNER JOIN Certification C ON R.certification_id = C.id\n" +
                "INNER JOIN User U ON R.user_id = U.id\n" +
                "INNER JOIN Category Ca ON C.category_id = Ca.id\n" +
                "WHERE C.quarter = :quarter AND R.status = :status ";

            Query query = session.createQuery(hql);
            query.setParameter("quarter", quarter);
            query.setParameter("status", status);
            requests = (ArrayList<RequestResult>)  query.list();
        }

        session.getTransaction().commit();
        session.close();

        return requests;
    }

    public static ArrayList<RequestResultExcel> getAllRequestForExcel(Integer quarter, String status) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        String hql = "";
        ArrayList<RequestResultExcel> requests = new ArrayList<>();

        if( quarter == null && status != null ) {
            hql = "SELECT new com.ibm.certmaster.results.RequestResultExcel(U.name, C.title, C.quarter, Ca.name, R.status, C.price, R.business_justification)\n" +
                    "FROM Request R\n" +
                    "INNER JOIN Certification C ON R.certification_id = C.id\n" +
                    "INNER JOIN User U ON R.user_id = U.id\n" +
                    "INNER JOIN Category Ca ON C.category_id = Ca.id\n" +
                    "WHERE R.status = :status ";

            Query query = session.createQuery(hql);
            query.setParameter("status", status);
            requests = (ArrayList<RequestResultExcel>)  query.list();
        }

        if( quarter != null && status == null ) {
            hql = "SELECT new com.ibm.certmaster.results.RequestResultExcel(U.name, C.title, C.quarter, Ca.name, R.status, C.price, R.business_justification)\n" +
                    "FROM Request R\n" +
                    "INNER JOIN Certification C ON R.certification_id = C.id\n" +
                    "INNER JOIN User U ON R.user_id = U.id\n" +
                    "INNER JOIN Category Ca ON C.category_id = Ca.id\n" +
                    "WHERE C.quarter = :quarter";

            Query query = session.createQuery(hql);
            query.setParameter("quarter", quarter);
            requests = (ArrayList<RequestResultExcel>)  query.list();
        }

        if( quarter == null && status == null ) {
            hql = "SELECT new com.ibm.certmaster.results.RequestResultExcel(U.name, C.title, C.quarter, Ca.name, R.status, C.price, R.business_justification)\n" +
                    "FROM Request R\n" +
                    "INNER JOIN Certification C ON R.certification_id = C.id\n" +
                    "INNER JOIN User U ON R.user_id = U.id\n" +
                    "INNER JOIN Category Ca ON C.category_id = Ca.id";

            Query query = session.createQuery(hql);
            requests = (ArrayList<RequestResultExcel>)  query.list();
        }

        if( quarter != null && status != null ) {
            hql = "SELECT new com.ibm.certmaster.results.RequestResultExcel(U.name, C.title, C.quarter, Ca.name, R.status, C.price, R.business_justification)\n" +
                    "FROM Request R\n" +
                    "INNER JOIN Certification C ON R.certification_id = C.id\n" +
                    "INNER JOIN User U ON R.user_id = U.id\n" +
                    "INNER JOIN Category Ca ON C.category_id = Ca.id\n" +
                    "WHERE C.quarter = :quarter AND R.status = :status ";

            Query query = session.createQuery(hql);
            query.setParameter("quarter", quarter);
            query.setParameter("status", status);
            requests = (ArrayList<RequestResultExcel>)  query.list();
        }

        session.getTransaction().commit();
        session.close();

        return requests;
    }

    public static ArrayList<RequestResult> getAllRequest(int quarter, long userId, String status) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        String hql = "SELECT new com.ibm.certmaster.results.RequestResult(R.id, U.id, U.name, C.title, Ca.id, C.quarter, Ca.name, R.status, C.price, R.business_justification)\n" +
                "FROM Request R\n" +
                "INNER JOIN Certification C ON R.certification_id = C.id\n" +
                "INNER JOIN User U ON R.user_id = U.id\n" +
                "INNER JOIN Category Ca ON C.category_id = Ca.id\n" +
                "WHERE C.quarter = :quarter  AND R.user_id = :userId AND R.status = :status";

        Query query = session.createQuery(hql);
        query.setParameter("quarter", quarter);
        query.setParameter("userId", userId);
        query.setParameter("status", status);

        ArrayList<RequestResult> requests = (ArrayList<RequestResult>) query.list();

        session.getTransaction().commit();
        session.close();

        return requests;
    }

    public static RequestResult updateRequest(String businessJustification, long requestId) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        String hql = "UPDATE Request set business_justification = :businessJustification WHERE id = :requestId";

        Query query = session.createQuery(hql);
        query.setParameter("businessJustification", businessJustification);
        query.setParameter("requestId", requestId);
        query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        return RequestsServices.findRequestResult(requestId);
    }

    public static RequestResult editRequest(String status, long requestId) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        String hql = "UPDATE Request set status = :status WHERE id = :requestId";

        Query query = session.createQuery(hql);
        query.setParameter("status", status);
        query.setParameter("requestId", requestId);
        query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        return RequestsServices.findRequestResult(requestId);

    }

    public static ArrayList<RequestResult> massAproveRequests(int quarter, long userId) {
        ArrayList<RequestResult> requestsToBeUpdated = getAllRequest(quarter, userId, "Pending");

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        String hql = "UPDATE Request R set R.status = 'Approved'\n " +
                "WHERE R.user_id = :userId\n" +
                    "AND R.certification_id IN (SELECT C.id FROM Certification  C WHERE quarter = :quarter)\n" +
                    "AND R.status = 'Pending'";

        Query query = session.createQuery(hql);
        query.setParameter("quarter", quarter);
        query.setParameter("userId", userId);
        query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        return requestsToBeUpdated;
    }
}
