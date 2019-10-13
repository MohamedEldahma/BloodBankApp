package com.example.bloodbankapp.ui.fragment.login;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.bloodbankapp.R;
import com.example.bloodbankapp.data.api.ApiServer;
import com.example.bloodbankapp.data.api.RetrofitClient;
import com.example.bloodbankapp.data.model.login.Login;
import com.example.bloodbankapp.data.model.new_password.NewPasswordModel;
import com.example.bloodbankapp.ui.fragment.login.LoginFragment;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbankapp.data.local.SharedPreferncesManger.clean;
import static com.example.bloodbankapp.data.local.SharedPreferncesManger.setSharedPreferences;
import static com.example.bloodbankapp.helper.HelperMathod.checkCorrespondPassword;
import static com.example.bloodbankapp.helper.HelperMathod.checkLengthPassword;
import static com.example.bloodbankapp.helper.HelperMathod.getStartFragments;

public class NewPasswordFragment extends Fragment {
    View view;
    @BindView(R.id.new_Password_Fragment_EditUser_CodePin)
    EditText newPasswordFragmentEditUserCodePin;

    @BindView(R.id.NewPassword_Fragment_EditUser_NewPassword)
    EditText NewPasswordFragmentEditUserNewPassword;

    @BindView(R.id.NewPassword_FragmentEdit_UserConfirmPassword)
    EditText NewPasswordFragmentEditUserConfirmPassword;

    @BindView(R.id.NewPassword_Fragment_BtnNewPassword)
    Button NewPasswordFragmentBtnNewPassword;

    Unbinder unbinder;


    ApiServer apiServer;
    public static Login login;
    private String getPinCodeForTest;
    private String UserPhone;
    private String newPassword, CodePin, ConfirmPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_new_password, container, false);

        unbinder = ButterKnife.bind(this, view);

        inti();

        // initialize ShardPreferences
        setSharedPreferences(getActivity());

        // class login retrofit
        ClassLoginRetrofit();
        // Class All OnClick
        ClassAllOnClick();
        return view;
    }

    private void inti() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            getPinCodeForTest = bundle.getString("getPinCodeForTest");
            UserPhone = bundle.getString("UserPhone");
        }


    }

    private void ClassAllOnClick() {


    }


    // class login retrofit
    private void ClassLoginRetrofit() {
        NewPasswordFragmentBtnNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPassword = NewPasswordFragmentEditUserNewPassword.getText().toString();
                CodePin = newPasswordFragmentEditUserCodePin.getText().toString();
                ConfirmPassword = NewPasswordFragmentEditUserConfirmPassword.getText().toString();


            Toast.makeText(getContext(), getPinCodeForTest + "--"  + CodePin, Toast.LENGTH_SHORT).show();

                if (getPinCodeForTest.equals(CodePin)) {

                    if (checkCorrespondPassword(newPassword,ConfirmPassword)&& checkLengthPassword(newPassword)) {

                        apiServer = RetrofitClient.getClient().create(ApiServer.class);
                        Call<NewPasswordModel> call = apiServer.newPassword(newPassword, ConfirmPassword, CodePin, UserPhone);
                        call.enqueue(new Callback<NewPasswordModel>() {
                            @Override
                            public void onResponse(Call<NewPasswordModel> call, Response<NewPasswordModel> response) {
                                Toast.makeText(getContext(), response.body().getMsg()   , Toast.LENGTH_SHORT).show();

                                if (response.body().getStatus() == 1) {

                                    clean(getActivity());

                                    if (getFragmentManager() != null) {
                                        getStartFragments( getFragmentManager(),R.id.fram_login_activity,new LoginFragment());
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<NewPasswordModel> call, Throwable t) {
                                newPasswordFragmentEditUserCodePin.setError(getResources().getString(R.string.confirmation_code_wrong));
                            }
                        });

                    } else {
                        if (!checkCorrespondPassword(newPassword,ConfirmPassword)) {
                            NewPasswordFragmentEditUserConfirmPassword.setError(getResources().getString(R.string.number_does_not_correspond));
                        }
                        if (!checkLengthPassword(newPassword)) {
                            NewPasswordFragmentEditUserNewPassword.setError(getResources().getString(R.string.It_should_bethe_largest6));

                        }
                    }

                } else {
                    newPasswordFragmentEditUserCodePin.setError(getResources().getString(R.string.confirmation_number_error));
                }

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
