package tn.medtech.recruitmentsystemapp.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class JobApplication {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("domain")
    @Expose
    private Domain domain;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("startDate")
    @Expose
    private Date startDate;
    @SerializedName("endDate")
    @Expose
    private Date endDate;
    @SerializedName("skills")
    @Expose
    private ArrayList<Skill> skills = new ArrayList<>();

    public JobApplication(String title, Domain domain, String description, Date startDate, Date endDate, ArrayList<Skill> skills) {
        this.title = title;
        this.domain = domain;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.skills = skills;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }
}
