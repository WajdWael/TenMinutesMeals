package com.example.tenminutesmeals;

public class Meal {
    private int id;
    private String name;
    private int minutes;

     public Meal(int id, String name, int minutes) {
        this.id = id;
        this.name = name;
        this.minutes = minutes;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMinutes() {
        return minutes;
    }
}
