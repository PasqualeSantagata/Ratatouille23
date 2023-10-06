package com.example.springclient.view.adapters;

public interface IRecycleViewElementoMenu {
    void onItemClick(int position);
    default void onButtonDeleted(int position){

    }
}
