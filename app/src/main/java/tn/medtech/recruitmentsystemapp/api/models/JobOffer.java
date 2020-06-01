package tn.medtech.recruitmentsystemapp.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class JobOffer {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("domain")
    @Expose
    private String domain;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("skills")
    @Expose
    private ArrayList<Skill> skills = new ArrayList<>();
    @SerializedName("Company")
    private String company;

    public JobOffer(String title, String domain, String description, String startDate, String endDate, ArrayList<Skill> skills) {
        this.title = title;
        this.domain = domain;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.skills = skills;
    }

    public JobOffer(String title, String domain, String description, String company,String startDate, String endDate, ArrayList<Skill> skills) {
        this.title = title;
        this.domain = domain;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.skills = skills;
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
