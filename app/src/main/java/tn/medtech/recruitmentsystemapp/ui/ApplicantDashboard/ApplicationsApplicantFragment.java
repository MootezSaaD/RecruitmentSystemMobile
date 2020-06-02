package tn.medtech.recruitmentsystemapp.ui.ApplicantDashboard;

import android.os.Bundle;
import android.util.Log;
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
import tn.medtech.recruitmentsystemapp.api.models.Application;
import tn.medtech.recruitmentsystemapp.api.services.JobService;
import tn.medtech.recruitmentsystemapp.api.services.ServiceGenerator;
import tn.medtech.recruitmentsystemapp.ui.Adapters.ApplicantApplicationAdapter;
import tn.medtech.recruitmentsystemapp.util.TokenService;

public class ApplicationsApplicantFragment extends Fragment {
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<Application> applicationList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("My Applications");
        return inflater.inflate(R.layout.fragment_applications_applicant, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getView().findViewById(R.id.applicationsApplicantRecyclerView);
        swipeContainer = getView().findViewById(R.id.swipeApplicantApplicationsContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMyApplications();
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        getMyApplications();
    }

    private void getMyApplications() {
        swipeContainer.setRefreshing(true);
        JobService jobService = ServiceGenerator.createService(JobService.class);
        Call<List<Application>> call = jobService.getApplications("Bearer " + TokenService.getToken());
        call.enqueue(new Callback<List<Application>>() {
            @Override
            public void onResponse(Call<List<Application>> call, Response<List<Application>> response) {
                swipeContainer.setRefreshing(false);
                applicationList = new ArrayList<>(response.body());
                Log.d("AppList", applicationList.toString());
                adapter = new ApplicantApplicationAdapter(applicationList);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                Toast.makeText(getActivity(), "Applications Loaded !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Application>> call, Throwable t) {
                swipeContainer.setRefreshing(false);
                Toast.makeText(getActivity(), "Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
