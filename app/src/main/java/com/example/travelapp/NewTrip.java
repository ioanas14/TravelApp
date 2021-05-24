package com.example.travelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class NewTrip extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "NewTrip";
    private EditText tripName;
    private FirebaseAuth auth;
    private Button btnAdd;
    private FirebaseUser user;
    private String userID;
    private String email;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.trips);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        auth = FirebaseAuth.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        email = user.getEmail();

        tripName = (EditText) findViewById(R.id.name);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch ((item.getItemId())){
                    case R.id.profile:
                        startActivity(new Intent(NewTrip.this, UserProfile.class));
                        break;

                    case R.id.trips:
                        startActivity(new Intent(NewTrip.this, Trips.class));
                        break;

                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(NewTrip.this, Login.class));
                        break;
                }
                return false;
            }
        });

        checkFilePermissions();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.addtrip:
                addTrip();
                break;
        }
    }

    private void checkFilePermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = NewTrip.this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck += NewTrip.this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            if (permissionCheck != 0) {
                this.requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1001); //Any number
            }
        }else{
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }


    private void addTrip() {
        //String name = tripName.getText().toString().trim();
        //Trip trip = new Trip(name);
       // mDatabase.child(userID).child("Trips").setValue(trip);

        Toast.makeText(NewTrip.this, "User has been registered!", Toast.LENGTH_LONG).show();
    }



}