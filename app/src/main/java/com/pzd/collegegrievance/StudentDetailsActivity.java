package com.pzd.collegegrievance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class StudentDetailsActivity extends AppCompatActivity {

    TextView nameView, emailView, phoneView,
            rollNoView, genderView, departmentView, subView, descriptionView;
    TextInputLayout acceptText;
    Button acceptBtn;
    ImageView verifyBtn;
    FirebaseFirestore FBStore;
    FirebaseAuth FAuth;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        toolbar = findViewById(R.id.myToolbarViewStatus);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Grievance Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        FBStore = FirebaseFirestore.getInstance();


        nameView = findViewById(R.id.nameView);
        emailView = findViewById(R.id.emailView);
        phoneView = findViewById(R.id.phoneView);
        rollNoView = findViewById(R.id.rollNoView);
        genderView = findViewById(R.id.genderView);
        departmentView = findViewById(R.id.departmentView);
        subView = findViewById(R.id.subjectView);
        descriptionView = findViewById(R.id.descriptionView);
        acceptText = findViewById(R.id.acceptanceTxt);
        acceptBtn = findViewById(R.id.acceptanceBtn);
        verifyBtn = findViewById(R.id.logoBtn);


        nameView.setText(getIntent().getStringExtra("FullName"));
        emailView.setText(getIntent().getStringExtra("Email"));
        phoneView.setText(getIntent().getStringExtra("PhoneNo"));
        rollNoView.setText(getIntent().getStringExtra("RollNo"));
        genderView.setText(getIntent().getStringExtra("Gender"));
        departmentView.setText(getIntent().getStringExtra("Department"));
        subView.setText(getIntent().getStringExtra("Subject of Grievance"));
        descriptionView.setText(getIntent().getStringExtra("Description"));


        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String acceptance = acceptText.getEditText().getText().toString().trim();

                if(TextUtils.isEmpty(acceptance)){
                    acceptText.setError("Please write Acceptance comment!");
                    Toast.makeText(StudentDetailsActivity.this, "Please write Acceptance comment!", Toast.LENGTH_SHORT).show();
                }
                else {

                    FirebaseUser user = FAuth.getCurrentUser();
                    DocumentReference reference = FBStore.collection("Grievances For College").document("");

                    /*reference.set("Comment", acceptText.getEditText().getText().toString());
                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("IsAccepted", acceptText.getEditText().getText().toString());
                    reference.set(userInfo);
                    verifyBtn.setBackgroundResource(R.drawable.accepted);*/

                }






            }
        });






    }
}