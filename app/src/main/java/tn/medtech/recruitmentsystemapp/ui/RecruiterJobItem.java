package tn.medtech.recruitmentsystemapp.ui;

public class RecruiterJobItem {
    private String jobTitle;
    private String jobCompany;
    private String jobDesc;

    public RecruiterJobItem(String jobTitle, String jobCompany, String jobDesc) {
        this.jobTitle = jobTitle;
        this.jobCompany = jobCompany;
        this.jobDesc = jobDesc;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getJobCompany() {
        return jobCompany;
    }

    public String getJobDesc() {
        return jobDesc;
    }
}
