package tn.medtech.recruitmentsystemapp.ui.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.api.models.JobOffer;
import tn.medtech.recruitmentsystemapp.ui.RecruiterDashboard.RecruiterJobOfferDetailsActivity;

public class RecruiterJobItemAdapter extends RecyclerView.Adapter<RecruiterJobItemAdapter.RecruiterJobViewHolder> {
    private ArrayList<JobOffer> list;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public RecruiterJobItemAdapter(ArrayList<JobOffer> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecruiterJobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recruiter_job_card, parent, false);
        RecruiterJobViewHolder rjvh = new RecruiterJobViewHolder(v, onItemClickListener);
        return rjvh;
    }

    // This binds the values to the view
    @Override
    public void onBindViewHolder(@NonNull RecruiterJobViewHolder holder, int position) {
        JobOffer currentItem = this.list.get(position);
        holder.jobTitleTextView.setText(currentItem.getTitle());
        holder.jobCompanyTextView.setText(currentItem.getCompany());
        holder.viewJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jobOffer = new Intent(v.getContext(), RecruiterJobOfferDetailsActivity.class);
                jobOffer.putExtra("jobOfferObject", new Gson().toJson(currentItem));
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

    public static class RecruiterJobViewHolder extends RecyclerView.ViewHolder {
        public TextView jobTitleTextView;
        public TextView jobCompanyTextView;
        public Button viewJobButton;
        public Button deleteJobButton;

        public RecruiterJobViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            jobTitleTextView = itemView.findViewById(R.id.recJobOfferTitle);
            jobCompanyTextView = itemView.findViewById(R.id.recJobOfferCompany);
            viewJobButton = itemView.findViewById(R.id.recJobViewBtn);
            deleteJobButton = itemView.findViewById(R.id.recJobDeleteBtn);

            deleteJobButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });



        }
    }
}
