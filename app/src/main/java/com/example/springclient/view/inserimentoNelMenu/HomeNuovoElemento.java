package com.example.springclient.view.inserimentoNelMenu;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.contract.ElementoMenuContract;
import com.example.springclient.entity.Categoria;
import com.example.springclient.presenter.ElementoMenuPresenter;

import java.util.List;

public class HomeNuovoElemento extends AppCompatActivity {

    private Spinner spinnerLingua;
    private Spinner spinnerCategoria;
    private Button buttonIndietro;
    private Button buttonOk;

    private ElementoMenuContract.Presenter elementoMenuPresenter;

    private List<Categoria> categorie;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Nuovo elemento del Menù");
        setContentView(R.layout.activity_home_nuovo_elemento_inserimento_nel_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        elementoMenuPresenter = new ElementoMenuPresenter(this);

    }

    private void initializeComponents() {
        buttonIndietro = findViewById(R.id.buttonIndietroNuovoElemento);
        buttonOk = findViewById(R.id.buttonOkNuovoElemento);

        buttonOk.setOnClickListener(view -> {
/*
            List<Categoria> categoriaList = elementoMenuPresenter
            SpinnerAdapterCategorie spinnerAdapterCategorie = new SpinnerAdapterCategorie(this,categoriaList);
            spinnerCategoria.setAdapter(spinnerAdapterCategorie);
            spinnerCategoria.getItemAtPosition(); */
        });

           /*   //Spinner
        //spinnerCategorie = findViewById(R.id.);
        List<String> categorie = new ArrayList<>();


          spinnerAdapterCategorie = new SpinnerAdapterCategorie(InserisciElementoActivity.this, categorie);
          spinnerCategorie.setAdapter(spinnerAdapterCategorie);

     */



    }

    public void setCategorie(List<Categoria> categorie) {
        this.categorie = categorie;
    }
}
