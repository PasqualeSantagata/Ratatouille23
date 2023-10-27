package com.example.springclient.view.adapters;

public interface IRecycleViewElementoMenu {
    void onItemClickRecyclerViewPortata(int position);
    default void onButtonDeleted(int position){

    }
}
