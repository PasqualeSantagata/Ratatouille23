package com.example.springclient.view.inserisciElemento;


import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import com.example.springclient.R;
import com.example.springclient.view.BaseAllergeniDialog;
import com.example.springclient.contract.InserisciElementoContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.presenter.FoodFactsPresenter;
import com.example.springclient.presenter.InserisciElementoPresenter;
import com.example.springclient.view.inserisciNuovaLingua.SelezioneNuovaLinguaActivity;
import com.example.springclient.view.StartGestioneMenuActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;

public class InserisciElementoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, InserisciElementoContract.InserisciElementoViewContract, BaseAllergeniDialog {
    private TextInputLayout nomeElementoTextInputLayout;
    private TextInputLayout prezzoElementoTextInputLayout;
    private TextInputLayout descrizioneTextInputLayout;
    private Button okButton;
    private Button indietroButton;
    private Button inserisciButton;
    private FoodFactsPresenter foodFactsPresenter;
    private AutoCompleteTextView autoTextView;
    private List<String> suggeriti;
    private ArrayAdapter<String> adapter;
    private Disposable autocompDisposable;
    private List<String> allergeni;
    private String linguaSelezionata;
    private List<String> lingue;
    private String categoriaSelezionata;
    private ElementoMenu elementoMenu;
    private InserisciElementoContract.Presenter inserisciElementoPresenter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("INSERISCI ELEMENTO");
        setContentView(R.layout.activity_inserisci_elemento_gestione_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        progressBar = findViewById(R.id.progressBarInsersciElemento);
        progressBar.setVisibility(View.INVISIBLE);

        inserisciElementoPresenter = new InserisciElementoPresenter(this);
        foodFactsPresenter = new FoodFactsPresenter(this);

        allergeni = new ArrayList<>();

        linguaSelezionata = getIntent().getStringExtra("lingua");
        categoriaSelezionata = getIntent().getStringExtra("categoria");

        inizializzaComponenti();
    }

    @Override
    public void inizializzaComponenti() {
        //Text
        nomeElementoTextInputLayout = findViewById(R.id.TextInputLayoutNomeInserisciElementoMenu);
        prezzoElementoTextInputLayout = findViewById(R.id.TextInputLayoutPrezzoInserisciElementoMenu);
        descrizioneTextInputLayout = findViewById(R.id.TextInputLayoutDescrizioneInserisciElementoMenu);
        autoTextView = (AutoCompleteTextView) nomeElementoTextInputLayout.getEditText();

        //adapter suggeritore nome elementi
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, suggeriti);

        autocompDisposable =
                RxTextView.textChangeEvents(autoTextView)
                        .debounce(500, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                charSequence -> {
                                    Editable editable = autoTextView.getText();
                                    if (editable != null && !editable.toString().isEmpty()) {
                                        foodFactsPresenter.getElementoMenuDetails(editable.toString());
                                    }
                                });

        //Buttons
        okButton = findViewById(R.id.buttonInserElemOk);
        indietroButton = findViewById(R.id.buttonInserElemIndietro);
        inserisciButton = findViewById(R.id.buttonInserisciElementoGestioneMenu);
        okButton.setOnClickListener(view -> {
            String nome, prezzo, descrizione;
            nome = nomeElementoTextInputLayout.getEditText().getText().toString();
            prezzo = prezzoElementoTextInputLayout.getEditText().getText().toString();
            descrizione = prezzoElementoTextInputLayout.getEditText().getText().toString();
            disabilitaErrori();
            if (inserisciElementoPresenter.validaInserimentoElemento(nome, prezzo, descrizione)) {
                elementoMenu = getElementoValues();
                inserisciElementoPresenter.inserisciElementoMenu(elementoMenu, categoriaSelezionata);

            }
        });
        indietroButton.setOnClickListener(view -> inserisciElementoPresenter.tornaStartGestioneMenu());
        inserisciButton.setOnClickListener(view -> dialogAllergeni(this, allergeni, false));

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
    public void mostraSelezioneNuovaLingua(){
        Intent nuovaLingua = new Intent(this, SelezioneNuovaLinguaActivity.class);
        nuovaLingua.putExtra("elemento", elementoMenu);

        cleanFields();
        startActivity(nuovaLingua);
    }

    @Override
    public void elementoInseritoCorrettamente() {
        cleanFields();
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_ok_two_button);
        TextView dialogTv = dialog.findViewById(R.id.textViewDialogOkTwoBtn);
        Button indietroButton = dialog.findViewById(R.id.okDialog);
        Button okButton = dialog.findViewById(R.id.okDialog2);
        indietroButton.setText("INDIETRO");
        okButton.setText("AGGIUNGI");
        dialogTv.setText("Elemento salvato correttamente. Se vuoi aggiungere anche una traduzione premi aggiungi, altrimenti premi " +
                "indietro per aggiungere un nuovo elemento");
        indietroButton.setOnClickListener(view -> inserisciElementoPresenter.mostraHomeNuovoElemento());
        okButton.setOnClickListener(view -> inserisciElementoPresenter.mostraSelezioneNuovaLingua());
        dialog.dismiss();
        dialog.show();

    }

    @Override
    public void mostraErroreInserimentoElemento(String errore) {
        if (errore.toLowerCase().contains("nome")) {
            nomeElementoTextInputLayout.setError(errore);
        }
        else if(errore.toLowerCase().contains("prezzo")){
            prezzoElementoTextInputLayout.setError(errore);
        } else if (errore.toLowerCase().contains("descrizione")) {
            descrizioneTextInputLayout.setError(errore);
        } else{
            Toast.makeText(this, errore, Toast.LENGTH_LONG).show();
        }

    }


    private ElementoMenu getElementoValues() {
        String nomeElemento, prezzoElemento;
        String descrizione;
        ElementoMenu elementoMenu;

        nomeElemento = nomeElementoTextInputLayout.getEditText().getText().toString();
        prezzoElemento = prezzoElementoTextInputLayout.getEditText().getText().toString();
        descrizione = descrizioneTextInputLayout.getEditText().getText().toString();
        elementoMenu = new ElementoMenu(nomeElemento, Float.parseFloat(prezzoElemento), descrizione, allergeni, linguaSelezionata);
        return elementoMenu;
    }

    private void disabilitaErrori(){
        nomeElementoTextInputLayout.setErrorEnabled(false);
        prezzoElementoTextInputLayout.setErrorEnabled(false);
        descrizioneTextInputLayout.setErrorEnabled(false);
    }



    private void cleanFields() {
        nomeElementoTextInputLayout.getEditText().setText("");
        prezzoElementoTextInputLayout.getEditText().setText("");
        descrizioneTextInputLayout.getEditText().setText("");
    }

    @Override
    public void autocompletamentoIrrangiungibile(){
        Toast.makeText(this, "Impossibile contattare il servizion dia autocompletamento", Toast.LENGTH_LONG).show();

    }

    @Override
    public void generaNomi(List<String> names) {
        suggeriti = names;
        adapter = new ArrayAdapter<>(InserisciElementoActivity.this, android.R.layout.simple_dropdown_item_1line, suggeriti);
        autoTextView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void mostraHomeNuovoElemento() {
        Intent intentHome = new Intent(this, HomeNuovoElementoActivity.class);
        startActivity(intentHome);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        autocompDisposable.dispose();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        linguaSelezionata = lingue.get(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void erroreInserimentoElemento(){
        Dialog dialog = new Dialog(this);
        mostraDialogErroreOneBtn(dialog, "Impossibile comunicare con il server", view -> dialog.dismiss());
    }

    @Override
    public void onBackPressed() {
        Dialog dialog = new Dialog(this);
        mostraDialogWarningTwoBtn(dialog,
                "Attenzione, tutti i dati inseriti verranno cancellati se torni indietro, continuare?",
                view -> {
                    Intent intentHome = new Intent(this, StartGestioneMenuActivity.class);
                    startActivity(intentHome);
                }, view -> dialog.dismiss());
    }

}