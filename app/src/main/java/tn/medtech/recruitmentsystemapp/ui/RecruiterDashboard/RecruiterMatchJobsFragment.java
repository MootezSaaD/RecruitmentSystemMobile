package tn.medtech.recruitmentsystemapp.ui.RecruiterDashboard;


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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.api.models.SelectedApplicant;
import tn.medtech.recruitmentsystemapp.api.services.JobService;
import tn.medtech.recruitmentsystemapp.api.services.ServiceGenerator;
import tn.medtech.recruitmentsystemapp.ui.Adapters.RecruiterJobMatchAdapter;
import tn.medtech.recruitmentsystemapp.util.TokenService;

public class RecruiterMatchJobsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipeContainer;
    private ArrayList<SelectedApplicant> selectedApplicantsList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Match Jobs");
        return inflater.inflate(R.layout.fragment_match_jobs, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getView().findViewById(R.id.recJobMatchView);
        swipeContainer = getView().findViewById(R.id.swipeRecruiterJobMatchContainer);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMatchedJobs();
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        getMatchedJobs();
    }

    public void getMatchedJobs() {
        swipeContainer.setRefreshing(true);
        JobService jobService = ServiceGenerator.createService(JobService.class);
        Call<List<SelectedApplicant>> call = jobService.getMatchedJobs("Bearer " + TokenService.getToken());
        call.enqueue(new Callback<List<SelectedApplicant>>() {
            @Override
            public void onResponse(Call<List<SelectedApplicant>> call, Response<List<SelectedApplicant>> response) {
                swipeContainer.setRefreshing(false);
                selectedApplicantsList = new ArrayList<>(response.body());
                adapter = new RecruiterJobMatchAdapter(selectedApplicantsList);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                Toast.makeText(getActivity(), "Jobs Matched!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<SelectedApplicant>> call, Throwable t) {
                swipeContainer.setRefreshing(false);
                Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
