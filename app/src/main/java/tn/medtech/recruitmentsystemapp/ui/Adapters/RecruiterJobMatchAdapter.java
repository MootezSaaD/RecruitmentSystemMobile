package tn.medtech.recruitmentsystemapp.ui.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.api.models.SelectedApplicant;
import tn.medtech.recruitmentsystemapp.util.DateParser;

public class RecruiterJobMatchAdapter extends RecyclerView.Adapter<RecruiterJobMatchAdapter.JobMatchViewHolder> {
    private ArrayList<SelectedApplicant> list;

    public RecruiterJobMatchAdapter(ArrayList<SelectedApplicant> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public JobMatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_match_card, parent, false);
        JobMatchViewHolder jmvh = new JobMatchViewHolder(v);
        return jmvh;
    }

    @Override
    public void onBindViewHolder(@NonNull JobMatchViewHolder holder, int position) {
        SelectedApplicant selectedApplicant = this.list.get(position);
        holder.jobMatchTitle.setText(selectedApplicant.getJobTitle());
        holder.jobMatchDescription.setText(selectedApplicant.getJobDescription());
        holder.jobMatchStartDate.setText(DateParser.parseDate(selectedApplicant.getJobStartDate()));
        holder.jobMatchEndDate.setText(DateParser.parseDate(selectedApplicant.getJobEndDate()));
        holder.jobMatchFirstName.setText(DateParser.parseDate(selectedApplicant.getFirstName()));
        holder.jobMatchLastName.setText(selectedApplicant.getLastName());
        holder.jobMatchEmail.setText(selectedApplicant.getEmail());
        holder.jobMatchPhoneNumber.setText(selectedApplicant.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public static class JobMatchViewHolder extends RecyclerView.ViewHolder {
        public TextView jobMatchTitle;
        public TextView jobMatchDescription;
        public TextView jobMatchStartDate;
        public TextView jobMatchEndDate;
        public TextView jobMatchFirstName;
        public TextView jobMatchLastName;
        public TextView jobMatchEmail;
        public TextView jobMatchPhoneNumber;


        public JobMatchViewHolder(@NonNull View itemView) {
            super(itemView);
            jobMatchTitle = itemView.findViewById(R.id.jobMatchTitle);
            jobMatchDescription = itemView.findViewById(R.id.jobMatchDescription);
            jobMatchStartDate = itemView.findViewById(R.id.jobMatchStartDate);
            jobMatchEndDate = itemView.findViewById(R.id.jobMatchEndDate);
            jobMatchFirstName = itemView.findViewById(R.id.jobMatchFirstName);
            jobMatchLastName = itemView.findViewById(R.id.jobMatchLastName);
            jobMatchEmail = itemView.findViewById(R.id.jobMatchEmail);
            jobMatchPhoneNumber = itemView.findViewById(R.id.jobMatchPhoneNumber);

        }
    }
}
