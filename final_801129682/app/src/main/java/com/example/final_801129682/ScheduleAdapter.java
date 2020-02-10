package com.example.final_801129682;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    ArrayList<Schedule> mData;

    public ScheduleAdapter(ArrayList<Schedule> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedular_display,parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Schedule schedule = mData.get(position);
        holder.textViewName.setText("Meet " + schedule.name);
        holder.textViewPlace.setText(schedule.place);
        holder.textViewDateTIme.setText(schedule.date + " " + schedule.time);
        holder.schedule = schedule;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewPlace, textViewDateTIme;
        Schedule schedule;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.tv_name);
            textViewPlace = itemView.findViewById(R.id.tv_place);
            textViewDateTIme = itemView.findViewById(R.id.tv_date_time);
        }
    }


}
