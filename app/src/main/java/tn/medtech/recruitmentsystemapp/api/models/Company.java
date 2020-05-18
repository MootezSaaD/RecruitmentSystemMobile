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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sector='" + sector + '\'' +
                '}';
    }
}
