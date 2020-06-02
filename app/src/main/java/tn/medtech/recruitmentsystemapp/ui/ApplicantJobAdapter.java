package tn.medtech.recruitmentsystemapp.ui;

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
import tn.medtech.recruitmentsystemapp.api.models.Applicant;
import tn.medtech.recruitmentsystemapp.api.models.JobOffer;

public class ApplicantJobAdapter extends RecyclerView.Adapter<ApplicantJobAdapter.ApplicantJobViewHolder> {
    private ArrayList<JobOffer> list;



    public static class ApplicantJobViewHolder extends RecyclerView.ViewHolder {
        public TextView jobTitleTextView;
        public TextView jobCompanyTextView;
        public TextView jobEndDateTextView;
        public Button viewJobButton;

        public ApplicantJobViewHolder (@NonNull View itemView) {
            super(itemView);
            jobTitleTextView = itemView.findViewById(R.id.appJobTitle);
            jobCompanyTextView = itemView.findViewById(R.id.appJobCompany);
            jobEndDateTextView = itemView.findViewById(R.id.appJobEndDate);
            viewJobButton = itemView.findViewById(R.id.jobDetailsBtn);
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
        holder.jobEndDateTextView.setText(currentItem.getEndDate());

        holder.viewJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jobOffer = new Intent(v.getContext(), ApplicantJobDetailActivity.class);
                jobOffer.putExtra("jobDetailsObject", new Gson().toJson(currentItem));
                v.getContext().startActivity(jobOffer);
            }
        });
    }

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
