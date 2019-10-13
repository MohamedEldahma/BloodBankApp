
package com.example.bloodbankapp.data.model.getdataprofil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataGetProfil {

    @SerializedName("client")
    @Expose
    private ClientGetProfil client;

    public ClientGetProfil getClient() {
        return client;
    }

    public void setClient(ClientGetProfil client) {
        this.client = client;
    }

}
