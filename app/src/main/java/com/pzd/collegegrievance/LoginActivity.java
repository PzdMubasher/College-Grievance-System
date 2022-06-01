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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    TextView grievanceTextView, forgetView;
    TextInputLayout emailLoginBox;
    TextInputLayout passwordLoginBox;
    Button loginButton, newRegisterView;
    ProgressBar loginProgressBar;
    View loginDivider;
    FirebaseAuth loginFAuth;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        grievanceTextView = findViewById(R.id.gTextView);
        forgetView = findViewById(R.id.forgetTextView);
        newRegisterView = findViewById(R.id.signUpTextView);
        emailLoginBox = findViewById(R.id.emailField);
        passwordLoginBox = findViewById(R.id.passwordField);
        loginButton = findViewById(R.id.loginButton);
        loginProgressBar = findViewById(R.id.loginProgressBar);
        loginDivider = findViewById(R.id.loginDivider);

        loginFAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailLoginBox.getEditText().getText().toString().trim();
                String password = passwordLoginBox.getEditText().getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    emailLoginBox.setError("Email is Required.");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    passwordLoginBox.setError("Password is Required.");
                    return;
                }


                loginProgressBar.setVisibility(View.VISIBLE);
                //Authenticate the User.

                loginFAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {

                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(LoginActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                        checkUserAccessLevel(authResult.getUser().getUid());

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            loginProgressBar.setVisibility(View.GONE);

                    }
                });
            }
        });

        newRegisterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
            }
        });

    }

    private void checkUserAccessLevel(String uid) {
        DocumentReference dr = db.collection("users").document(uid);
        //extract the Data
        dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: " + documentSnapshot.getData());
                //identify the user access Level

                if (documentSnapshot.getString("isAdmin") != null) {
                    //user is admin
                    startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                }else{
                    //its user
                    startActivity(new Intent(getApplicationContext(), UserActivity.class));
                }
                finish();
            }
        });
    }


 /* @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), UserActivity.class));
            finish();
        }
    }*/
}

/* addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                            checkUserAccessLevel(task.get)
                            startActivity(new Intent(getApplicationContext(), UserActivity.class));

                        }else{
                            Toast.makeText(LoginActivity.this, "Error! Failed to Login!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            loginProgressBar.setVisibility(View.GONE);
                        }

                    }
                });
            }



            String email = emailLoginBox.getEditText().getText().toString().trim();
                String password = passwordLoginBox.getEditText().getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    emailLoginBox.setError("Email is Required.");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    passwordLoginBox.setError("Password is Required.");
                    return;
                }
                if (password.length() < 6) {
                    passwordLoginBox.setError("Password must be 6 characters long.");
                }
  */