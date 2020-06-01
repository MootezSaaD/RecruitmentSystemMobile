package tn.medtech.recruitmentsystemapp.ui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.api.models.Applicant;
import tn.medtech.recruitmentsystemapp.api.models.JobOffer;

public class ApplicantJobAdapter extends RecyclerView.Adapter<ApplicantJobAdapter.ApplicantJobViewHolder> {
    private ArrayList<JobOffer> list;



    public static class ApplicantJobViewHolder extends RecyclerView.ViewHolder {
        public TextView jobTitleTextView;
        public TextView jobCompanyTextView;
        public TextView jobDescriptionTextView;
        public TextView jobDomainTextView;
        public TextView jobSkillsTextView;
        public TextView jobEndDateTextView;

        public ApplicantJobViewHolder (@NonNull View itemView) {
            super(itemView);
            jobTitleTextView = itemView.findViewById(R.id.appJobTitle);
            jobCompanyTextView = itemView.findViewById(R.id.appJobCompany);
            jobDescriptionTextView = itemView.findViewById(R.id.appJobDescription);
            jobDomainTextView = itemView.findViewById(R.id.appJobDomain);
            jobSkillsTextView = itemView.findViewById(R.id.appJobSkills);
            jobEndDateTextView = itemView.findViewById(R.id.appJobEndDate);


        }

    }

    public ApplicantJobAdapter(ArrayList<JobOffer> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ApplicantJobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.applicant_job_card, parent, false);
        ApplicantJobViewHolder ajvh = new ApplicantJobViewHolder(v);
        return ajvh;
    }

    // This binds the values to the view
    @Override
    public void onBindViewHolder(@NonNull ApplicantJobViewHolder holder, int position) {
        JobOffer currentItem = this.list.get(position);
        holder.jobTitleTextView.setText(currentItem.getTitle());
        holder.jobCompanyTextView.setText(currentItem.getCompany());
        holder.jobDescriptionTextView.setText(currentItem.getDescription());
        holder.jobDomainTextView.setText(currentItem.getDomain());
        holder.jobSkillsTextView.setText(currentItem.getSkills().toString());
        holder.jobEndDateTextView.setText(currentItem.getEndDate());

    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }
}
