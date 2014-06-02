package com.beernotes.app.models;

/**
 * Created by andrew on 2/22/14.
 */
public class Ingredient {

    private String name;

    public Ingredient(){}

    public Ingredient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}