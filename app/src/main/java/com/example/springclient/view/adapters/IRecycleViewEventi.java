package com.example.springclient.view.adapters;

public interface IRecycleViewEventi {
    void onItemClickRecyclerView(int position);
    default void onButtonRecyclerView(int position){

    }
}
