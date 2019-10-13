
package com.example.bloodbankapp.data.model.getdataprofil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDataProfil {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DataGetProfil data;

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

    public DataGetProfil getData() {
        return data;
    }

    public void setData(DataGetProfil data) {
        this.data = data;
    }

}
