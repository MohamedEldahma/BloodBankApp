
package com.example.bloodbankapp.data.model.updateprofil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataUpdatProfil {

    @SerializedName("client")
    @Expose
    private ClientUpdatProfil client;

    public ClientUpdatProfil getClient() {
        return client;
    }

    public void setClient(ClientUpdatProfil client) {
        this.client = client;
    }

}
