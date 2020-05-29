package tn.medtech.recruitmentsystemapp.api.services;

import retrofit2.Call;
import retrofit2.http.GET;
import tn.medtech.recruitmentsystemapp.api.models.Skill;

public interface skillService {
    @GET("skills/list")
    Call<Skill> getSkills();
}
