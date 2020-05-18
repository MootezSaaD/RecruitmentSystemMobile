package tn.medtech.recruitmentsystemapp.api.models;

public class Applicant extends User {
    private String phoneNumber;
    public Applicant(String firstName, String lastName, String email,String phoneNumber, String... vars) {
        super(firstName, lastName, email, vars);
        this.phoneNumber = phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
