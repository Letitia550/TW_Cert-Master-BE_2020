package com.tw.certmaster.factories;

import com.github.javafaker.Faker;
import com.tw.certmaster.models.Category;
import com.tw.certmaster.services.CategoriesService;


import java.util.ArrayList;

public class CategoriesFactory {
    public static ArrayList<Category> fabricate(int howMany)
    {
        Faker faker = new Faker();

        ArrayList<Category> fabricated = new ArrayList<Category>();

        for(int i=1; i<=howMany; i++) {
            Category category = new Category(faker.job().field());

            fabricated.add(CategoriesService.createCategory(category));
        }

        return fabricated;
    }
}