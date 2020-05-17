package tn.medtech.recruitmentsystemapp.api.services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import tn.medtech.recruitmentsystemapp.api.models.User;

public interface UserClient {
    @POST("register")
    Call<User> createAccount(@Body User user);
}
