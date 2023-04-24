package com.example.rickandmorty.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LocationData {
    @SerializedName("info")
    @Expose
    private InfoLocation info;

    @SerializedName("results")
    @Expose
    private ArrayList<ResultLocation> resultForLocations;

    public InfoLocation getInfo() {
        return info;
    }

    public void setInfo(InfoLocation info) {
        this.info = info;
    }

    public ArrayList<ResultLocation> getResults() {
        return resultForLocations;
    }

    public void setResults(ArrayList<ResultLocation> resultForLocations) {
        this.resultForLocations = resultForLocations;
    }
}