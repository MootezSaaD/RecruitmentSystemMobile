package tn.medtech.recruitmentsystemapp.ui.ApplicantDashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.api.models.JobOffer;
import tn.medtech.recruitmentsystemapp.api.services.JobService;
import tn.medtech.recruitmentsystemapp.api.services.ServiceGenerator;
import tn.medtech.recruitmentsystemapp.ui.Adapters.ApplicantJobAdapter;
import tn.medtech.recruitmentsystemapp.ui.JobsRepository;
import tn.medtech.recruitmentsystemapp.util.TokenService;

import static android.content.Context.MODE_PRIVATE;

public class JobsApplicantFragment extends Fragment {
    ArrayList<JobOffer> jobList;
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private JobsRepository jobsRepository = JobsRepository.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Jobs List");
        return inflater.inflate(R.layout.fragment_jobs_applicant, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        jobList = new ArrayList<>(jobsRepository.findAll());
        recyclerView = getView().findViewById(R.id.jobsApplicantRecyclerView);
        swipeContainer = getView().findViewById(R.id.swipeApplicantJobsContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getJobs();
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new ApplicantJobAdapter(jobList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        if (!jobsRepository.isLoaded()) {
            getJobs();
        }
    }

    private void getJobs() {
        swipeContainer.setRefreshing(true);
        TokenService tokenService = TokenService.getInstance(this.getActivity().getSharedPreferences("prefs", MODE_PRIVATE));
        JobService jobService = ServiceGenerator.createServiceWithAuth(JobService.class, tokenService);
        Call<List<JobOffer>> call = jobService.getAllJobs();
        call.enqueue(new Callback<List<JobOffer>>() {
            @Override
            public void onResponse(Call<List<JobOffer>> call, Response<List<JobOffer>> response) {
                swipeContainer.setRefreshing(false);
                jobsRepository.addAll(response.body());
                jobList = new ArrayList<>(response.body());
                adapter = new ApplicantJobAdapter(jobList);
                recyclerView.setAdapter(adapter);
                Toast.makeText(getActivity(), "Jobs loaded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<JobOffer>> call, Throwable t) {
                swipeContainer.setRefreshing(false);
                Toast.makeText(getActivity(), "Something went wrong... Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
