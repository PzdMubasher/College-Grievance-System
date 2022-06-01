package com.pzd.collegegrievance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class UserActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    Animation topAnim, bottomAnim, leftAnim, rightAnim;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TextView txtUserName, firstText, secondText, thirdText, fourthText;
    ImageView penView, iconView, helpImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        setUpToolbar();
        CardView cardView = (CardView) findViewById(R.id.istCardView);
        CardView cardView1 = (CardView) findViewById(R.id.secondCardView);

        txtUserName = findViewById(R.id.userName);
        firstText = findViewById(R.id.istText);
        secondText = findViewById(R.id.sndText);
        thirdText = findViewById(R.id.thirdText);
        fourthText = findViewById(R.id.fourthText);
        penView = findViewById(R.id.penView);
        iconView = findViewById(R.id.imageIconView);
        helpImage = findViewById(R.id.helpImage);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        leftAnim = AnimationUtils.loadAnimation(this, R.anim.left_animation);
        rightAnim = AnimationUtils.loadAnimation(this, R.anim.right_animation);



        firstText.setAnimation(leftAnim);
        secondText.setAnimation(rightAnim);
        thirdText.setAnimation(leftAnim);
        penView.setAnimation(rightAnim);
        fourthText.setAnimation(rightAnim);
        iconView.setAnimation(leftAnim);
        helpImage.setAnimation(bottomAnim);




        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserActivity2.class));

            }
        });
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserViewStatusActivity.class));
            }
        });
    }


    private void setUpToolbar() {
        drawerLayout = (DrawerLayout) findViewById(R.id.myDrawerLayout);
        toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


    }

    public void logOut(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        DocumentReference reference;
        FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

        reference = fireStore.collection("users").document(userID);

        reference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.getResult().exists()) {
                            String nameResult = task.getResult().getString("FullName");

                            txtUserName.setText("Welcome!" + "\n" + nameResult);

                        } else {
                            Toast.makeText(UserActivity.this, "Not Exist!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}