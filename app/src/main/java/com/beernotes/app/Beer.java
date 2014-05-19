package com.beernotes.app;

/**
 * Created by andrew on 2/22/14.
 */
public class Beer {

    private String name;
    private String beerType;
    private String notes;

    public Beer(){}

    public Beer(String name, String beerType, String notes) {
        this.name = name;
        this.beerType = beerType;
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getBeerType() {
        return beerType;
    }

    public void setBeerType(String beerType) {
        this.beerType = beerType;
    }


    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
