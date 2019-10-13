package com.example.bloodbankapp.ui.fragment.home.donationrequest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bloodbankapp.R;
import com.example.bloodbankapp.adapter.AdapterSpinner;
import com.example.bloodbankapp.adapter.DonationRequestsAdapterRecycler;
import com.example.bloodbankapp.data.api.ApiServer;
import com.example.bloodbankapp.data.api.RetrofitClient;
import com.example.bloodbankapp.data.model.bloodtyps.BloodTyps;
import com.example.bloodbankapp.data.model.bloodtyps.DatumBloodTyp;
import com.example.bloodbankapp.data.model.donationRequests.DonationRequestsModel;
import com.example.bloodbankapp.data.model.donationRequests.RequestBloodData;
import com.example.bloodbankapp.data.model.generatedModel;
import com.example.bloodbankapp.data.model.governorates.DatumGovernorat;
import com.example.bloodbankapp.data.model.governorates.Governorates;
import com.example.bloodbankapp.helper.OnEndless;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbankapp.data.local.SharedPreferncesManger.LoadData;
import static com.example.bloodbankapp.data.local.SharedPreferncesManger.USER_API_TOKEN;
import static com.example.bloodbankapp.helper.HelperMathod.error;
import static com.example.bloodbankapp.helper.HelperMathod.verbose;

public class DonationRequestsHomeFragment extends Fragment {
    View view;
    @BindView(R.id.donationRequests_Fragment_SearchImgBtn)
    ImageButton donationRequestsFragmentSearchImgBtn;
    @BindView(R.id.donationRequests_Fragment_CitySpn)
    Spinner donationRequestsFragmentCitySpn;
    @BindView(R.id.donationRequests_BloodTypeSpn)
    Spinner donationRequestsBloodTypeSpn;
    private ProgressBar donationRequestsFragmentProgressBar;
    Unbinder unbinder;
    @BindView(R.id.donationRequests_Fragment_ShowRequest_RecyclerView)
    RecyclerView donationRequestsFragmentShowRequestRecyclerView;

    private ApiServer apiServer;
    private boolean checkFilterPost = true;
    private int max = 0;
    private DonationRequestsAdapterRecycler donationRequestsAdapterRecycler;
    private ArrayList<RequestBloodData> donationRequestsModleArrayList;
    private ArrayList<generatedModel> generatedModelArrayListGovernortare;
    private generatedModel governorateGeneratedModel;
    private AdapterSpinner adapterSpinner;


    private AdapterSpinner bloodTypeAdapterSpinner;
    private ArrayList<DatumBloodTyp> bloodTypeGeneratedModelArrayList;
    private DatumBloodTyp bloodTypeGeneratedModel;

    private long idGovernorates;
    private long idBloodTypes;
    private SwipeRefreshLayout donationRequestsFragmentShowRequestSwipeRefresh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_donation_request, container, false);

        unbinder = ButterKnife.bind(this, view);

        inti();

        getDataBloodType();

        onEndless();

        OnClickAllTools();

        SwipeRefresh();

        return view;
    }


    // initialize tools
    private void inti() {
        donationRequestsFragmentProgressBar = view.findViewById(R.id.donationRequests_Fragment_ProgressBar);
        donationRequestsFragmentShowRequestSwipeRefresh = view.findViewById(R.id.donationRequests_Fragment_ShowRequest_SwipeRefresh);

        donationRequestsFragmentProgressBar.setVisibility(View.INVISIBLE);

        donationRequestsModleArrayList = new ArrayList<>();
        bloodTypeGeneratedModelArrayList = new ArrayList<>();
        generatedModelArrayListGovernortare = new ArrayList<>();
        apiServer = RetrofitClient.getClient().create(ApiServer.class);


    }

    // listener from count items  recyclerView
    private void onEndless() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        donationRequestsFragmentShowRequestRecyclerView.setLayoutManager(linearLayoutManager);

        OnEndless onEndless = new OnEndless(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= max || max != 0 || current_page == 1) {
                    if (checkFilterPost) {
                        getAllDonationRequests(current_page);
                    } else {
                        geDonationRequestsFilter(current_page);
                    }
                }
            }
        };

        donationRequestsFragmentShowRequestRecyclerView.addOnScrollListener(onEndless);
        donationRequestsAdapterRecycler = new DonationRequestsAdapterRecycler(donationRequestsModleArrayList, getActivity());
        donationRequestsFragmentShowRequestRecyclerView.setAdapter(donationRequestsAdapterRecycler);

        getAllDonationRequests(1);

    }

    // get data GovernorateRegiter And Blood Type Spinner
    private void getDataBloodType() {


        apiServer.getGovernorate().enqueue(new Callback<Governorates>() {
            @Override
            public void onResponse(Call<Governorates> call, Response<Governorates> response) {
                List<DatumGovernorat> governoratesDatumList = response.body().getData();
                ArrayList<String> governorat = new ArrayList<>();
                final ArrayList<Integer> idGovern = new ArrayList<>();
                governorat.add(getString(R.string.governorates));
                idGovern.add(0);

                for (int i = 0; i < governoratesDatumList.size(); i++) {

                    String governorateName = governoratesDatumList.get(i).getName();
                    Integer governoratId = governoratesDatumList.get(i).getId();
                    governorat.add(governorateName);
                    idGovern.add(governoratId);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item, governorat);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                donationRequestsBloodTypeSpn.setAdapter(adapter);

                donationRequestsBloodTypeSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        verbose("onItemSelected: " + parent.getItemAtPosition(position));
                        idGovernorates= parent.getItemIdAtPosition(position);
                        }


                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }


            @Override
            public void onFailure(Call<Governorates> call, Throwable t) {
                error("GavernoratesResponse Onfailure: " + t.getMessage());



            }

        });
        apiServer.getBlood_types().enqueue(new Callback<BloodTyps>() {
            @Override
            public void onResponse(Call<BloodTyps> call, Response<BloodTyps> response) {
                List<DatumBloodTyp> bloodTypesData=response.body().getData();
                ArrayList<String>typBlood=new ArrayList<>();
                final ArrayList<Integer>idBlood=new ArrayList<Integer>();
                typBlood.add(getString(R.string.blood_type));
                idBlood.add(0);
                for (int i=0;i<bloodTypesData.size();i++){
                    String bloodTypNAme = bloodTypesData.get(i).getName();
                    Integer bloodTypId  = bloodTypesData.get(i).getId();
                    typBlood.add(bloodTypNAme);
                    idBlood.add(bloodTypId);

                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_spinner_item, typBlood);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                    donationRequestsBloodTypeSpn.setAdapter(adapter);
                    donationRequestsBloodTypeSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            verbose("onItemSelected: " + parent.getItemAtPosition(position));
                            idBloodTypes = parent.getItemIdAtPosition(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }




            }

            @Override
            public void onFailure(Call<BloodTyps> call, Throwable t) {

            }
        });
    }

    //  Donation Requests Filter
    private void geDonationRequestsFilter(int current_page) {

        donationRequestsFragmentProgressBar.setVisibility(View.VISIBLE);

        apiServer.getDonationRequestsFilter(LoadData(getActivity(), USER_API_TOKEN), String.valueOf(idBloodTypes), idGovernorates,current_page).enqueue(new Callback<DonationRequestsModel>() {
            @Override
            public void onResponse(Call<DonationRequestsModel> call, Response<DonationRequestsModel> response) {
                try {
                    Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    if (response.body().getStatus() == 1) {

                        if (response.body().getData().getTotal() == 0) {
                            Toast.makeText(getContext(), "Not Pagination DataNotifyPage", Toast.LENGTH_SHORT).show();
                        }
                        // Clear all data list
                        donationRequestsModleArrayList.clear();
                        // notify DataNotifyPage Set Changed
                        donationRequestsAdapterRecycler.notifyDataSetChanged();
                        // add All
                        donationRequestsModleArrayList.addAll(response.body().getData().getRequestBloodData());
                        // notify DataNotifyPage Set Changed
                        donationRequestsAdapterRecycler.notifyDataSetChanged();
                        //  set Visibility INVISIBLE
                        donationRequestsFragmentProgressBar.setVisibility(View.INVISIBLE);

                    } else {

                        Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        donationRequestsFragmentProgressBar.setVisibility(View.INVISIBLE);

                    }
                } catch (Exception e) {

                    e.getMessage();
                }


            }

            @Override
            public void onFailure(Call<DonationRequestsModel> call, Throwable t) {
                donationRequestsFragmentProgressBar.setVisibility(View.INVISIBLE);

            }
        });

    }

    //  All Donation Requests
    private void getAllDonationRequests(int current_page) {

        donationRequestsFragmentProgressBar.setVisibility(View.VISIBLE);

        apiServer.getDonationRequests(LoadData(getActivity(), USER_API_TOKEN), current_page).enqueue(new Callback<DonationRequestsModel>() {
            @Override
            public void onResponse(Call<DonationRequestsModel> call, Response<DonationRequestsModel> response) {

                try {
                    if (response.body().getStatus() == 1) {

                        if (response.body().getData().getTotal() == 0) {
                            Toast.makeText(getContext(), "Not Pagination DataNotifyPage", Toast.LENGTH_SHORT).show();
                        }
                        max = response.body().getData().getLastPage();

                        donationRequestsModleArrayList.addAll(response.body().getData().getRequestBloodData());

                        donationRequestsAdapterRecycler.notifyDataSetChanged();

                        donationRequestsFragmentProgressBar.setVisibility(View.INVISIBLE);
                    } else {
                        Toast.makeText(getContext(), "Not Pagination DataNotifyPage", Toast.LENGTH_SHORT).show();
                        donationRequestsFragmentProgressBar.setVisibility(View.INVISIBLE);
                    }

                }catch (Exception e){
                    e.getMessage();
                }

            }

            @Override
            public void onFailure(Call<DonationRequestsModel> call, Throwable t) {
                donationRequestsFragmentProgressBar.setVisibility(View.INVISIBLE);

            }
        });

    }

    // this is method all in click
    private void OnClickAllTools() {
        // edit text search keyword
        donationRequestsFragmentSearchImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (donationRequestsFragmentCitySpn.getSelectedItemPosition() ==
                        0 && donationRequestsBloodTypeSpn.getSelectedItemPosition() == 0 && !checkFilterPost) {

                    donationRequestsAdapterRecycler = new DonationRequestsAdapterRecycler(donationRequestsModleArrayList, getActivity());
                    donationRequestsFragmentShowRequestRecyclerView.setAdapter(donationRequestsAdapterRecycler);
                    checkFilterPost = true;

                } else {

                    donationRequestsAdapterRecycler = new DonationRequestsAdapterRecycler(donationRequestsModleArrayList, getActivity());
                    donationRequestsFragmentShowRequestRecyclerView.setAdapter(donationRequestsAdapterRecycler);
                    checkFilterPost = false;

                    geDonationRequestsFilter(1);

                }

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //  swipeRefresh All list
    private void SwipeRefresh() {

        donationRequestsFragmentShowRequestSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllDonationRequests(1);

                donationRequestsFragmentShowRequestSwipeRefresh.setRefreshing(true);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                donationRequestsFragmentShowRequestSwipeRefresh.setRefreshing(false);

            }
        });
    }
}
