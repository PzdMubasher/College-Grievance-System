package com.pzd.collegegrievance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    FirebaseFirestore FStore;
    ArrayList<AdminAdapter> userArrayAdapter;
    AdminAdapterLink adapterLink;
    RecyclerView myRecycleView;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TextView txtAdminName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("fetching data...");
        progressDialog.show();

        FStore = FirebaseFirestore.getInstance();
        txtAdminName = findViewById(R.id.adminName);

        // ImageButton logoutButton = (ImageButton) findViewById(R.id.logoutButton1);

        myRecycleView = findViewById(R.id.myAdminList);
        myRecycleView.setHasFixedSize(true);
        myRecycleView.setLayoutManager(new LinearLayoutManager(this));

        myRecycleView = findViewById(R.id.myAdminList);
        myRecycleView.setHasFixedSize(true);
        myRecycleView.setLayoutManager(new LinearLayoutManager(this));

        userArrayAdapter = new ArrayList<AdminAdapter>();
        adapterLink = new AdminAdapterLink(AdminActivity.this, userArrayAdapter);

        myRecycleView.setAdapter(adapterLink);

        EventChangeListener();
        setUpToolbar();

    }

    private void EventChangeListener() {

        FStore.collection("Grievances For College to Admin")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {

                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("Error!", error.getMessage());
                            return;

                        }

                        for (DocumentChange documentChange : value.getDocumentChanges()) {

                            if (documentChange.getType() == DocumentChange.Type.ADDED) {
                                userArrayAdapter.add(documentChange.getDocument().toObject(AdminAdapter.class));
                            }
                            if (documentChange.getType() == DocumentChange.Type.MODIFIED) {
                                userArrayAdapter.add(documentChange.getDocument().toObject(AdminAdapter.class));
                            }

                            adapterLink.notifyDataSetChanged();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();

                        }

                    }
                });

    }


    private void setUpToolbar() {
        drawerLayout = (DrawerLayout) findViewById(R.id.myDrawerAdmin);
        toolbar = (Toolbar) findViewById(R.id.myToolbarAdmin);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Admin Panel");
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        DocumentReference reference;

        reference = FStore.collection("users").document(userID);

        reference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.getResult().exists()) {

                            String nameResult = task.getResult().getString("FullName");

                            txtAdminName.setText("Welcome!" + "\n" + nameResult);

                        } else {
                            Toast.makeText(AdminActivity.this, "Not Exist!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void logOut(View view) {

        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}
