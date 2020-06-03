package tn.medtech.recruitmentsystemapp.ui.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.api.models.Degree;
import tn.medtech.recruitmentsystemapp.api.models.JobOffer;
import tn.medtech.recruitmentsystemapp.ui.ApplicantDashboard.ApplicantJobDetailActivity;
import tn.medtech.recruitmentsystemapp.util.DateParser;

public class ApplicantDegreeAdapter extends RecyclerView.Adapter<ApplicantDegreeAdapter.ApplicantDegreeViewHolder> {

    private ArrayList<Degree> list;

    public static class ApplicantDegreeViewHolder extends RecyclerView.ViewHolder {
        public TextView degreeType;
        public TextView degreeDomain;

        public ApplicantDegreeViewHolder(@NonNull View itemView) {
            super(itemView);
            degreeType = itemView.findViewById(R.id.degreeType);
            degreeDomain = itemView.findViewById(R.id.degreeDomain);
        }

    }

    public ApplicantDegreeAdapter(ArrayList<Degree> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ApplicantDegreeAdapter.ApplicantDegreeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.applicant_degree_card, parent, false);
        ApplicantDegreeAdapter.ApplicantDegreeViewHolder advh = new ApplicantDegreeAdapter.ApplicantDegreeViewHolder(v);
        return advh;
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicantDegreeViewHolder holder, int position) {
        Degree currentDegree = this.list.get(position);
        holder.degreeType.setText(currentDegree.getType());
        holder.degreeDomain.setText(currentDegree.getDomain());
    }

    // This binds the values to the view
    @Override
    public int getItemCount() {
        return this.list.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<JobOffer> list) {
        list.addAll(list);
        notifyDataSetChanged();
    }
}
