package tn.medtech.recruitmentsystemapp.api.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import tn.medtech.recruitmentsystemapp.api.models.User;

public interface UserClient {
    @POST("users/authenticate")
    Call<User> login(@Body User user);

    @POST("users/register")
    Call<User> register(@Body User user);

    @POST("users/register")
    Call<User> registerApplicant(@Body User user);

    @GET("users/@me")
    Call<User> getProfile(@Header("Authorization") String token);

}
