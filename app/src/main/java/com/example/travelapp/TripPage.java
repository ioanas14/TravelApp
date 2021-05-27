package com.example.travelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TripPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_page);

        final TextView title = (TextView) findViewById(R.id.title);

        if(getIntent().hasExtra("selected_trip")) {
            Trip trip = getIntent().getParcelableExtra("selected_trip");
            title.setText(trip.getTripName());
        }

    }
}