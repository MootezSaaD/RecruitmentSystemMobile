package tn.medtech.recruitmentsystemapp.api.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import tn.medtech.recruitmentsystemapp.api.models.Response;
import tn.medtech.recruitmentsystemapp.api.models.Skill;

public interface SkillService {
    @GET("skills/list")
    Call<List<Skill>> getSkills();

    @GET("applicants/skills")
    Call<List<Skill>> getApplicantSkills();

    @POST("applicants/skills")
    Call<Response> addSkills(@Body List<Skill> array);

}
