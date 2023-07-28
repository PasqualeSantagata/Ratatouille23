package com.example.springclient.view.statoOrdinazioni;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;

public class HomeStatoOrdinazione extends AppCompatActivity {

    RecyclerView recyclerViewOrdinazioniCorrenti;
    RecyclerView recyclerViewOrdinazioniPrenotate;
    RecyclerView recyclerViewOrdinazioniEvase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle("STATO DELLE ORDINAZIONI");
        setContentView(R.layout.activity_stato_ordinazioni);
    }
}
