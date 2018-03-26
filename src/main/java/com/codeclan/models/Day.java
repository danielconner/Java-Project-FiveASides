package com.codeclan.models;

public enum Day {

    MONDAY("monday"),
    TUESDAY("tuesday"),
    WEDNESDAY("wednesday"),
    THURSDAY("thursday"),
    FRIDAY("friday"),
    SATURDAY("saturday"),
    SUNDAY("sunday");

    private final String day;

    Day(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }


}

