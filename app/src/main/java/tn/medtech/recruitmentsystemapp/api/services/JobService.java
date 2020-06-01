package tn.medtech.recruitmentsystemapp.api.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import tn.medtech.recruitmentsystemapp.api.models.JobOffer;

public interface JobService {
    @POST("jobs/create")
    Call<JobOffer> createJob(@Header("Authorization") String token, @Body JobOffer jobOffer);

    @GET("jobs/")
    Call<List<JobOffer>> getJobs(@Header("Authorization") String token);

    @GET("applicants/jobs")
    Call<List<JobOffer>> getMyJobs(@Header("Authorization") String token);

    @POST("test")
    Call<JobOffer> testCreateJob(@Header("Authorization") String token, @Body JobOffer jobOffer);

}
