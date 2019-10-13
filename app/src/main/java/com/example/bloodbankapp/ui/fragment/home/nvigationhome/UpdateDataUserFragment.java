package com.example.bloodbankapp.ui.fragment.home.nvigationhome;

import android.os.Bundle;
import android.util.Log;
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
import com.example.bloodbankapp.data.model.getdataprofil.GetDataProfil;
import com.example.bloodbankapp.data.model.governorates.DatumGovernorat;
import com.example.bloodbankapp.data.model.governorates.Governorates;
import com.example.bloodbankapp.data.model.updateprofil.UpdateProfil;
import com.example.bloodbankapp.helper.CalendrHelper;
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

import static com.example.bloodbankapp.data.local.SharedPreferncesManger.Key_password;
import static com.example.bloodbankapp.data.local.SharedPreferncesManger.LoadData;
import static com.example.bloodbankapp.data.local.SharedPreferncesManger.USER_API_TOKEN;
import static com.example.bloodbankapp.helper.HelperMathod.ToolBar;
import static com.example.bloodbankapp.helper.HelperMathod.checkCorrespondPassword;
import static com.example.bloodbankapp.helper.HelperMathod.checkLengthPassword;
import static com.example.bloodbankapp.helper.HelperMathod.error;
import static com.example.bloodbankapp.helper.HelperMathod.verbose;

public class UpdateDataUserFragment extends Fragment {

    @BindView(R.id.updateDataUser_Fragment_EditUserName)
    EditText updateDataUserFragmentEditUserName;
    @BindView(R.id.updateDataUser_Fragment_EditEmail)
    EditText updateDataUserFragmentEditEmail;
    @BindView(R.id.updateDataUser_Fragment_EditDataOfBirth)
    EditText updateDataUserFragmentEditDataOfBirth;
    @BindView(R.id.updateDataUser_Fragment_BloodTypes_Spinner)
    Spinner updateDataUserFragmentBloodTypesSpinner;
    @BindView(R.id.updateDataUser_Fragment_DataLastDonation)
    EditText updateDataUserFragmentDataLastDonation;
    @BindView(R.id.updateDataUser_Fragment_GovernoratesSpinner)
    Spinner updateDataUserFragmentGovernoratesSpinner;
    @BindView(R.id.updateDataUser_Fragment_CiteSpinner)
    Spinner updateDataUserFragmentCiteSpinner;
    @BindView(R.id.updateDataUser_Fragment_PasswordEdit)
    EditText updateDataUserFragmentPasswordEdit;
    @BindView(R.id.updateDataUser_Fragment_Emphasis_PasswordEdit)
    EditText updateDataUserFragmentEmphasisPasswordEdit;
    @BindView(R.id.updateDataUser_Fragment_PhoneEdit)
    EditText updateDataUserFragmentPhoneEdit;
    @BindView(R.id.updateDataUser_Fragment_Update_DataUserBtn)
    Button updateDataUserFragmentUpdateDataUserBtn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Unbinder unbinder;
    @BindView(R.id.appBar_Layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.progress_Bar)
    ProgressBar progressBar;
//    private ProgressBar progressBar;
    private View view;
    ApiServer apiServer;
    private int idCity;
    private long idBloodTypes;
    private Integer returnIcCity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_updata_data_users, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiServer = RetrofitClient.getClient().create(ApiServer.class);
        inti();
        progressBar.setVisibility(View.INVISIBLE);
        getDataBloodTyp();
        getGovernoratfromSever();
        GetDataProfile();

        return view;
    }

    private void inti() {
        // add value tool bar
        ToolBar(getFragmentManager(), getActivity(), toolbar, getResources().getString(R.string.modify_the_data));

    }


    public void getDataBloodTyp() {
        // get  Blood types
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


                    updateDataUserFragmentBloodTypesSpinner.setAdapter(adapter);
                    updateDataUserFragmentBloodTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        // get  PaginationData governorates
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
                updateDataUserFragmentGovernoratesSpinner.setAdapter(adapter);

                updateDataUserFragmentGovernoratesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        // get  PaginationData cities
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

                updateDataUserFragmentCiteSpinner.setAdapter(adapter);

                updateDataUserFragmentCiteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    @OnClick(R.id.updateDataUser_Fragment_Update_DataUserBtn)
    public void onViewClicked() {

        if (checkLengthPassword(updateDataUserFragmentPasswordEdit.getText().toString())
                && checkCorrespondPassword(updateDataUserFragmentPasswordEdit.getText().toString(), updateDataUserFragmentEmphasisPasswordEdit.getText().toString())) {
            Call<UpdateProfil> call = apiServer.onUpdate(
                    updateDataUserFragmentEditUserName.getText().toString(), updateDataUserFragmentEditEmail.getText().toString(),
                    updateDataUserFragmentEditDataOfBirth.getText().toString(), idCity,
                    updateDataUserFragmentPhoneEdit.getText().toString(), updateDataUserFragmentDataLastDonation.getText().toString(),
                    updateDataUserFragmentPasswordEdit.getText().toString(), updateDataUserFragmentEmphasisPasswordEdit.getText().toString()
                    , idBloodTypes, LoadData(getActivity(), USER_API_TOKEN));
            call.enqueue(new Callback<UpdateProfil>() {

                @Override
                public void onResponse(Call<UpdateProfil> call, Response<UpdateProfil> response) {
                    Log.d("response", response.body().getMsg());
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();


                    }
                }

                @Override
                public void onFailure(Call<UpdateProfil> call, Throwable t) {
                    Toast.makeText(getContext(), "EROOOOR", Toast.LENGTH_SHORT).show();


                }
            });
        } else {


            if (!checkLengthPassword(updateDataUserFragmentPasswordEdit.getText().toString())) {
                updateDataUserFragmentEmphasisPasswordEdit.setError(getResources().getString(R.string.It_should_bethe_largest6));
            }
            if (!checkCorrespondPassword(updateDataUserFragmentPasswordEdit.getText().toString(), updateDataUserFragmentEmphasisPasswordEdit.getText().toString())) {
                updateDataUserFragmentEmphasisPasswordEdit.setError(getResources().getString(R.string.number_does_not_correspond));
            }
        }

    }


    public void GetDataProfile() {
        apiServer.getProfile(LoadData(getActivity(), USER_API_TOKEN)).enqueue(new Callback<GetDataProfil>() {

            @Override
            public void onResponse(Call<GetDataProfil> call, Response<GetDataProfil> response) {
                Log.d("response", response.body().getMsg());
                if (response.body().getStatus() == 1) {
                    updateDataUserFragmentEditUserName.setText(response.body().getData().getClient().getName());
                    updateDataUserFragmentEditEmail.setText(response.body().getData().getClient().getEmail());
                    updateDataUserFragmentEditDataOfBirth.setText(response.body().getData().getClient().getBirthDate());
                    updateDataUserFragmentDataLastDonation.setText(response.body().getData().getClient().getDonationLastDate());
                    updateDataUserFragmentPhoneEdit.setText(response.body().getData().getClient().getPhone());
                    updateDataUserFragmentPasswordEdit.setText(LoadData(getActivity(), Key_password));
                    updateDataUserFragmentEmphasisPasswordEdit.setText(LoadData(getActivity(), Key_password));

                    updateDataUserFragmentGovernoratesSpinner.setSelection(Integer.parseInt(response.body().getData().getClient().getCity().getGovernorateId()));

                    updateDataUserFragmentBloodTypesSpinner.setSelection(Integer.parseInt(response.body().getData().getClient().getBloodTypeId()));

                    returnIcCity = response.body().getData().getClient().getCity().getId();

                }
            }

            @Override
            public void onFailure(Call<GetDataProfil> call, Throwable t) {

            }
        });

    }

    @OnClick({R.id.updateDataUser_Fragment_EditDataOfBirth, R.id.updateDataUser_Fragment_DataLastDonation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.updateDataUser_Fragment_EditDataOfBirth:
                CalendrHelper calendrHelper = new CalendrHelper(getContext());
                calendrHelper.showCalendr(updateDataUserFragmentEditDataOfBirth);
                break;
            case R.id.updateDataUser_Fragment_DataLastDonation:
                CalendrHelper calendrHelper1 = new CalendrHelper(getContext());
                calendrHelper1.showCalendr(updateDataUserFragmentDataLastDonation);
                break;
        }
    }

//    @SuppressLint("ClickableViewAccessibility")
//    public void OnClick() {
//
//        DecimalFormat decimalFormat = new DecimalFormat("00");
//        calendar = Calendar.getInstance();
//        YEAR = Integer.parseInt(decimalFormat.format(calendar.get(Calendar.YEAR)));
//        MONTH = Integer.parseInt(decimalFormat.format(calendar.get(Calendar.MONTH)));
//        DAY_OF_MONTH = Integer.parseInt(decimalFormat.format(calendar.get(Calendar.DAY_OF_MONTH)));
//
//        DataOfBirth = "1972" + "-" + "01" + "-" + "01";
//        updateDataUserFragmentEditDataOfBirth.setText(DataOfBirth);
//        updateDataUserFragmentEditDataOfBirth.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//
//                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
//                        @SuppressLint("DefaultLocale")
//                        @Override
//                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                            DataOfBirth = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", dayOfMonth);
//                            updateDataUserFragmentEditDataOfBirth.setText(DataOfBirth);
//                        }
//                    }, YEAR, MONTH, DAY_OF_MONTH);
//
//                    datePickerDialog.show();
//                    return true;
//                }
//                return false;
//            }
//        });
//
//        updateDataUserFragmentDataLastDonation.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    Calendar calendar = Calendar.getInstance();
//                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
//                        @SuppressLint("DefaultLocale")
//                        @Override
//                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                            DataLastDonation = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", dayOfMonth);
//                            updateDataUserFragmentDataLastDonation.setText(DataLastDonation);
//                        }
//                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//
//                    datePickerDialog.show();
//
//                    return true;
//                }
//                return false;
//            }
//        });
//
//    }


}
