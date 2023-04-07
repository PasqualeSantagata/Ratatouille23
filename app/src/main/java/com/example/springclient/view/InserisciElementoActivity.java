package com.example.springclient.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.contract.ElementoMenuContract;
import com.example.springclient.entity.Allergene;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.presenter.ElementoMenuPresenter;
import com.example.springclient.view.adapters.SpinnerAdapterCategorie;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class InserisciElementoActivity extends AppCompatActivity implements ElementoMenuContract.View {

    private TextInputLayout nomeElementoTextInputLayout;
    private TextInputLayout prezzoElementoTextInputLayout;
    private TextInputLayout elencoAllergeniTextInputLayout;
    private TextInputLayout descrizioneTextInputLayout;
    private Button okButton;
    private Button indietroButton;
    private ElementoMenuPresenter elementoMenuPresenter;

    private Spinner spinnerCategorie;

    private SpinnerAdapterCategorie spinnerAdapterCategorie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserisci_elemento);

        elementoMenuPresenter = new ElementoMenuPresenter(this);
        initializeComponents();

    }


    @Override
    public void initializeComponents() {

        okButton = findViewById(R.id.buttonInserElemOk);
        indietroButton = findViewById(R.id.buttonInserElemIndietro);

        nomeElementoTextInputLayout = findViewById(R.id.TextInputLayoutNomeInserisciElementoMenu);
        prezzoElementoTextInputLayout = findViewById(R.id.TextInputLayoutPrezzoInserisciElementoMenu);
        elencoAllergeniTextInputLayout = findViewById(R.id.TextInputLayoutAllergeniInserisciElementoMenu);
        descrizioneTextInputLayout = findViewById(R.id.TextInputLayoutDescrizioneInserisciElementoMenu);

        //Spinner
        spinnerCategorie = findViewById(R.id.categorie_spinner);
        List<String> categorie = new ArrayList<>();
        categorie.add("Primo");
        categorie.add("Secondo");
        categorie.add("Drink");
        categorie.add("Dessert");
        spinnerAdapterCategorie = new SpinnerAdapterCategorie(InserisciElementoActivity.this, categorie);
        spinnerCategorie.setAdapter(spinnerAdapterCategorie);




        okButton.setOnClickListener(view -> {
            if(checkFields()) {
                ElementoMenu elementoMenu = getElementoValues();
                elementoMenuPresenter.saveElementoMenu(elementoMenu);
            }
        });


        indietroButton.setOnClickListener(view -> {

        });



    }

    @Override
    public ElementoMenu getElementoValues() {
        String nomeElemento, prezzoElemento;
        String[] elencoAllergeni;
        String descrizione;

        ElementoMenu elementoMenu;

        List<Allergene> listOfAllergeni = new ArrayList<>();

        nomeElemento = nomeElementoTextInputLayout.getEditText().toString();
        prezzoElemento = prezzoElementoTextInputLayout.getEditText().toString();
        elencoAllergeni = elencoAllergeniTextInputLayout.getEditText().toString().split(",");
        descrizione = descrizioneTextInputLayout.getEditText().toString();

        for(String s: elencoAllergeni) {
            listOfAllergeni.add(new Allergene(s));
        }
        elementoMenu = new ElementoMenu(nomeElemento, Float.parseFloat(prezzoElemento), descrizione, listOfAllergeni);


        return  elementoMenu;

    }

    private boolean checkFields(){
        boolean checked = true;
        String nomeElemento, prezzoElemento;
        String[] elencoAllergeni;
        String descrizione;


        nomeElemento = nomeElementoTextInputLayout.getEditText().toString();
        prezzoElemento = prezzoElementoTextInputLayout.getEditText().toString();
        elencoAllergeni = elencoAllergeniTextInputLayout.getEditText().toString().split(",");
        descrizione = descrizioneTextInputLayout.getEditText().toString();


        if(nomeElemento.matches("")){
            nomeElementoTextInputLayout.setError("Nome non valido");

            checked = false;
        }
        else{
            Log.d("valore", String.valueOf(10));
            nomeElementoTextInputLayout.setErrorEnabled(false);
        }

        if(prezzoElemento.isEmpty() || !prezzoElemento.matches("[+-]?([0-9]*[.])?[0-9]+")){
            prezzoElementoTextInputLayout.setError("Prezzo non valido");

            checked = false;
        }
        else{
            prezzoElementoTextInputLayout.setErrorEnabled(false);
        }
        if(descrizione.isEmpty()){
            descrizioneTextInputLayout.setError("Descrizione non valida");

            checked = false;
        }
        else{
            prezzoElementoTextInputLayout.setErrorEnabled(false);
        }


        return checked;
    }


    @Override
    public void cleanFields() {
        nomeElementoTextInputLayout.getEditText().setText("");
        prezzoElementoTextInputLayout.getEditText().setText("");
        elencoAllergeniTextInputLayout.getEditText().setText("");
        descrizioneTextInputLayout.getEditText().setText("");

    }


}