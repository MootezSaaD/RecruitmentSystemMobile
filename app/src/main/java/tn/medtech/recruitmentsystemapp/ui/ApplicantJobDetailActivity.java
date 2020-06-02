package tn.medtech.recruitmentsystemapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;

import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.api.models.JobOffer;
import tn.medtech.recruitmentsystemapp.util.DateParser;

public class ApplicantJobDetailActivity extends AppCompatActivity {
    TextView title;
    TextView company;
    TextView skills;
    TextView description;
    TextView endDate;
    TextView startDate;
    ChipGroup jobSkillsChipGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_job_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        JobOffer jobOffer = new Gson().fromJson(intent.getStringExtra("jobDetailsObject"), JobOffer.class);
        title = findViewById(R.id.appDetailJobTitle);
        company = findViewById(R.id.appDetailJobCompany);
        skills = findViewById(R.id.appDetailJobSkills);
        description = findViewById(R.id.appDetailJobDescription);
        endDate = findViewById(R.id.appDetailJobEndDate);
        startDate = findViewById(R.id.appDetailJobStartDate);

        title.setText(jobOffer.getTitle());
        company.setText(jobOffer.getCompany());
        description.setText(jobOffer.getDescription());
        endDate.setText(DateParser.parseDate(jobOffer.getStartDate()));
        startDate.setText(DateParser.parseDate(jobOffer.getStartDate()));
        skills.setText(jobOffer.getSkills().toString());

    }
}
