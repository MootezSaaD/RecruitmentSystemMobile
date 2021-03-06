package tn.medtech.recruitmentsystemapp.ui.ApplicantDashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.api.models.User;
import tn.medtech.recruitmentsystemapp.ui.Authentication.Login.LoginActivity;
import tn.medtech.recruitmentsystemapp.ui.RecruiterDashboard.RecruiterDashboardActivity;
import tn.medtech.recruitmentsystemapp.util.TokenService;

public class ApplicantDashboardActivity extends AppCompatActivity {

    User applicant;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    View headerView;
    TextView navRecruiterName;
    TextView navRecruiterEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        applicant = new Gson().fromJson(intent.getStringExtra("applicantObject"), User.class);
        setContentView(R.layout.activity_applicant_dashboard);
        toolbar = findViewById(R.id.appToolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.appDrawerLayout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView = findViewById(R.id.appNavView);
        headerView = navigationView.getHeaderView(0);
        navRecruiterName = headerView.findViewById(R.id.navRecName);
        navRecruiterEmail = headerView.findViewById(R.id.navRecEmail);
        navRecruiterName.setText(applicant.getFirstName() + " " + applicant.getLastName());
        navRecruiterEmail.setText(applicant.getEmail());

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.appFragmentContainer, new JobsApplicantFragment()).commit();
            navigationView.setCheckedItem(R.id.recNavList);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.appNavJobs:
                        getSupportFragmentManager().beginTransaction().replace(R.id.appFragmentContainer, new JobsApplicantFragment()).commit();
                        break;
                    case R.id.appNavApplications:
                        getSupportFragmentManager().beginTransaction().replace(R.id.appFragmentContainer, new ApplicationsApplicantFragment()).commit();
                        break;
                    case R.id.appNavProfile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.appFragmentContainer, new ProfileApplicantFragment()).commit();
                        break;
                    case R.id.appWexp:
                        getSupportFragmentManager().beginTransaction().replace(R.id.appFragmentContainer, new WorkExperienceFragment()).commit();
                        break;
                    case R.id.appDegrees:
                        getSupportFragmentManager().beginTransaction().replace(R.id.appFragmentContainer, new DegreeListFragment()).commit();
                        break;
                    case R.id.appLogout:
                        TokenService tokenService = TokenService.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
                        tokenService.deleteToken();
                        Intent i = new Intent(ApplicantDashboardActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
