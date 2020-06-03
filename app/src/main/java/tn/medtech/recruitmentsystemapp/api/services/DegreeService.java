package tn.medtech.recruitmentsystemapp.api.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import tn.medtech.recruitmentsystemapp.api.models.Degree;

public interface DegreeService {

    @GET("applicants/degrees")
    Call<List<Degree>> getDegrees(@Header("Authorization") String token);

    @POST("applicants/degree")
    Call<Degree> addDegree(@Header("Authorization") String token, @Body Degree degree);
}
