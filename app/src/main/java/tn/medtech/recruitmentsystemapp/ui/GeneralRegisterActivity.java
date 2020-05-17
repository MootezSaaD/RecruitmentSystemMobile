package tn.medtech.recruitmentsystemapp.ui;
/*
* This activity contains the common form that any user should pass by.
*
* */
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import tn.medtech.recruitmentsystemapp.R;

public class GeneralRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_register);
    }
}
