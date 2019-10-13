package com.example.bloodbankapp.ui.fragment.welcomesplashscreen;


import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bloodbankapp.R;
import com.example.bloodbankapp.helper.HelperMathod;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {

    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_splash, container, false);

        int SPLASH_DISPLAY_LENGTH = 200;
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                InformationFragment informationFragment = new InformationFragment();
                HelperMathod.getStartFragments(getFragmentManager(),R.id.frame_splash,informationFragment);
            }
        }, SPLASH_DISPLAY_LENGTH);

        return view;
    }

}
