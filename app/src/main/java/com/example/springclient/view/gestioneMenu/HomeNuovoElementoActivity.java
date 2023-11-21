package com.example.springclient.view.gestioneMenu;

import android.content.Context;
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
import com.example.springclient.contract.HomeNuovoElementoContract;
import com.example.springclient.presenter.HomeNuovoElementoPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;

public class HomeNuovoElementoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, HomeNuovoElementoContract.View {

    private Spinner spinnerLingua;
    private Spinner spinnerCategoria;
    private Button buttonIndietro;
    private Button buttonOk;
    private FloatingActionButton fabAggiungiCategoria;
    private HomeNuovoElementoContract.Presenter homeNuovoElementoPresenter;
    private List<String> categorie;
    private List<String> lingue;
    private String categoriaSelezionata;
    private String linguaSelezionata;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("NUOVO ELEMENTO DEL MENÚ");
        setContentView(R.layout.activity_home_nuovo_elemento_gestione_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        homeNuovoElementoPresenter = new HomeNuovoElementoPresenter(this);
        homeNuovoElementoPresenter.getNomiCategorie();
    }

    public void initializeComponents() {
        //Spinner lingua
        spinnerLingua = findViewById(R.id.spinnerLingueHomeInserisciElemento);
        spinnerLingua.setOnItemSelectedListener(this);
        lingue = Arrays.asList(getResources().getStringArray(R.array.array_lingue));
        ArrayAdapter adapterLingue = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lingue);
        adapterLingue.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLingua.setAdapter(adapterLingue);

        //Spinner categoria
        spinnerCategoria = findViewById(R.id.spinnerCategoriaHomeInserimentoNelMenu);
        spinnerCategoria.setOnItemSelectedListener(this);
        ArrayAdapter adapterCategorie = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorie);
        adapterCategorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapterCategorie);

        //Buttons
        buttonIndietro = findViewById(R.id.buttonIndietroNuovoElemento);
        buttonOk = findViewById(R.id.buttonOkNuovoElemento);
        fabAggiungiCategoria = findViewById(R.id.fabAggiungiCategoriaNuovoElementoGestioneMenu);
        buttonOk.setOnClickListener(view -> {
            Intent intent = new Intent(this, InserisciElementoActivity.class);
            intent.putExtra("categoria", categoriaSelezionata);
            intent.putExtra("lingua", linguaSelezionata);
            startActivity(intent);
        });

        buttonIndietro.setOnClickListener(view -> {
            onBackPressed();
        });

        fabAggiungiCategoria.setOnClickListener(view -> {
            Intent intent = new Intent(this, CreaCategoriaActivity.class);
            startActivity(intent);
        });


    }

    @Override
    public void setNomiCategorie(List<String> nomiCategorie) {
        this.categorie = nomiCategorie;
        initializeComponents();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == R.id.spinnerCategoriaHomeInserimentoNelMenu) {
            categoriaSelezionata = categorie.get(i);
        } else if (adapterView.getId() == R.id.spinnerLingueHomeInserisciElemento) {
            linguaSelezionata = lingue.get(i);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    @Override
    public Context getContext(){
        return getContext();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, StartGestioneMenuActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
