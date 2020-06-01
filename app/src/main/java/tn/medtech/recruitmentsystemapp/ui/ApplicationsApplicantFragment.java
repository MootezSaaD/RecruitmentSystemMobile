package tn.medtech.recruitmentsystemapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.api.models.JobOffer;
import tn.medtech.recruitmentsystemapp.api.models.Skill;
import tn.medtech.recruitmentsystemapp.api.services.JobService;
import tn.medtech.recruitmentsystemapp.api.services.ServiceGenerator;
import tn.medtech.recruitmentsystemapp.util.TokenService;

public class ApplicationsApplicantFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<JobOffer> applicationList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("My Applications");
        return inflater.inflate(R.layout.fragment_applications_applicant, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<JobOffer> exampleList = new ArrayList<>();
        ArrayList<Skill> skills = new ArrayList<Skill>();
        skills.add(new Skill(0,"Pyhton2","required"));
        exampleList.add(new JobOffer("Senior Web Dev.", "Web Development", "Description", "Facebook", "nill", "nill", skills));
        exampleList.add(new JobOffer("Senior Web Dev.", "Web Development", "Description", "Facebook", "nill", "27-1-2020", skills));
        recyclerView = getView().findViewById(R.id.applicationsApplicantRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new ApplicantApplicationAdapter(exampleList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getMyApplications() {
        JobService jobService = ServiceGenerator.createService(JobService.class);
        Call<List<JobOffer>> call = jobService.getMyJobs("Bearer " + TokenService.getToken());
        call.enqueue(new Callback<List<JobOffer>>() {
            @Override
            public void onResponse(Call<List<JobOffer>> call, Response<List<JobOffer>> response) {
                applicationList = (ArrayList<JobOffer>) response.body();
                applicationList.forEach(domain -> {
                    System.out.println(domain.getTitle());
                });
            }
            @Override
            public void onFailure(Call<List<JobOffer>> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
