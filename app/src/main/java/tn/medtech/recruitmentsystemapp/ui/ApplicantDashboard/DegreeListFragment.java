package tn.medtech.recruitmentsystemapp.ui.ApplicantDashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
import tn.medtech.recruitmentsystemapp.api.models.Degree;
import tn.medtech.recruitmentsystemapp.api.services.DegreeService;
import tn.medtech.recruitmentsystemapp.api.services.ServiceGenerator;
import tn.medtech.recruitmentsystemapp.ui.Adapters.ApplicantDegreeAdapter;
import tn.medtech.recruitmentsystemapp.util.TokenService;

public class DegreeListFragment extends Fragment {
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private FloatingActionButton addDegreeBtn;
    ArrayList<Degree> degreeList = new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Degrees List");
        return inflater.inflate(R.layout.fragment_list_degree, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        addDegreeBtn = getView().findViewById(R.id.degreeAddFAB);
        recyclerView = getView().findViewById(R.id.degreesRecyclerView);
        swipeContainer = getView().findViewById(R.id.degreesContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDegrees();
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        getDegrees();

        addDegreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ApplicantAddDegree.class));
            }
        });
    }

    private void getDegrees() {
        swipeContainer.setRefreshing(true);
        DegreeService degreeService = ServiceGenerator.createService(DegreeService.class);
        Call<List<Degree>> call = degreeService.getDegrees("Bearer " + TokenService.getToken());
        call.enqueue(new Callback<List<Degree>>() {
            @Override
            public void onResponse(Call<List<Degree>> call, Response<List<Degree>> response) {
                swipeContainer.setRefreshing(false);
                degreeList = new ArrayList<>(response.body());
                adapter = new ApplicantDegreeAdapter(degreeList);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Degree>> call, Throwable t) {
                swipeContainer.setRefreshing(false);
                Toast.makeText(getActivity(), "Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
