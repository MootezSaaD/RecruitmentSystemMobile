package tn.medtech.recruitmentsystemapp.ui.ApplicantDashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.api.models.JobOffer;
import tn.medtech.recruitmentsystemapp.api.models.Response;
import tn.medtech.recruitmentsystemapp.api.models.Skill;
import tn.medtech.recruitmentsystemapp.api.services.JobService;
import tn.medtech.recruitmentsystemapp.api.services.ServiceGenerator;
import tn.medtech.recruitmentsystemapp.util.DateParser;
import tn.medtech.recruitmentsystemapp.util.TokenService;

public class ApplicantJobDetailActivity extends AppCompatActivity {
    TextView title;
    TextView company;
    TextView description;
    TextView endDate;
    TextView startDate;
    Button applyBtn;
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
        jobSkillsChipGroup = findViewById(R.id.appDetailJobSkillsChipGroup);
        description = findViewById(R.id.appDetailJobDescription);
        endDate = findViewById(R.id.appDetailJobEndDate);
        startDate = findViewById(R.id.appDetailJobStartDate);
        applyBtn = findViewById(R.id.jobApplyBtn);


        title.setText(jobOffer.getTitle());
        company.setText(jobOffer.getCompany());
        description.setText(jobOffer.getDescription());
        endDate.setText(DateParser.parseDate(jobOffer.getStartDate()));
        startDate.setText(DateParser.parseDate(jobOffer.getStartDate()));
        for (Skill skill : jobOffer.getSkills()) {
            Chip skillChip = new Chip(this);
            skillChip.setText(skill.toString());
            jobSkillsChipGroup.addView(skillChip);
        }


        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyForJob(jobOffer.getId());

            }
        });

    }

    public void applyForJob(int jobID) {
        TokenService tokenService = TokenService.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        JobService jobService = ServiceGenerator.createServiceWithAuth(JobService.class, tokenService);
        Call<Response> call = jobService.applyForJob(jobID);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Toast.makeText(ApplicantJobDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(ApplicantJobDetailActivity.this, "Something went wrong.. !", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
