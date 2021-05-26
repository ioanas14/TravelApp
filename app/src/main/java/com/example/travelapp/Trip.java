package com.example.travelapp;

public class Trip {
    String tripName;
    String tripDate;

    public Trip(String tripName, String tripDate) {
        this.tripName = tripName;
        this.tripDate = tripDate;
    }

    public String getTripName() {
        return tripName;
    }

    public String getTripDate() {
        return tripDate;
    }

    public Trip(){

    }
}
