package tn.medtech.recruitmentsystemapp.ui.RecruiterDashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;

import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.api.models.JobOffer;
import tn.medtech.recruitmentsystemapp.api.models.Skill;
import tn.medtech.recruitmentsystemapp.util.DateParser;

public class RecruiterJobOfferDetailsActivity extends AppCompatActivity {
    TextView title;
    TextView company;
    TextView startDate;
    TextView endDate;
    TextView description;
    ChipGroup jobSkillsChipGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_job_offer_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        JobOffer jobOffer = new Gson().fromJson(intent.getStringExtra("jobOfferObject"), JobOffer.class);
        setTitle(jobOffer.getTitle());
        title = findViewById(R.id.recJobTitle);
        company = findViewById(R.id.recJobCompany);
        startDate = findViewById(R.id.recJobstartDate);
        endDate = findViewById(R.id.recJobEndDate);
        description = findViewById(R.id.recDetailJobDescription);

        title.setText(jobOffer.getTitle());
        company.setText(jobOffer.getCompany());
        description.setText(jobOffer.getDescription());
        startDate.setText(DateParser.parseDate(jobOffer.getStartDate()));
        endDate.setText(DateParser.parseDate(jobOffer.getEndDate()));
        jobSkillsChipGroup = findViewById(R.id.recJobSkills);


        for(Skill skill : jobOffer.getSkills()) {
            Chip skillChip = new Chip(this);
            skillChip.setText(skill.toString());
            jobSkillsChipGroup.addView(skillChip);
        }



    }
}
