package com.example.bloodbankapp.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.bloodbankapp.R;
import com.example.bloodbankapp.adapter.NotificationAdapterRecycler;
import com.example.bloodbankapp.data.api.ApiServer;
import com.example.bloodbankapp.data.api.RetrofitClient;
import com.example.bloodbankapp.data.model.notifications.DataNotify;
import com.example.bloodbankapp.data.model.notifications.Notifications;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbankapp.data.local.SharedPreferncesManger.LoadData;
import static com.example.bloodbankapp.data.local.SharedPreferncesManger.USER_API_TOKEN;
import static com.example.bloodbankapp.helper.HelperMathod.ToolBar;

public class NotificationsFragment extends Fragment {

    @BindView(R.id.notifications_Fragment_ShowPost_RecyclerView)
    RecyclerView notificationsFragmentShowPostRecyclerView;
     ProgressBar notificationsFragmentFavouriteFragmentProgBar;
    Unbinder unbinder;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private NotificationAdapterRecycler notificationAdapterRecycler;

    private ApiServer apiServer;
    private View view;
    private ArrayList<DataNotify> notificationsArrayList;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_notification, container, false);

        unbinder = ButterKnife.bind(this, view);

        inti();

        getNotifications();

        return view;

    }

    // initialize tools
    private void inti() {
        notificationsFragmentFavouriteFragmentProgBar = view.findViewById(R.id.notifications_Fragment_FavouriteFragment_ProgBar);
        notificationsFragmentFavouriteFragmentProgBar.setVisibility(View.INVISIBLE);

        notificationsArrayList = new ArrayList<>();
        apiServer = RetrofitClient.getClient().create(ApiServer.class);

        // add value tool bar
        ToolBar(getFragmentManager(),getActivity(), toolbar, getResources().getString(R.string.notify));

    }


    // get all  post
    private void getNotifications() {
        try {
            // get  PaginationData  post
            apiServer.getNotifications(LoadData(getActivity(), USER_API_TOKEN)).enqueue(new Callback<Notifications>() {
                @Override
                public void onResponse(Call<Notifications> call, Response<Notifications> response) {
                    Log.d(" notifications 5", response.body().getMsg());

                    try {
                        notificationsFragmentFavouriteFragmentProgBar.setVisibility(View.VISIBLE);

                        if (response.body().getStatus() == 1) {

                            notificationsArrayList.addAll(response.body().getDataNotifyPage().getData());

                            notificationAdapterRecycler = new NotificationAdapterRecycler(notificationsArrayList, getActivity());

                            notificationsFragmentShowPostRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                            notificationsFragmentShowPostRecyclerView.setAdapter(notificationAdapterRecycler);

                            notificationsFragmentFavouriteFragmentProgBar.setVisibility(View.INVISIBLE);

                        } else {

                            notificationsFragmentFavouriteFragmentProgBar.setVisibility(View.INVISIBLE);

                            Toast.makeText(getContext(), "Not PaginationData ", Toast.LENGTH_SHORT).show();

                        }

                    }catch (Exception e){
                        e.getMessage();
                    }
                }

                @Override
                public void onFailure(Call<Notifications> call, Throwable t) {
                    Log.d("Throwable",t.getMessage());

                }
            });

        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
