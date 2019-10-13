
package com.example.bloodbankapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeneralData {



    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("governorate_id")
    @Expose
    private String governorate_id;

    @SerializedName("governorate")
    @Expose
    private GeneralData governorate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGovernorate_id() {
        return governorate_id;
    }

    public void setGovernorate_id(String governorate_id) {
        this.governorate_id = governorate_id;
    }

    public GeneralData getGovernorate() {
        return governorate;
    }

    public void setGovernorate(GeneralData governorate) {
        this.governorate = governorate;
    }
}
