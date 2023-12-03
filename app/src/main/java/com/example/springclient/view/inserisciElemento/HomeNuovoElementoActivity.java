package com.example.springclient.view.inserisciElemento;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import com.example.springclient.R;
import com.example.springclient.contract.InserisciElementoContract;
import com.example.springclient.presenter.InserisciElementoPresenter;
import com.example.springclient.view.gestioneCategorie.CreaCategoriaActivity;
import com.example.springclient.view.StartGestioneMenuActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;

public class HomeNuovoElementoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, InserisciElementoContract.HomeNuovoElmentoView{

    private Spinner spinnerLingua;
    private Spinner spinnerCategoria;
    private Button buttonIndietro;
    private Button buttonOk;
    private FloatingActionButton fabAggiungiCategoria;
    private InserisciElementoContract.Presenter homeNuovoElementoPresenter;
    private List<String> categorie;
    private List<String> lingue;
    private String categoriaSelezionata;
    private String linguaSelezionata;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("NUOVO ELEMENTO DEL MENÚ");
        setContentView(R.layout.activity_home_nuovo_elemento_gestione_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        progressBar = findViewById(R.id.progressBarHomeNuovoElemento);
        progressBar.setVisibility(View.INVISIBLE);
        homeNuovoElementoPresenter = new InserisciElementoPresenter(this);
        homeNuovoElementoPresenter.getNomiCategorie();
    }

    @Override
    public void inizializzaComponenti() {
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

        buttonOk.setOnClickListener(view -> homeNuovoElementoPresenter.mostraInserisciElemento());

        buttonIndietro.setOnClickListener(view -> homeNuovoElementoPresenter.mostraStartGestioneMenu());

        fabAggiungiCategoria.setOnClickListener(view -> homeNuovoElementoPresenter.mostraCreaCategoria());


    }

    @Override
    public void erroreComunicazioneServer(String messaggio){
        Dialog dialog = new Dialog(this);
        mostraDialogErroreOneBtn(dialog, messaggio, view -> dialog.dismiss());
    }

    @Override
    public void tornaIndietro() {
        onBackPressed();
    }

    @Override
    public void mostraPorgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void nascondiProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean isVisibile() {
        return getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED);
    }

    @Override
    public void setNomiCategorie(List<String> nomiCategorie) {
        this.categorie = nomiCategorie;
        inizializzaComponenti();
    }
    @Override
    public void mostraCreaCategoria() {
        Intent intent = new Intent(this, CreaCategoriaActivity.class);
        startActivity(intent);
    }

    @Override
    public void mostraInserisciElemento() {
        Intent intent = new Intent(this, InserisciElementoActivity.class);
        intent.putExtra("categoria", categoriaSelezionata);
        intent.putExtra("lingua", linguaSelezionata);
        startActivity(intent);
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
    public void onBackPressed() {
        Intent intent = new Intent(this, StartGestioneMenuActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
