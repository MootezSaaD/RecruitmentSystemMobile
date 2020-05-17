package tn.medtech.recruitmentsystemapp.api.models;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String jwtToken;
    private String userId;

    // For user registration and getting back the user after login
    public User(String firstName, String lastName, String email, String ...vars) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = vars[0];
        this.jwtToken = vars[1];
    }
    // For user login
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
