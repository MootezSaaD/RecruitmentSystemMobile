package tn.medtech.recruitmentsystemapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.api.models.Company;
import tn.medtech.recruitmentsystemapp.api.models.Recruiter;
import tn.medtech.recruitmentsystemapp.api.models.User;
import tn.medtech.recruitmentsystemapp.api.services.UserClient;

public class RecruiterRegisterActivity extends AppCompatActivity {

    TextInputLayout companyName;
    TextInputLayout companyDesc;
    TextInputLayout companySector;
    Button finalRegisterBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_registration);
        finalRegisterBtn = findViewById(R.id.finalRecRegisterBtn);
        companyName = findViewById(R.id.companyNameFld);
        companyDesc = findViewById(R.id.companyDescFld);
        companySector = findViewById(R.id.comapnySectorFld);


        Intent intent = getIntent();
        User recruiter = new Gson().fromJson(intent.getStringExtra("recruiterObject"), User.class);
        finalRegisterBtn.setOnClickListener(v -> {
            Company company = new Company(companyName.getEditText().getText().toString(),companyDesc.getEditText().getText().toString(),companySector.getEditText().getText().toString() );
            recruiter.setCompany(company);
            sendRegisterRequest(recruiter);
        });

    }

    public void sendRegisterRequest(User recruiter){
        Retrofit.Builder retroBuilder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/api/users/")
                .addConverterFactory(GsonConverterFactory.create());
        String gson = new Gson().toJson(recruiter);
        Log.d("Rec", gson);
        Retrofit retrofit = retroBuilder.build();
        Log.d("Body", retrofit.toString());
        // Call the UserClient and get the user object for the request
        UserClient userClient = retrofit.create(UserClient.class);
        Call<User> call = userClient.register(recruiter);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(RecruiterRegisterActivity.this, "Recruiter Added !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(RecruiterRegisterActivity.this, "Unexpected error !", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
