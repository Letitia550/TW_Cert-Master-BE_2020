package com.tw.certmaster;

import com.tw.certmaster.results.RequestResult;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;

public abstract class BaseTest {

    @LocalServerPort
    protected int port;

    protected TestRestTemplate restTemplate = new TestRestTemplate();

    protected HttpHeaders headers = new HttpHeaders();

    protected String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    protected void truncateAllTables() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query<RequestResult> truncateRequestsTableQuery = session.createQuery("DELETE FROM Request");
        truncateRequestsTableQuery.executeUpdate();

        Query<RequestResult> truncateUsersTableQuery = session.createQuery("DELETE FROM User");
        truncateUsersTableQuery.executeUpdate();

        Query<RequestResult> truncateRolesTableQuery = session.createQuery("DELETE FROM Role");
        truncateRolesTableQuery.executeUpdate();

        Query<RequestResult> truncateCertificationsTableQuery = session.createQuery("DELETE FROM Certification");
        truncateCertificationsTableQuery.executeUpdate();

        Query<RequestResult> truncateCategoriesTableQuery = session.createQuery("DELETE FROM Category");
        truncateCategoriesTableQuery.executeUpdate();

        session.getTransaction().commit();
        session.close();
    }
}
