package com.example.springclient.view.nuovaOrdinazione;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.contract.ElementoMenuContract;
import com.example.springclient.presenter.ElementoMenuPresenter;

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

    private ElementoMenuContract.Presenter presenter = new ElementoMenuPresenter(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("RIEPILOGO ORDINAZIONE");
        setContentView(R.layout.activity_riepilogo_ordinazione_nuova_ordinazione);

    }

    private void InitializeComponents() {


    }

    private void setHorizontalRecycleView (RecyclerView recyclerView){
        RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        LinearLayoutManager horizontalLayout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayout);

    }


}


