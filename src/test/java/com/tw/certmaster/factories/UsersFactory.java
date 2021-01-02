package com.tw.certmaster.factories;

import com.github.javafaker.Faker;
import com.tw.certmaster.models.User;
import com.tw.certmaster.services.UsersServices;

import java.util.ArrayList;

public class UsersFactory {
    public static ArrayList<User> fabricate(int howMany, long roleId)
    {
        Faker faker = new Faker();

        ArrayList<User> fabricated = new ArrayList<User>();

        for(int i=1; i<=howMany; i++) {
            User user = new User(
                    faker.name().fullName(),
                    faker.internet().emailAddress(),
                    roleId
            );

            fabricated.add(UsersServices.createUser(user));
        }

        return fabricated;
    }

    public static ArrayList<User> fabricate(int howMany)
    {
        Faker faker = new Faker();

        ArrayList<User> fabricated = new ArrayList<User>();

        for(int i=1; i<=howMany; i++) {
            User user = new User(
                    faker.name().fullName(),
                    faker.internet().emailAddress(),
                    RolesFactory.fabricate(1).get(0).getId()
            );

            fabricated.add(UsersServices.createUser(user));
        }

        return fabricated;
    }
}
