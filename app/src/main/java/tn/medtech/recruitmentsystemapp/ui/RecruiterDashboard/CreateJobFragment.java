package tn.medtech.recruitmentsystemapp.ui.RecruiterDashboard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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
import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.api.models.Domain;
import tn.medtech.recruitmentsystemapp.api.models.JobOffer;
import tn.medtech.recruitmentsystemapp.api.models.Skill;
import tn.medtech.recruitmentsystemapp.api.services.DomainService;
import tn.medtech.recruitmentsystemapp.api.services.JobService;
import tn.medtech.recruitmentsystemapp.api.services.ServiceGenerator;
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
    ChipGroup requiredSkillsChipGroup;
    ChipGroup optionalSkillsChipGroup;
    List<Skill> skillsList;
    List<Domain> domainList;
    AutoCompleteTextView requiredSkills;
    AutoCompleteTextView optionalSkills;
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
        requiredSkills = v.findViewById(R.id.actvRequiredSkills);
        optionalSkills = v.findViewById(R.id.actvOptionalSkills);
        requiredSkillsChipGroup = v.findViewById(R.id.requiredSkillsChipGroup);
        optionalSkillsChipGroup = v.findViewById(R.id.optionalSkillsChipGroup);
        postJob = v.findViewById(R.id.postJobBtn);
        jobTitle = v.findViewById(R.id.titleFld);
        jobDescription = v.findViewById(R.id.descFld);

        DatePickerUniversal startDateDPU = new DatePickerUniversal(startDate, "yyyy-MM-dd", 1);
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
        TokenService tokenService = TokenService.getInstance(this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE));
        SkillService skillService = ServiceGenerator.createServiceWithAuth(SkillService.class, tokenService);
        Call<List<Skill>> call = skillService.getSkills();
        call.enqueue(new Callback<List<Skill>>() {

            @Override
            public void onResponse(Call<List<Skill>> call, Response<List<Skill>> response) {
                skillsList = response.body();
                System.out.println(skillsList.size());
                skillsList.forEach(skill -> System.out.println(skill.getSkillName() + " Type: " + skill.getSkillType()));
                // Create the array adapter
                ArrayAdapter<Skill> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, skillsList);
                // Add the adapter to both actv
                requiredSkills.setAdapter(arrayAdapter);
                optionalSkills.setAdapter(arrayAdapter);
                // Add to the ChipGroup
                requiredSkills.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Chip skillChip = new Chip(getActivity());
                        Skill skill = (Skill) parent.getItemAtPosition(position);
                        skill.setSkillType("Required");
                        skillChip.setText(skill.toString());
                        skillChip.setCloseIconVisible(true);
                        skillChip.setOnCloseIconClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                requiredSkillsChipGroup.removeView(view);
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
                        requiredSkillsChipGroup.addView(skillChip);
                        // Adding skills to the job offer's skill arrayList
                        applicationSkills.add(skill);
                        // Clear the actv
                        requiredSkills.setText("");
                    }
                });

                optionalSkills.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Chip skillChip = new Chip(getActivity());
                        Skill skill = (Skill) parent.getItemAtPosition(position);
                        skill.setSkillType("Optional");
                        skillChip.setText(skill.toString());
                        skillChip.setCloseIconVisible(true);
                        skillChip.setOnCloseIconClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                optionalSkillsChipGroup.removeView(view);
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
                        optionalSkillsChipGroup.addView(skillChip);
                        // Adding skills to the job offer's skill arrayList
                        applicationSkills.add(skill);
                        // Clear the actv
                        optionalSkills.setText("");
                    }
                });
            }


            @Override
            public void onFailure(Call<List<Skill>> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong... Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDomains() {
        TokenService tokenService = TokenService.getInstance(this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE));
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
                Toast.makeText(getActivity(), "Something went wrong... Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addJobOfferRequest(JobOffer jobOffer) {
        TokenService tokenService = TokenService.getInstance(this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE));
        JobService jobService = ServiceGenerator.createServiceWithAuth(JobService.class, tokenService);

        Call<JobOffer> call = jobService.createJob(jobOffer);
        // FOR TESTING PURPOSES
        String gson = new Gson().toJson(jobOffer);
        call.enqueue(new Callback<JobOffer>() {
            @Override
            public void onResponse(Call<JobOffer> call, Response<JobOffer> response) {
                Toast.makeText(getActivity(), "Job offer posted!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JobOffer> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong... Please try later!", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}

