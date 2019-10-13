
package com.example.bloodbankapp.data.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataLogin {

    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("client")
    @Expose
    private ClientLogin client;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public ClientLogin getClient() {
        return client;
    }

    public void setClient(ClientLogin client) {
        this.client = client;
    }

}
