package com.example.sctravelbuddyjavaapp;


public class Attraction {
    String name;
    String address;
    String description;
    int type;//1-Tourist Attraction red, 2-Hotel yellow, 3-Restaurant green

    public Attraction(String name,String address,String description, int type)
    {
        this.name=name;
        this.address=address;
        this.description=description;
        this.type=type;
    }

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
