package com.example.springclient.view.nuovaOrdinazione;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.contract.ElementoMenuContract;
import com.example.springclient.entity.Ordinazione;
import com.example.springclient.entity.Portata;
import com.example.springclient.presenter.ElementoMenuPresenter;
import com.example.springclient.view.adapters.IRecycleViewElementoMenu;
import com.example.springclient.view.adapters.RecycleViewAdapterRiepilogoOrdinazione;

import java.util.ArrayList;
import java.util.List;

public class RiepilogoOrdinazioneActivity extends AppCompatActivity implements IRecycleViewElementoMenu {

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

    private ElementoMenuContract.Presenter presenterOrdinazione;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("RIEPILOGO ORDINAZIONE");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_riepilogo_ordinazione_nuova_ordinazione);
        ordinazione = (Ordinazione) getIntent().getSerializableExtra("ordinazione");
        portate = new ArrayList<>();
        for(Portata o : ordinazione.getElementiOrdinati()){
            portate.add(o);
        }
        presenterOrdinazione = new ElementoMenuPresenter(this);
        Log.d("WEWE", ordinazione.getElementiOrdinati().get(0).getElementoMenu().getNome());
    }

    private void InitializeComponents() {
        RecyclerView  recyclerViewRiepilogo = findViewById(R.id.RecyclerViewRiepilogoOrdinazione);
        adapterElementoMenu = new RecycleViewAdapterRiepilogoOrdinazione(this,this ,portate);
        recyclerViewRiepilogo.setAdapter(adapterElementoMenu);
        GridLayoutManager horizontal = new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false);
        recyclerViewRiepilogo.setLayoutManager(horizontal);



    }

    private void setHorizontalRecycleView (RecyclerView recyclerView){
        LinearLayoutManager horizontalLayout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayout);
    }


    @Override
    public void onItemClick(int position) {
        //Aprire menu di android per far elminiare una certa pietanza dall'ordinaizone

    }
}


