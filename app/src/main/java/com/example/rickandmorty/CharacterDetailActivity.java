package com.example.rickandmorty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rickandmorty.Entity.ResultCharacter;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CharacterDetailActivity extends AppCompatActivity {

    private String characterId = "";
    private TextView name_of_character_text_view;
    private ImageView image_of_character_image_view;
    private TextView status_of_character_text_view;
    private TextView species_of_character_text_view;
    private TextView gender_of_character_text_view;
    private TextView origin_of_character_text_view;
    private TextView location_of_character_text_view;
    private TextView episodes_text_view;
    private TextView created_at_of_character_text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);

        name_of_character_text_view = findViewById(R.id.name_of_character_text_view);
        image_of_character_image_view = findViewById(R.id.character_image_view);
        status_of_character_text_view = findViewById(R.id.status_of_character_text_view);
        species_of_character_text_view = findViewById(R.id.species_of_character_text_view);
        gender_of_character_text_view = findViewById(R.id.gender_of_character_text_view);
        origin_of_character_text_view = findViewById(R.id.origin_of_character_text_view);
        location_of_character_text_view = findViewById(R.id.location_of_character_text_view);
        episodes_text_view = findViewById(R.id.episodes_text_view);
        created_at_of_character_text_view = findViewById(R.id.created_at_of_character_text_view);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            setCharacterId(bundle.getString("CharacterId"));
            getCharacterDetailFromNetwork(getCharacterId());
        }
    }

    private void getCharacterDetailFromNetwork(String characterId) {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://rickandmortyapi.com/api/character/" + characterId)
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
                    ResultCharacter character = new Gson().fromJson(responseBody, ResultCharacter.class);

                    CharacterDetailActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            name_of_character_text_view.setText(character.getName());

                            Glide.with(CharacterDetailActivity.this)
                                    .load(character.getImage())
                                    .centerCrop()
                                    .into(image_of_character_image_view);

                            status_of_character_text_view.setText(character.getStatus());
                            species_of_character_text_view.setText(character.getSpecies());
                            gender_of_character_text_view.setText(character.getGender());
                            origin_of_character_text_view.setText(character.getOrigin().getName());
                            location_of_character_text_view.setText(character.getLocation().getName());

                            int tmpLength;
                            int tmpUrlCharacterLength = "https://rickandmortyapi.com/api/episode/".length();

                            String episodeBox = "";
                            for (String episodes : character.getEpisode()) {
                                tmpLength = episodes.length();

                                episodeBox += episodes.substring(tmpUrlCharacterLength, tmpLength) + ", ";
                            }
                            String episodesList = episodeBox.substring(0, episodeBox.length() - 2);

                            episodes_text_view.setText(episodesList);
                            created_at_of_character_text_view.setText(character.getCreated());
                        }
                    });
                }
            }
        });
    }

    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }
}