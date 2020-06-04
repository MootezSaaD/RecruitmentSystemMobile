package tn.medtech.recruitmentsystemapp.api.services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import tn.medtech.recruitmentsystemapp.api.models.AccessToken;
import tn.medtech.recruitmentsystemapp.api.models.Response;
import tn.medtech.recruitmentsystemapp.api.models.User;

public interface UserClient {
    @POST("users/authenticate")
    Call<User> login(@Body User user);

    @POST("users/register")
    Call<Response> register(@Body User user);

    @GET("users/@me")
    Call<User> getProfile(@Header("Authorization") String token);

    @POST("users/refresh-token")
    @FormUrlEncoded
    Call<AccessToken> refreshToken(@Field("refreshToken") String refreshToken);
}
