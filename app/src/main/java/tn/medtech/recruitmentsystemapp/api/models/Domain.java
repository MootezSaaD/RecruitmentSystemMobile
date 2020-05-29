package tn.medtech.recruitmentsystemapp.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Domain implements Serializable {
    @SerializedName("name")
    @Expose
    private String domainName;

    public Domain(String domainName){
        this.domainName = domainName;
    }

    public String getDomainName() {
        return domainName;
    }

}
