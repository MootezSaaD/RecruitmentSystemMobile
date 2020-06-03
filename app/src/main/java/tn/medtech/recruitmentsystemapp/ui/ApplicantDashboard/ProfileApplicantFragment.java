package tn.medtech.recruitmentsystemapp.ui.ApplicantDashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.api.models.Skill;
import tn.medtech.recruitmentsystemapp.api.services.ServiceGenerator;
import tn.medtech.recruitmentsystemapp.api.services.SkillService;
import tn.medtech.recruitmentsystemapp.util.TokenService;

public class ProfileApplicantFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    List<Skill> skillsList;
    ArrayList<Skill> applicantSkills;
    List<Skill> addedSkills;

    TextView name;
    TextView phoneNumber;
    AutoCompleteTextView skill1;
    ChipGroup applicantSkillsChipGroup;
    Button updateProfileBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("General");
        return inflater.inflate(R.layout.fragment_profile_applicant, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getApplicantSkills();


        getSkills();
        addedSkills = new ArrayList<>();

        View v = getView();
        name = v.findViewById(R.id.applicantNameFld);
        phoneNumber = v.findViewById(R.id.applicantPhoneFld);
        skill1 = v.findViewById(R.id.profileSkill1);
        updateProfileBtn = v.findViewById(R.id.updateProfileBtn);
        applicantSkillsChipGroup = v.findViewById(R.id.applicantSkillsChipGroup);

        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSkills(addedSkills);
            }
        });
    }

    private void getSkills() {
        SkillService skillService = ServiceGenerator.createService(SkillService.class);
        Call<List<Skill>> call = skillService.getSkills("Bearer " + TokenService.getToken());
        call.enqueue(new Callback<List<Skill>>() {
            @Override
            public void onResponse(Call<List<Skill>> call, Response<List<Skill>> response) {
                skillsList = response.body();
                skillsList.forEach(skill -> System.out.println(skill.getSkillName() + " Type: " + skill.getSkillType()));
                // Create the array adapter
                ArrayAdapter<Skill> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, skillsList);
                // Add the adapter to actv
                skill1.setAdapter(arrayAdapter);
                skill1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Chip skillChip = new Chip(getActivity());
                        Skill skill = (Skill) parent.getItemAtPosition(position);
                        skillChip.setText(skill.toString());
                        skillChip.setCloseIconVisible(true);
                        skillChip.setOnCloseIconClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                applicantSkillsChipGroup.removeView(view);
                            }
                        });
                        applicantSkillsChipGroup.addView(skillChip);
                        // Adding skills to the job offer's skill arrayList
                        Skill skill2 = new Skill(skill.toString());
                        addedSkills.add(skill2);
                        // Clear the actv
                        skill1.setText("");
                    }
                });

            }

            @Override
            public void onFailure(Call<List<Skill>> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                Log.d("Throwable", "Exception", t);
            }
        });
    }

    private void getApplicantSkills() {
        SkillService skillService = ServiceGenerator.createService(SkillService.class);
        Call<List<Skill>> call = skillService.getApplicantSkills("Bearer " + TokenService.getToken());
        call.enqueue(new Callback<List<Skill>>() {
            @Override
            public void onResponse(Call<List<Skill>> call, Response<List<Skill>> response) {
                addedSkills = new ArrayList<>(response.body());
                addedSkills.forEach(skill -> {
                    Chip skillChip = new Chip(getActivity());
                    skillChip.setText(skill.toString());
                    skillChip.setCloseIconVisible(true);
                    skillChip.setOnCloseIconClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            applicantSkillsChipGroup.removeView(view);
                            Iterator<Skill> iterator = addedSkills.iterator();
                            while (iterator.hasNext()) {
                                if (iterator.next().toString().toLowerCase().equals(skillChip.getText().toString().toLowerCase())) {
                                    iterator.remove();
                                    break;
                                }
                            }
                        }
                    });
                    applicantSkillsChipGroup.addView(skillChip);
                });
            }

            @Override
            public void onFailure(Call<List<Skill>> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                Log.d("Throwable", "Exception", t);
            }
        });
    }

    public void addSkills(List<Skill> skills) {
        SkillService skillService = ServiceGenerator.createService(SkillService.class);
        System.out.println(addedSkills.toString());
        Call<tn.medtech.recruitmentsystemapp.api.models.Response> call = skillService.addSkills("Bearer " + TokenService.getToken(), addedSkills);
        call.enqueue(new Callback<tn.medtech.recruitmentsystemapp.api.models.Response>() {
            @Override
            public void onResponse(Call<tn.medtech.recruitmentsystemapp.api.models.Response> call, Response<tn.medtech.recruitmentsystemapp.api.models.Response> response) {
                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<tn.medtech.recruitmentsystemapp.api.models.Response> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
