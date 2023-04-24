package com.example.rickandmorty;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rickandmorty.Entity.ResultLocation;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    protected Context context;
    private ArrayList<ResultLocation> locationArrayList;
    private int checkedPosition = 0;
    PageViewModel pageViewModel;

    public LocationAdapter(ArrayList<ResultLocation> locationArrayList, Context context) {
        this.context = context;
        this.locationArrayList = locationArrayList;
    }

    public void SetLocationArrayList(ArrayList<ResultLocation> locationArrayList) {
        this.locationArrayList = new ArrayList<>();
        this.locationArrayList = locationArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_location, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        pageViewModel = new ViewModelProvider((ViewModelStoreOwner) holder.itemView.getContext()).get(PageViewModel.class);
        holder.bind(locationArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return locationArrayList.size();
    }

    class LocationViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout location_linear_layout;
        private final TextView location_title_text_view;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);

            location_linear_layout = itemView.findViewById(R.id.location_linear_layout);
            location_title_text_view = itemView.findViewById(R.id.location_title_text_view);
        }

        void bind(final ResultLocation locationData) {

            if (checkedPosition == getAdapterPosition()) {
                location_title_text_view.setTextColor(Color.parseColor("#FFFFFFFF"));
                location_linear_layout.setBackgroundColor(Color.parseColor("#0AB6CC"));
            } else {
                location_title_text_view.setTextColor(Color.parseColor("#0AB6CC"));
                location_linear_layout.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            }

            location_title_text_view.setText(locationData.getName());

            location_linear_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    location_title_text_view.setTextColor(Color.parseColor("#FFFFFFFF"));
                    location_linear_layout.setBackgroundColor(Color.parseColor("#0AB6CC"));

                    pageViewModel.setCharacterList(locationData.getResidents());

                    if (checkedPosition != getAdapterPosition()) {
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                    }
                }
            });
        }
    }

    public ArrayList<ResultLocation> getLocationArrayList() {
        return locationArrayList;
    }

    public void setLocationArrayList(ArrayList<ResultLocation> locationArrayList) {
        this.locationArrayList = locationArrayList;
    }

    public int getCheckedPosition() {
        return checkedPosition;
    }

    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
    }
}