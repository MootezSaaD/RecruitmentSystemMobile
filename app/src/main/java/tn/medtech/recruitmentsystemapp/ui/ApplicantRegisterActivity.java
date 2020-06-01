package tn.medtech.recruitmentsystemapp.ui;

import android.content.Intent;
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
import tn.medtech.recruitmentsystemapp.api.models.User;
import tn.medtech.recruitmentsystemapp.api.services.ServiceGenerator;
import tn.medtech.recruitmentsystemapp.api.services.UserClient;

public class ApplicantRegisterActivity extends AppCompatActivity {

    TextInputLayout phoneNumber;
    Button finalRegisterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_register);

        finalRegisterBtn = findViewById(R.id.applicantRegisterBtnBtn);
        phoneNumber = findViewById(R.id.phoneNbrFldBtn);

        Intent intent = getIntent();
        User applicant = new Gson().fromJson(intent.getStringExtra("applicantObject"), User.class);
        finalRegisterBtn.setOnClickListener(v -> {
            applicant.setPhoneNumber(phoneNumber.getEditText().getText().toString());
            sendRegisterRequest(applicant);
        });
    }

    public void sendRegisterRequest(User applicant) {
        // Call the UserClient and get the user object for the request
        UserClient userClient = ServiceGenerator.createService(UserClient.class);
        Call<User> call = userClient.registerApplicant(applicant);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(ApplicantRegisterActivity.this, "Applicant Added !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ApplicantRegisterActivity.this, "Unexpected error !", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}

