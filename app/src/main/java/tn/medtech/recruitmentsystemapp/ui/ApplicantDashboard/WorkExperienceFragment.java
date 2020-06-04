package tn.medtech.recruitmentsystemapp.ui.ApplicantDashboard;

import android.content.Intent;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.api.models.WorkExperience;
import tn.medtech.recruitmentsystemapp.api.services.ServiceGenerator;
import tn.medtech.recruitmentsystemapp.api.services.WorkExperienceService;
import tn.medtech.recruitmentsystemapp.ui.Adapters.WorkExperienceAdapter;
import tn.medtech.recruitmentsystemapp.util.TokenService;

import static android.content.Context.MODE_PRIVATE;

public class WorkExperienceFragment extends Fragment {
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<WorkExperience> workexpList;
    private FloatingActionButton addExperience;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Work Experience");
        return inflater.inflate(R.layout.applicant_list_work_experience, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addExperience = getView().findViewById(R.id.wexpListFAB);
        recyclerView = getView().findViewById(R.id.wexpListJobsRecyclerView);
        swipeContainer = getView().findViewById(R.id.swipeApplicantWorExpList);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getWorkExperiences();
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        getWorkExperiences();
        addExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ApplicantAddWorkExperience.class));
            }
        });

    }

    public void getWorkExperiences() {
        swipeContainer.setRefreshing(true);
        TokenService tokenService = TokenService.getInstance(this.getActivity().getSharedPreferences("prefs", MODE_PRIVATE));
        WorkExperienceService workExperienceService = ServiceGenerator.createServiceWithAuth(WorkExperienceService.class, tokenService);
        Call<List<WorkExperience>> call = workExperienceService.getWorkExperiences();
        call.enqueue(new Callback<List<WorkExperience>>() {
            @Override
            public void onResponse(Call<List<WorkExperience>> call, Response<List<WorkExperience>> response) {
                swipeContainer.setRefreshing(false);
                workexpList = new ArrayList<>(response.body());
                adapter = new WorkExperienceAdapter(workexpList);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                Toast.makeText(getActivity(), "Work Experiences Loaded !", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<List<WorkExperience>> call, Throwable t) {
                swipeContainer.setRefreshing(false);
                Toast.makeText(getActivity(), "Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
