package com.example.springclient.view.gestioneCategorie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import com.example.springclient.R;
import com.example.springclient.view.BaseAllergeniDialog;
import com.example.springclient.contract.ModificaElementoContract;
import com.example.springclient.model.entity.ElementoMenu;
import com.example.springclient.presenter.ModificaElementoPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class FiltraCategoriaGestioneMenuActivity extends AppCompatActivity implements BaseAllergeniDialog, ModificaElementoContract.ViewDefinisciOrdineContract {
    private Button buttonNome;
    private Button buttonPrezzo;
    private Button buttonOk;
    private Button buttonAnnulla;
    private Button buttonTabellaAllergeni;
    private CheckBox checkboxNome;
    private CheckBox checkboxPrezzo;
    private CheckBox checkboxAllergeni;
    private List<String> allergeni;
    private List<ElementoMenu> elementiMenu;
    private String nome;
    private Intent intentVisualizzaCategoria;
    private int flagOrdinamento;
    private ModificaElementoContract.Presenter modificaElementoPresenter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtra_categoria_gestione_menu);
        elementiMenu = (List<ElementoMenu>) getIntent().getSerializableExtra("elementiCategoria");
        nome = getIntent().getStringExtra("nomeCategoria");
        allergeni = new ArrayList<>();
        modificaElementoPresenter = new ModificaElementoPresenter(this);
        inizializzaComponenti();
    }

    @Override
    public void inizializzaComponenti() {
        //buttons
        buttonNome = findViewById(R.id.buttonNomeFiltraCategoria);
        buttonPrezzo = findViewById(R.id.buttonPrezzoFiltraCategoria);
        buttonTabellaAllergeni = findViewById(R.id.buttonFiltraTabellaAllergeni);
        buttonOk = findViewById(R.id.buttonOkFiltraCategoria);
        buttonAnnulla = findViewById(R.id.buttonAnnullaFiltraCategoria);
        progressBar = findViewById(R.id.progressBarFiltraCategoriaGestioneMenu);
        progressBar.setVisibility(View.INVISIBLE);

        buttonNome.setOnClickListener(view -> {
            if (buttonNome.getText().toString().equals(getString(R.string.nome_up))) {
                buttonNome.setText(R.string.nome_down);

            } else {
                buttonNome.setText(R.string.nome_up);

            }

        });
        buttonPrezzo.setOnClickListener(view -> {
            if (buttonPrezzo.getText().toString().equals(getString(R.string.prezzo_up))) {
                buttonPrezzo.setText(R.string.prezzo_down);

            } else {
                buttonPrezzo.setText(R.string.prezzo_up);

            }
        });
        buttonTabellaAllergeni.setOnClickListener(view -> dialogAllergeni(this, allergeni, false));
        intentVisualizzaCategoria = new Intent(this, VisualizzaElementiDellaCategoriaActivity.class);

        buttonOk.setOnClickListener(view -> {
            if (checkboxAllergeni.isChecked()) {
                filtraAllergeni();
            }
            if (checkboxNome.isChecked()) {
                if (buttonNome.getText().toString().equals(getString(R.string.nome_up))) {
                    flagOrdinamento = 1;
                    Collections.sort(elementiMenu, ElementoMenu.compareNomeCrescente);
                } else {
                    flagOrdinamento = 2;
                    Collections.sort(elementiMenu, ElementoMenu.compareNomeDecrescente);
                }
            }
            if (checkboxPrezzo.isChecked()) {
                if (buttonPrezzo.getText().toString().equals(getString(R.string.prezzo_up))) {
                    flagOrdinamento = 3;
                    Collections.sort(elementiMenu, ElementoMenu.comparePrezzoCrescente);
                } else {
                    flagOrdinamento = 4;
                    Collections.sort(elementiMenu, ElementoMenu.comparePrezzoDecrescente);
                }
            }

            modificaElementoPresenter.modificaOrdineCategoria(nome, flagOrdinamento);

        });
        buttonAnnulla.setOnClickListener(view -> modificaElementoPresenter.mostraVisualizzaElementiDellaCategoria());

        //Check box
        checkboxNome = findViewById(R.id.checkBoxFiltroNome);
        checkboxPrezzo = findViewById(R.id.checkBoxFiltroPrezzo);
        checkboxAllergeni = findViewById(R.id.checkBoxFiltroTabellaAllergene);
        checkboxNome.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                checkboxPrezzo.setChecked(false);
            }
        });
        checkboxPrezzo.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                checkboxNome.setChecked(false);
            }
        });

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

    private void filtraAllergeni() {
        List<String> allergeniElemento;
        Iterator<ElementoMenu> elementoMenuIterator = elementiMenu.iterator();
        while (elementoMenuIterator.hasNext()) {
            ElementoMenu e = elementoMenuIterator.next();
            allergeniElemento = e.getElencoAllergeni();
            for (String s : allergeni) {
                if (allergeniElemento.contains(s)) {
                    elementoMenuIterator.remove();
                    break;
                }
            }
        }

    }

    @Override
    public void onBackPressed() {
        intentVisualizzaCategoria.putExtra("idCategoria", getIntent().getSerializableExtra("idCategoria"));
        intentVisualizzaCategoria.putExtra("nomeCategoria", getIntent().getStringExtra("nomeCategoria"));
        intentVisualizzaCategoria.putExtra("elementiCategoria", getIntent().getSerializableExtra("elementiCategoria"));
        startActivity(intentVisualizzaCategoria);
        super.onBackPressed();
    }

    @Override
    public void impossibileModificareOrdine(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}
