package tn.medtech.recruitmentsystemapp.api.models;

public class Company {
    private String name;
    private String description;
    private String sector;

    public Company(String name, String description, String sector) {
        this.name = name;
        this.description = description;
        this.sector = sector;
    }
}
