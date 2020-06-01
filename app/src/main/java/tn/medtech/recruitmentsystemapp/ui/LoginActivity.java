package tn.medtech.recruitmentsystemapp.ui;

import android.content.Intent;
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
import tn.medtech.recruitmentsystemapp.api.models.User;
import tn.medtech.recruitmentsystemapp.api.services.ServiceGenerator;
import tn.medtech.recruitmentsystemapp.api.services.UserClient;
import tn.medtech.recruitmentsystemapp.util.TokenService;

public class LoginActivity extends AppCompatActivity {

    TextView registerTextView;
    TextInputLayout emailFld;
    TextInputLayout passwordFld;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        UserClient userClient = ServiceGenerator.createService(UserClient.class);
        // Perform the login request
        Call<User> call = userClient.login(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 400) {
                    Toast.makeText(LoginActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                } else {
                    // Just testing
                    //Toast.makeText(LoginActivity.this, "Name :"+response.body().getFirstName(), Toast.LENGTH_SHORT).show();
                    // Store the JWT in SharedPreferences
                    TokenService.init(getApplicationContext());
                    TokenService.storeToken(response.body().getJwtToken());
                    // Redirect depending on the response, i.e. the user's role (Applicant or Recuiter).
                    // Redirecting to a dummy activity to test the JWT service
                    if (response.body().getUserType().equalsIgnoreCase("applicant"))
                        startActivity(new Intent(LoginActivity.this, ApplicantDashboardActivity.class));
                    else if (response.body().getUserType().equalsIgnoreCase("recruiter")) {
                        startActivity(new Intent(LoginActivity.this, RecruiterDashboardActivity.class)
                                .putExtra("recruiterObject", new Gson().toJson(
                                        new User(response.body().getFirstName(),
                                                response.body().getLastName(),
                                                response.body().getEmail(),
                                                "",
                                                response.body().getCompany())
                                )));
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Unexpected error !", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
