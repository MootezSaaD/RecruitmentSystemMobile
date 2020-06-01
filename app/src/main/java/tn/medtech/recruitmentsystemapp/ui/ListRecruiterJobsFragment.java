package tn.medtech.recruitmentsystemapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.api.models.JobOffer;
import tn.medtech.recruitmentsystemapp.api.services.JobService;
import tn.medtech.recruitmentsystemapp.api.services.ServiceGenerator;
import tn.medtech.recruitmentsystemapp.util.TokenService;

public class ListRecruiterJobsFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<JobOffer> jobOffers;
    private JobsRepository jobsRepository = JobsRepository.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Job Offers List");
        return inflater.inflate(R.layout.fragment_list_recruiter_jobs, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        jobOffers = new ArrayList<>(jobsRepository.findAll());
        recyclerView = getView().findViewById(R.id.recListJobsRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new RecruiterJobItemAdapter(jobOffers);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        if (!jobsRepository.isLoaded()) {
            getAllJobs();
        }

    }

    public void getAllJobs() {
        JobService jobService = ServiceGenerator.createService(JobService.class);
        Call<List<JobOffer>> call = jobService.getJobs("Bearer " + TokenService.getToken());

        call.enqueue(new Callback<List<JobOffer>>() {
            @Override
            public void onResponse(Call<List<JobOffer>> call, Response<List<JobOffer>> response) {
                jobsRepository.addAll(response.body());
                jobOffers = new ArrayList<>(response.body());
                adapter = new RecruiterJobItemAdapter(jobOffers);
                recyclerView.setAdapter(adapter);
                Toast.makeText(getActivity(), "Jobs loaded !", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<List<JobOffer>> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
