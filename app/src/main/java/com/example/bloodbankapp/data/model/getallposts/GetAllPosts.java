
package com.example.bloodbankapp.data.model.getallposts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllPosts {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DataGetAllPosts data;

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

    public DataGetAllPosts getData() {
        return data;
    }

    public void setData(DataGetAllPosts data) {
        this.data = data;
    }

}
