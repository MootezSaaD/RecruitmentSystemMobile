package tn.medtech.recruitmentsystemapp.api.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import tn.medtech.recruitmentsystemapp.api.models.Skill;

public interface SkillService {
    @GET("skills/list")
    Call<List<Skill>> getSkills(@Header("Authorization") String token);
}
