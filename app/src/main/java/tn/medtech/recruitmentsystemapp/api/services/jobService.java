package tn.medtech.recruitmentsystemapp.api.services;

import retrofit2.Call;
import retrofit2.http.POST;
import tn.medtech.recruitmentsystemapp.api.models.Job;

public interface jobService {
    @POST("job/create")
    Call<Job> createJob();
}
