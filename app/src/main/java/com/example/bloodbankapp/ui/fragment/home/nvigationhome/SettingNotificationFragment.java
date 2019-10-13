package com.example.bloodbankapp.ui.fragment.home.nvigationhome;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.bloodbankapp.R;
import com.example.bloodbankapp.adapter.AdapterGridView;
import com.example.bloodbankapp.data.api.ApiServer;
import com.example.bloodbankapp.data.api.RetrofitClient;
import com.example.bloodbankapp.data.model.GeneralData;
import com.example.bloodbankapp.data.model.generate.GovernoratesAndBloodTypesModel;
import com.example.bloodbankapp.data.model.notificationsSettings.NotificationsSettings;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbankapp.data.local.SharedPreferncesManger.LoadData;
import static com.example.bloodbankapp.data.local.SharedPreferncesManger.USER_API_TOKEN;
import static com.example.bloodbankapp.helper.HelperMathod.ToolBar;

public class SettingNotificationFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appBar_Layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.settingNotification_Fragment_BloodTypeGrid)
    GridView settingNotificationFragmentBloodTypeGrid;
    @BindView(R.id.settingNotification_Fragment_GovernortesGrid)
    GridView settingNotificationFragmentGovernortesGrid;
    @BindView(R.id.settingNotification_Fragment_SaveBtn)
    Button settingNotificationFragmentSaveBtn;


    private View view;
    private ApiServer apiServer;
    // var adapter grid view governortares
    private AdapterGridView adapterGovernortGridView;
    private AdapterGridView adapterBloodTypeGridView;
    private List<GeneralData> governortareGeneratedModelArrayList = new ArrayList<>();

    // var adapter grid view  blood Type
    private List<GeneralData> bloodTypeGeneratedModelArrayList = new ArrayList<>();
    private List<Integer> idBloodType;
    private List<Integer> idGovernorates;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_setting_notifications, container, false);

        unbinder = ButterKnife.bind(this, view);

        // initializer tools
        inti();

        getDataBloodTypeAndGovernorates();

        return view;
    }

    // initializer tools
    private void inti() {

        apiServer = RetrofitClient.getClient().create(ApiServer.class);
        governortareGeneratedModelArrayList = new ArrayList<>();

        idBloodType = new ArrayList<>();
        idGovernorates = new ArrayList<>();
        // add value tool bar
        ToolBar(getFragmentManager(), getActivity(), toolbar, getResources().getString(R.string.setting_notify));

    }

    //get DataNotifyPage and Governorates
    public void getDataBloodTypeAndGovernorates() {
        // get  PaginationData governorates
        apiServer.getNotificationsSettings(LoadData(getActivity(), USER_API_TOKEN)).enqueue(new Callback<NotificationsSettings>() {
            @Override
            public void onResponse(Call<NotificationsSettings> call, Response<NotificationsSettings> response) {

                try {
                    if (response.body().getStatus() == 1) {
                        for (int i = 0; i < response.body().getData().getBloodTypes().size(); i++) {
                            idBloodType.add(Integer.valueOf(response.body().getData().getBloodTypes().get(i)));
                        }
                        for (int i = 0; i < response.body().getData().getGovernorates().size(); i++) {
                            idGovernorates.add(Integer.valueOf(response.body().getData().getGovernorates().get(i)));
                        }
                        // get data Blood Types
                        BloodTypes();
                        // get data Governorate
                        Governorate();
                    }

                } catch (Exception e) {
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<NotificationsSettings> call, Throwable t) {

            }
        });

    }

    // get data Blood_types
    private void BloodTypes() {

            // get data Blood_types
            apiServer.getBlood_types1().enqueue(new Callback<GovernoratesAndBloodTypesModel>() {
                @Override
                public void onResponse(Call<GovernoratesAndBloodTypesModel> call, Response<GovernoratesAndBloodTypesModel> response) {
                    Log.d("response BloodTypes ", response.body().getMsg());
                    try {
                    if (response.body().getStatus() == 1) {

                        bloodTypeGeneratedModelArrayList.addAll(response.body().getData());

                        adapterBloodTypeGridView = new AdapterGridView(getActivity(), bloodTypeGeneratedModelArrayList, idBloodType);
                        settingNotificationFragmentBloodTypeGrid.setAdapter(adapterBloodTypeGridView);
                    } }catch (Exception e){
                    e.getMessage();
                }
                }

                @Override
                public void onFailure(Call<GovernoratesAndBloodTypesModel> call, Throwable t) {

                }
            });

    }

    // get data governorates
    private void Governorate() {

        // get data governorates
        apiServer.getGovernorate1().enqueue(new Callback<GovernoratesAndBloodTypesModel>() {
            @Override
            public void onResponse(Call<GovernoratesAndBloodTypesModel> call, Response<GovernoratesAndBloodTypesModel> response) {

                try {
                    if (response.body().getStatus() == 1) {

                        governortareGeneratedModelArrayList.addAll(response.body().getData());
                        adapterGovernortGridView = new AdapterGridView(getActivity(), governortareGeneratedModelArrayList, idGovernorates);
                        settingNotificationFragmentGovernortesGrid.setAdapter(adapterGovernortGridView);
                    }

                }catch (Exception e){
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<GovernoratesAndBloodTypesModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.settingNotification_Fragment_SaveBtn)
    public void onViewClicked() {

        // get data governorates
        apiServer.ChangeNotificationsSettings(LoadData(getActivity(), USER_API_TOKEN)
                , adapterGovernortGridView.numCheck, adapterBloodTypeGridView.numCheck)
                .enqueue(new Callback<NotificationsSettings>() {
                    @Override
                    public void onResponse(Call<NotificationsSettings> call, Response<NotificationsSettings> response) {
                        Log.d("responses", LoadData(getActivity(), USER_API_TOKEN));

                        try {
                            if (response.body().getStatus() == 1) {
                                Toast.makeText(getActivity(), "Msg" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Status" + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.getMessage();
                        }

                    }

                    @Override
                    public void onFailure(Call<NotificationsSettings> call, Throwable t) {
                        Log.d("responsess", t.getMessage());
                    }
                });
    }


}
