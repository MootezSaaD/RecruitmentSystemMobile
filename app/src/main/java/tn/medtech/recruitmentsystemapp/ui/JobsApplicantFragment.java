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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.api.models.JobOffer;
import tn.medtech.recruitmentsystemapp.api.models.Skill;
import tn.medtech.recruitmentsystemapp.api.services.JobService;
import tn.medtech.recruitmentsystemapp.api.services.ServiceGenerator;
import tn.medtech.recruitmentsystemapp.util.TokenService;

public class JobsApplicantFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<JobOffer> jobList;
    private JobsRepository jobsRepository = JobsRepository.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Jobs List");
        getJobs();
        return inflater.inflate(R.layout.fragment_jobs_applicant, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        jobList = new ArrayList<>(jobsRepository.findAll());
        recyclerView = getView().findViewById(R.id.jobsApplicantRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new ApplicantJobAdapter(jobList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        if(!jobsRepository.isLoaded()) {
            getJobs();
        }
    }

    private void getJobs() {
        JobService jobService = ServiceGenerator.createService(JobService.class);
        Call<List<JobOffer>> call = jobService.getJobs("Bearer " + TokenService.getToken());
        call.enqueue(new Callback<List<JobOffer>>() {
            @Override
            public void onResponse(Call<List<JobOffer>> call, Response<List<JobOffer>> response) {
                jobsRepository.addAll(response.body());
                jobList = new ArrayList<>(response.body());
                adapter = new ApplicantJobAdapter(jobList);
                recyclerView.setAdapter(adapter);
                Toast.makeText(getActivity(), "Jobs loaded !", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<JobOffer>> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
