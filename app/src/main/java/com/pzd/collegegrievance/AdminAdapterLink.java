package com.pzd.collegegrievance;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class AdminAdapterLink extends RecyclerView.Adapter<AdminAdapterLink.AdminViewHolder> {

    Context context;
    ArrayList<AdminAdapter> adminAdapterArrayList;

    public AdminAdapterLink(Context context, ArrayList<AdminAdapter> adminAdapterArrayList) {
        this.context = context;
        this.adminAdapterArrayList = adminAdapterArrayList;
    }

    @NonNull
    @Override
    public AdminAdapterLink.AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_list_item, parent, false);

        return new AdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAdapterLink.AdminViewHolder holder, int position) {
        AdminAdapter adminAdapter = adminAdapterArrayList.get(position);

        holder.listName.setText(adminAdapter.getFullName());
        holder.listDepartment.setText(adminAdapter.getDepartment());
        holder.listRollNo.setText(adminAdapter.getRollNo());

        holder.buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, StudentDetailsActivity.class);
                intent.putExtra("FullName", adminAdapter.getFullName());
                intent.putExtra("Email", adminAdapter.getUserEmail());
                intent.putExtra("PhoneNo", adminAdapter.getPhoneNo());
                intent.putExtra("RollNo", adminAdapter.getRollNo());
                intent.putExtra("Gender", adminAdapter.getGender());
                intent.putExtra("Department", adminAdapter.getDepartment());
                intent.putExtra("Subject of Grievance", adminAdapter.getSubject_of_Grievance());
                intent.putExtra("Description", adminAdapter.getDescription_of_Grievance());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return adminAdapterArrayList.size();
    }

    public static class AdminViewHolder extends RecyclerView.ViewHolder{

        TextView listName;
        TextView listDepartment;
        TextView listRollNo;
        Button buttonView;

        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);

            listName = itemView.findViewById(R.id.textView1);
            listRollNo = itemView.findViewById(R.id.textView2);
            listDepartment = itemView.findViewById(R.id.textView3);
            buttonView = itemView.findViewById(R.id.adminView);
        }

    }

}
