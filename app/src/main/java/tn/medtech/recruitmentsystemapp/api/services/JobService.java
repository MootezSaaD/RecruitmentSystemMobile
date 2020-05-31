package tn.medtech.recruitmentsystemapp.api.services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import tn.medtech.recruitmentsystemapp.api.models.JobOffer;

public interface JobService {
    @POST("jobs/create")
    Call<JobOffer> createJob(@Header("Authorization") String token, @Body JobOffer jobOffer);

    @POST("test")
    Call<JobOffer> testCreateJob(@Header("Authorization") String token, @Body JobOffer jobOffer);

}
