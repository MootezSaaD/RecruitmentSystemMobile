package tn.medtech.recruitmentsystemapp.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Date;

public class Job  implements Serializable {
    /*
    {
    "domain": "Software Engineering",
    "title": "Data Analyst",
    "description": "Loremp ipmsum",
    "startDate": "2020-01-12",
    "endDate": "2020-01-30",
    "skills": {
        "1": {
            "name": "Team Member",
            "type": "Required"
        },
        "2":  {
            "name": "Statistics",
            "type": "Required"
        },
        "3":  {
            "name": "Python",
            "type": "Required"
        }
    }

}
     */

    @SerializedName("domain")
    @Expose
    private Domain domain;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("startDate")
    @Expose
    private Date startDate;

    @SerializedName("endDate")
    @Expose
    private Date endDate;

    @SerializedName("skills")
    @Expose
    private Skill[] skills;

    public Job(Domain domain, String title, String description, Date startDate, Date endDate, Skill[] skills) {
        this.domain = domain;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.skills = skills;
    }

    public Domain getDomain() {
        return domain;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Skill[] getSkills() {
        return skills;
    }
}
