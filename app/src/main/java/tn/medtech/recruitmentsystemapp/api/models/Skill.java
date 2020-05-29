package tn.medtech.recruitmentsystemapp.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Skill implements Serializable {
    @SerializedName("skillName")
    @Expose
    private String skillName;
    @SerializedName("skillType")
    @Expose
    private String skillType;

    public Skill(String skillName, String skillType) {
        this.skillName = skillName;
        this.skillType = skillType;
    }

    public String getSkillName() {
        return skillName;
    }

    public String getSkillType() {
        return skillType;
    }

}
