package tn.medtech.recruitmentsystemapp.ui;

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

public class RecruiterJobOfferDetailsActivity extends AppCompatActivity {
    TextView title;
    TextView company;
    ChipGroup jobSkillsChipGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_job_offer_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        JobOffer jobOffer = new Gson().fromJson(intent.getStringExtra("jobOfferObject"), JobOffer.class);
        title = findViewById(R.id.recJobTitle);
        company = findViewById(R.id.recJobCompany);

        title.setText(jobOffer.getTitle());
        company.setText(jobOffer.getCompany());
        jobSkillsChipGroup = findViewById(R.id.recJobSkills);
        for(Skill skill : jobOffer.getSkills()) {
            Chip skillChip = new Chip(this);
            skillChip.setText(skill.toString());
            jobSkillsChipGroup.addView(skillChip);
        }



    }
}
