package tn.medtech.recruitmentsystemapp.api.services;

import retrofit2.Call;
import retrofit2.http.GET;
import tn.medtech.recruitmentsystemapp.api.models.Domain;

public interface domainService {
    @GET("domains/list")
    Call<Domain> getDomains();
}
