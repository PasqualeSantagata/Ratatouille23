package com.example.springclient.view.gestioneMenu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.contract.CategoriaContract;
import com.example.springclient.presenter.CategoriaMenuPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeNuovoElementoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerLingua;
    private Spinner spinnerCategoria;
    private Button buttonIndietro;
    private Button buttonOk;
    private FloatingActionButton fabAggiungiCategoria;
    private CategoriaContract.Presenter categoriaPresenter;
    private List<String> categorie;
    private List<String> lingue;
    private String categoriaSelezionata;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("NUOVO ELEMENTO DEL MENÚ");
        setContentView(R.layout.activity_home_nuovo_elemento_gestione_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        categoriaPresenter = new CategoriaMenuPresenter(this);
        categoriaPresenter.getNomiCategorie();

    }

    private void initializeComponents() {
        buttonIndietro = findViewById(R.id.buttonIndietroNuovoElemento);
        buttonOk = findViewById(R.id.buttonOkNuovoElemento);
        spinnerCategoria = findViewById(R.id.spinnerCategoriaHomeInserimentoNelMenu);
        spinnerCategoria.setOnItemSelectedListener(this);
        spinnerLingua =  findViewById(R.id.spinnerLinguaHomeInserimentoNelMenu);
        fabAggiungiCategoria = findViewById(R.id.fabAggiungiCategoriaNuovoElementoGestioneMenu);

        spinnerLingua.setOnItemSelectedListener(this);

        lingue = new ArrayList<>();

        ArrayAdapter adapterCategorie = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorie);
        adapterCategorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapterCategorie);

        //List<Categoria> categoriaList = elementoMenuPresenter.;
        //SpinnerAdapterCategorie spinnerAdapterCategorie = new SpinnerAdapterCategorie(this,categorie);
        //spinnerCategoria.setAdapter(spinnerAdapterCategorie);

        buttonOk.setOnClickListener(view -> {
            Intent intent = new Intent(this, InserisciElementoActivity.class);
            intent.putExtra("categoria", categoriaSelezionata);
            startActivity(intent);
        });

        buttonIndietro.setOnClickListener(view -> {
            Intent intent = new Intent(this, StartGestioneMenuActivity.class);
            startActivity(intent);
        });

        fabAggiungiCategoria.setOnClickListener(view -> {
            Intent intent = new Intent(this, CreaCategoriaActivity.class);
            startActivity(intent);
        });


    }

    public void setCategorie(List<String> categorie) {
        this.categorie = categorie;
        initializeComponents();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        categoriaSelezionata = categorie.get(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
