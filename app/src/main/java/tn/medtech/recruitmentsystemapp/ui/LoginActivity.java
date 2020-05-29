package tn.medtech.recruitmentsystemapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.api.models.User;
import tn.medtech.recruitmentsystemapp.api.services.UserClient;
import tn.medtech.recruitmentsystemapp.util.TokenService;

public class LoginActivity extends AppCompatActivity {

    TextView registerTextView;
    EditText emailFld;
    EditText passwordFld;
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
                        emailFld.getText().toString(),
                        passwordFld.getText().toString()
                );
                sendLoginRequest(user);
            }
        });
    }

    private void sendLoginRequest(User user) {
        // Start by creating the Retrofit instance
        Retrofit.Builder retroBuilder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/api/user/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = retroBuilder.build();
        // Call the UserClient and get the user object for the request
        UserClient userClient = retrofit.create(UserClient.class);
        // Perform the login request
        Call<User> call = userClient.login(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                // Just testing
                //Toast.makeText(LoginActivity.this, "Name :"+response.body().getFirstName(), Toast.LENGTH_SHORT).show();
                // Store the JWT in SharedPreferences
                TokenService.init(getApplicationContext());
                TokenService.storeToken(response.body().getJwtToken());
                // Redirect depending on the response, i.e. the user's role (Applicant or Recuiter).
                // Redirecting to a dummy activity to test the JWT service
                if(response.body().getUserType().equalsIgnoreCase("applicant"))
                    startActivity(new Intent(LoginActivity.this,ApplicantDashboardActivity.class));
                else if(response.body().getUserType().equalsIgnoreCase("recruiter"))
                    startActivity(new Intent(LoginActivity.this,RecruiterDashboardActivity.class));
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Unexpected error !", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
