package com.example.bloodbankapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.bloodbankapp.R;
import com.example.bloodbankapp.adapter.PageHomeNavagationAdapter;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment {


    @BindView(R.id.home_Navgation_TabBar)
    TabLayout homeNavgationTabBar;
    @BindView(R.id.home_Navgation_ViewPager)
    ViewPager homeNavgationViewPager;
    Unbinder unbinder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        unbinder = ButterKnife.bind(this, view);

        initTabBar();

        return view;

    }


    private void initTabBar() {

        homeNavgationTabBar.addTab(homeNavgationTabBar.newTab().setText(getResources().getString(R.string.articles)));
        homeNavgationTabBar.addTab(homeNavgationTabBar.newTab().setText(getResources().getString(R.string.requst_donation)));

        homeNavgationTabBar.setTabGravity(TabLayout.GRAVITY_FILL);

        PageHomeNavagationAdapter pageHomeNavagationAdapter = new PageHomeNavagationAdapter(getFragmentManager(), homeNavgationTabBar.getTabCount());
        homeNavgationViewPager.setAdapter(pageHomeNavagationAdapter);

        int tabIconColor = ContextCompat.getColor(getContext(), R.color.color_tabbar_line_select);
        homeNavgationTabBar.setSelectedTabIndicatorColor(tabIconColor);

        homeNavgationTabBar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                homeNavgationViewPager.setCurrentItem(tab.getPosition());

                int tabIconColor = ContextCompat.getColor(getContext(), R.color.color_tabbar_line_select);
                homeNavgationTabBar.setSelectedTabIndicatorColor(tabIconColor);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(getContext(), R.color.white);
                homeNavgationTabBar.setSelectedTabIndicatorColor(tabIconColor);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        homeNavgationViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(homeNavgationTabBar));


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
