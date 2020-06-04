package tn.medtech.recruitmentsystemapp.ui.Authentication.Login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.api.models.User;
import tn.medtech.recruitmentsystemapp.api.services.ServiceGenerator;
import tn.medtech.recruitmentsystemapp.api.services.UserClient;
import tn.medtech.recruitmentsystemapp.ui.ApplicantDashboard.ApplicantDashboardActivity;
import tn.medtech.recruitmentsystemapp.ui.RecruiterDashboard.RecruiterDashboardActivity;
import tn.medtech.recruitmentsystemapp.util.TokenService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TokenService tokenService = TokenService.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        if (tokenService.getAccessToken() == null) {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        } else {
            this.getProfile(tokenService);
        }
    }

    private void getProfile(TokenService tokenService) {
        UserClient userClient = ServiceGenerator.createServiceWithAuth(UserClient.class, tokenService);
        Call<User> call = userClient.getProfile();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body().getUserType().equalsIgnoreCase("applicant"))
                    startActivity(new Intent(MainActivity.this, ApplicantDashboardActivity.class)
                            .putExtra("applicantObject", new Gson().toJson(
                                    new User(response.body().getFirstName(),
                                            response.body().getLastName(),
                                            response.body().getEmail(),
                                            "",
                                            response.body().getPhoneNumber())
                            )));
                else if (response.body().getUserType().equalsIgnoreCase("recruiter")) {
                    startActivity(new Intent(MainActivity.this, RecruiterDashboardActivity.class)
                            .putExtra("recruiterObject", new Gson().toJson(
                                    new User(response.body().getFirstName(),
                                            response.body().getLastName(),
                                            response.body().getEmail(),
                                            "",
                                            response.body().getCompany())
                            )));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
