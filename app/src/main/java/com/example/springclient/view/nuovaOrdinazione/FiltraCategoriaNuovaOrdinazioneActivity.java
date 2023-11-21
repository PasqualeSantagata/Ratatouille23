package com.example.springclient.view.nuovaOrdinazione;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.contract.BaseAllergeniDialog;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.entity.Portata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class FiltraCategoriaNuovaOrdinazioneActivity extends AppCompatActivity implements BaseAllergeniDialog {
    private Button buttonNome;
    private Button buttonPrezzo;
    private Button buttonOk;
    private Button buttonAnnulla;
    private CheckBox checkboxNome;
    private CheckBox checkboxPrezzo;
    private CheckBox checkboxAllergeni;
    private Button buttonTabellaAllergeni;
    private List<String> allergeni;
    private List<Portata> elementiMenu;
    private String nome;
    private Intent intentVisualizzaCategoria;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtra_categoria_gestione_menu);
        elementiMenu = (List<Portata>) getIntent().getSerializableExtra("elementiMenu");
        nome = getIntent().getStringExtra("nomeCategoria");
        allergeni = new ArrayList<>();
        initializeComponents();

    }

    private void initializeComponents() {
        //buttons
        buttonNome = findViewById(R.id.buttonNomeFiltraCategoria);
        buttonPrezzo = findViewById(R.id.buttonPrezzoFiltraCategoria);
        buttonTabellaAllergeni = findViewById(R.id.buttonFiltraTabellaAllergeni);
        buttonOk = findViewById(R.id.buttonOkFiltraCategoria);
        buttonAnnulla = findViewById(R.id.buttonAnnullaFiltraCategoria);

        buttonNome.setOnClickListener(view -> {
            if(buttonNome.getText().toString().equals(getString(R.string.nome_up))){
                buttonNome.setText(R.string.nome_down);
            }else{
                buttonNome.setText(R.string.nome_up);
            }

        });
        buttonPrezzo.setOnClickListener(view -> {
            if(buttonPrezzo.getText().toString().equals(getString(R.string.prezzo_up))){
                buttonPrezzo.setText(R.string.prezzo_down);
            }else{
                buttonPrezzo.setText(R.string.prezzo_up);
            }
        });
        buttonTabellaAllergeni.setOnClickListener(view -> {
            dialogAllergeni(this, allergeni, false);
        });
        intentVisualizzaCategoria = new Intent(this, VisualizzaCategoriaActivity.class);

        buttonOk.setOnClickListener(view -> {
            if(checkboxAllergeni.isChecked()){
                filtraAllergeni();
            }
            if(checkboxNome.isChecked()) {
                if (buttonNome.getText().toString().equals(getString(R.string.nome_up))) {
                   Collections.sort(elementiMenu, Portata.compareNomeCrescente);

                } else {
                    Collections.sort(elementiMenu, Portata.compareNomeDecrescente);
                }
            }
            if(checkboxPrezzo.isChecked()){
                if(buttonPrezzo.getText().toString().equals(getString(R.string.prezzo_up))){
                    Collections.sort(elementiMenu, Portata.comparePrezzoCrescente);
                }else{
                    Collections.sort(elementiMenu, Portata.comparePrezzoDecrescente);
                }
            }
            intentVisualizzaCategoria.putExtra("elementi",(Serializable) elementiMenu);
            intentVisualizzaCategoria.putExtra("nomeCategoria", nome);

            startActivity(intentVisualizzaCategoria);
        });
        buttonAnnulla.setOnClickListener(view -> {
            onBackPressed();
        });

        //Check box
        checkboxNome = findViewById(R.id.checkBoxFiltroNome);
        checkboxPrezzo = findViewById(R.id.checkBoxFiltroPrezzo);
        checkboxAllergeni = findViewById(R.id.checkBoxFiltroTabellaAllergene);
        checkboxNome.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                checkboxPrezzo.setChecked(false);
            }
        });
        checkboxPrezzo.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                checkboxNome.setChecked(false);
            }
        });

    }

    private void filtraAllergeni(){
        List<String> allergeniElemento;
        Iterator<Portata> elementoMenuIterator = elementiMenu.iterator();
        while(elementoMenuIterator.hasNext()){
            ElementoMenu e = elementoMenuIterator.next().getElementoMenu();
            allergeniElemento = e.getElencoAllergeni();
            for(String s: allergeni){
                if(allergeniElemento.contains(s)){
                    elementoMenuIterator.remove();
                    break;
                }
            }
        }

    }
    @Override
    public void onBackPressed() {
        intentVisualizzaCategoria.putExtra("elementi",(Serializable) elementiMenu);
        intentVisualizzaCategoria.putExtra("nomeCategoria", nome);

        startActivity(intentVisualizzaCategoria);
        super.onBackPressed();
    }
}
