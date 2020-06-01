package tn.medtech.recruitmentsystemapp.ui;
/*
 * This activity contains the common form that any user should pass by.
 *
 * */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.api.models.Company;
import tn.medtech.recruitmentsystemapp.api.models.User;

public class GeneralRegisterActivity extends AppCompatActivity {

    TextInputLayout firstName;
    TextInputLayout lastName;
    TextInputLayout email;
    TextInputLayout password;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_register);
        Bundle intent = getIntent().getExtras();
        int role = intent.getInt("role");
        System.out.print(role);
        firstName = findViewById(R.id.firstNameFld);
        lastName = findViewById(R.id.lastNameFld);
        email = findViewById(R.id.emailFld);
        password = findViewById(R.id.passwordFld);
        next = findViewById(R.id.nextBtn);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (role == 1) {
                    // Create a recruiter
                    User recruiter = new User(
                            firstName.getEditText().getText().toString(),
                            lastName.getEditText().getText().toString(),
                            email.getEditText().getText().toString(),
                            password.getEditText().getText().toString(),
                            (Company) null);
                    Intent finalRecruiterIntent = new Intent(GeneralRegisterActivity.this, RecruiterRegisterActivity.class);
                    finalRecruiterIntent.putExtra("recruiterObject", new Gson().toJson(recruiter));
                    startActivity(finalRecruiterIntent);

                } else {
                    // Create an applicant
                    User applicant = new User(firstName.getEditText().getText().toString(),
                            lastName.getEditText().getText().toString(),
                            email.getEditText().getText().toString(),
                            password.getEditText().getText().toString(),
                            "");
                    Intent finalApplicantInent = new Intent(GeneralRegisterActivity.this, ApplicantRegisterActivity.class);
                    finalApplicantInent.putExtra("applicantObject", new Gson().toJson(applicant));
                    startActivity(finalApplicantInent);
                }
            }
        });

    }
}
