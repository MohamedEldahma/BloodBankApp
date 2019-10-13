
package com.example.bloodbankapp.data.model.favourite;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavouriteModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("favourite")
    @Expose
    private FavouriteDataModel favouriteDataModel;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public FavouriteDataModel getFavouriteDataModel() {
        return favouriteDataModel;
    }

    public void setFavouriteDataModel(FavouriteDataModel favouriteDataModel) {
        this.favouriteDataModel = favouriteDataModel;
    }

}
