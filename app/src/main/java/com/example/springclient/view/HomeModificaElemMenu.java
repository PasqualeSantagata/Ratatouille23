package com.example.springclient.view;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;

public class HomeModificaElemMenu extends AppCompatActivity {

    private Button buttonCerca;
    private Button buttonCategorie;
    private Button buttonIndietro;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Modifica Elemento del Menù");
        setContentView(R.layout.activity_home_modifica_elem_menu);


    }


    private void initializeComponents() {


    }
}
