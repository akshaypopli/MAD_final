package com.example.final_801129682;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    ArrayList<String> mdata;

    public PlaceAdapter(ArrayList<String> mdata) {
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.placelist,parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String placeSearch = String.valueOf(mdata.get(position));
        holder.textViewPlaceSearched.setText(placeSearch);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewPlaceSearched;
        String placeSearch;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPlaceSearched = itemView.findViewById(R.id.tv_placesearched);

        }
    }

}
