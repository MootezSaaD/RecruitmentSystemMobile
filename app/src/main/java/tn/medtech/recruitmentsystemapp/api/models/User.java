package tn.medtech.recruitmentsystemapp.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("userType")
    @Expose
    String userType;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("company")
    @Expose
    private Company company;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    private String token;
    private String userId;

    // For user registration and getting back the user after login
    public User(String userType, String firstName, String lastName, String email, String... vars) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userType = userType;
        this.password = vars[0];
        this.token = vars[1];
    }

    // For user login
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Constructor for recruiter
    public User(String firstName, String lastName, String email, String password,Company company) {
        this.userType = "Recuriter";
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.company = company;
        this.password = password;
    }

    // Constructor for applicant
    public User(String firstName, String lastName, String email, String password,String phoneNumber) {
        this.userType = "Applicant";
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getJwtToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    public Company getCompany() {
        return company;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
