package tn.medtech.recruitmentsystemapp.ui.ApplicantDashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.api.models.Degree;
import tn.medtech.recruitmentsystemapp.api.models.Domain;
import tn.medtech.recruitmentsystemapp.api.services.DegreeService;
import tn.medtech.recruitmentsystemapp.api.services.DomainService;
import tn.medtech.recruitmentsystemapp.api.services.ServiceGenerator;
import tn.medtech.recruitmentsystemapp.util.TokenService;

public class ApplicantAddDegree extends AppCompatActivity {

    Button degreeType;
    Button degreeDomain;
    Button addBtn;
    ArrayList<String> domainNames = new ArrayList<>();
    List<Domain> domainList;
    ArrayList<String> degreeTypes = new ArrayList<>();
    int domainPosition = 0;
    int typePosition = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_degree_add_applicant);
        setTitle("Add Degree");
        degreeTypes.add("BSc");
        degreeTypes.add("MSc");
        degreeTypes.add("PhD");
        getDomains();

        degreeDomain = findViewById(R.id.btnDegreeDomain);
        degreeType = findViewById(R.id.btnDegreeType);
        addBtn = findViewById(R.id.addDegreeBtn);

        degreeDomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence[] domainsName = domainNames.toArray(new CharSequence[domainNames.size()]);
                AlertDialog.Builder builder = new AlertDialog.Builder(ApplicantAddDegree.this);
                builder.setTitle("Domain")
                        .setSingleChoiceItems(domainsName, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                domainPosition = i;
                                degreeDomain.setText(domainNames.get(domainPosition));
                            }
                        })
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                builder.create().show();
            }
        });

        degreeType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence[] degree = degreeTypes.toArray(new CharSequence[degreeTypes.size()]);
                AlertDialog.Builder builder = new AlertDialog.Builder(ApplicantAddDegree.this);
                builder.setTitle("Degree Type")
                        .setSingleChoiceItems(degree, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                typePosition = i;
                                degreeType.setText(degreeTypes.get(typePosition));
                            }
                        })
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                builder.create().show();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Degree degree = new Degree(degreeType.getText().toString(), degreeDomain.getText().toString());
                addDegree(degree);
            }
        });

    }

    private void addDegree(Degree degree) {
        TokenService tokenService = TokenService.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        DegreeService degreeService = ServiceGenerator.createServiceWithAuth(DegreeService.class, tokenService);
        Call<Degree> call = degreeService.addDegree(degree);
        call.enqueue(new Callback<Degree>() {
            @Override
            public void onResponse(Call<Degree> call, Response<Degree> response) {
                Toast.makeText(ApplicantAddDegree.this, "Degree added!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Degree> call, Throwable t) {
                Toast.makeText(ApplicantAddDegree.this, "Something went wrong... Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDomains() {
        TokenService tokenService = TokenService.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        DomainService domainService = ServiceGenerator.createServiceWithAuth(DomainService.class, tokenService);
        Call<List<Domain>> call = domainService.getDomains();
        call.enqueue(new Callback<List<Domain>>() {

            @Override
            public void onResponse(Call<List<Domain>> call, Response<List<Domain>> response) {
                domainList = response.body();
                domainList.forEach(domain -> {
                    domainNames.add(domain.getDomainName());
                });
            }

            @Override
            public void onFailure(Call<List<Domain>> call, Throwable t) {
                Toast.makeText(ApplicantAddDegree.this, "Something went wrong... Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
