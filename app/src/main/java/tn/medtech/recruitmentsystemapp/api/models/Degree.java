package tn.medtech.recruitmentsystemapp.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Degree {

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("domain")
    @Expose
    private String domain;

    public Degree(String type, String domain) {
        this.type = type;
        this.domain = domain;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
