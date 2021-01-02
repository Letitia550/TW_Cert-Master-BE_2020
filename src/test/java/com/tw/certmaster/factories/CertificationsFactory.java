package com.tw.certmaster.factories;

import com.github.javafaker.Faker;
import com.tw.certmaster.models.Certification;
import com.tw.certmaster.services.CertificationsServices;

import java.util.ArrayList;

public class CertificationsFactory {
    public static ArrayList<Certification>
    fabricate(int howMany)
    {
        Faker faker = new Faker();
        ArrayList<Certification> fabricated = new ArrayList<Certification>();

        for (int i=1; i<=howMany; i++) {
            Certification certification = new Certification(
                    faker.company().name(),
                    faker.number().numberBetween(1, 4),
                    faker.number().numberBetween(100, 2000),
                    CategoriesFactory.fabricate(1).get(0).getId()
            );

            fabricated.add(CertificationsServices.createCertification(certification));
        }

        return fabricated;
    }

    public static ArrayList<Certification> fabricate(int howMany, long categoryId)
    {
        Faker faker = new Faker();
        ArrayList<Certification> fabricated = new ArrayList<Certification>();

        for (int i=1; i<=howMany; i++) {
            Certification certification = new Certification(
                    faker.company().name(),
                    faker.number().numberBetween(1, 4),
                    faker.number().numberBetween(100, 2000),
                    categoryId
            );

            fabricated.add(certification);
        }

        return fabricated;
    }
}
