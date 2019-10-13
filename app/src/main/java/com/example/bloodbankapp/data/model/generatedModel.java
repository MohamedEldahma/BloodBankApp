package com.example.bloodbankapp.data.model;

import java.util.List;

public class generatedModel {

    private String Name;
    private int id;

    private List<Integer> idGovernorates_Blood;
    private List<GeneralData> data;
    public generatedModel(int id, String name ) {
        this.id = id;
        this.Name = name;

    }

    // Receiving data from  setting notification
    public generatedModel(Integer id, String name, List<Integer> idGovernorates_Blood) {
        this.id = id;
        this.Name = name;
        this.idGovernorates_Blood= idGovernorates_Blood;
    }

    public generatedModel(List<GeneralData> data ) {
        this.data = data;
    }

    public List<Integer> getIdGovernorates_Blood() {
        return idGovernorates_Blood;
    }

    public List<GeneralData> getData() {
        return data;
    }

    public void setData(List<GeneralData> data) {
        this.data = data;
    }

    public void setIdGovernorates_Blood(List<Integer> idGovernorates_Blood) {
        this.idGovernorates_Blood = idGovernorates_Blood;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
