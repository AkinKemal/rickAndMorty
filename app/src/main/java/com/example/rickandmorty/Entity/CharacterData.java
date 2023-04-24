package com.example.rickandmorty.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CharacterData {

    @SerializedName("info")
    @Expose
    private InfoCharacter info;

    @SerializedName("results")
    @Expose
    private ArrayList<ResultCharacter> resultForCharacters;

    public InfoCharacter getInfo() {
        return info;
    }

    public void setInfo(InfoCharacter info) {
        this.info = info;
    }

    public ArrayList<ResultCharacter> getResults() {
        return resultForCharacters;
    }

    public void setResults(ArrayList<ResultCharacter> resultForCharacters) {
        this.resultForCharacters = resultForCharacters;
    }
}