package com.example.foodapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodapp.Models.Users;
import com.example.foodapp.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    ActivitySignUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();
        progressDialog=new ProgressDialog(SignUp.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("Your account is being created");
        
        binding.btnnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                auth.createUserWithEmailAndPassword(binding.etEmail.getText().toString(),binding.etPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // Toast.makeText(SignUp.this, "error abc", Toast.LENGTH_SHORT).show();
                        if(task.isSuccessful())
                        {
                            Users users=new Users(binding.etPhone.getText().toString(),binding.etEmail.getText().toString(),binding.etPass.getText().toString());

                            String id=task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(users);

                            Toast.makeText(SignUp.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                           // Toast.makeText(SignUp.this, "Error", Toast.LENGTH_SHORT).show();
                            Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


    }
}