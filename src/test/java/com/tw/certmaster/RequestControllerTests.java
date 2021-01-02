package com.tw.certmaster;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.certmaster.factories.CertificationsFactory;
import com.tw.certmaster.factories.RequestsFactory;
import com.tw.certmaster.factories.UsersFactory;
import com.tw.certmaster.models.Certification;
import com.tw.certmaster.models.Request;
import com.tw.certmaster.models.User;
import com.tw.certmaster.results.RequestResult;
import com.tw.certmaster.services.RequestsServices;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

public class RequestControllerTests extends BaseTest {

    @Test
    public void itGetsAllTheRequestsOfUserCorrectly() throws Exception {
        truncateAllTables();

        User user = UsersFactory.fabricate(1).get(0);

        ArrayList<Request> requests = RequestsFactory.fabricate(20, user.getId());

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/requests?user_id=" + user.getId()), HttpMethod.GET, entity, String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        ArrayList<RequestResult> allRequests = RequestsServices.getRequestsOfUser(user.getId());
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(allRequests);

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void itFailsGracefullyWhenGivenUserDoesNotExists() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/requests?user_id=100"), HttpMethod.GET, entity, String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        String expected = "{\"message\":\"Bad request\",\"details\":[\"User with the given id does not exist.\"]}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void itFailsGracefullyWhenGivenUserIsNull() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/requests?user_id"), HttpMethod.GET, entity, String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        String expected = "{\"message\":\"Bad request\",\"details\":[\"The user id cannot be null.\"]}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void itReturnsEmptyArrayIfTheUserDoesNotHaveRequests() throws Exception{
        truncateAllTables();

        User user = UsersFactory.fabricate(1).get(0);

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query<RequestResult> query1 = session.createQuery("DELETE FROM Request");
        query1.executeUpdate();

        session.getTransaction().commit();
        session.close();

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/requests?user_id=" + user.getId()), HttpMethod.GET, entity, String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        JSONAssert.assertEquals("[]", response.getBody(), false);
    }

    @Test
    public void itGetsAllTheRequestsByQuarterCorrectly() throws Exception {
        truncateAllTables();

        ArrayList<Request> requests = RequestsFactory.fabricate(20);

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/admin/requests?quarter=2"), HttpMethod.GET, entity, String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        ArrayList<RequestResult> allRequests = RequestsServices.getAllRequest(2, null);
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(allRequests);

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void itGetsAllTheRequestsByStatusCorrectly() throws Exception {
        truncateAllTables();

        ArrayList<Request> requests = RequestsFactory.fabricate(20);

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/admin/requests?status=Approved"), HttpMethod.GET, entity, String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        ArrayList<RequestResult> allRequests = RequestsServices.getAllRequest(null, "Approved");
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(allRequests);

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void itGetsAllTheRequestsByStatusAndQuarterCorrectly() throws Exception {
        truncateAllTables();

        ArrayList<Request> requests = RequestsFactory.fabricate(20);

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/admin/requests?quarter=2&status=Approved"), HttpMethod.GET, entity, String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        ArrayList<RequestResult> allRequests = RequestsServices.getAllRequest(2, "Approved");
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(allRequests);

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void itCreatesARequest() throws Exception {
        truncateAllTables();

        User user = UsersFactory.fabricate(1).get(0);

        Certification certification = CertificationsFactory.fabricate(1).get(0);

        headers.add("Content-Type", "application/json");

        String businessJustification = "Lorem ipsum dolor sit amet.";

        JSONObject requestBody = new JSONObject();
        requestBody.put("certification_id", certification.getId());
        requestBody.put("user_id", user.getId());
        requestBody.put("business_justification", businessJustification);

        HttpEntity<String> entity = new HttpEntity<String>(requestBody.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/requests"), HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        String hql = "SELECT COUNT(*) FROM Request WHERE certification_id = :certification_id AND user_id = :user_id AND business_justification = :business_justification";
        Query query = session.createQuery(hql);
        query.setParameter("certification_id", certification.getId());
        query.setParameter("user_id", user.getId());
        query.setParameter("business_justification", businessJustification);
        Long count = (Long) query.uniqueResult();

        session.getTransaction().commit();
        session.close();

        assertEquals(Long.valueOf(1), count);
    }

    @Test
    public void itDeletesARequest() throws Exception {
        truncateAllTables();

        Request request = RequestsFactory.fabricate(1).get(0);

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        headers.add("Content-Type", "application/json");

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/requests/" + request.getId()), HttpMethod.DELETE, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        String expected = "Request successfully deleted.";

        assertEquals(expected, response.getBody());

        assertNull(RequestsServices.findRequest(request.getId()));
    }

    @Test
    public void itModifiesARequest() throws Exception {
        truncateAllTables();

        String businessJustification = "Asaddadadadadadada";

        Request request = RequestsFactory.fabricate(1).get(0);

        headers.add("Content-Type", "application/json");

        JSONObject requestBody = new JSONObject();
        requestBody.put("business_justification", businessJustification);

        HttpEntity<String> entity = new HttpEntity<String>(requestBody.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/requests/" + request.getId()), HttpMethod.PATCH, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Request refreshedRequest = RequestsServices.findRequest(request.getId());

        assertEquals(businessJustification, refreshedRequest.getBusiness_justification());
    }

    @Test
    public void itFailsWhenBusinessJustificationIsWrong() throws Exception {
        truncateAllTables();

        String businessJustification = "adk";

        Request request = RequestsFactory.fabricate(1).get(0);

        headers.add("Content-Type", "application/json");

        JSONObject requestBody = new JSONObject();
        requestBody.put("business_justification", businessJustification);

        HttpEntity<String> entity = new HttpEntity<String>(requestBody.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/requests/" + request.getId()), HttpMethod.PATCH, entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        String expected = "{\"message\":\"Bad request\",\"details\":[\"Enter at least 10 characters for business justification.\"]}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void itModifiesARequestByAdmin() throws Exception {
        truncateAllTables();

        String status = "Approved";

        Request request =  RequestsFactory.fabricate(1).get(0);

        headers.add("Content-Type", "application/json");

        JSONObject requestBody = new JSONObject();
        requestBody.put("status", status);

        HttpEntity<String> entity = new HttpEntity<String>(requestBody.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("admin/requests/" + request.getId()), HttpMethod.PATCH, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Request refreshedRequest = RequestsServices.findRequest(request.getId());

        assertEquals(status, refreshedRequest.getStatus());
    }

    @Test
    public void itFailsWhenStatuesIsWrong() throws Exception {
        truncateAllTables();

        String status = "adk";

        Request request = RequestsFactory.fabricate(1).get(0);

        headers.add("Content-Type", "application/json");

        JSONObject requestBody = new JSONObject();
        requestBody.put("status", status);

        HttpEntity<String> entity = new HttpEntity<String>(requestBody.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("admin/requests/" + request.getId()), HttpMethod.PATCH, entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        String expected = "{\"message\":\"Bad request\",\"details\":[\"Status must be 'Approved', 'Pending' or 'Rejected'.\"]}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void itAMassApprovesRequests() throws Exception {
        truncateAllTables();

        User user = UsersFactory.fabricate(1).get(0);

        ArrayList<Request> requests = RequestsFactory.fabricate(20, user.getId());

        Integer quarter = 2;
        Long userId = user.getId();

        JSONObject requestBody = new JSONObject();
        requestBody.put("quarter", quarter);
        requestBody.put("user_id", userId);

        headers.add("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<String>(requestBody.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/admin/requests/mass-approve"), HttpMethod.PATCH, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        for(int i = 0; i < requests.size(); i++) {
            RequestResult refreshedRequest = RequestsServices.findRequestResult(requests.get(i).getId());

            if( refreshedRequest.getQuarter() == quarter ) {
                assertEquals("Approved", refreshedRequest.getStatus());
            }
        }
    }

    @Test
    public void itFailsWhenQuarterIsNull() throws Exception {
        truncateAllTables();

        Request request = RequestsFactory.fabricate(1).get(0);

        headers.add("Content-Type", "application/json");

        JSONObject requestBody = new JSONObject();
        requestBody.put("user_id", 4);

        HttpEntity<String> entity = new HttpEntity<String>(requestBody.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/admin/requests/mass-approve"), HttpMethod.PATCH, entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        String expected = "{\"message\":\"Bad request\",\"details\":[\"The quarter cannot be null.\"]}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void itFailsWhenUserIdIsNull() throws Exception {
        truncateAllTables();

        Request request = RequestsFactory.fabricate(1).get(0);

        JSONObject requestBody = new JSONObject();
        requestBody.put("quarter", 3);

        headers.add("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<String>(requestBody.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/admin/requests/mass-approve"), HttpMethod.PATCH, entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        String expected = "{\"message\":\"Bad request\",\"details\":[\"The user id cannot be null.\"]}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void itFailsWhenQuarterIsWrong() throws Exception {
        truncateAllTables();

        Request request = RequestsFactory.fabricate(1).get(0);

        headers.add("Content-Type", "application/json");

        JSONObject requestBody = new JSONObject();
        requestBody.put("user_id", 4);
        requestBody.put("quarter", 7);

        HttpEntity<String> entity = new HttpEntity<String>(requestBody.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/admin/requests/mass-approve"), HttpMethod.PATCH, entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        String expected = "{\"message\":\"Bad request\",\"details\":[\"Quarter must be 1, 2, 3 or 4.\"]}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void itFailsWhenUserIdIsWrong() throws Exception {
        truncateAllTables();

        Request request = RequestsFactory.fabricate(1).get(0);

        headers.add("Content-Type", "application/json");

        JSONObject requestBody = new JSONObject();
        requestBody.put("user_id", 56789);
        requestBody.put("quarter", 2);

        HttpEntity<String> entity = new HttpEntity<String>(requestBody.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/admin/requests/mass-approve"), HttpMethod.PATCH, entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        String expected = "{\"message\":\"Bad request\",\"details\":[\"User with the given id does not exist.\"]}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }
}