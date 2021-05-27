package com.example.travelapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Trip implements Parcelable {
    String tripName;
    String tripDate;

    public Trip(String tripName, String tripDate) {
        this.tripName = tripName;
        this.tripDate = tripDate;
    }

    protected Trip(Parcel in) {
        tripName = in.readString();
        tripDate = in.readString();
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

    public String getTripName() {
        return tripName;
    }

    public String getTripDate() {
        return tripDate;
    }

    public Trip(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tripName);
        dest.writeString(tripDate);
    }
}
