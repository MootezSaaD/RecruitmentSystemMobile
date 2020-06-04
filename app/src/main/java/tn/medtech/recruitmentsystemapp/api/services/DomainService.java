package tn.medtech.recruitmentsystemapp.api.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import tn.medtech.recruitmentsystemapp.api.models.Domain;

public interface DomainService {
    @GET("domains/list")
    Call<List<Domain>> getDomains();
}
