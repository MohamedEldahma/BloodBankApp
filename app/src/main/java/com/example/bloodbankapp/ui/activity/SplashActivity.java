package com.example.bloodbankapp.ui.activity;

import android.content.Intent;

import android.os.Bundle;

import com.example.bloodbankapp.R;
import com.example.bloodbankapp.helper.HelperMathod;
import com.example.bloodbankapp.ui.fragment.welcomesplashscreen.SplashFragment;

import androidx.appcompat.app.AppCompatActivity;



public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SplashFragment splashFragment = new SplashFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_splash, splashFragment).commit();


    }

//    private void getShowSlider() {
//        // start Slider Fragment == 2000 mills
//        new android.os.Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                try {
//                    // start Splash Fragment
//                    startActivity(new Intent(SplashActivity.this, ReplaceActivity.class));
//
////                   getStartFragments( getSupportFragmentManager(),R.id.splashActivityReplaceFragment,new SlideFragment());
//                } catch (Exception e) {
//                    e.getMessage();
//                }
//
//            }
//        }, 2000);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        try {
//
//            getShowSlider();
//
//        } catch (Exception e) {
//            e.getMessage();
//        }
//
//    }
}
