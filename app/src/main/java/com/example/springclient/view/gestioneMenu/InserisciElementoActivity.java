package com.example.springclient.view.gestioneMenu;


import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.apiUtils.ProdottoResponse;
import com.example.springclient.contract.ElementoMenuContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.presenter.ElementoMenuPresenter;
import com.example.springclient.presenter.FoodFactsPresenter;
import com.google.android.material.textfield.TextInputLayout;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;

public class InserisciElementoActivity extends AppCompatActivity implements ElementoMenuContract.View {

    private ElementoMenuContract.Presenter presenter = new ElementoMenuPresenter(this);
    private TextInputLayout nomeElementoTextInputLayout;
    private TextInputLayout prezzoElementoTextInputLayout;
    private TextInputLayout elencoAllergeniTextInputLayout;
    private TextInputLayout descrizioneTextInputLayout;
    private Button okButton;
    private Button indietroButton;
    private Button inserisciButton;
    private ElementoMenuPresenter elementoMenuPresenter;
    private FoodFactsPresenter foodFactsPresenter;
    private AutoCompleteTextView autoTextView;
    private List<String> suggeriti;
    private ArrayAdapter<String> adapter;
    private List<ProdottoResponse> prodotti;
    private Disposable autocompDisposable;
    private String linguaInserita;
    private CheckBox checkBoxArachidi, checkBoxAnidrideSolforosa, checkBoxCrostacei, checkBoxFruttaGuscio,
            checkBoxGlutine, checkBoxLatte, checkBoxLupini, checkBoxMolluschi, checkBoxPesce,
            checkBoxSedano, checkBoxSenape, checkBoxSesamo, checkBoxSoia, checkBoxUova;
    private List<String> allergeni;
    private List<CheckBox> checkBoxes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("INSERISCI ELEMENTO");
        setContentView(R.layout.activity_inserisci_elemento_gestione_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        elementoMenuPresenter = new ElementoMenuPresenter(this);
        foodFactsPresenter = new FoodFactsPresenter(this);
        //linguaInserita = getIntent().getStringExtra("lingua");
        initializeComponents();
    }

    public void elementoSalvatoCorrettamente() {
        Intent intentElemCaricato = new Intent(this, ElementoInseritoCorrettamenteActivity.class);
        startActivity(intentElemCaricato);
    }

    @Override
    public void initializeComponents() {
        okButton = findViewById(R.id.buttonInserElemOk);
        indietroButton = findViewById(R.id.buttonInserElemIndietro);
        inserisciButton = findViewById(R.id.buttonInserisciElementoGestioneMenu);


        nomeElementoTextInputLayout = findViewById(R.id.TextInputLayoutNomeInserisciElementoMenu);
        prezzoElementoTextInputLayout = findViewById(R.id.TextInputLayoutPrezzoInserisciElementoMenu);
        descrizioneTextInputLayout = findViewById(R.id.TextInputLayoutDescrizioneInserisciElementoMenu);
        autoTextView = (AutoCompleteTextView) nomeElementoTextInputLayout.getEditText();

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
                    String descrizione = prodotti.get(i).getGeneric_name();
                    if(descrizione == null) {
                        Toast.makeText(this, "Descrizione non disponibile", Toast.LENGTH_SHORT).show();

                    }
                    descrizioneTextInputLayout.getEditText().setText(descrizione);
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
            Intent intentHome = new Intent(this, HomeNuovoElementoActivity.class);
            startActivity(intentHome);
        });
        inserisciButton.setOnClickListener(view -> {
            dialogAllergeni();
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
        //elencoAllergeni = elencoAllergeniTextInputLayout.getEditText().getText().toString().split(",");
        descrizione = descrizioneTextInputLayout.getEditText().getText().toString();
        Log.e("prezzo:", prezzoElemento);
        elementoMenu = new ElementoMenu(nomeElemento, Float.parseFloat(prezzoElemento), descrizione, allergeni, lingua);
        return  elementoMenu;
    }

    private boolean checkFields(){
        boolean checked = true;
        String nomeElemento, prezzoElemento;
        String[] elencoAllergeni;
        String descrizione;

        nomeElemento = nomeElementoTextInputLayout.getEditText().getText().toString();
        prezzoElemento = prezzoElementoTextInputLayout.getEditText().getText().toString();
       // elencoAllergeni = elencoAllergeniTextInputLayout.getEditText().getText().toString().split(",");
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

    public void listenerAllergeni(){
        allergeni = new ArrayList<>();
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


    public void dialogAllergeni(){
        Dialog dialogAllergeni = new Dialog(this);
        dialogAllergeni.setContentView(R.layout.dialog_tabella_allergeni);
        checkBoxArachidi = dialogAllergeni.findViewById(R.id.checkBoxArachidi);
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
        listenerAllergeni();
        dialogAllergeni.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        autocompDisposable.dispose();
    }
}