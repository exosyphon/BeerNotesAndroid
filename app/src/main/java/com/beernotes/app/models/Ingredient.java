package com.beernotes.app.models;

/**
 * Created by andrew on 2/22/14.
 */
public class Ingredient {

    private String name;
    private String amount;
    private String unit;
    private String addTime;

    public Ingredient(){}

    public Ingredient(String name, String amount, String unit, String addTime) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.addTime = addTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}