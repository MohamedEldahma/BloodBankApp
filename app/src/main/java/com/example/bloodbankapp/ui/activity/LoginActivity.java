package com.example.bloodbankapp.ui.activity;

import android.os.Bundle;

import com.example.bloodbankapp.R;
import com.example.bloodbankapp.ui.fragment.login.LoginFragment;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginFragment loginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fram_login_activity,loginFragment).commit();



    }
}
