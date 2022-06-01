package com.pzd.collegegrievance;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HostelFragment extends Fragment {

    RadioGroup radioGroup;
    RadioButton Male, Female, Other;
    Button buttonSubmit;
    TextInputLayout nameBox, emailBox, phoneBox, rollNoBox, subOfGrievance, descriptionBox;

    FirebaseFirestore firebaseStore;
    FirebaseAuth FAuth;

    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;

    ArrayList<String> arrayList_departments;
    ArrayAdapter<String> arrayAdapter_departments;


    public HostelFragment() {
        // Required empty public constructor
        super(R.layout.fragment_hostel);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        radioGroup = (RadioGroup) getView().findViewById(R.id.radioGroup);
        Male = (RadioButton) getView().findViewById(R.id.radio_button_1);
        Female = (RadioButton) getView().findViewById(R.id.radio_button_2);
        Other = (RadioButton) getView().findViewById(R.id.radio_button_3);
        TextView textViewRadio = (TextView) getView().findViewById(R.id.textRadio);

        nameBox = (TextInputLayout) getView().findViewById(R.id.nameBox);
        emailBox = (TextInputLayout) getView().findViewById(R.id.emailBox);
        phoneBox = (TextInputLayout) getView().findViewById(R.id.phoneBox);
        rollNoBox = (TextInputLayout) getView().findViewById(R.id.rollNoBox);
        subOfGrievance = (TextInputLayout) getView().findViewById(R.id.subGrievance);
        descriptionBox = (TextInputLayout) getView().findViewById(R.id.textDescription);
        buttonSubmit = (Button) getView().findViewById(R.id.buttonSubmitGrievance);

        firebaseStore = FirebaseFirestore.getInstance();
        FAuth = FirebaseAuth.getInstance();


        textInputLayout = (TextInputLayout) getView().findViewById(R.id.textLayout);
        autoCompleteTextView = (AutoCompleteTextView) getView().findViewById(R.id.autoCompleteView);

        arrayList_departments = new ArrayList<>();
        arrayList_departments.add("Computer Science and Engineering");
        arrayList_departments.add("Information Technology");
        arrayList_departments.add("Electronics and Communication Engineering");
        arrayList_departments.add("Mechanical Engineering");
        arrayList_departments.add("Civil Engineering");
        arrayList_departments.add("Electrical Engineering");
        arrayList_departments.add("BioMedical Engineering");
        arrayList_departments.add("Food Technology");
        arrayList_departments.add("Printing Technology");
        arrayList_departments.add("Environmental Science");
        arrayList_departments.add("Physics");
        arrayList_departments.add("Chemistry");
        arrayList_departments.add("Mathematics");
        arrayList_departments.add("Physiotherapy");
        arrayList_departments.add("Pharmacy");


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String nameB = nameBox.getEditText().getText().toString().trim();
                String emailB = emailBox.getEditText().getText().toString().trim();
                String phoneNoB = phoneBox.getEditText().getText().toString().trim();
                String rollNoB = rollNoBox.getEditText().getText().toString().trim();
                String subGrievanceB = subOfGrievance.getEditText().getText().toString().trim();
                String descriptionGrievanceB = descriptionBox.getEditText().getText().toString().trim();

                String r1 = Male.getText().toString();
                String r2 = Female.getText().toString();
                String r3 = Other.getText().toString();


                if (TextUtils.isEmpty(nameB)) {
                    nameBox.setError("Name is Required!");
                }
                if (TextUtils.isEmpty(emailB)) {
                    emailBox.setError("Email is Required!");
                }
                if (TextUtils.isEmpty(phoneNoB)) {
                    phoneBox.setError("PhoneNo is Required!");
                }
                if (TextUtils.isEmpty(rollNoB)) {
                    rollNoBox.setError("ROllNo is Required!");
                }
                if (TextUtils.isEmpty(subGrievanceB)) {
                    subOfGrievance.setError("Subject is Required!");
                }


                if (TextUtils.isEmpty(nameB)) {

                    Toast.makeText(getActivity(), "Fill Up the Required Fields!", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(emailB)) {
                    Toast.makeText(getActivity(), "Fill Up the Required Fields!", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(phoneNoB)) {
                    Toast.makeText(getActivity(), "Fill Up the Required Fields!", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(rollNoB)) {
                    Toast.makeText(getActivity(), "Fill Up the Required Fields!", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(subGrievanceB)) {
                    Toast.makeText(getActivity(), "Fill Up the Required Fields!", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(descriptionGrievanceB)) {
                    Toast.makeText(getActivity(), "Fill Up the Required Fields!", Toast.LENGTH_SHORT).show();

                } else if (descriptionGrievanceB.length() > 300) {
                    Toast.makeText(getActivity(), "Description Should be less than 300 Words!", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseUser user = FAuth.getCurrentUser();
                    DocumentReference df = firebaseStore.collection("Grievances For Hostel ").document(user.getUid())
                            .collection("Student Grievance").document();
                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("fullName", nameBox.getEditText().getText().toString());
                    userInfo.put("userEmail", emailBox.getEditText().getText().toString());
                    userInfo.put("phoneNo", phoneBox.getEditText().getText().toString());
                    userInfo.put("rollNo", rollNoBox.getEditText().getText().toString());
                    userInfo.put("Subject_of_Grievance", subOfGrievance.getEditText().getText().toString());
                    userInfo.put("Description_of_Grievance", descriptionBox.getEditText().getText().toString());
                    userInfo.put("department", autoCompleteTextView.getText().toString());
                    if (radioGroup.getCheckedRadioButtonId() == R.id.radio_button_1) {
                        userInfo.put("gender", r1);
                    } else if (radioGroup.getCheckedRadioButtonId() == R.id.radio_button_2) {
                        userInfo.put("gender", r2);
                    } else if (radioGroup.getCheckedRadioButtonId() == R.id.radio_button_3) {
                        userInfo.put("gender", r3);
                    }

                    df.set(userInfo);


                    DocumentReference df2 = firebaseStore.collection("Grievances For College to Admin").document();
                    Map<String, Object> userInfo2 = new HashMap<>();
                    userInfo2.put("fullName", nameBox.getEditText().getText().toString());
                    userInfo2.put("userEmail", emailBox.getEditText().getText().toString());
                    userInfo2.put("phoneNo", phoneBox.getEditText().getText().toString());
                    userInfo2.put("rollNo", rollNoBox.getEditText().getText().toString());
                    userInfo2.put("Subject_of_Grievance", subOfGrievance.getEditText().getText().toString());
                    userInfo2.put("Description_of_Grievance", descriptionBox.getEditText().getText().toString());
                    userInfo2.put("department", autoCompleteTextView.getText().toString());
                    if (radioGroup.getCheckedRadioButtonId() == R.id.radio_button_1) {
                        userInfo2.put("gender", r1);
                    } else if (radioGroup.getCheckedRadioButtonId() == R.id.radio_button_2) {
                        userInfo2.put("gender", r2);
                    } else if (radioGroup.getCheckedRadioButtonId() == R.id.radio_button_3) {
                        userInfo2.put("gender", r3);
                    }

                    df2.set(userInfo2);


                    openDialog();
                }
            }
        });


        arrayAdapter_departments = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.drop_down_layout, arrayList_departments);
        autoCompleteTextView.setAdapter(arrayAdapter_departments);

        autoCompleteTextView.setThreshold(1);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hostel, container, false);
    }

    public void openDialog() {
        HostelFragDialogBox hostelFragDialogBox = new HostelFragDialogBox();
        hostelFragDialogBox.show(getFragmentManager(), "Done");
        if (nameBox != null) {
            nameBox.getEditText().getText().clear();
        }
        if (emailBox != null) {
            emailBox.getEditText().getText().clear();
        }
        if (phoneBox != null) {
            phoneBox.getEditText().getText().clear();
        }
        if (rollNoBox != null) {
            rollNoBox.getEditText().getText().clear();
        }
        if (subOfGrievance != null) {
            subOfGrievance.getEditText().getText().clear();
        }
        if (descriptionBox != null) {
            descriptionBox.getEditText().getText().clear();
        }
        if (Male.isChecked()) {
            Male.setChecked(false);
        }
        if (Female.isChecked()) {
            Female.setChecked(false);
        }
        if (Other.isChecked()) {
            Other.setChecked(false);
        }
        if (autoCompleteTextView != null) {
            autoCompleteTextView.getText().clear();
        }
    }

}