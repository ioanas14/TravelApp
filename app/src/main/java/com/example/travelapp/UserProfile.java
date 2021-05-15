package com.example.travelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);

//        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
//            @Override
//            public void onNavigationItemReselected(@NonNull MenuItem item) {
//                switch ((item.getItemId())){
//                    case R.id.profile:
//
//
//                    case R.id.trips:
//                        Intent intent = new Intent(UserProfile.this, Trips.class);
//                        startActivity(intent);
//                        //finish();
//                        //overridePendingTransition(0, 0);
//                        return;
//
//                    case R.id.logout:
//                            // TO DO
//                        break;
//                }
//            }
//        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch ((item.getItemId())){
                    case R.id.profile:
                        break;

                    case R.id.trips:
                        startActivity(new Intent(UserProfile.this, Trips.class));
                        break;

                    case R.id.logout:
                        startActivity(new Intent(UserProfile.this, Logout.class));
                        break;
                }
                return false;
            }

        });



    }
}