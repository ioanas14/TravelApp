package com.example.travelapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    ArrayList<Trip> mlist;
    Context context;

    public Adapter(Context context, ArrayList<Trip> mList) {
        this.mlist = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.triplayout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {
        Trip trip = mlist.get(position);
        holder.tripName.setText(trip.getTripName());
        holder.tripDate.setText(trip.getTripDate());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tripName, tripDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tripName = itemView.findViewById(R.id.nameOfTrip);
            tripDate = itemView.findViewById(R.id.dateOfTrip);
        }
    }
}
