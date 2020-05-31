package tn.medtech.recruitmentsystemapp.api.services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import tn.medtech.recruitmentsystemapp.api.models.Applicant;
import tn.medtech.recruitmentsystemapp.api.models.Recruiter;
import tn.medtech.recruitmentsystemapp.api.models.User;

public interface UserClient {
    @POST("authenticate")
    Call<User> login(@Body User user);

    @POST("register")
    Call<User> register(@Body User user);

    @POST("register")
    Call<User> registerApplicant(@Body User user);
}
