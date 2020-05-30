package tn.medtech.recruitmentsystemapp.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.api.models.Domain;
import tn.medtech.recruitmentsystemapp.api.models.Skill;
import tn.medtech.recruitmentsystemapp.api.services.DomainService;
import tn.medtech.recruitmentsystemapp.api.services.SkillService;
import tn.medtech.recruitmentsystemapp.util.TokenService;

public class CreateJobActivity extends AppCompatActivity {

    Button startDate;
    Button endDate;
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
                        startDate.setText(year1+"-"+(month1+1)+"-"+day1);
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
                        endDate.setText(year1+"-"+(month1+1)+"-"+day1);
                    }
                }, year, month, day);
                dpd.show();
            }
        });
    }

    private void getSkills(){
        Retrofit.Builder retroBuilder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/api/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = retroBuilder.build();
        SkillService skillService = retrofit.create(SkillService.class);
        Call<List<Skill>> call = skillService.getSkills("Bearer "+TokenService.getToken());
        call.enqueue(new Callback<List<Skill>>() {

            @Override
            public void onResponse(Call<List<Skill>> call, Response<List<Skill>> response) {
                skillsList = response.body();
                System.out.println(skillsList.size());
                skillsList.forEach(skill -> System.out.println(skill.getSkillName() + " Type: "+ skill.getSkillType()));
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

    private void getDomains(){
        Retrofit.Builder retroBuilder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/api/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = retroBuilder.build();
        DomainService domainService = retrofit.create(DomainService.class);
        Call<List<Domain>> call = domainService.getDomains("Bearer "+TokenService.getToken());
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
}

