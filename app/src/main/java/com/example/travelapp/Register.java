package com.example.travelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private Button registerUser;
    private TextView hasAccount;
    private EditText editFullName, editPassword, editEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        registerUser = findViewById(R.id.registerBtn);
        registerUser.setOnClickListener(this);

        hasAccount = findViewById(R.id.loginText);
        hasAccount.setOnClickListener(this);

        editFullName = (EditText) findViewById(R.id.fullName);
        editPassword = (EditText) findViewById(R.id.Password);
        editEmail = (EditText) findViewById(R.id.email);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.registerBtn:
                registerUser();
                startActivity(new Intent(this, Login.class));
                break;
            case R.id.loginText:
                startActivity(new Intent(this, Login.class));
                break;
        }
    }

    private void registerUser() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String name = editFullName.getText().toString().trim();

        if(name.isEmpty()){
            editFullName.setError("Full name is required!");
            editFullName.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editEmail.setError("Email is required!");
            editEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editPassword.setError("Password is required!");
            editPassword.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Please provide valid e-mail!");
            editEmail.requestFocus();
            return;
        }

        if(password.length() < 6){
            editPassword.setError("Password must be at least 6 characters long!");
            editPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(name, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull  Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Register.this, "User has been registered!", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(Register.this, "Failed to register!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(Register.this, "Failed to register!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}