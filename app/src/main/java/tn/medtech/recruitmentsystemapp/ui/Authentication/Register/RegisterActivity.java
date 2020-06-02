package tn.medtech.recruitmentsystemapp.ui.Authentication.Register;
/*
 * This activity contains two buttons that allows the user to
 * register as a candidate or recruiter.
 * */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.ui.Authentication.Login.LoginActivity;

public class RegisterActivity extends AppCompatActivity {

    TextView loginTextView;
    Button applicantBtn;
    Button recruiterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        applicantBtn = findViewById(R.id.applicantRegisterBtn);
        recruiterBtn = findViewById(R.id.recRegisterBtn);
        loginTextView = findViewById(R.id.textViewLogin);
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });
        applicantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent applicantIntent = new Intent(RegisterActivity.this, GeneralRegisterActivity.class);
                applicantIntent.putExtra("role", 0);
                startActivity(applicantIntent);
            }
        });
        recruiterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recruiterIntent = new Intent(RegisterActivity.this, GeneralRegisterActivity.class);
                recruiterIntent.putExtra("role", 1);
                startActivity(recruiterIntent);
            }
        });
    }
}
