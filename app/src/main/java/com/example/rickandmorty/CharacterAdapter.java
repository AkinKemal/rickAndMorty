package com.example.rickandmorty;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rickandmorty.Entity.ResultCharacter;

import java.util.ArrayList;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {

    Integer[] genderIconId = {
            R.drawable.baseline_male_24,
            R.drawable.baseline_female_24,
            R.drawable.baseline_priority_high_24,
            R.drawable.baseline_question_mark_24,
    };
    private ArrayList<ResultCharacter> characterArrayList;

    public CharacterAdapter(ArrayList<ResultCharacter> characterArrayList) {
        this.setCharacterArrayList(characterArrayList);
    }

    @Override
    public CharacterAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_character, viewGroup, false);
        CharacterAdapter.ViewHolder holder = new CharacterAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CharacterAdapter.ViewHolder holder, final int position) {

        ResultCharacter character = getCharacterArrayList().get(position);

        Glide.with(holder.itemView)
                .load(character.getImage())
                .centerCrop()
                .into(holder.character_image_view);

        holder.character_title_text_view.setText(character.getName());

        int id;
        switch (character.getGender()) {
            case "Male":
                id = 0;
                break;
            case "Female":
                id = 1;
                break;
            case "Genderless":
                id = 2;
                break;
            default:
                id = 3;
                break;
        }
        Glide.with(holder.itemView)
                .load(genderIconId[id])
                .centerCrop()
                .into(holder.gender_image_view);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), CharacterDetailActivity.class);
                String tmpId = String.valueOf(character.getId());
                intent.putExtra("CharacterId", tmpId);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return characterArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView character_title_text_view;
        private final ImageView gender_image_view;
        private final ImageView character_image_view;

        public ViewHolder(View v) {
            super(v);

            character_title_text_view = v.findViewById(R.id.character_title_text_view);
            gender_image_view = v.findViewById(R.id.gender_image_view);
            character_image_view = v.findViewById(R.id.character_image_view);
        }
    }

    public ArrayList<ResultCharacter> getCharacterArrayList() {
        return characterArrayList;
    }

    public void setCharacterArrayList(ArrayList<ResultCharacter> characterArrayList) {
        this.characterArrayList = characterArrayList;
    }
}