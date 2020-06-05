package tn.medtech.recruitmentsystemapp.ui.RecruiterDashboard;

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
import tn.medtech.recruitmentsystemapp.ui.Adapters.RecruiterJobItemAdapter;
import tn.medtech.recruitmentsystemapp.ui.JobsRepository;
import tn.medtech.recruitmentsystemapp.util.TokenService;

import static android.content.Context.MODE_PRIVATE;

public class ListRecruiterJobsFragment extends Fragment {
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerView;
    private RecruiterJobItemAdapter adapter;
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
        swipeContainer = getView().findViewById(R.id.swipeRecruiterJobsContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllJobs();
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
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
        swipeContainer.setRefreshing(true);
        TokenService tokenService = TokenService.getInstance(this.getActivity().getSharedPreferences("prefs", MODE_PRIVATE));
        JobService jobService = ServiceGenerator.createServiceWithAuth(JobService.class, tokenService);
        Call<List<JobOffer>> call = jobService.getJobs();

        call.enqueue(new Callback<List<JobOffer>>() {
            @Override
            public void onResponse(Call<List<JobOffer>> call, Response<List<JobOffer>> response) {
                swipeContainer.setRefreshing(false);
                jobsRepository.addAll(response.body());
                jobOffers = new ArrayList<>(response.body());
                adapter = new RecruiterJobItemAdapter(jobOffers);
                adapter.setOnItemClickListener(new RecruiterJobItemAdapter.OnItemClickListener() {
                    @Override
                    public void onDeleteClick(int position) {
                        JobOffer deleteJobOffer = jobOffers.get(position);
                        removeItem(position);
                        // Remove it from the database
                        removeJobOffer(deleteJobOffer.getId());
                    }
                });
                recyclerView.setAdapter(adapter);

                if (jobOffers.size() > 0) {
                    Toast.makeText(getActivity(), "Jobs loaded !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "No Jobs Found !", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<List<JobOffer>> call, Throwable t) {
                swipeContainer.setRefreshing(false);
                Toast.makeText(getActivity(), "Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void removeItem(int position) {
        jobOffers.remove(position);
        adapter.notifyItemRemoved(position);
    }

    public void removeJobOffer(int jobOfferID) {
        TokenService tokenService = TokenService.getInstance(this.getActivity().getSharedPreferences("prefs", MODE_PRIVATE));
        JobService jobService = ServiceGenerator.createServiceWithAuth(JobService.class, tokenService);
        Call<tn.medtech.recruitmentsystemapp.api.models.Response> call = jobService.deleteJob(jobOfferID);
        call.enqueue(new Callback<tn.medtech.recruitmentsystemapp.api.models.Response>() {
            @Override
            public void onResponse(Call<tn.medtech.recruitmentsystemapp.api.models.Response> call, Response<tn.medtech.recruitmentsystemapp.api.models.Response> response) {
                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<tn.medtech.recruitmentsystemapp.api.models.Response> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong !", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
