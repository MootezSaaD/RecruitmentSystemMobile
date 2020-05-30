package tn.medtech.recruitmentsystemapp.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Domain implements Serializable {
    @SerializedName("name")
    @Expose
    private String domainName;
    @SerializedName("id")
    @Expose
    private int id;

    public Domain(String domainName, int id) {
        this.domainName = domainName;
        this.id = id;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return domainName;
    }
}
