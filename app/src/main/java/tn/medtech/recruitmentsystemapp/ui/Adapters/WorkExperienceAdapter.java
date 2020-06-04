package tn.medtech.recruitmentsystemapp.ui.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.api.models.WorkExperience;
import tn.medtech.recruitmentsystemapp.util.DateParser;

public class WorkExperienceAdapter extends RecyclerView.Adapter<WorkExperienceAdapter.WorkExperienceViewHolder> {
    private ArrayList<WorkExperience> list;

    public WorkExperienceAdapter(ArrayList<WorkExperience> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public WorkExperienceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.applicant_work_experience_card, parent, false);
        WorkExperienceViewHolder wevh = new WorkExperienceViewHolder(v);
        return wevh;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkExperienceViewHolder holder, int position) {
        WorkExperience workExperience = this.list.get(position);
        holder.workExpTitle.setText(workExperience.getTitle());
        holder.workExpCompany.setText(workExperience.getCompanyName());
        holder.workExpDomain.setText(workExperience.getDomain());
        holder.workExpStartDate.setText(DateParser.parseDate(workExperience.getStartDate()));
        holder.workExpEndDate.setText(DateParser.parseDate(workExperience.getStartDate()));
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public static class WorkExperienceViewHolder extends RecyclerView.ViewHolder {
        public TextView workExpTitle;
        public TextView workExpCompany;
        public TextView workExpDomain;
        public TextView workExpStartDate;
        public TextView workExpEndDate;


        public WorkExperienceViewHolder(@NonNull View itemView) {
            super(itemView);
            workExpTitle = itemView.findViewById(R.id.wexpJobTitle);
            workExpCompany = itemView.findViewById(R.id.wexpJobCompany);
            workExpDomain = itemView.findViewById(R.id.wexpDomain);
            workExpStartDate = itemView.findViewById(R.id.wexpStartDate);
            workExpEndDate = itemView.findViewById(R.id.wexpEndDate);
        }
    }
}
