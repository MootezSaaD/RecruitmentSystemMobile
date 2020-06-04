package tn.medtech.recruitmentsystemapp.api.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import tn.medtech.recruitmentsystemapp.api.models.Response;
import tn.medtech.recruitmentsystemapp.api.models.WorkExperience;

public interface WorkExperienceService {

    @POST("applicants//work-experiences")
    Call<Response> addWorkExperience(@Body WorkExperience workExperience);

    @GET("applicants//work-experiences")
    Call<List<WorkExperience>> getWorkExperiences();
}
