package com.example.bloodbankapp.adapter;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SplashViewPagerAdapter extends FragmentPagerAdapter {


    private final List<Fragment> fragmentList=new ArrayList<>();
    private final List<String> fragmentListTitle=new ArrayList<>();


    public SplashViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentListTitle.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentListTitle.get (position);
    }
    public void  addFragment(Fragment fragment, String Title){

        fragmentList.add(fragment);
        fragmentListTitle.add(Title);
    };
}


