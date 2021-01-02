package com.tw.certmaster.factories;

import com.github.javafaker.Faker;
import com.tw.certmaster.models.Role;
import com.tw.certmaster.services.RolesServices;

import java.util.ArrayList;

public class RolesFactory {
    public static ArrayList<Role> fabricate(int howMany) {
        ArrayList<String> possibleRoles = new ArrayList<String>();
        possibleRoles.add("Admin");
        possibleRoles.add("Normal User");

        Faker faker = new Faker();

        ArrayList<Role> fabricated = new ArrayList<Role>();

        for(int i=1; i<=howMany; i++) {
            Role role = new Role(
                    faker.options().nextElement(possibleRoles)
            );

            if( RolesServices.exists(role.getName()) ) {
                fabricated.add(role);
            } else {
                fabricated.add(RolesServices.createRole(role));
            }
        }

        return fabricated;
    }
}