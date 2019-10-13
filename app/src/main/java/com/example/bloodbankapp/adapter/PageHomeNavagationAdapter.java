package com.example.bloodbankapp.adapter;
import com.example.bloodbankapp.ui.fragment.home.articalposts.ArticlesPostFragment;
import com.example.bloodbankapp.ui.fragment.home.donationrequest.DonationRequestsHomeFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PageHomeNavagationAdapter extends FragmentStatePagerAdapter {

    int postion;
    public PageHomeNavagationAdapter(FragmentManager fm, int postion) {
        super(fm);
         this.postion =postion;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){

            case 0:
                return new ArticlesPostFragment();
            case 1:
                return new DonationRequestsHomeFragment();
            default:
                return null;
        }
     }

    @Override
    public int getCount() {
        return postion;
    }
}
