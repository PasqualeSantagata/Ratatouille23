package com.example.springclient.view.statoOrdinazioni;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.utils.StatoDellaOrdinazione;
import com.example.springclient.view.adapters.IRecycleViewOrdinazioniCorrenti;
import com.example.springclient.view.adapters.IRecycleViewOrdinazioniEvase;
import com.example.springclient.view.adapters.IRecycleViewOrdinazioniPrenotate;
import com.example.springclient.view.adapters.RecycleViewAdapterOrdinazioniCorrenti;
import com.example.springclient.view.adapters.RecycleViewAdapterOrdinazioniEvase;
import com.example.springclient.view.adapters.RecycleViewAdapterOrdinazioniPrenotate;

import java.util.List;

public class HomeStatoOrdinazione extends AppCompatActivity implements IRecycleViewOrdinazioniCorrenti, IRecycleViewOrdinazioniPrenotate, IRecycleViewOrdinazioniEvase {

    RecyclerView recyclerViewOrdinazioniCorrenti;
    RecyclerView recyclerViewOrdinazioniPrenotate;
    RecyclerView recyclerViewOrdinazioniEvase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle("STATO DELLE ORDINAZIONI");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_stato_ordinazioni);



    }

    public void initializeComponents() {
        recyclerViewOrdinazioniCorrenti = findViewById(R.id.recyclerViewOrdiniDaEvadereStatoOrdinazioni);
        recyclerViewOrdinazioniPrenotate = findViewById(R.id.recyclerViewOrdiniPrenotatiStatoOrdinazioni);
        recyclerViewOrdinazioniEvase = findViewById(R.id.recycleViewOrdiniEvasiStatoOrdinazioni);

        //Reecuperare i dati e usare setDatiRecycleView per impostarle

    }

    public void setDatiRecycleViewOrdinazioniCorrenti(RecyclerView recyclerView, List<StatoDellaOrdinazione> ordinazione){
        RecycleViewAdapterOrdinazioniCorrenti adapter = new RecycleViewAdapterOrdinazioniCorrenti(this, this, ordinazione);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void setDatiRecycleViewOrdinazioniPrenotate(RecyclerView recyclerView, List<StatoDellaOrdinazione> ordinazione){
        RecycleViewAdapterOrdinazioniPrenotate adapter = new RecycleViewAdapterOrdinazioniPrenotate(this, this, ordinazione);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void setDatiRecycleViewOrdinazioniEvase(RecyclerView recyclerView, List<StatoDellaOrdinazione> ordinazione){
        RecycleViewAdapterOrdinazioniEvase adapter = new RecycleViewAdapterOrdinazioniEvase(this, this, ordinazione);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onItemClickOrdinazioniCorrenti(int position) {

    }

    @Override
    public void onGreenButtonClickOrdinazioniCorrenti(int position) {

    }

    @Override
    public void onRedButtonClickOrdinazioniCorrenti(int position) {

    }

    @Override
    public void onItemClickOrdinazioniEvase(int position) {

    }

    @Override
    public void onItemClickOrdinazioniPrenotate(int position) {

    }

    @Override
    public void onGreenButtonClickOrdinazioniPrenotate(int position) {

    }

    @Override
    public void onRedButtonClickOrdinazioniPrenotate(int position) {

    }
}
