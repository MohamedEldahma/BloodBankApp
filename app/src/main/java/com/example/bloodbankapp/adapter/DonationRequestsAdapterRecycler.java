package com.example.bloodbankapp.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.bloodbankapp.R;
import com.example.bloodbankapp.data.api.ApiServer;
import com.example.bloodbankapp.data.api.RetrofitClient;
import com.example.bloodbankapp.data.model.donationRequestNotifications.DonationRequestNotifications;
import com.example.bloodbankapp.data.model.donationRequests.RequestBloodData;
import com.example.bloodbankapp.ui.fragment.home.donationrequest.ContentDonationFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbankapp.data.local.SharedPreferncesManger.LoadData;
import static com.example.bloodbankapp.data.local.SharedPreferncesManger.USER_API_TOKEN;
import static com.example.bloodbankapp.helper.HelperMathod.getStartFragments;

public class DonationRequestsAdapterRecycler extends RecyclerView.Adapter<DonationRequestsAdapterRecycler.ViewHolder> {

    public static List<RequestBloodData> requestBloodData;


    private ApiServer apiServer;
    Activity context;
    private boolean numFavorite = true;
    private static final Integer CALL = 0x2;

    public DonationRequestsAdapterRecycler(List<RequestBloodData> postsArrayList, Activity context) {
        this.requestBloodData = postsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.set_adapter_donatione_equest, null);
        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        apiServer = RetrofitClient.getClient().create(ApiServer.class);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        viewHolder.setAdapterDonationRequestsNameOfTheCaseTxt.setText(requestBloodData.get(i).getPatientName());
        viewHolder.setAdapterDonationRequestsHospitalTxt.setText(requestBloodData.get(i).getHospitalName());
        viewHolder.setAdapterDonationRequestsCityTxt.setText(requestBloodData.get(i).getCityModel().getName());
        viewHolder.setAdapterDonationRequestsBloodTypeTex.setText(requestBloodData.get(i).getBloodType().getName());
        viewHolder.setAdapterDonationRequestsCallImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                            == PackageManager.PERMISSION_GRANTED) {

                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + requestBloodData.get(i).getPhone()));
                        context.startActivity(callIntent);

                    } else {
                        AslForPermission(CALL);
                    }

                } catch (Exception e) {
                    Log.e("Demo application", "Failed to invoke call", e);
                }
            }
        });


        viewHolder.setAdapterDonationRequestscardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getAllDonationDisplay(requestBloodData.get(i).getId());

                Bundle bundle = new Bundle();
                bundle.putInt("returnResult", 0);
                bundle.putInt("getId", requestBloodData.get(i).getId());

//                bundle.putString("getPatientAge", requestBloodData.get(i).getPatientAge());
//                bundle.putString("getBloodType", requestBloodData.get(i).getBloodType().getName());
//                bundle.putString("getBagsNum", requestBloodData.get(i).getBagsNum());
//                bundle.putString("getHospitalName", requestBloodData.get(i).getHospitalName());
//                bundle.putString("getHospitalAddress", requestBloodData.get(i).getHospitalAddress());
//                bundle.putString("getPhone", requestBloodData.get(i).getPhone());
//                bundle.putString("getNotes", requestBloodData.get(i).getNotes());
//                bundle.putDouble("getLatitude", Double.parseDouble(requestBloodData.get(i).getLatitude()));
//                bundle.putDouble("getLongitude", Double.parseDouble(requestBloodData.get(i).getLongitude()));

                Fragment fragment = new ContentDonationFragment();
                fragment.setArguments(bundle);
                getStartFragments(((FragmentActivity) context).getSupportFragmentManager(), R.id.ReplaceContentAll
                        , fragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return requestBloodData.size();
    }

    // inner class to hold a reference to each item of RecyclerView
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.setAdapter_DonationRequests_DetailsImg)
        ImageView setAdapterDonationRequestsDetailsImg;
        @BindView(R.id.setAdapter_DonationRequests_CallImg)
        ImageView setAdapterDonationRequestsCallImg;
        @BindView(R.id.setAdapter_DonationRequests_NameOfTheCaseTxt)
        TextView setAdapterDonationRequestsNameOfTheCaseTxt;
        @BindView(R.id.setAdapter_DonationRequests_HospitalTxt)
        TextView setAdapterDonationRequestsHospitalTxt;
        @BindView(R.id.setAdapter_DonationRequests_CityTxt)
        TextView setAdapterDonationRequestsCityTxt;

        @BindView(R.id.setAdapter_Donation_RequestsBloodTypeTex)
        TextView setAdapterDonationRequestsBloodTypeTex;
        @BindView(R.id.setAdapter_Donation_RequestscardView)
        CardView setAdapterDonationRequestscardView;

        View view;

        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemLayoutView;
            ButterKnife.bind(this, view);
        }
    }


    private void AslForPermission(Integer requestCode) {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.CALL_PHONE)) {

                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CALL_PHONE}, requestCode);

            } else {

                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CALL_PHONE}, requestCode);

            }
        } else {

            Toast.makeText(context, "" + Manifest.permission.CALL_PHONE + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }

    //  All Donation Requests
    private void getAllDonationDisplay(int donation_id) {


        apiServer.getAllDonationDisplay(LoadData(context, USER_API_TOKEN),
                donation_id).enqueue(new Callback<DonationRequestNotifications>() {
            @Override
            public void onResponse(Call<DonationRequestNotifications> call, Response<DonationRequestNotifications> response) {

                if (response.body().getStatus() == 1) {
                    Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Not Pagination DataNotifyPage", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DonationRequestNotifications> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


}
