package tn.medtech.recruitmentsystemapp.api.models;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String jwtToken;
    private String userId;

    // For user registration
    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
    // For user login
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
