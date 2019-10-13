package com.example.bloodbankapp.ui.fragment.home.donationrequest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bloodbankapp.R;
import com.example.bloodbankapp.adapter.AdapterSpinner;
import com.example.bloodbankapp.data.api.ApiServer;
import com.example.bloodbankapp.data.api.RetrofitClient;
import com.example.bloodbankapp.data.model.bloodtyps.BloodTyps;
import com.example.bloodbankapp.data.model.bloodtyps.DatumBloodTyp;
import com.example.bloodbankapp.data.model.cities.Cities;
import com.example.bloodbankapp.data.model.cities.DatumCities;
import com.example.bloodbankapp.data.model.donationRequests.DonationRequestsModel;
import com.example.bloodbankapp.data.model.generatedModel;
import com.example.bloodbankapp.data.model.governorates.DatumGovernorat;
import com.example.bloodbankapp.data.model.governorates.Governorates;
import com.example.bloodbankapp.ui.activity.MapsActivity;

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
import static com.example.bloodbankapp.helper.HelperMathod.error;
import static com.example.bloodbankapp.helper.HelperMathod.verbose;
import static com.example.bloodbankapp.ui.activity.MapsActivity.longitude;
import static com.example.bloodbankapp.ui.activity.MapsActivity.latitude;

public class AddDonationRequestFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.addDonation_RequestFragment_EditUserName)
    EditText addDonationRequestFragmentEditUserName;
    @BindView(R.id.addDonation_Request_FragmentEditAge)
    EditText addDonationRequestFragmentEditAge;
    @BindView(R.id.addDonation_Request_FragmentBloodTypesSpinner)
    Spinner addDonationRequestFragmentBloodTypesSpinner;
    @BindView(R.id.addDonation_Request_Fragment_EditNumberBag)
    Spinner addDonationRequestFragmentEditNumberBag;
    @BindView(R.id.addDonation_Request_Fragment_NameHospitalEdit)
    EditText addDonationRequestFragmentNameHospitalEdit;
    @BindView(R.id.addDonation_Request_Fragment_AddressHospitalEdit)
    EditText addDonationRequestFragmentAddressHospitalEdit;
    @BindView(R.id.addDonation_Request_Fragment_GovernoratesSpinner)
    Spinner addDonationRequestFragmentGovernoratesSpinner;
    @BindView(R.id.addDonation_Request_Fragment_CiteSpinner)
    Spinner addDonationRequestFragmentCiteSpinner;
    @BindView(R.id.addDonation_Request_Fragment_PhoneEdit)
    EditText addDonationRequestFragmentPhoneEdit;
    @BindView(R.id.addDonation_Request_Fragment_NoteEdit)
    EditText addDonationRequestFragmentNoteEdit;
    @BindView(R.id.addDonation_Fragment_SendBtn)
    Button addDonationFragmentSendBtn;

    @BindView(R.id.addDonation_Request_LocationMapImg)
    ImageView addDonationRequestLocationMapImg;
    ProgressBar addDonationRequestprogressBar;


    private View view;
    private ApiServer apiServer;
    // value adapter city
    private AdapterSpinner adapterSpinner;
    private ArrayList<generatedModel> generatedModelArrayListCity;
    private generatedModel cityGeneratedModel;
    // value adapter governortare
    private AdapterSpinner governortareAdapterSpinner;
    private ArrayList<generatedModel> generatedModelArrayListGovernortare;
    private generatedModel governorateGeneratedModel;
    // value adapter bloodType
    private AdapterSpinner bloodTypeAdapterSpinner;
    private ArrayList<generatedModel> bloodTypeGeneratedModelArrayList;
    private generatedModel bloodTypeGeneratedModel;
    // value adapter Number Bag
    private AdapterSpinner numberBagAdapterSpinner;
    private ArrayList<generatedModel> numberBagGeneratedModelArrayList;
    private generatedModel numberBagGeneratedModel;

    private int idCity;
    private long idBloodTypes;
    private int idGovernorates;

    private int numBagBlood;
    private String EditUserName, EditAge, NameHospitalEdit, AddressHospitalEdit, PhoneEdit, NoteEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_donation_request, container, false);

        unbinder = ButterKnife.bind(this, view);

        // initializer tools
        inti();

        //get DataNotifyPage CityRegiter and Governorates
        getGovernoratfromSever();
        spinnerPloodTyp();
        // add spinner number bag blood
        addNumberBagBlood();

        addDonationRequestprogressBar.setVisibility(View.INVISIBLE);


        return view;
    }

    // initializer tools
    private void inti() {
        addDonationRequestprogressBar = view.findViewById(R.id.addDonation_Request_progressBar);

        apiServer = RetrofitClient.getClient().create(ApiServer.class);
        generatedModelArrayListGovernortare = new ArrayList<>();
        generatedModelArrayListCity = new ArrayList<>();
        bloodTypeGeneratedModelArrayList = new ArrayList<>();
        numberBagGeneratedModelArrayList = new ArrayList<>();


        // add value tool bar
        ToolBar(getFragmentManager(), getActivity(), toolbar, getResources().getString(R.string.donation_request));
    }


    //get DataNotifyPage CityRegiter and Governorates
    public void spinnerPloodTyp(){
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


                    addDonationRequestFragmentBloodTypesSpinner.setAdapter(adapter);
                    addDonationRequestFragmentBloodTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    public void getGovernoratfromSever() {

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
                addDonationRequestFragmentGovernoratesSpinner.setAdapter(adapter);

                addDonationRequestFragmentGovernoratesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position != 0) {
                            setSpinnerCity((idGovern.get(position)));
                        }
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
    }

    private void setSpinnerCity(Integer gavernoratesId) {
        apiServer.getCities(gavernoratesId).enqueue(new Callback<Cities>() {
                    @Override
                    public void onResponse(Call<Cities> call, Response<Cities> response) {
                        List<DatumCities> citiesDatumList = response.body().getData();
                        ArrayList<String> cities = new ArrayList<>();
                        final ArrayList<Integer> citiesId = new ArrayList<>();

                        cities.add(getString(R.string.city));
                        citiesId.add(0);

                        for (int i = 0; i < citiesDatumList.size(); i++) {
                            String cityName = citiesDatumList.get(i).getName();
                            Integer cityId = citiesDatumList.get(i).getId();

                            cities.add(cityName);
                            citiesId.add(cityId);
                        }

                        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                                android.R.layout.simple_spinner_item, cities);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        addDonationRequestFragmentCiteSpinner.setAdapter(adapter);

                        addDonationRequestFragmentCiteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != 0) {
                                    verbose("onCityItemSelected: " + citiesId.get(position));
                                    idCity = citiesId.get(position);
                                }

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<Cities> call, Throwable t) {
                        error("CitiesResponse Onfailure: " + t.getMessage());

                    }
                });
    }

    // add spinner number bag blood
    private void addNumberBagBlood() {
        numberBagGeneratedModelArrayList.add(new generatedModel(0, "Number Bag"));
        numberBagGeneratedModelArrayList.add(new generatedModel(1, "1"));
        numberBagGeneratedModelArrayList.add(new generatedModel(2, "2"));
        numberBagGeneratedModelArrayList.add(new generatedModel(3, "3"));
        numberBagGeneratedModelArrayList.add(new generatedModel(4, "4"));
        numberBagGeneratedModelArrayList.add(new generatedModel(5, "5"));
        numberBagGeneratedModelArrayList.add(new generatedModel(6, "6"));
        numberBagGeneratedModelArrayList.add(new generatedModel(7, "7"));
        numberBagGeneratedModelArrayList.add(new generatedModel(8, "8"));
        numberBagGeneratedModelArrayList.add(new generatedModel(9, "9"));
        numberBagGeneratedModelArrayList.add(new generatedModel(10, "10"));


        numberBagAdapterSpinner = new AdapterSpinner(getContext(), numberBagGeneratedModelArrayList);
        addDonationRequestFragmentEditNumberBag.setAdapter(numberBagAdapterSpinner);
        addDonationRequestFragmentEditNumberBag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    numBagBlood = numberBagGeneratedModelArrayList.get(position).getId();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    //  All Donation Requests
    private void SendDonationRequests() {
        addDonationRequestprogressBar.setVisibility(View.VISIBLE);

        addDonationRequestprogressBar.setVisibility(View.VISIBLE);
        apiServer.CreateDonationRequests(LoadData(getActivity(), USER_API_TOKEN), EditUserName, EditAge, idBloodTypes, numBagBlood, NameHospitalEdit
                , AddressHospitalEdit, idCity, PhoneEdit, NoteEdit, latitude, longitude).enqueue(new Callback<DonationRequestsModel>() {
            @Override
            public void onResponse(Call<DonationRequestsModel> call, Response<DonationRequestsModel> response) {
                addDonationRequestprogressBar.setVisibility(View.VISIBLE);

                if (response.body().getStatus() == 1) {
                    Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    addDonationRequestprogressBar.setVisibility(View.INVISIBLE);
                    clearText();
                } else {
                    Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    addDonationRequestprogressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<DonationRequestsModel> call, Throwable t) {
                addDonationRequestprogressBar.setVisibility(View.INVISIBLE);

            }
        });

    }

    private void clearText() {
        addDonationRequestFragmentEditUserName.getText().clear();
        addDonationRequestFragmentEditAge.getText().clear();
        addDonationRequestFragmentNameHospitalEdit.getText().clear();
        addDonationRequestFragmentAddressHospitalEdit.getText().clear();
        addDonationRequestFragmentPhoneEdit.getText().clear();
        addDonationRequestFragmentNoteEdit.getText().clear();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.addDonation_Fragment_SendBtn)
    public void onViewClicked() {

        getDataEdit();

        SendDonationRequests();
    }

    private void getDataEdit() {
        // get data from ediText
        EditUserName = addDonationRequestFragmentEditUserName.getText().toString();
        EditAge = addDonationRequestFragmentEditAge.getText().toString();
        NameHospitalEdit = addDonationRequestFragmentNameHospitalEdit.getText().toString();
        AddressHospitalEdit = addDonationRequestFragmentAddressHospitalEdit.getText().toString();
        PhoneEdit = addDonationRequestFragmentPhoneEdit.getText().toString();
        NoteEdit = addDonationRequestFragmentNoteEdit.getText().toString();
    }

    @OnClick(R.id.addDonation_Request_LocationMapImg)
    public void onViewClickedLocation() {

        startActivity(new Intent(getActivity(), MapsActivity.class));
    }
}
