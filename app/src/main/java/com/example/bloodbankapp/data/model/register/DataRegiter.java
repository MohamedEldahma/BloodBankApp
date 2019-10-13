
package com.example.bloodbankapp.data.model.register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataRegiter {

    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("client")
    @Expose
    private ClientRegiter client;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public ClientRegiter getClient() {
        return client;
    }

    public void setClient(ClientRegiter client) {
        this.client = client;
    }

}
