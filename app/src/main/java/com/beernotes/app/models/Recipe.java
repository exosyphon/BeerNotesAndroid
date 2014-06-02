package com.beernotes.app.models;

/**
 * Created by andrew on 2/22/14.
 */
public class Recipe {

    private String name;
    private String boilNotes;

    public Recipe(){}

    public Recipe(String name, String boilNotes) {
        this.name = name;
        this.boilNotes = boilNotes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBoilNotes() {
        return boilNotes;
    }

    public void setBoilNotes(String notes) {
        this.boilNotes = notes;
    }
}
