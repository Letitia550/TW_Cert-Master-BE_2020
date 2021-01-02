package com.tw.certmaster.factories;

import com.github.javafaker.Faker;
import com.tw.certmaster.models.Request;
import com.tw.certmaster.services.RequestsServices;

import java.util.ArrayList;
import java.util.List;

public class RequestsFactory {
    public static ArrayList<Request> fabricate(int howMany)
    {
        Faker faker = new Faker();

        List<String> statusList = new ArrayList<String>();
        statusList.add("Approved");
        statusList.add("Pending");
        statusList.add("Rejected");

        ArrayList<Request> fabricated = new ArrayList<Request>();

        for(int i=1; i<=howMany; i++) {
            Request request = new Request(
                    faker.options().nextElement(statusList),
                    faker.lorem().sentence(),
                    UsersFactory.fabricate(1).get(0).getId(),
                    CertificationsFactory.fabricate(1).get(0).getId()
            );

            fabricated.add(RequestsServices.createRequest(request));
        }

        return fabricated;
    }

    public static ArrayList<Request> fabricate(int howMany, long userId)
    {
        Faker faker = new Faker();

        List<String> statusList = new ArrayList<String>();
        statusList.add("Approved");
        statusList.add("Pending");
        statusList.add("Rejected");

        ArrayList<Request> fabricated = new ArrayList<Request>();

        for(int i=1; i<=howMany; i++) {
            Request request = new Request(
                    faker.options().nextElement(statusList),
                    faker.lorem().sentence(),
                    userId,
                    CertificationsFactory.fabricate(1).get(0).getId()
            );

            fabricated.add(RequestsServices.createRequest(request));
        }

        return fabricated;
    }

    public static ArrayList<Request> fabricate(int howMany, long userId ,long certificationId)
    {
        Faker faker = new Faker();

        List<String> statusList = new ArrayList<String>();
        statusList.add("Approved");
        statusList.add("Pending");
        statusList.add("Rejected");

        ArrayList<Request> fabricated = new ArrayList<Request>();

        for(int i=1; i<=howMany; i++) {
            Request request = new Request(
                    faker.options().nextElement(statusList),
                    faker.lorem().sentence(),
                    userId,
                    certificationId
            );
        }
        return fabricated;
    }
}

