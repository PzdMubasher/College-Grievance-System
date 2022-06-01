package com.pzd.collegegrievance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

public class StudentDetailsUserActivity extends AppCompatActivity {

    TextView nameView, emailView, phoneView,
            rollNoView, genderView, departmentView, subView, descriptionView;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details_user);


        toolbar = findViewById(R.id.myToolbarViewStatus);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Grievance Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        nameView = findViewById(R.id.nameView);
        emailView = findViewById(R.id.emailView);
        phoneView = findViewById(R.id.phoneView);
        rollNoView = findViewById(R.id.rollNoView);
        genderView = findViewById(R.id.genderView);
        departmentView = findViewById(R.id.departmentView);
        subView = findViewById(R.id.subjectView);
        descriptionView = findViewById(R.id.descriptionView);


        nameView.setText(getIntent().getStringExtra("FullName"));
        emailView.setText(getIntent().getStringExtra("Email"));
        phoneView.setText(getIntent().getStringExtra("PhoneNo"));
        rollNoView.setText(getIntent().getStringExtra("RollNo"));
        genderView.setText(getIntent().getStringExtra("Gender"));
        departmentView.setText(getIntent().getStringExtra("Department"));
        subView.setText(getIntent().getStringExtra("Subject of Grievance"));
        descriptionView.setText(getIntent().getStringExtra("Description"));


    }
}