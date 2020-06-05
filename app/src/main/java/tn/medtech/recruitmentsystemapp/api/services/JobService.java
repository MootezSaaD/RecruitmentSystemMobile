package tn.medtech.recruitmentsystemapp.api.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import tn.medtech.recruitmentsystemapp.api.models.Application;
import tn.medtech.recruitmentsystemapp.api.models.JobOffer;
import tn.medtech.recruitmentsystemapp.api.models.Response;
import tn.medtech.recruitmentsystemapp.api.models.SelectedApplicant;

public interface JobService {
    @POST("jobs/create")
    Call<JobOffer> createJob(@Body JobOffer jobOffer);

    @GET("recruiters/jobs")
    Call<List<JobOffer>> getJobs();

    @DELETE("jobs/{jobID}")
    Call<Response>  deleteJob(@Path("jobID") int jobID);

    @PUT("applicants/jobs/{jobID}")
    Call<Response> applyForJob(@Path("jobID") int jobID);

    @GET("applicants/jobs")
    Call<List<Application>> getApplications();

    @GET("recruiters/jobs/applicant-suggestions")
    Call<List<SelectedApplicant>> getMatchedJobs();

    @POST("test")
    Call<JobOffer> testCreateJob(@Body JobOffer jobOffer);

}
