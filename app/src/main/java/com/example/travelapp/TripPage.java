package com.example.travelapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class TripPage extends AppCompatActivity {

    private Button uploadBtn;
    private ImageView imageView;
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private String userID;
    private DatabaseReference photos;
    private StorageReference storageRef;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_page);

        uploadBtn = findViewById(R.id.uploadBtn);
        imageView = findViewById(R.id.currentPhoto);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("Trips");
        storageRef = FirebaseStorage.getInstance().getReference().child("Users").child(userID);


        if(getIntent().hasExtra("selected_trip")) {
            Trip trip = getIntent().getParcelableExtra("selected_trip");
            photos = mDatabase.child(trip.getTripName()).child("Photos");
        }

        // the empty photo is pressed
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });

        // the "Upload" button is pressed
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUri != null){
                    uploadToFirebase(imageUri);
                } else{
                    Toast.makeText(TripPage.this, "Please select image!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // uploads the photo to Firebase
    private void uploadToFirebase(Uri uri) {
        StorageReference fileRef = storageRef.child(System.currentTimeMillis() + "." + getFileExtension(uri));

        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        photos.setValue(uri.toString());
                        Toast.makeText(TripPage.this, "Uploaded succesfully!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TripPage.this, "Uploading failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // it returns the selected image's extension
    private String getFileExtension(Uri mUri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2 && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }
}