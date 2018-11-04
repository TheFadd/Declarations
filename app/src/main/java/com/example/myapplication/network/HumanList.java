package com.example.myapplication.network;

import com.example.myapplication.Human;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HumanList {

    @SerializedName("items")
    private List<Human> humans = new ArrayList<>();

    public List<Human> getHumans(){
        return humans;
    }

    @Override
    public String toString() {
        return "HumanList{" +
                "humans=" + humans +
                '}';
    }
}
