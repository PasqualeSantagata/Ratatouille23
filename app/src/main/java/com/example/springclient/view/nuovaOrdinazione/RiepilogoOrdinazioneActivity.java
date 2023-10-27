package com.example.springclient.view.nuovaOrdinazione;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.contract.ElementoMenuContract;
import com.example.springclient.contract.OrdinazioneContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.entity.Ordinazione;
import com.example.springclient.entity.Portata;
import com.example.springclient.presenter.ElementoMenuPresenter;
import com.example.springclient.presenter.OrdinazionePresenter;
import com.example.springclient.view.adapters.IRecycleViewElementoMenu;
import com.example.springclient.view.adapters.RecycleViewAdapterRiepilogoOrdinazione;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RiepilogoOrdinazioneActivity extends AppCompatActivity implements IRecycleViewElementoMenu, Serializable {

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
    private Ordinazione ordinazione;
    private List<Portata> portate;
    private RecycleViewAdapterRiepilogoOrdinazione adapterElementoMenu;
    private ElementoMenuPresenter elementoMenuPresenter;
    private OrdinazioneContract.Presenter presenterOrdinazione;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("RIEPILOGO ORDINAZIONE");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_riepilogo_ordinazione_nuova_ordinazione);
        ordinazione = (Ordinazione) getIntent().getSerializableExtra("ordinazione");
        elementoMenuPresenter = new ElementoMenuPresenter(this);
        portate = new ArrayList<>();
        for(Portata o : ordinazione.getElementiOrdinati()){
            portate.add(o);
        }
        presenterOrdinazione = new OrdinazionePresenter(this);
        buttonOk = findViewById(R.id.buttonOkRiepilogo);
        buttonIndietro = findViewById(R.id.buttonIndietroRiepilogo);
        buttonOk.setOnClickListener(view -> {
            //elementoMenuPresenter.traduciOrdinazione();


            presenterOrdinazione.savePortate(portate);
        });
        buttonIndietro.setOnClickListener(view -> onBackPressed());
        initializeComponents();
    }

    private void initializeComponents() {
        RecyclerView  recyclerViewRiepilogo = findViewById(R.id.RecyclerViewRiepilogoOrdinazione);
        adapterElementoMenu = new RecycleViewAdapterRiepilogoOrdinazione(this,this ,portate);
        recyclerViewRiepilogo.setAdapter(adapterElementoMenu);
        GridLayoutManager horizontal = new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false);
        recyclerViewRiepilogo.setLayoutManager(horizontal);
    }


    public void salvaOrdinazione(List<Portata> portateOrdinazione){
        ordinazione.setElementiOrdinati(portateOrdinazione);
        presenterOrdinazione.aggiungiOrdinazione(ordinazione);
    }

    @Override
    public void onItemClick(int position) {
        //Aprire menu di android per far elminiare una certa pietanza dall'ordinaizone

    }
    @Override
    public void onBackPressed() {
        Intent intentEsploraCategorie = new Intent(this, EsploraCategorieActivity.class);
        intentEsploraCategorie.putExtra("ordinazione",ordinazione);
        startActivity(intentEsploraCategorie);
        super.onBackPressed();
    }
}


