package com.example.bloodbankapp.data.api;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static String BaseUrl = "http://ipda3-tech.com/blood-bank/api/v1/";
    public static Retrofit retrofit;

    public static Retrofit getClient() {

        if (retrofit==null) {
                retrofit =new Retrofit.Builder().baseUrl(BaseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
