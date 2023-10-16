package com.example.springclient.view.gestioneMenu;

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
import com.example.springclient.entity.Categoria;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.presenter.CategoriaMenuPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class ModificaElementoActivity extends AppCompatActivity implements CategoriaContract.View, AdapterView.OnItemSelectedListener {
    private TextInputLayout textInputLayoutNome;
    private TextInputLayout textInputLayoutDescrizione;
    private TextInputLayout textInputLayoutPrezzo;
    private Button buttonOk;
    private Button buttonIndietro;
    private Spinner spinnerCategoria;
    private FloatingActionButton fabAggiungiCategoria;
    private ElementoMenu elementoMenu;
    private List<String> nomiCategoria;
    private CategoriaMenuPresenter categoriaMenuPresenter;
    private String categoriaSelezionata;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("MODIFICA ELEMENTO NEL MENU");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_modifica_elemento_gestione_menu);
        elementoMenu = (ElementoMenu) getIntent().getSerializableExtra("elementoMenu");
        categoriaMenuPresenter = new CategoriaMenuPresenter(this);
        categoriaMenuPresenter.getNomiCategoriaDisponibili(elementoMenu.getId().toString());
    }

    public void initializeComponents() {
        textInputLayoutNome = findViewById(R.id.textInputLayoutModificaNome);
        textInputLayoutPrezzo= findViewById(R.id.textInputLayoutModificaPrezzo);
        textInputLayoutDescrizione = findViewById(R.id.textInputLayoutModificaDescrizione);
        textInputLayoutNome.getEditText().setText((elementoMenu.getNome()));
        textInputLayoutDescrizione.getEditText().setText((elementoMenu.getDescrizione()));
        textInputLayoutPrezzo.getEditText().setText(elementoMenu.getPrezzo().toString());
        fabAggiungiCategoria = findViewById(R.id.floatingModificaCategoria);

        spinnerCategoria = findViewById(R.id.spinnerCategoriaInserimentoNelMenu);
        spinnerCategoria.setOnItemSelectedListener(this);
        ArrayAdapter adapterCategorie = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nomiCategoria);
        adapterCategorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapterCategorie);
        fabAggiungiCategoria.setVisibility(View.INVISIBLE);


        fabAggiungiCategoria.setOnClickListener(view -> {
            if(categoriaSelezionata != null){
                categoriaMenuPresenter.aggiungiElemento(categoriaSelezionata, elementoMenu);
                //se non aggiunge è perchè è già presente
            }
        });


    }

    @Override
    public void setNomiCategorie(List<String> nomiCategori) {
        this.nomiCategoria = nomiCategori;
        initializeComponents();
    }

    @Override
    public void setCategorie(List<Categoria> categorie) {

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        categoriaSelezionata = nomiCategoria.get(i);
        fabAggiungiCategoria.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
