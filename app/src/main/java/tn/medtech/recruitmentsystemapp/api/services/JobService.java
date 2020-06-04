package tn.medtech.recruitmentsystemapp.api.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import tn.medtech.recruitmentsystemapp.api.models.Application;
import tn.medtech.recruitmentsystemapp.api.models.JobOffer;
import tn.medtech.recruitmentsystemapp.api.models.Response;
import tn.medtech.recruitmentsystemapp.api.models.SelectedApplicant;

public interface JobService {
    @POST("jobs/create")
    Call<JobOffer> createJob(@Header("Authorization") String token, @Body JobOffer jobOffer);

    @GET("jobs/")
    Call<List<JobOffer>> getJobs(@Header("Authorization") String token);

    @PUT("applicants/jobs/{jobID}")
    Call<Response> applyForJob(@Header("Authorization") String token, @Path("jobID") int jobID);

    @GET("applicants/jobs")
    Call<List<Application>> getApplications(@Header("Authorization") String token);

    @GET("recruiters/jobs/applicant-suggestions")
    Call<List<SelectedApplicant>> getMatchedJobs(@Header("Authorization") String token);

    @POST("test")
    Call<JobOffer> testCreateJob(@Header("Authorization") String token, @Body JobOffer jobOffer);

}
