package com.example.springclient.view;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.apiUtils.ProdottoResponse;
import com.example.springclient.contract.ElementoMenuContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.presenter.ElementoMenuPresenter;
import com.example.springclient.presenter.FoodFactsPresenter;
import com.example.springclient.view.adapters.SpinnerAdapterCategorie;
import com.google.android.material.textfield.TextInputLayout;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;

public class InserisciElementoActivity extends AppCompatActivity implements ElementoMenuContract.View {

    private TextInputLayout nomeElementoTextInputLayout;
    private TextInputLayout prezzoElementoTextInputLayout;
    private TextInputLayout elencoAllergeniTextInputLayout;
    private TextInputLayout descrizioneTextInputLayout;
    private Button okButton;
    private Button indietroButton;
    private ElementoMenuPresenter elementoMenuPresenter;
    private FoodFactsPresenter foodFactsPresenter;
    private Spinner spinnerCategorie;
    private SpinnerAdapterCategorie spinnerAdapterCategorie;
    private AutoCompleteTextView autoTextView;
    private List<String> suggeriti;
    private ArrayAdapter<String> adapter;
    private List<ProdottoResponse> prodotti;
    private Disposable autocompDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserisci_elemento);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        elementoMenuPresenter = new ElementoMenuPresenter(this);
        foodFactsPresenter = new FoodFactsPresenter(this);
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
        autoTextView = (AutoCompleteTextView) nomeElementoTextInputLayout.getEditText();
        //Spinner
        spinnerCategorie = findViewById(R.id.categorie_spinner);
        List<String> categorie = new ArrayList<>();

        //renderle leggibili dal DB
        categorie.add("Primo");
        categorie.add("Secondo");
        categorie.add("Drink");
        categorie.add("Dessert");


        spinnerAdapterCategorie = new SpinnerAdapterCategorie(InserisciElementoActivity.this, categorie);
        spinnerCategorie.setAdapter(spinnerAdapterCategorie);

        //adapter suggeritore nome elementi
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, suggeriti);
        autoTextView.setThreshold(3);

        autocompDisposable =
            RxTextView.afterTextChangeEvents(autoTextView)
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    charSequence -> {
                        Editable editable = autoTextView.getText();
                        if (editable != null && !editable.toString().isEmpty()) {
                            foodFactsPresenter.getElementoMenuDetails(editable.toString());
                        }
                    });


        autoTextView.setOnItemClickListener(
                (adapterView, view, i, l) -> {
                    descrizioneTextInputLayout.getEditText().setText(prodotti.get(i).getGeneric_name());
                    if (prodotti.get(i).getAllergens()!= null){
                        /** traduci allergeni da allergens.json*/

                    }
                    else{
                        elencoAllergeniTextInputLayout.setHint("allergeni non trovati");
                    }
                });


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
        String descrizione, lingua = "Italiano";

        ElementoMenu elementoMenu;

        List<String> listOfAllergeni = new ArrayList<>();

        nomeElemento = nomeElementoTextInputLayout.getEditText().getText().toString();
        prezzoElemento = prezzoElementoTextInputLayout.getEditText().getText().toString();
        elencoAllergeni = elencoAllergeniTextInputLayout.getEditText().getText().toString().split(",");
        descrizione = descrizioneTextInputLayout.getEditText().getText().toString();
        Log.e("prezzo:", prezzoElemento);
        Collections.addAll(listOfAllergeni, elencoAllergeni);
        elementoMenu = new ElementoMenu(nomeElemento, Float.parseFloat(prezzoElemento), descrizione, listOfAllergeni, lingua);
        return  elementoMenu;
    }

    private boolean checkFields(){
        boolean checked = true;
        String nomeElemento, prezzoElemento;
        String[] elencoAllergeni;
        String descrizione;

        nomeElemento = nomeElementoTextInputLayout.getEditText().getText().toString();
        prezzoElemento = prezzoElementoTextInputLayout.getEditText().getText().toString();
        elencoAllergeni = elencoAllergeniTextInputLayout.getEditText().getText().toString().split(",");
        descrizione = descrizioneTextInputLayout.getEditText().getText().toString();

        if(nomeElemento.equals("")){
            nomeElementoTextInputLayout.setError("Nome non valido");
            checked = false;
        }
        else{
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
            String s = "";
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

    public void showErrors(List<String> listOfErrors){
        for(String s: listOfErrors) {
          if(s.toLowerCase().contains("nome")) {
              nomeElementoTextInputLayout.setError(s);
          }
        }
    }

    public void generateNames(List<String> names, List<ProdottoResponse> prodotti){
       // suggeriti.clear();
        suggeriti = names;
        this.prodotti = prodotti;
        Log.d("suggeriti: ", suggeriti.toString());
        adapter = new ArrayAdapter<>(InserisciElementoActivity.this,android.R.layout.simple_dropdown_item_1line, suggeriti);
        autoTextView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        autocompDisposable.dispose();
    }
}