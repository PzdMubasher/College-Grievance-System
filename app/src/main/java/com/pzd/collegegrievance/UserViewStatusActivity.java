package com.pzd.collegegrievance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserViewStatusActivity extends AppCompatActivity {

    FirebaseFirestore FStore;
    //ArrayList<StatusAdapter> userStatusArrayAdapter;
    //StatusAdapterLink adapterLink;
    RecyclerView myRecycleView;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_status);

        toolbar = findViewById(R.id.myToolbarViewStatus);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Grievance Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FStore = FirebaseFirestore.getInstance();

        myRecycleView = findViewById(R.id.userListStatus);
       // myRecycleView.setHasFixedSize(true);
       // myRecycleView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        DocumentReference reference;

        Query query = FStore.collection("Grievances For College").document(userID).collection("Student Grievance");

        FirestoreRecyclerOptions<StatusAdapter> options = new FirestoreRecyclerOptions.Builder<StatusAdapter>()
                .setQuery(query, StatusAdapter.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<StatusAdapter, StatusAdapterHolder>(options) {


            @NonNull
            @Override
            public StatusAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_status_list_item, parent, false);

                return new StatusAdapterHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull StatusAdapterHolder statusAdapterHolder, int i, @NonNull StatusAdapter statusAdapter) {

                statusAdapterHolder.listName.setText(statusAdapter.getFullName());
                statusAdapterHolder.listSubject.setText(statusAdapter.getSubject_of_Grievance());

                statusAdapterHolder.buttonViewDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), StudentDetailsUserActivity.class);
                        intent.putExtra("FullName", statusAdapter.getFullName());
                        intent.putExtra("Email", statusAdapter.getUserEmail());
                        intent.putExtra("PhoneNo", statusAdapter.getPhoneNo());
                        intent.putExtra("RollNo", statusAdapter.getRollNo());
                        intent.putExtra("Gender", statusAdapter.getGender());
                        intent.putExtra("Department", statusAdapter.getDepartment());
                        intent.putExtra("Subject of Grievance", statusAdapter.getSubject_of_Grievance());
                        intent.putExtra("Description", statusAdapter.getDescription_of_Grievance());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);


                    }
                });

            }
        };

        myRecycleView.setHasFixedSize(true);
        myRecycleView.setLayoutManager(new LinearLayoutManager(this));
        myRecycleView.setAdapter(adapter);



    }


    private static class StatusAdapterHolder extends RecyclerView.ViewHolder {

        TextView listName, listSubject;
        Button buttonViewDetails;

        public StatusAdapterHolder(@NonNull View itemView) {
            super(itemView);

            listName = itemView.findViewById(R.id.textViewUser1);
            listSubject = itemView.findViewById(R.id.textViewUser2);
            buttonViewDetails = itemView.findViewById(R.id.buttonViewDetails);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}