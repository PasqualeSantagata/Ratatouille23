package com.example.springclient.view.nuovaOrdinazione;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;

public class RiepilogoOrdinazione extends AppCompatActivity {

    private ImageView imageViewPrimi;
    private ImageView imageViewSecondi;
    private ImageView imageViewBevande;
    private ImageView imageViewSushi;
    private ImageView imageViewPizze;
    private ImageView imageViewDessert;

    private Button buttonIndietro;
    private Button buttonOk;

    private RecyclerView recyclerViewPrimi;
    private RecyclerView recyclerViewSecondi;
    private RecyclerView recyclerViewBevande;
    private RecyclerView recyclerViewSushi;
    private RecyclerView recyclerViewPizze;
    private RecyclerView recyclerViewDessert;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("RIEPILOGO ORDINAZIONE");
        setContentView(R.layout.activity_riepilogo_ordinazione_nuova_ordinazione);

    }

    private void InitializeComponents() {


    }



}


