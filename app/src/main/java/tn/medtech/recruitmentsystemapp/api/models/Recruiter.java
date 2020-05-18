package tn.medtech.recruitmentsystemapp.api.models;

public class Recruiter extends User {

    private Company company;
    public Recruiter(String firstName, String lastName, String email, Company company, String... vars) {
        super(firstName, lastName, email, vars);
        this.company = company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Recruiter{" +
                "company=" + company +
                '}';
    }
}
