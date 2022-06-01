package com.pzd.collegegrievance;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class AdminAdapter {

    String fullName, department, rollNo, userEmail, gender, Subject_of_Grievance, Description_of_Grievance , phoneNo;



    public AdminAdapter() {
        //Empty Constructor
    }

    public AdminAdapter(String fullName, String department, String rollNo, String gender, String userEmail,
                        String subject_of_Grievance, String description_of_Grievance, String phoneNo) {

        this.fullName = fullName;
        this.department = department;
        this.rollNo = rollNo;
        this.gender = gender;
        this.userEmail = userEmail;
        this.Subject_of_Grievance = subject_of_Grievance;
        this.Description_of_Grievance = description_of_Grievance;
        this.phoneNo = phoneNo;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getSubject_of_Grievance() {
        return Subject_of_Grievance;
    }

    public void setSubject_of_Grievance(String subject_of_Grievance) {
        Subject_of_Grievance = subject_of_Grievance;
    }

    public String getDescription_of_Grievance() {
        return Description_of_Grievance;
    }

    public void setDescription_of_Grievance(String description_of_Grievance) {
        Description_of_Grievance = description_of_Grievance;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
