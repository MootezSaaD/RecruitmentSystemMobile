package tn.medtech.recruitmentsystemapp.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import tn.medtech.recruitmentsystemapp.util.DatePickerUniversal;
import tn.medtech.recruitmentsystemapp.util.TokenService;

public class CreateJobFragment extends Fragment {

    TextInputEditText startDate;
    TextInputEditText endDate;
    Button postJob;
    TextInputEditText jobTitle;
    TextInputEditText jobDescription;
    Button domain;
    ChipGroup skillsChipGroup;
    List<Skill> skillsList;
    List<Domain> domainList;
    AutoCompleteTextView skills;
    // We will the store the skills to submit here
    ArrayList<Skill> applicationSkills = new ArrayList<>();
    ArrayList<Domain> domains = new ArrayList<>();
    ArrayList<String> domainNames = new ArrayList<>();
    int domainPosition = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Add Job Offer");
        return inflater.inflate(R.layout.fragment_job_create, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Fetch skills
        getSkills();
        getDomains();
        View v = getView();
        startDate = v.findViewById(R.id.startDateFld);
        endDate = v.findViewById(R.id.endDateFld);
        domain = v.findViewById(R.id.btnDomain);
        skills = v.findViewById(R.id.actvSkills);
        skillsChipGroup = v.findViewById(R.id.skillsChipGroup);
        postJob = v.findViewById(R.id.postJobBtn);
        jobTitle = v.findViewById(R.id.titleFld);
        jobDescription = v.findViewById(R.id.descFld);

        DatePickerUniversal startDateDPU = new DatePickerUniversal(startDate, "yyyy-MM-dd", 0);
        DatePickerUniversal endDateDPU = new DatePickerUniversal(endDate, "yyyy-MM-dd", 1);

        domain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence[] domainsName = domainNames.toArray(new CharSequence[domainNames.size()]);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

        postJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobOffer jobOffer = new JobOffer(jobTitle.getText().toString(), domains.get(domainPosition).getDomainName(), jobDescription.getText().toString(),
                        startDate.getText().toString(), startDate.getText().toString(), applicationSkills);
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
                ArrayAdapter<Skill> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, skillsList);
                // Add the adapter to actv
                skills.setAdapter(arrayAdapter);
                // Add to the ChipGroup
                skills.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Chip skillChip = new Chip(getActivity());
                        Skill skill = (Skill) parent.getItemAtPosition(position);
                        skillChip.setText(skill.toString());
                        skillChip.setCloseIconVisible(true);
                        skillChip.setOnCloseIconClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                skillsChipGroup.removeView(view);
                                // Need to handle removal from applicationSkills arrayList
                                Iterator<Skill> iterator = applicationSkills.iterator();
                                while (iterator.hasNext()) {
                                    if (iterator.next().toString().toLowerCase().equals(skillChip.getText().toString().toLowerCase())) {
                                        iterator.remove();
                                        break;
                                    }
                                }
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
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
                domainList = response.body();
                domainList.forEach(domain -> {
                    System.out.println(domain.getDomainName());
                    domainNames.add(domain.getDomainName());
                    domains.add(domain);
                });
            }


            @Override
            public void onFailure(Call<List<Domain>> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addJobOfferRequest(JobOffer jobOffer) {
        // Usual drill..
        Retrofit.Builder retroBuilder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/api/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = retroBuilder.build();
        JobService jobService = retrofit.create(JobService.class);
        Call<JobOffer> call = jobService.createJob("Bearer " + TokenService.getToken(), jobOffer);
        // FOR TESTING PURPOSES
        String gson = new Gson().toJson(jobOffer);
        Log.d("Rec", gson);
        call.enqueue(new Callback<JobOffer>() {
            @Override
            public void onResponse(Call<JobOffer> call, Response<JobOffer> response) {
                Toast.makeText(getActivity(), "Job Offer Posted !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JobOffer> call, Throwable t) {
                Toast.makeText(getActivity(), "Unexpected error !", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}

