package tn.medtech.recruitmentsystemapp.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
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
    private String token;
    private String userId;

    // For user registration and getting back the user after login
    public User(String firstName, String lastName, String email, String... vars) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = vars[0];
        this.token = vars[1];
    }
    // For user login
    public User(String email, String password) {
        this.email = email;
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
}
