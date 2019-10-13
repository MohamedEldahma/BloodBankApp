
package com.example.bloodbankapp.data.model.generate;

import com.example.bloodbankapp.data.model.GeneralData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GovernoratesAndBloodTypesModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<GeneralData> data = null;

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

    public List<GeneralData> getData() {
        return data;
    }

    public void setData(List<GeneralData> data) {
        this.data = data;
    }

}
