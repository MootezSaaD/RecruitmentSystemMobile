package tn.medtech.recruitmentsystemapp.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Recruiter extends User implements Serializable {
    @SerializedName("company")
    @Expose
    private Company company;

    public Recruiter(String firstName, String lastName, String email, Company company, String... vars) {
        super("Recruiter", firstName, lastName, email, vars);
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
