package com.example.springclient.view.inserimentoNelMenu;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.contract.ElementoMenuContract;
import com.example.springclient.presenter.ElementoMenuPresenter;

public class HomeNuovoElemento extends AppCompatActivity {

    private Spinner spinnerLingua;
    private Spinner spinnerCategoria;
    private Button buttonIndietro;
    private Button buttonOk;

    private ElementoMenuContract.Presenter elementoMenuPresenter = new ElementoMenuPresenter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Nuovo elemento del Menù");
        setContentView(R.layout.activity_home_nuovo_elemento_inserimento_nel_menu);

    }

    private void initializeComponents() {
        buttonIndietro = findViewById(R.id.buttonIndietroNuovoElemento);
        buttonOk = findViewById(R.id.buttonOkNuovoElemento);

           /*   //Spinner
        //spinnerCategorie = findViewById(R.id.);
        List<String> categorie = new ArrayList<>();

        //renderle leggibili dal DB

        categorie.add("Primo");
        categorie.add("Secondo");
        categorie.add("Drink");
        categorie.add("Dessert");

          spinnerAdapterCategorie = new SpinnerAdapterCategorie(InserisciElementoActivity.this, categorie);
          spinnerCategorie.setAdapter(spinnerAdapterCategorie);

     */



    }

}
