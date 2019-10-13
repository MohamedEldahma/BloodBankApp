package com.example.bloodbankapp.ui.fragment.home.nvigationhome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bloodbankapp.R;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.bloodbankapp.helper.HelperMathod.ToolBar;

public class AboutAppFragment extends Fragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Unbinder unbinder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view = inflater.inflate(R.layout.fragment_about_app, container, false);

        unbinder = ButterKnife.bind(this, view);
        
        // add value tool bar
        ToolBar(getFragmentManager(),getActivity(), toolbar, getResources().getString(R.string.about));


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
