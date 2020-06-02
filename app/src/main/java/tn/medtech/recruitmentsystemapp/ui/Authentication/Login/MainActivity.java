package tn.medtech.recruitmentsystemapp.ui.Authentication.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import tn.medtech.recruitmentsystemapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Redirect to login page
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
    }
}
