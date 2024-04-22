package com.example.foodapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.example.foodapp.Models.Users;
import com.example.foodapp.databinding.ActivitySignInBinding;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.example.foodapp.R;
import com.google.firebase.database.FirebaseDatabase;

public class SignIn extends AppCompatActivity {

    ActivitySignInBinding binding;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    GoogleSignInClient googleSignInClient;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
         googleSignInClient = GoogleSignIn.getClient(this, gso);



        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(SignIn.this);
        progressDialog.setTitle("Logging In");
        progressDialog.setMessage("Your account is signing in...");

        binding.btnnsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String email = binding.etEmail.getText().toString();
                String password = binding.etPass.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(SignIn.this, SampleFood.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(SignIn.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(SignIn.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, SignUp.class);
                startActivity(intent);
            }
        });

        binding.btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        // Check if the user is already signed in
//        if (auth.getCurrentUser() != null) {
//            Intent intent = new Intent(SignIn.this, SampleFood.class);
//            startActivity(intent);
//            finish(); // Finish the current activity
//        }
    }

    private void signIn() {
        Intent signInIntent=googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    int RC_SIGN_IN=65;
    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (data != null) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Log.d("TAG", "Firebase auth with Google: " + account.getId());
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    Log.w("TAG", "Google sign in failed", e);
                }
            } else {
                Log.w("TAG", "onActivityResult: Intent data is null");
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "onComplete: success");
                            FirebaseUser user = auth.getCurrentUser();

                            Users users=new Users();
                            users.setUserId(user.getUid());
                            users.setName(user.getDisplayName());
                            users.setProfilePic(user.getPhotoUrl().toString());
                            database.getReference().child("Users").child(user.getUid()).setValue(users);


                            Intent intent=new Intent(SignIn.this,SampleFood.class);
                            startActivity(intent);
                            Toast.makeText(SignIn.this, "Sign in with Google", Toast.LENGTH_SHORT).show();
                            // Update UI accordingly
                        } else {
                            Log.w("TAG", "onComplete: failure", task.getException());
                            Toast.makeText(SignIn.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            Snackbar.make(binding.getRoot(),"Authentication Failed",Snackbar.LENGTH_SHORT);
                            // Update UI accordingly
                        }
                    }
                });
    }


}

