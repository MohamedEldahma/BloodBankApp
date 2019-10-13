package com.example.bloodbankapp.ui.fragment.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bloodbankapp.R;
import com.example.bloodbankapp.data.api.ApiServer;
import com.example.bloodbankapp.data.api.RetrofitClient;
import com.example.bloodbankapp.data.model.bloodtyps.BloodTyps;
import com.example.bloodbankapp.data.model.bloodtyps.DatumBloodTyp;
import com.example.bloodbankapp.data.model.cities.Cities;
import com.example.bloodbankapp.data.model.cities.DatumCities;
import com.example.bloodbankapp.data.model.governorates.DatumGovernorat;
import com.example.bloodbankapp.data.model.governorates.Governorates;
import com.example.bloodbankapp.data.model.register.Register;
import com.example.bloodbankapp.helper.CalendrHelper;

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

import static com.example.bloodbankapp.helper.HelperMathod.checkCorrespondPassword;
import static com.example.bloodbankapp.helper.HelperMathod.checkLengthPassword;
import static com.example.bloodbankapp.helper.HelperMathod.error;
import static com.example.bloodbankapp.helper.HelperMathod.verbose;

public class RegisterFragment extends Fragment {


    Unbinder unbinder;

    @BindView(R.id.register_Fragment_EditUserName)
    EditText registerFragmentEditUserName;
    @BindView(R.id.register_Fragment_EditEmail)
    EditText registerFragmentEditEmail;
    @BindView(R.id.register_Fragment_EditDataOfBirth)
    EditText registerFragmentEditDataOfBirth;
    @BindView(R.id.registerFragmentBloodTypesSpinner)
    Spinner registerFragmentBloodTypesSpinner;
    @BindView(R.id.register_Fragment_DataLastDonation)
    EditText registerFragmentDataLastDonation;
    @BindView(R.id.register_Fragment_GovernoratesSpinner)
    Spinner registerFragmentGovernoratesSpinner;
    @BindView(R.id.register_Fragment_CiteSpinner)
    Spinner registerFragmentCiteSpinner;
    @BindView(R.id.register_Fragment_PasswordEdit)
    EditText registerFragmentPasswordEdit;
    @BindView(R.id.register_Fragment_Emphasis_PasswordEdit)
    EditText registerFragmentEmphasisPasswordEdit;
    @BindView(R.id.register_Fragment_PhoneEdit)
    EditText registerFragmentPhoneEdit;
    @BindView(R.id.register_Fragment_RegisterBtn)
    Button registerFragmentRegisterBtn;
    @BindView(R.id.redister_Fragment_TolBr)
    Toolbar redisterFragmentTolBr;


    ApiServer apiServer;
    @BindView(R.id.progress_Bar)
    ProgressBar progressBar;


    private int idCity;
    private long idBloodTypes;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registers, container, false);
        //

        unbinder = ButterKnife.bind(this, view);
        apiServer = RetrofitClient.getClient().create(ApiServer.class);
        spinnerPloodTyp();
        getGovernoratfromSever();
        return view;
    }


    public void spinnerPloodTyp() {
        apiServer.getBlood_types().enqueue(new Callback<BloodTyps>() {
            @Override
            public void onResponse(Call<BloodTyps> call, Response<BloodTyps> response) {
                List<DatumBloodTyp> bloodTypesData = response.body().getData();
                ArrayList<String> typBlood = new ArrayList<>();
                final ArrayList<Integer> idBlood = new ArrayList<Integer>();
                typBlood.add(getString(R.string.blood_type));
                idBlood.add(0);
                for (int i = 0; i < bloodTypesData.size(); i++) {
                    String bloodTypNAme = bloodTypesData.get(i).getName();
                    Integer bloodTypId = bloodTypesData.get(i).getId();
                    typBlood.add(bloodTypNAme);
                    idBlood.add(bloodTypId);

                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_spinner_item, typBlood);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                    registerFragmentBloodTypesSpinner.setAdapter(adapter);
                    registerFragmentBloodTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                governorat.add(getString(R.string.governorate));
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
                registerFragmentGovernoratesSpinner.setAdapter(adapter);

                registerFragmentGovernoratesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        apiServer.getCities(gavernoratesId)
                .enqueue(new Callback<Cities>() {
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

                        registerFragmentCiteSpinner.setAdapter(adapter);

                        registerFragmentCiteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.register_Fragment_RegisterBtn)
    public void onViewClicked() {

        if (checkLengthPassword(registerFragmentPasswordEdit.getText().toString())
                && checkCorrespondPassword(registerFragmentPasswordEdit.getText().toString(), registerFragmentEmphasisPasswordEdit.getText().toString())) {
             apiServer.onRegister(
                    registerFragmentEditUserName.getText().toString(), registerFragmentEditEmail.getText().toString(),
                    registerFragmentEditDataOfBirth.getText().toString(), idCity,
                    registerFragmentPhoneEdit.getText().toString(), registerFragmentDataLastDonation.getText().toString(),
                    registerFragmentPasswordEdit.getText().toString(), registerFragmentEmphasisPasswordEdit.getText().toString()
                    , idBloodTypes).enqueue(new Callback<Register>() {

                @Override
                public void onResponse(Call<Register> call, Response<Register> response) {
                    String msg = response.body().getMsg();

                    if (response.body().getStatus() == 1) {
//                        Intent intent = new Intent(getContext(), HomeNavgation.class);
//                        startActivity(intent);
                        Toast.makeText(getContext(), "tru"+msg, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "false"+msg, Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onFailure(Call<Register> call, Throwable t) {
                    Toast.makeText(getContext(), "eroor", Toast.LENGTH_SHORT).show();

                }
            });
        } else {


            if (!checkLengthPassword(registerFragmentPasswordEdit.getText().toString())) {
                registerFragmentEmphasisPasswordEdit.setError(getResources().getString(R.string.It_should_bethe_largest6));
            }
            if (!checkCorrespondPassword(registerFragmentPasswordEdit.getText().toString(), registerFragmentEmphasisPasswordEdit.getText().toString())) {
                registerFragmentEmphasisPasswordEdit.setError(getResources().getString(R.string.number_does_not_correspond));
            }
        }

    }


    @OnClick({R.id.register_Fragment_EditDataOfBirth, R.id.register_Fragment_DataLastDonation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_Fragment_EditDataOfBirth:
                CalendrHelper calendrHelper = new CalendrHelper(getContext());
                calendrHelper.showCalendr(registerFragmentEditDataOfBirth);

                break;
            case R.id.register_Fragment_DataLastDonation:
                CalendrHelper calendrHelper1 = new CalendrHelper(getContext());
                calendrHelper1.showCalendr(registerFragmentDataLastDonation);
                break;
        }
    }
}
