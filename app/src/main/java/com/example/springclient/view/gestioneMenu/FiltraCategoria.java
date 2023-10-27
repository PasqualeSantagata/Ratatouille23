package com.example.springclient.view.gestioneMenu;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.entity.ElementoMenu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class FiltraCategoria extends AppCompatActivity {
    private Button buttonNome;
    private Button buttonPrezzo;
    private Button buttonOk;
    private Button buttonAnnulla;
    private CheckBox checkboxNome;
    private CheckBox checkboxPrezzo;
    private CheckBox checkboxAllergeni;
    private Button buttonTabellaAllergeni;
    private List<CheckBox> checkBoxes;
    private CheckBox checkBoxArachidi, checkBoxAnidrideSolforosa, checkBoxCrostacei, checkBoxFruttaGuscio,
            checkBoxGlutine, checkBoxLatte, checkBoxLupini, checkBoxMolluschi, checkBoxPesce,
            checkBoxSedano, checkBoxSenape, checkBoxSesamo, checkBoxSoia, checkBoxUova;
    private List<String> allergeni;
    private List<ElementoMenu> elementiMenu;
    private String nome;
    private Intent intentVisualizzaCategoria;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtra_categoria_gestione_menu);
        elementiMenu = (List<ElementoMenu>) getIntent().getSerializableExtra("elementiMenu");
        nome = getIntent().getStringExtra("nomeCategoria");

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
            dialogAllergeni();
        });
        intentVisualizzaCategoria = new Intent(this, VisualizzaElementiDellaCategoriaActivity.class);

        buttonOk.setOnClickListener(view -> {
            if(checkboxAllergeni.isChecked()){
                filtraAllergeni();
            }
            if(checkboxNome.isChecked()) {
                if (buttonNome.getText().toString().equals(getString(R.string.nome_up))) {
                    Collections.sort(elementiMenu, ElementoMenu.compareNomeCrescente);
                } else {
                    Collections.sort(elementiMenu, ElementoMenu.compareNomeDecrescente);
                }
            }
            if(checkboxPrezzo.isChecked()){
                if(buttonPrezzo.getText().toString().equals(getString(R.string.prezzo_up))){
                    Collections.sort(elementiMenu, ElementoMenu.comparePrezzoCrescente);
                }else{
                    Collections.sort(elementiMenu, ElementoMenu.comparePrezzoDecrescente);
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
        Iterator<ElementoMenu> elementoMenuIterator = elementiMenu.iterator();
        while(elementoMenuIterator.hasNext()){
            ElementoMenu e = elementoMenuIterator.next();
            allergeniElemento = e.getElencoAllergeni();
            for(String s: allergeni){
                if(allergeniElemento.contains(s)){
                    elementoMenuIterator.remove();
                    break;
                }
            }
        }

    }

    public void dialogAllergeni(){
        Dialog dialogAllergeni = new Dialog(this);
        dialogAllergeni.setContentView(R.layout.dialog_tabella_allergeni);
        checkBoxArachidi = dialogAllergeni.findViewById(R.id.checkBoxFiltroTabellaAllergene);
        checkBoxAnidrideSolforosa = dialogAllergeni.findViewById(R.id.checkBoxAnidrideSolforosa);
        checkBoxCrostacei = dialogAllergeni.findViewById(R.id.checkBoxCrostacei);
        checkBoxFruttaGuscio = dialogAllergeni.findViewById(R.id.checkBoxFruttaGuscio);
        checkBoxGlutine = dialogAllergeni.findViewById(R.id.checkBoxGlutine);
        checkBoxLatte = dialogAllergeni.findViewById(R.id.checkBoxLatte);
        checkBoxLupini = dialogAllergeni.findViewById(R.id.checkBoxLupini);
        checkBoxMolluschi = dialogAllergeni.findViewById(R.id.checkBoxMolluschi);
        checkBoxPesce = dialogAllergeni.findViewById(R.id.checkBoxPesce);
        checkBoxSedano = dialogAllergeni.findViewById(R.id.checkBoxSedano);
        checkBoxSenape = dialogAllergeni.findViewById(R.id.checkBoxSenape);
        checkBoxSesamo = dialogAllergeni.findViewById(R.id.checkBoxSesamo);
        checkBoxSoia = dialogAllergeni.findViewById(R.id.checkBoxSoia);
        checkBoxUova = dialogAllergeni.findViewById(R.id.checkBoxUova);
        Button buttonOkDialog = dialogAllergeni.findViewById(R.id.buttonOkTabellaAllergeniDialog);
        buttonOkDialog.setOnClickListener(view -> {
            dialogAllergeni.dismiss();
        });

        listenerAllergeni();
        dialogAllergeni.show();
    }

    public void listenerAllergeni(){
        if(allergeni == null) {
            allergeni = new ArrayList<>();
        }
        checkBoxes = new ArrayList<>();
        checkBoxes.add(checkBoxArachidi);
        checkBoxes.add(checkBoxAnidrideSolforosa);
        checkBoxes.add(checkBoxCrostacei);
        checkBoxes.add(checkBoxFruttaGuscio);
        checkBoxes.add(checkBoxGlutine);
        checkBoxes.add(checkBoxLatte);
        checkBoxes.add(checkBoxLupini);
        checkBoxes.add(checkBoxMolluschi);
        checkBoxes.add(checkBoxPesce);
        checkBoxes.add(checkBoxSedano);
        checkBoxes.add(checkBoxSenape);
        checkBoxes.add(checkBoxSesamo);
        checkBoxes.add(checkBoxSoia);
        checkBoxes.add(checkBoxUova);
        for(CheckBox c: checkBoxes){
            String valore = (String)c.getTag();
            if(allergeni.contains(valore)){
                c.setChecked(true);
            }
            c.setOnCheckedChangeListener((compoundButton, b) -> {
                if(b){
                    if(!allergeni.contains(valore)){
                        allergeni.add(valore);
                    }
                }
                else{
                    allergeni.remove(valore);
                }
            });

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
