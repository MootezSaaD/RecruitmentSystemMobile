package tn.medtech.recruitmentsystemapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.api.models.Applicant;
import tn.medtech.recruitmentsystemapp.api.services.UserClient;

        public class ApplicantRegisterActivity extends AppCompatActivity {

            EditText phoneNumber;
            Button finalRegisterBtn;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_applicant_register);

                finalRegisterBtn = findViewById(R.id.applicantRegisterBtnBtn);
                phoneNumber = findViewById(R.id.phoneNbrFldBtn);



                Intent intent = getIntent();
                Applicant applicant = new Gson().fromJson(intent.getStringExtra("applicantObject"), Applicant.class);
                finalRegisterBtn.setOnClickListener(v -> {
                    applicant.setPhoneNumber(phoneNumber.getText().toString());
                    sendRegisterRequest(applicant);
                });
            }

            public void sendRegisterRequest(Applicant applicant){
                Retrofit.Builder retroBuilder = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:3000/api/user/")
                        .addConverterFactory(GsonConverterFactory.create());
                String gson = new Gson().toJson(applicant);
                Log.d("Rec", gson);
                Retrofit retrofit = retroBuilder.build();
                Log.d("Body", retrofit.toString());
                // Call the UserClient and get the user object for the request
                UserClient userClient = retrofit.create(UserClient.class);
                Call<Applicant> call = userClient.registerApplicant(applicant);
                call.enqueue(new Callback<Applicant>() {
                    @Override
                    public void onResponse(Call<Applicant> call, Response<Applicant> response) {
                        Toast.makeText(ApplicantRegisterActivity.this, "Applicant Added !", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Applicant> call, Throwable t) {
                        Toast.makeText(ApplicantRegisterActivity.this, "Unexpected error !", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
            }
        }

