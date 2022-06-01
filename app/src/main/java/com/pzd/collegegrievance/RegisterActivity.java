package com.pzd.collegegrievance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    TextView regTextView, verificationTxt;
    TextInputLayout regNameBox, regEmailBox, regPhoneBox, regPasswordBox;
    Button signUpButton, loginView;
    ProgressBar regProgressBar;
    View regDivider;
    FirebaseAuth registerFAuth;
    FirebaseFirestore fireStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        regTextView = findViewById(R.id.registerTextView);
        loginView = findViewById(R.id.loginTextView);
        regNameBox = findViewById(R.id.nameBox);
        regEmailBox = findViewById(R.id.emailBox);
        regPhoneBox = findViewById(R.id.phoneBox);
        regPasswordBox = findViewById(R.id.passwordBox);
        signUpButton = findViewById(R.id.signUpButton);
        regProgressBar = findViewById(R.id.registerProgressBar);
        regDivider = findViewById(R.id.registerDivider);
        verificationTxt = findViewById(R.id.verificationTxt);


        registerFAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();

        /*if(registerFAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }*/


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = regEmailBox.getEditText().getText().toString().trim();
                String password = regPasswordBox.getEditText().getText().toString().trim();
                String name = regNameBox.getEditText().getText().toString().trim();
                String phoneNo = regPhoneBox.getEditText().getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    regNameBox.setError("Name is Required!");
                }

                if (TextUtils.isEmpty(email)) {
                    regEmailBox.setError("Email is Required!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    regPasswordBox.setError("Password is Required!");
                    return;
                }
                if (TextUtils.isEmpty(phoneNo)) {
                    regPhoneBox.setError("Phone No is Required!");
                }

                if (password.length() < 6) {
                    regPasswordBox.setError("Password must be 6 characters long!");
                }

                regProgressBar.setVisibility(View.VISIBLE);

                // Register the user to Firebase

                registerFAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            // verification
                            FirebaseUser user = registerFAuth.getCurrentUser();
                           /* user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(RegisterActivity.this, "Verification Email has been Sent!", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG", "onFailure: Email not sent" + e.getMessage());

                                }
                            });*/


                            Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            DocumentReference df = fireStore.collection("users").document(user.getUid());
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("FullName", regNameBox.getEditText().getText().toString());
                            userInfo.put("UserEmail", regEmailBox.getEditText().getText().toString());
                            userInfo.put("Phone No.", regPhoneBox.getEditText().getText().toString());

                            //If the user is Admin
                            userInfo.put("isUser", 1);

                            df.set(userInfo);
                            verificationTxt.setVisibility(View.GONE);
                            startActivity(new Intent(getApplicationContext(), UserActivity.class));

                        } else {
                            Toast.makeText(RegisterActivity.this, "Failed to Register!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            regProgressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

        loginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }
}