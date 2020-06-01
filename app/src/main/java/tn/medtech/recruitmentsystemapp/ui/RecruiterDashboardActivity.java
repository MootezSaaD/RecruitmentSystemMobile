package tn.medtech.recruitmentsystemapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class RecruiterDashboardActivity extends AppCompatActivity {
    Button createJobButton;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    View headerView;
    TextView navRecruiterName;
    TextView navRecruiterEmail;
    User recruiter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        recruiter = new Gson().fromJson(intent.getStringExtra("recruiterObject"), User.class);
        setContentView(R.layout.activity_recruiter_dashboard);
        Log.d("Recruiter From Dash", recruiter.toString());
        toolbar = findViewById(R.id.recToolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.recDrawerLayout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView = findViewById(R.id.recNavView);
        headerView = navigationView.getHeaderView(0);
        navRecruiterName = headerView.findViewById(R.id.navRecName);
        navRecruiterEmail = headerView.findViewById(R.id.navRecEmail);
        navRecruiterName.setText(recruiter.getFirstName() + " " + recruiter.getLastName());
        navRecruiterEmail.setText(recruiter.getEmail());

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.recFragmentContainer, new ListRecruiterJobsFragment()).commit();
            navigationView.setCheckedItem(R.id.recNavList);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.recNavList:
                        getSupportFragmentManager().beginTransaction().replace(R.id.recFragmentContainer, new ListRecruiterJobsFragment()).commit();
                        break;
                    case R.id.recNavAdd:
                        getSupportFragmentManager().beginTransaction().replace(R.id.recFragmentContainer, new CreateJobFragment()).commit();
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
