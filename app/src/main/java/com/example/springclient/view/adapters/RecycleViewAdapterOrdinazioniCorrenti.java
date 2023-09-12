package com.example.springclient.view.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.entity.Ordinazione;

import java.util.List;

public class RecycleViewAdapterOrdinazioniCorrenti extends RecyclerView.Adapter<RecycleViewAdapterOrdinazioniCorrenti.MyViewHolder> {

    Context context;
    List<Ordinazione> ordinazioni;

    @NonNull
    @Override
    public RecycleViewAdapterOrdinazioniCorrenti.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapterOrdinazioniCorrenti.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }
}
