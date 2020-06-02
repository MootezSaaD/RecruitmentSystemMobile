package tn.medtech.recruitmentsystemapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.api.models.Application;
import tn.medtech.recruitmentsystemapp.api.models.JobOffer;

public class ApplicantApplicationAdapter extends RecyclerView.Adapter<ApplicantApplicationAdapter.ApplicantApplicationViewHolder> {
    private ArrayList<Application> list;

    public static class ApplicantApplicationViewHolder extends RecyclerView.ViewHolder {
        public TextView jobTitleTextView;
        public TextView jobCompanyTextView;
        public Chip chipStatus;

        public ApplicantApplicationViewHolder(@NonNull View itemView){
            super(itemView);
            jobTitleTextView = itemView.findViewById(R.id.appJobTitle);
            jobCompanyTextView = itemView.findViewById(R.id.appJobCompany);
            chipStatus = itemView.findViewById(R.id.applicationStatusChip);
        }
    }

    public ApplicantApplicationAdapter(ArrayList<Application> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ApplicantApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.applicant_application_card, parent, false);
        ApplicantApplicationViewHolder aavh = new ApplicantApplicationViewHolder(v);
        return aavh;
    }

    // This binds the values to the view
    @Override
    public void onBindViewHolder(@NonNull ApplicantApplicationViewHolder holder, int position) {
        Application currentItem = this.list.get(position);
        holder.jobTitleTextView.setText(currentItem.getTitle());
        holder.jobCompanyTextView.setText(currentItem.getCompany());
        holder.chipStatus.setText(currentItem.getStatus());

    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }
}
