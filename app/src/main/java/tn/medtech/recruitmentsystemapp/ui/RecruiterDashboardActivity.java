package tn.medtech.recruitmentsystemapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tn.medtech.recruitmentsystemapp.R;

public class RecruiterDashboardActivity extends AppCompatActivity {
    Button createJobButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_dashboard);
        createJobButton = findViewById(R.id.createJobButton);

        createJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jobIntent = new Intent(RecruiterDashboardActivity.this, CreateJobActivity.class);
                startActivity(jobIntent);
            }
        });
    }
}
