package com.example.bloodbankapp.ui.fragment.login;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.bloodbankapp.R;
import com.example.bloodbankapp.data.api.ApiServer;
import com.example.bloodbankapp.data.api.RetrofitClient;
import com.example.bloodbankapp.data.model.login.Login;
import com.example.bloodbankapp.ui.activity.HomeNavgation;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbankapp.data.local.SharedPreferncesManger.KEY_IS_CHECK_BOX;
import static com.example.bloodbankapp.data.local.SharedPreferncesManger.Key_password;
import static com.example.bloodbankapp.data.local.SharedPreferncesManger.LoadBoolean;
import static com.example.bloodbankapp.data.local.SharedPreferncesManger.LoadData;
import static com.example.bloodbankapp.data.local.SharedPreferncesManger.SaveData;
import static com.example.bloodbankapp.data.local.SharedPreferncesManger.USER_API_TOKEN;
import static com.example.bloodbankapp.data.local.SharedPreferncesManger.USER_EMAIL;
import static com.example.bloodbankapp.data.local.SharedPreferncesManger.USER_ID;
import static com.example.bloodbankapp.data.local.SharedPreferncesManger.USER_NAME;
import static com.example.bloodbankapp.data.local.SharedPreferncesManger.USER_PHONE;
import static com.example.bloodbankapp.data.local.SharedPreferncesManger.setSharedPreferences;
import static com.example.bloodbankapp.helper.HelperMathod.getStartFragments;


public class LoginFragment extends Fragment {
    View view;
    @BindView(R.id.login_Fragment_EditUserPhone)
    EditText loginFragmentEditUserPhone;
    @BindView(R.id.login_Fragment_EditUserPassword)
    EditText loginFragmentEditUserPassword;
    @BindView(R.id.login_FragmentTxt_ForgetPassword)
    TextView loginFragmentTxtForgetPassword;
    @BindView(R.id.login_Fragment_BtnLogin)
    Button loginFragmentBtnLogin;
    @BindView(R.id.login_Fragment_BtnRegister)
    Button loginFragmentBtnRegister;
    Unbinder unbinder;
    @BindView(R.id.login_Fragment_ChkBox)
    CheckBox loginFragmentChkBox;

    ApiServer apiServer;
    public static Login login;
     ProgressBar loginFragmentProgressBar;
    private boolean Checked;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login, container, false);


        unbinder = ButterKnife.bind(this, view);

        //initializer
        inti();
        // initialize ShardPreferences
        setSharedPreferences(getActivity());

        // class login retrofit
        ClassLoginRetrofit();
        // Class All OnClick
        ClassAllOnClick();
        // get data all User
        getDataUserShrPreferences();

        return view;
    }

    private void inti() {
        loginFragmentProgressBar = view.findViewById(R.id.login_Fragment_ProgressBar);
    }

    private void ClassAllOnClick() {


        /// on click register
        loginFragmentBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (getFragmentManager() != null) {
                    getStartFragments(getFragmentManager(), R.id.fram_login_activity, new RegisterFragment());
                }

            }
        });

        loginFragmentChkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Checked = isChecked;
            }
        });
        loginFragmentTxtForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getFragmentManager() != null) {
                   getStartFragments(getFragmentManager(), R.id.fram_login_activity, new ForgetPasswordFragment());
                }
            }
        });
    }

    private void getDataUserShrPreferences() {

        loginFragmentEditUserPhone.setText(LoadData(getActivity(), USER_PHONE));
        loginFragmentEditUserPassword.setText(LoadData(getActivity(), Key_password));

        // check is checkBox is Checked
        if (LoadBoolean(getActivity(), KEY_IS_CHECK_BOX, false)) {
            Log.d("response", "true");
            loginFragmentChkBox.setChecked(true);
        } else {
            loginFragmentChkBox.setChecked(false);
            Log.d("response", "false");

        }
    }

    private void ClassSharedPreferences(int id, String ApiToken, String name, String email, String phone, String password) {

        SaveData(getActivity(), USER_API_TOKEN, String.valueOf(ApiToken));
        SaveData(getActivity(), USER_ID, String.valueOf(id));
        SaveData(getActivity(), USER_NAME, String.valueOf(name));
        SaveData(getActivity(), USER_EMAIL, String.valueOf(email));
        SaveData(getActivity(), USER_PHONE, String.valueOf(phone));
        SaveData(getActivity(), Key_password, String.valueOf(password));
        SaveData(getActivity(), KEY_IS_CHECK_BOX, Checked);
    }

    // class login retrofit
    private void ClassLoginRetrofit() {
        loginFragmentBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginFragmentProgressBar.setVisibility(View.INVISIBLE);

                apiServer = RetrofitClient.getClient().create(ApiServer.class);
                Call<Login> call = apiServer.onLogin(loginFragmentEditUserPhone.getText().toString()
                        , loginFragmentEditUserPassword.getText().toString());
                call.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        try {
                            loginFragmentProgressBar.setVisibility(View.VISIBLE);

                            Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                            if (response.body().getStatus() == 1) {
                                Log.d("response", response.body().getData().getClient().getEmail());
                                login = response.body();

                                // check is checkBox is Checked
                                if (loginFragmentChkBox.isChecked()) {
                                    // save data login user
                                    ClassSharedPreferences(response.body().getData().getClient().getId()
                                            , response.body().getData().getApiToken()
                                            , response.body().getData().getClient().getName(),
                                            response.body().getData().getClient().getEmail(),
                                            response.body().getData().getClient().getPhone()
                                            , loginFragmentEditUserPassword.getText().toString());

                                    loginFragmentProgressBar.setVisibility(View.VISIBLE);

                                }else {
                                    loginFragmentProgressBar.setVisibility(View.VISIBLE);
                                }


                                Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getContext(), HomeNavgation.class);
                                startActivity(intent);
                            }
                        }catch (Exception e){
                            e.getMessage();
                        }

                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
