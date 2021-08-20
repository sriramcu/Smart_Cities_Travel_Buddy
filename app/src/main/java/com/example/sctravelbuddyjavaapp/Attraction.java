package com.example.sctravelbuddyjavaapp;

/**
 * Class to represent tourist attractions of any given city
 * name- name of the attraction
 * address- full address where attraction is located
 * description- a sentence or a phrase summarising the attraction
 * type- type of attraction, 1-Tourist Attraction (red), 2-Hotel (yellow), 3-Restaurant (green)
 */
public class Attraction {
    String name;
    String address;
    String description;
    int type;

    // Constructor
    public Attraction(String name,String address,String description, int type)
    {
        this.name=name;
        this.address=address;
        this.description=description;
        this.type=type;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getAddress(){
        return address;
    }

    public String getDescription(){
        return description;
    }

    public int getType(){
        return type;
    }
}
