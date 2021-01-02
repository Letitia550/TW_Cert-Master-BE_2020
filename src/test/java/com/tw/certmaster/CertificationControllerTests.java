package com.tw.certmaster;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.certmaster.factories.CertificationsFactory;
import com.tw.certmaster.results.CertificationResult;
import com.tw.certmaster.services.CertificationsServices;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CertificationControllerTests extends BaseTest {

    @Test
    public void itGetsAllTheCertificationsCorrectly() throws Exception {
        truncateAllTables();

        CertificationsFactory.fabricate(20);

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/certifications"), HttpMethod.GET, entity, String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        ArrayList<CertificationResult> allCertifications = CertificationsServices.getCertifications();
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(allCertifications);

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void itReturnsEmptyArrayIfThereAreNoCertifications() throws Exception {
        truncateAllTables();

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/certifications"), HttpMethod.GET, entity, String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        JSONAssert.assertEquals("[]", response.getBody(), false);
    }
}

