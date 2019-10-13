package com.example.bloodbankapp.ui.fragment.home.donationrequest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodbankapp.R;
import com.example.bloodbankapp.data.api.ApiServer;
import com.example.bloodbankapp.data.api.RetrofitClient;
import com.example.bloodbankapp.data.model.donationRequestNotifications.DonationRequestNotifications;
import com.example.bloodbankapp.helper.GPSTracker;
import com.example.bloodbankapp.ui.activity.MapsShowDonationActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.AppBarLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbankapp.data.local.SharedPreferncesManger.LoadData;
import static com.example.bloodbankapp.data.local.SharedPreferncesManger.USER_API_TOKEN;
import static com.example.bloodbankapp.helper.HelperMathod.ToolBar;

public class ContentDonationFragment extends Fragment implements OnMapReadyCallback {
    View view;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appBar_Layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.fragment_ContentDonation_NameTxt)
    TextView fragmentContentDonationNameTxt;
    @BindView(R.id.fragment_ContentDonation_AgeTxt)
    TextView fragmentContentDonationAgeTxt;
    @BindView(R.id.fragment_ContentDonation_BloodTypeTxt)
    TextView fragmentContentDonationBloodTypeTxt;
    @BindView(R.id.fragment_ContentDonation_NumTxt)
    TextView fragmentContentDonationNumTxt;
    @BindView(R.id.fragment_ContentDonation_HospitalTxt)
    TextView fragmentContentDonationHospitalTxt;
    @BindView(R.id.fragment_ContentDonation_HospitalAddressTxt)
    TextView fragmentContentDonationHospitalAddressTxt;
    @BindView(R.id.fragment_ContentDonation_NumberPhoneTxt)
    TextView fragmentContentDonationNumberPhoneTxt;
    @BindView(R.id.fragment_ContentDonation_DetailsTxt)
    TextView fragmentContentDonationDetailsTxt;
    @BindView(R.id.fragment_ContentDonation_CallBtn)
    Button fragmentContentDonationCallBtn;
    Unbinder unbinder;
    @BindView(R.id.fragment_ContentDonation_Map)
    MapView fragmentContentDonationMap;
    @BindView(R.id.fragment_ContentDonation_MapBtn)
    Button fragmentContentDonationMapBtn;
    @BindView(R.id.content_Donation_progressBar)
    ProgressBar contentDonationprogressBar;

    @BindView(R.id.scrollView3)
    ScrollView scrollView3;
    private boolean myCondition = true;
    private String getPatientName, getPhone;
    static double getLatitude, getLongitude;
    private static final Integer CALL = 0x2;
    private GoogleMap map;

    // GPSTracker class
    GPSTracker gps;

    private LatLng frLatLng;
    private double latitude, longitude;
    private String getDonationRequestId;

    ApiServer apiServer;
    private int returnResult = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_content_donations, container, false);


        unbinder = ButterKnife.bind(this, view);

        apiServer = RetrofitClient.getClient().create(ApiServer.class);


        getDataReturnDetails();

        fragmentContentDonationMap.onCreate(savedInstanceState);

        // GPS Tracker
        //GPSTrackerS();

        // map view
        MapViews();

        // on cleck button show map
        fragmentContentDonationMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "map", Toast.LENGTH_SHORT).show();

                Intent map = new Intent(getContext(), MapsShowDonationActivity.class);
                map.putExtra("latitudePoint", getLatitude);
                map.putExtra("longitudePoint", getLongitude);
                startActivity(map);

            }
        });


        return view;
    }

    private void GPSTrackerS() {

        gps = new GPSTracker(getActivity(), getActivity());

        // Check if GPS enabled
        if (gps.getIsGPSTrackingEnabled()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            //   is for new line
            Toast.makeText(getContext(), "Your Location is - \n Lat: "
                    + latitude + "\n Long: " + longitude, Toast.LENGTH_LONG).show();

        } else {
            // Can't get location.
            // GPS or network is not enabled.
            // Ask user to enable GPS/network in settings.
            gps.showSettingsAlert();
        }

    }

    private void MapViews() {
        // Create class object
        fragmentContentDonationMap.getMapAsync(this);

    }


    private void getDataReturnDetails() {

        // get data Donation Requests Adapter Recycler returnResult == 0   and return notification adapter   returnResult == 1
        Bundle bundle = getArguments();
        if (bundle != null) {
            returnResult = bundle.getInt("returnResult");
            if (returnResult == 0) {
                // get change is read Notification and get data notify
                getAllDonationDisplay(bundle.getInt("getId"));

            } else if (returnResult == 1) {
                // get data Notification Requests Adapter Recycler
                getDonationRequestId = bundle.getString("getDonationRequestId");
                // get change is read Notification and get data notify
                assert getDonationRequestId != null;
                getAllDonationDisplay(Integer.parseInt(getDonationRequestId));

            }
        }

        fragmentContentDonationCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE)
                            == PackageManager.PERMISSION_GRANTED) {

                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + getPhone));
                        startActivity(callIntent);

                    } else {
                        AslForPermission(CALL);
                    }

                } catch (Exception e) {
                    Log.e("Demo application", "Failed to invoke call" + e.getMessage());
                }
            }
        });


    }

    //  All Donation Requests
    private void getAllDonationDisplay(int donation_id) {
        contentDonationprogressBar.setVisibility(View.VISIBLE);
        apiServer.getAllDonationDisplay(LoadData(getActivity(), USER_API_TOKEN),
                donation_id).enqueue(new Callback<DonationRequestNotifications>() {
            @Override
            public void onResponse(Call<DonationRequestNotifications> call, Response<DonationRequestNotifications> response) {

                try {
                    if (response.body().getStatus() == 1) {
                        getPatientName = response.body().getData().getPatientName();
                        getPhone = response.body().getData().getPhone();

                        fragmentContentDonationNameTxt.setText(getPatientName);
                        fragmentContentDonationAgeTxt.setText(response.body().getData().getPatientAge());
                        fragmentContentDonationBloodTypeTxt.setText(response.body().getData().getBloodType().getName());
                        fragmentContentDonationNumTxt.setText(response.body().getData().getBagsNum());
                        fragmentContentDonationHospitalTxt.setText(response.body().getData().getHospitalName());
                        fragmentContentDonationHospitalAddressTxt.setText(response.body().getData().getHospitalAddress());
                        fragmentContentDonationNumberPhoneTxt.setText(getPhone);
                        fragmentContentDonationDetailsTxt.setText(response.body().getData().getNotes());

                        getLatitude = Double.parseDouble(response.body().getData().getLatitude());
                        getLongitude = Double.parseDouble(response.body().getData().getLongitude());

                        // add value tool bar
                        ToolBar(getFragmentManager(), getActivity(), toolbar, getResources().getString(R.string.donation_request) + ": " + getPatientName);
                        contentDonationprogressBar.setVisibility(View.INVISIBLE);


                        //   Toast.makeText(getActivity(),getLongitude+""+ response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Not Pagination DataNotifyPage", Toast.LENGTH_SHORT).show();
                        contentDonationprogressBar.setVisibility(View.INVISIBLE);

                    }
                } catch (Exception e) {
                    e.getMessage();
                }

            }

            @Override
            public void onFailure(Call<DonationRequestNotifications> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void AslForPermission(Integer requestCode) {

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CALL_PHONE)) {

                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, requestCode);

            } else {

                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, requestCode);

            }
        } else {

            Toast.makeText(getActivity(), "" + Manifest.permission.CALL_PHONE + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.tab_menu, menu);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        // get lat and long
        frLatLng = new LatLng(getLatitude, getLongitude);

        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);

        if (ActivityCompat.checkSelfPermission(getContext()
                , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        map.addMarker(new MarkerOptions().position(frLatLng)).setTitle(getString(R.string.my_location));
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(frLatLng, 520));

    }


    @Override
    public void onResume() {
        fragmentContentDonationMap.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        fragmentContentDonationMap.onPause();
    }


}
