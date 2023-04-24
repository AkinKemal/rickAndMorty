package com.example.rickandmorty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.rickandmorty.Entity.LocationData;
import com.example.rickandmorty.Entity.ResultCharacter;
import com.example.rickandmorty.Entity.ResultLocation;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler_view_locations;
    private RecyclerView recycler_view_characters;
    PageViewModel pageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);

        recycler_view_locations = findViewById(R.id.recycler_view_locations);
        recycler_view_characters = findViewById(R.id.recycler_view_characters);

        pageViewModel.getCharacterList().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> characterIdList) {

                recycler_view_characters.setVisibility(View.GONE);
                if (!characterIdList.isEmpty()) {
                    getCharacterFromNetwork(characterIdList);
                }
            }
        });

        getLocationFromNetwork();
    }

    private void getLocationFromNetwork() {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("https://rickandmortyapi.com/api/location")
                .method("GET", null)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.isSuccessful()) {
                    final String responseBody = response.body().string();
                    LocationData location = new Gson().fromJson(responseBody, LocationData.class);

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            setAdapterForLocations(location.getResults());

                            getCharacterFromNetwork(location.getResults().get(0).getResidents());
                        }
                    });
                }

            }
        });
    }

    private void setAdapterForLocations(ArrayList<ResultLocation> resultLocationArrayList) {

        ArrayList<ResultLocation> tmpLocationArrayList = new ArrayList<>();

        LocationAdapter locationAdapter = new LocationAdapter(tmpLocationArrayList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler_view_locations.setLayoutManager(mLayoutManager);
        recycler_view_locations.setAdapter(locationAdapter);

        locationAdapter.SetLocationArrayList(resultLocationArrayList);
    }

    private void getCharacterFromNetwork(ArrayList<String> idListOfCharacters) {

        int tmpLength;
        String tmpCharactersList = "https://rickandmortyapi.com/api/character/";
        int tmpUrlCharacterLength = tmpCharactersList.length();

        for (String character : idListOfCharacters) {
            tmpLength = character.length();

            tmpCharactersList += character.substring(tmpUrlCharacterLength, tmpLength) + ", ";
        }

        String charactersList = tmpCharactersList.substring(0, tmpCharactersList.length() - 2);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(charactersList)
                .method("GET", null)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull okhttp3.Response response) throws IOException {

                if (response.isSuccessful()) {

                    final String responseBody = response.body().string();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JsonParser parser = new JsonParser();
                            JsonElement element = parser.parse(responseBody);

                            if (element.isJsonArray()) {
                                Type characterListType = new TypeToken<ArrayList<ResultCharacter>>() {}.getType();
                                ArrayList<ResultCharacter> charactersList = new Gson().fromJson(responseBody, characterListType);
                                setAdapterForCharacters(charactersList);
                            } else if (element.isJsonObject()) {
                                ResultCharacter character = new Gson().fromJson(responseBody, ResultCharacter.class);
                                ArrayList<ResultCharacter> charactersList = new ArrayList<>();
                                charactersList.add(character);
                                setAdapterForCharacters(charactersList);
                            }
                        }
                    });
                }
            }
        });
    }

    private void setAdapterForCharacters(ArrayList<ResultCharacter> resultForCharacters) {

        recycler_view_characters.setVisibility(View.VISIBLE);

        CharacterAdapter adapter = new CharacterAdapter(resultForCharacters);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycler_view_characters.setLayoutManager(mLayoutManager);
        recycler_view_characters.setAdapter(adapter);
    }
}