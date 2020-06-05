package tn.medtech.recruitmentsystemapp.ui.Authentication.Login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.api.models.AccessToken;
import tn.medtech.recruitmentsystemapp.api.models.User;
import tn.medtech.recruitmentsystemapp.api.services.ServiceGenerator;
import tn.medtech.recruitmentsystemapp.api.services.UserClient;
import tn.medtech.recruitmentsystemapp.ui.ApplicantDashboard.ApplicantDashboardActivity;
import tn.medtech.recruitmentsystemapp.ui.Authentication.Register.RegisterActivity;
import tn.medtech.recruitmentsystemapp.ui.RecruiterDashboard.RecruiterDashboardActivity;
import tn.medtech.recruitmentsystemapp.util.TokenService;

public class LoginActivity extends AppCompatActivity {

    TextView registerTextView;
    TextInputLayout emailFld;
    TextInputLayout passwordFld;
    Button loginBtn;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getApplicationContext().getSharedPreferences(getApplicationContext().getPackageName(), Activity.MODE_PRIVATE);
        // Check if the variable "redirectAfterRegisterMessage" exists
        if (sharedPreferences.contains("redirectAfterRegisterMessage")) {
            Toast.makeText(this, sharedPreferences.getString("redirectAfterRegisterMessage", ""), Toast.LENGTH_SHORT).show();
            sharedPreferences.edit().remove("redirectAfterRegisterMessage").commit();
        }
        registerTextView = findViewById(R.id.textViewLogin);
        loginBtn = findViewById(R.id.loginBtn);
        emailFld = findViewById(R.id.loginEmailFld);
        passwordFld = findViewById(R.id.loginPasswordFld);
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(
                        emailFld.getEditText().getText().toString(),
                        passwordFld.getEditText().getText().toString()
                );
                sendLoginRequest(user);
            }
        });
    }

    private void sendLoginRequest(User user) {
        TokenService tokenService = TokenService.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        UserClient userClient = ServiceGenerator.createService(UserClient.class);

        // Perform the login request
        Call<User> call = userClient.login(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 400) {
                    Toast.makeText(LoginActivity.this, "Wrong credentials!", Toast.LENGTH_SHORT).show();
                } else {
                    // Just testing
                    //Toast.makeText(LoginActivity.this, "Name :"+response.body().getFirstName(), Toast.LENGTH_SHORT).show();
                    // Store the JWT in SharedPreferences
                    AccessToken accessToken = new AccessToken();
                    accessToken.setAccessToken(response.body().getAccessToken());
                    accessToken.setRefreshToken(response.body().getRefreshToken());
                    tokenService.saveToken(accessToken);
                    // Redirect depending on the response, i.e. the user's role (Applicant or Recuiter).
                    // Redirecting to a dummy activity to test the JWT service
                    if (response.body().getUserType().equalsIgnoreCase("applicant")) {
                        startActivity(new Intent(LoginActivity.this, ApplicantDashboardActivity.class)
                                .putExtra("applicantObject", new Gson().toJson(
                                        new User(response.body().getFirstName(),
                                                response.body().getLastName(),
                                                response.body().getEmail(),
                                                "",
                                                response.body().getPhoneNumber())
                                )));
                        finish();
                    } else if (response.body().getUserType().equalsIgnoreCase("recruiter")) {
                        startActivity(new Intent(LoginActivity.this, RecruiterDashboardActivity.class)
                                .putExtra("recruiterObject", new Gson().toJson(
                                        new User(response.body().getFirstName(),
                                                response.body().getLastName(),
                                                response.body().getEmail(),
                                                "",
                                                response.body().getCompany())
                                )));
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Something went wrong... Please try later!", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
