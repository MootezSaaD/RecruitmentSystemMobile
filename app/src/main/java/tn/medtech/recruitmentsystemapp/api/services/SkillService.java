package tn.medtech.recruitmentsystemapp.api.services;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import tn.medtech.recruitmentsystemapp.api.models.Response;
import tn.medtech.recruitmentsystemapp.api.models.Skill;

public interface SkillService {
    @GET("skills/list")
    Call<List<Skill>> getSkills(@Header("Authorization") String token);

    @GET("applicants/skills")
    Call<List<Skill>> getApplicantSkills(@Header("Authorization") String token);

    @POST("applicants/skills")
    Call<Response> addSkills(@Header("Authorization") String token, @Body List<Skill> array);

}
