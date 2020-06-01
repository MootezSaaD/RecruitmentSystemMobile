package tn.medtech.recruitmentsystemapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tn.medtech.recruitmentsystemapp.R;

public class RecruiterJobItemAdapter extends RecyclerView.Adapter<RecruiterJobItemAdapter.RecruiterJobViewHolder> {
    private ArrayList<RecruiterJobItem> list;

    public static class RecruiterJobViewHolder extends RecyclerView.ViewHolder {
        public TextView jobTitleTextView;
        public TextView jobCompanyTextView;

        public RecruiterJobViewHolder(@NonNull View itemView) {
            super(itemView);
            jobTitleTextView = itemView.findViewById(R.id.recJobOfferTitle);
            jobCompanyTextView = itemView.findViewById(R.id.recJobOfferCompany);
        }
    }

    public RecruiterJobItemAdapter(ArrayList<RecruiterJobItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecruiterJobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recruiter_job_card, parent, false);
        RecruiterJobViewHolder rjvh = new RecruiterJobViewHolder(v);
        return rjvh;
    }

    // This binds the values to the view
    @Override
    public void onBindViewHolder(@NonNull RecruiterJobViewHolder holder, int position) {
        RecruiterJobItem currentItem = this.list.get(position);
        holder.jobTitleTextView.setText(currentItem.getJobTitle());
        holder.jobCompanyTextView.setText(currentItem.getJobCompany());
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }
}
