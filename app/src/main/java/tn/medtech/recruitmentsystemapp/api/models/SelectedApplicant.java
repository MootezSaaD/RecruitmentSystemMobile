package tn.medtech.recruitmentsystemapp.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SelectedApplicant {
    @SerializedName("firstName")
    @Expose
    String firstName;

    @SerializedName("lastName")
    @Expose
    String lastName;

    @SerializedName("email")
    @Expose
    String email;

    @SerializedName("phoneNumber")
    @Expose
    String phoneNumber;

    @SerializedName("jobTitle")
    @Expose
    String jobTitle;

    @SerializedName("jobDescription")
    @Expose
    String jobDescription;

    @SerializedName("jobStartDate")
    @Expose
    String jobStartDate;

    @SerializedName("jobEndDate")
    @Expose
    String jobEndDate;

    public SelectedApplicant(String firstName, String lastName, String email, String phoneNumber, String jobTitle, String jobDescription, String jobStartDate, String jobEndDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.jobStartDate = jobStartDate;
        this.jobEndDate = jobEndDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobStartDate() {
        return jobStartDate;
    }

    public void setJobStartDate(String jobStartDate) {
        this.jobStartDate = jobStartDate;
    }

    public String getJobEndDate() {
        return jobEndDate;
    }

    public void setJobEndDate(String jobEndDate) {
        this.jobEndDate = jobEndDate;
    }
}
