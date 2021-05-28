package com.example.travelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ShowPhotos extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private String userID;
    private DatabaseReference mDatabase;
    private DatabaseReference photos;
    private StorageReference storageRef;
    private RecyclerView recyclerView;
    private ArrayList<Photo> list;
    private PhotosAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photos);

        recyclerView = findViewById(R.id.recyclerViewPhotos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("Trips");
        storageRef = FirebaseStorage.getInstance().getReference().child("Users").child(userID);

        String trip = getIntent().getStringExtra("clicked_trip");
        photos = mDatabase.child(trip).child("Photos");

        list = new ArrayList<>();
        adapter = new PhotosAdapter(this, list);
        recyclerView.setAdapter(adapter);

        photos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Photo photo = dataSnapshot.getValue(Photo.class);
                    list.add(photo);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}