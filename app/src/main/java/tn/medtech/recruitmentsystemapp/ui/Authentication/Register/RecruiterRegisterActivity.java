package tn.medtech.recruitmentsystemapp.ui.Authentication.Register;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.api.models.Company;
import tn.medtech.recruitmentsystemapp.api.models.User;
import tn.medtech.recruitmentsystemapp.api.services.ServiceGenerator;
import tn.medtech.recruitmentsystemapp.api.services.UserClient;
import tn.medtech.recruitmentsystemapp.ui.Authentication.Login.LoginActivity;

public class RecruiterRegisterActivity extends AppCompatActivity {

    TextInputLayout companyName;
    TextInputLayout companyDesc;
    TextInputLayout companySector;
    Button finalRegisterBtn;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getApplicationContext().getSharedPreferences(getApplicationContext().getPackageName(), Activity.MODE_PRIVATE);
        setContentView(R.layout.activity_recruiter_registration);
        finalRegisterBtn = findViewById(R.id.finalRecRegisterBtn);
        companyName = findViewById(R.id.companyNameFld);
        companyDesc = findViewById(R.id.companyDescFld);
        companySector = findViewById(R.id.comapnySectorFld);


        Intent intent = getIntent();
        User recruiter = new Gson().fromJson(intent.getStringExtra("recruiterObject"), User.class);
        finalRegisterBtn.setOnClickListener(v -> {
            Company company = new Company(companyName.getEditText().getText().toString(), companyDesc.getEditText().getText().toString(), companySector.getEditText().getText().toString());
            recruiter.setCompany(company);
            sendRegisterRequest(recruiter);
        });

    }

    public void sendRegisterRequest(User recruiter) {
        // Call the UserClient and get the user object for the request
        UserClient userClient = ServiceGenerator.createService(UserClient.class);
        Call<tn.medtech.recruitmentsystemapp.api.models.Response> call = userClient.register(recruiter);
        call.enqueue(new Callback<tn.medtech.recruitmentsystemapp.api.models.Response>() {
            @Override
            public void onResponse(Call<tn.medtech.recruitmentsystemapp.api.models.Response> call, Response<tn.medtech.recruitmentsystemapp.api.models.Response> response) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("redirectAfterRegisterMessage", response.body().getMessage());
                editor.apply();
                startActivity(new Intent(RecruiterRegisterActivity.this, LoginActivity.class));
            }

            @Override
            public void onFailure(Call<tn.medtech.recruitmentsystemapp.api.models.Response> call, Throwable t) {
                Toast.makeText(RecruiterRegisterActivity.this, "Something went wrong... Please try later!", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
