package tn.medtech.recruitmentsystemapp.ui.ApplicantDashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.api.models.Domain;
import tn.medtech.recruitmentsystemapp.api.models.WorkExperience;
import tn.medtech.recruitmentsystemapp.api.services.DomainService;
import tn.medtech.recruitmentsystemapp.api.services.ServiceGenerator;
import tn.medtech.recruitmentsystemapp.api.services.WorkExperienceService;
import tn.medtech.recruitmentsystemapp.util.DatePickerUniversal;
import tn.medtech.recruitmentsystemapp.util.TokenService;

public class ApplicantAddWorkExperience extends AppCompatActivity {
    TextInputEditText workExperienceTitle;
    TextInputEditText workExperienceCompany;
    TextInputEditText startDate;
    TextInputEditText endDate;
    Button addWorkExperience;
    Button domain;
    ArrayList<Domain> domains = new ArrayList<>();
    ArrayList<String> domainNames = new ArrayList<>();
    List<Domain> domainList;
    int domainPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_add_work_experience);
        setTitle("Add Work Experience");
        // Get Domains
        getDomains();
        // Get from the view
        workExperienceTitle = findViewById(R.id.weTitleFld);
        workExperienceCompany = findViewById(R.id.weCompanyFld);
        startDate = findViewById(R.id.weStartDateFld);
        endDate = findViewById(R.id.weEndDateFld);
        domain = findViewById(R.id.weDomainFld);
        addWorkExperience = findViewById(R.id.addWorkExperienceBtn);

        DatePickerUniversal startDateDPU = new DatePickerUniversal(startDate, "yyyy-MM-dd", false);
        DatePickerUniversal endDateDPU = new DatePickerUniversal(endDate, "yyyy-MM-dd", false);

        domain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence[] domainsName = domainNames.toArray(new CharSequence[domainNames.size()]);
                AlertDialog.Builder builder = new AlertDialog.Builder(ApplicantAddWorkExperience.this);
                builder.setTitle("Domain")
                        .setSingleChoiceItems(domainsName, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                domainPosition = i;
                                domain.setText(domains.get(domainPosition).getDomainName());
                            }
                        })
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                builder.create().show();
            }

        });

        addWorkExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkExperience workExperience = new WorkExperience();
                workExperience.setTitle(workExperienceTitle.getEditableText().toString());
                workExperience.setCompanyName(workExperienceCompany.getEditableText().toString());
                workExperience.setDomain(domains.get(domainPosition).getDomainName());
                workExperience.setStartDate(startDate.getText().toString());
                workExperience.setEndDate(endDate.getText().toString());
                addWorkExperience(workExperience);
            }
        });
    }

    private void getDomains() {
        TokenService tokenService = TokenService.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        DomainService domainService = ServiceGenerator.createServiceWithAuth(DomainService.class, tokenService);
        Call<List<Domain>> call = domainService.getDomains();
        call.enqueue(new Callback<List<Domain>>() {

            @Override
            public void onResponse(Call<List<Domain>> call, Response<List<Domain>> response) {
                domainList = response.body();
                domainList.forEach(domain -> {
                    System.out.println(domain.getDomainName());
                    domainNames.add(domain.getDomainName());
                    domains.add(domain);
                });
            }


            @Override
            public void onFailure(Call<List<Domain>> call, Throwable t) {
                Toast.makeText(ApplicantAddWorkExperience.this, "Something went wrong... Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addWorkExperience(WorkExperience workExperience) {
        TokenService tokenService = TokenService.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        WorkExperienceService workExperienceService = ServiceGenerator.createServiceWithAuth(WorkExperienceService.class, tokenService);
        Call<tn.medtech.recruitmentsystemapp.api.models.Response> call = workExperienceService.addWorkExperience(workExperience);
        call.enqueue(new Callback<tn.medtech.recruitmentsystemapp.api.models.Response>() {
            @Override
            public void onResponse(Call<tn.medtech.recruitmentsystemapp.api.models.Response> call, Response<tn.medtech.recruitmentsystemapp.api.models.Response> response) {
                Toast.makeText(ApplicantAddWorkExperience.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<tn.medtech.recruitmentsystemapp.api.models.Response> call, Throwable t) {
                Toast.makeText(ApplicantAddWorkExperience.this, "Something went wrong... Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
