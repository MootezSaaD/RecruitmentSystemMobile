package tn.medtech.recruitmentsystemapp.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.api.models.Domain;
import tn.medtech.recruitmentsystemapp.api.models.JobOffer;
import tn.medtech.recruitmentsystemapp.api.models.Skill;
import tn.medtech.recruitmentsystemapp.api.services.DomainService;
import tn.medtech.recruitmentsystemapp.api.services.JobService;
import tn.medtech.recruitmentsystemapp.api.services.SkillService;
import tn.medtech.recruitmentsystemapp.util.TokenService;

public class CreateJobActivity extends AppCompatActivity {

    Button startDate;
    Button endDate;
    Button postJob;
    EditText jobTitle;
    EditText jobDescription;
    String applicationStartDate = "";
    String applicationEndate = "";
    Button domain;
    Calendar c;
    DatePickerDialog dpd;
    ChipGroup skillsChipGroup;
    List<Skill> skillsList;
    AutoCompleteTextView skills;
    // We will the store the skills to submit here
    ArrayList<Skill> applicationSkills = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_create);
        // Fetch skills
        getSkills();
        startDate = findViewById(R.id.btnStartDate);
        endDate = findViewById(R.id.btnEndDate);
        domain = findViewById(R.id.btnDomain);
        skills = findViewById(R.id.actvSkills);
        skillsChipGroup = findViewById(R.id.skillsChipGroup);
        postJob = findViewById(R.id.postJobBtn);
        jobTitle = findViewById(R.id.titleFld);
        jobDescription = findViewById(R.id.descFld);
        domain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDomains();
            }
        });

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                dpd = new DatePickerDialog(CreateJobActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year1, int month1, int day1) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year1);
                        calendar.set(Calendar.MONTH, month1);
                        calendar.set(Calendar.DAY_OF_MONTH, day1);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        applicationStartDate = simpleDateFormat.format(calendar.getTime());
                        startDate.setText(year1 + "-" + (month1 + 1) + "-" + day1);

                    }
                }, year, month, day);
                dpd.show();
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                dpd = new DatePickerDialog(CreateJobActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year1, int month1, int day1) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year1);
                        calendar.set(Calendar.MONTH, month1);
                        calendar.set(Calendar.DAY_OF_MONTH, day1);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        applicationEndate = simpleDateFormat.format(calendar.getTime());
                        endDate.setText(year1 + "-" + (month1 + 1) + "-" + day1);
                    }
                }, year, month, day);
                dpd.show();
            }
        });

        postJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobOffer jobOffer = new JobOffer(jobTitle.getText().toString(), null, jobDescription.getText().toString(),
                        applicationStartDate, applicationEndate, applicationSkills);
                addJobOfferRequest(jobOffer);
            }
        });
    }

    private void getSkills() {
        Retrofit.Builder retroBuilder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/api/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = retroBuilder.build();
        SkillService skillService = retrofit.create(SkillService.class);
        Call<List<Skill>> call = skillService.getSkills("Bearer " + TokenService.getToken());
        call.enqueue(new Callback<List<Skill>>() {

            @Override
            public void onResponse(Call<List<Skill>> call, Response<List<Skill>> response) {
                skillsList = response.body();
                System.out.println(skillsList.size());
                skillsList.forEach(skill -> System.out.println(skill.getSkillName() + " Type: " + skill.getSkillType()));
                // Create the array adapter
                ArrayAdapter<Skill> arrayAdapter = new ArrayAdapter<>(CreateJobActivity.this, android.R.layout.simple_list_item_1, skillsList);
                // Add the adapter to actv
                skills.setAdapter(arrayAdapter);
                // Add to the ChipGroup
                skills.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Chip skillChip = new Chip(CreateJobActivity.this);
                        Skill skill = (Skill) parent.getItemAtPosition(position);
                        skillChip.setText(skill.toString());
                        skillChip.setCloseIconVisible(true);
                        skillChip.setOnCloseIconClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                skillsChipGroup.removeView(view);
                                // Need to handle removal from applicationSkills arrayList
                            }
                        });
                        skillsChipGroup.addView(skillChip);
                        // Adding skills to the job offer's skill arrayList
                        applicationSkills.add(skill);
                        // Clear the actv
                        skills.setText("");
                    }
                });

            }


            @Override
            public void onFailure(Call<List<Skill>> call, Throwable t) {
                Toast.makeText(CreateJobActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDomains() {
        Retrofit.Builder retroBuilder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/api/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = retroBuilder.build();
        DomainService domainService = retrofit.create(DomainService.class);
        Call<List<Domain>> call = domainService.getDomains("Bearer " + TokenService.getToken());
        call.enqueue(new Callback<List<Domain>>() {

            @Override
            public void onResponse(Call<List<Domain>> call, Response<List<Domain>> response) {
                List<Domain> domains = response.body();
                domains.forEach(domain -> System.out.println(domain.getDomainName()));
            }


            @Override
            public void onFailure(Call<List<Domain>> call, Throwable t) {
                Toast.makeText(CreateJobActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void addJobOfferRequest(JobOffer jobOffer) {
        // Usual drill..
        Retrofit.Builder retroBuilder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/api/job/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = retroBuilder.build();
        JobService jobService = retrofit.create(JobService.class);
        Call<JobOffer> call = jobService.testCreateJob("Bearer " + TokenService.getToken(),jobOffer);
        // FOR TESTING PURPOSES
        String gson = new Gson().toJson(jobOffer);
        Log.d("Rec", gson);
        call.enqueue(new Callback<JobOffer>() {
            @Override
            public void onResponse(Call<JobOffer> call, Response<JobOffer> response) {
                Toast.makeText(CreateJobActivity.this, "Job Offer Posted !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JobOffer> call, Throwable t) {
                Toast.makeText(CreateJobActivity.this, "Unexpected error !", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}

