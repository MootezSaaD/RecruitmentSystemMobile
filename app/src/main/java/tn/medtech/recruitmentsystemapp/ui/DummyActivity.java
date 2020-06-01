package tn.medtech.recruitmentsystemapp.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import tn.medtech.recruitmentsystemapp.R;
import tn.medtech.recruitmentsystemapp.util.TokenService;

/*
 * Dummy activity for testing purposes.
 * */
public class DummyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);
        Toast.makeText(this, "Token" + TokenService.getToken(), Toast.LENGTH_SHORT).show();
    }
}
