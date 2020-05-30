package tn.medtech.recruitmentsystemapp.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Skill implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String skillName;
    @SerializedName("type")
    @Expose
    private String skillType;

    public Skill(int id, String skillName, String skillType) {
        this.id = id;
        this.skillName = skillName;
        this.skillType = skillType;
    }

    public String getSkillName() {
        return skillName;
    }

    public String getSkillType() {
        return skillType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public void setSkillType(String skillType) {
        this.skillType = skillType;
    }

    @Override
    public String toString() {
        return skillName +" (" +skillType+" )";
    }
}
