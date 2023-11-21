package com.example.springclient.view.gestioneMenu;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.contract.BaseAllergeniDialog;
import com.example.springclient.contract.ModificaElementoContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.presenter.ModificaElementoPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class ModificaElementoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, ModificaElementoContract.View, BaseAllergeniDialog {
    private TextInputLayout textInputLayoutNome;
    private TextInputLayout textInputLayoutDescrizione;
    private TextInputLayout textInputLayoutPrezzo;
    private TextInputEditText textInputEditTextLingua;
    private Button buttonOk;
    private Button buttonIndietro;
    private Button buttonModificaAllergeni;
    private Spinner spinnerCategoria;
    private FloatingActionButton fabAggiungiCategoria;
    private FloatingActionButton fabAggiungiLingua;
    private ElementoMenu elementoMenu;
    private List<String> nomiCategoria;
    private String categoriaSelezionata;
    private List<String> allergeni;
    private ModificaElementoContract.Presenter modificaElementoMenuPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("MODIFICA ELEMENTO NEL MENU");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_modifica_elemento_gestione_menu);
        elementoMenu = (ElementoMenu) getIntent().getSerializableExtra("elementoMenu");
        modificaElementoMenuPresenter = new ModificaElementoPresenter(this);
        modificaElementoMenuPresenter.getNomiCategoriaDisponibili(elementoMenu.getId().toString());

    }

    @Override
    public void initializeComponents() {
        textInputLayoutNome = findViewById(R.id.textInputLayoutModificaNome);
        textInputLayoutPrezzo = findViewById(R.id.textInputLayoutModificaPrezzo);
        textInputLayoutDescrizione = findViewById(R.id.textInputLayoutModificaDescrizione);
        textInputLayoutNome.getEditText().setText((elementoMenu.getNome()));
        textInputLayoutDescrizione.getEditText().setText((elementoMenu.getDescrizione()));
        textInputLayoutPrezzo.getEditText().setText(elementoMenu.getPrezzo().toString());
        fabAggiungiCategoria = findViewById(R.id.floatingModificaCategoria);
        buttonModificaAllergeni = findViewById(R.id.buttonModificaAllergeni);
        buttonIndietro = findViewById(R.id.buttonIndietroModElemGestioneMenu);
        textInputEditTextLingua = findViewById(R.id.textInputEditTextLinguaCorrenteModificaElemGestioneMenu);
        fabAggiungiLingua = findViewById(R.id.floatingActionButtonAggiungiLingua);
        buttonOk = findViewById(R.id.buttonOkModifica);

        spinnerCategoria = findViewById(R.id.spinnerCategoriaInserimentoNelMenu);
        spinnerCategoria.setOnItemSelectedListener(this);
        ArrayAdapter adapterCategorie = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nomiCategoria);
        adapterCategorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapterCategorie);
        textInputEditTextLingua.setText(elementoMenu.getLingua());

        fabAggiungiLingua.setOnClickListener(view -> {
            Intent nuovaLingua = new Intent(this, SelezioneNuovaLinguaActivity.class);
            nuovaLingua.putExtra("elemento", elementoMenu);
            startActivity(nuovaLingua);
        });

        fabAggiungiCategoria.setOnClickListener(view -> {
            if (categoriaSelezionata != null) {
                modificaElementoMenuPresenter.aggiungiElementoAllaCategoria(categoriaSelezionata, elementoMenu);
            }
        });

        buttonModificaAllergeni.setOnClickListener(view -> {
            allergeni = elementoMenu.getElencoAllergeni();
            dialogAllergeni(this, allergeni, false);
        });
        buttonOk.setOnClickListener(view -> {
            if(controllaCampi()) {
                disabilitaErrori();
                modificaElementoMenuPresenter.modificaElementoMenu(elementoMenu);
            }

        });
        buttonIndietro.setOnClickListener(view -> {
            //questa schermata è raggiungibile da due diverse quindi indietro dovrebbe corrispondere ad onBackPressed di default
            mostraDialogWarningTwoBtn("Attenzione se torni indietro tutte le modifiche effettuate fino ad ora andranno perse! Continuare? ");
        });

    }

    public void disabilitaErrori() {
        textInputLayoutNome.setErrorEnabled(false);
        textInputLayoutPrezzo.setErrorEnabled(false);
        textInputLayoutDescrizione.setErrorEnabled(false);
    }


    private boolean controllaCampi() {
        boolean corretto = true;
        String nome, descrizione, prezzo;
        nome = textInputLayoutNome.getEditText().getText().toString();
        descrizione = textInputLayoutDescrizione.getEditText().getText().toString();
        prezzo = textInputLayoutPrezzo.getEditText().getText().toString();

        if(nome.equals("")){
            textInputLayoutNome.setError("Nome non valido");
            corretto = false;
        }
        if(prezzo.equals("")){
            textInputLayoutPrezzo.setError("Prezzo non valido");
            corretto = false;
        }
        if(descrizione.equals("")){
            textInputLayoutDescrizione.setError("Descrizione non valida");
            corretto = false;
        }
        return corretto;
    }

    private void mostraDialogWarningTwoBtn(String messaggio) {
        Dialog dialogAttenzione = new Dialog(this);
        dialogAttenzione.setContentView(R.layout.dialog_warning_two_button);

        TextView messaggiodialog = dialogAttenzione.findViewById(R.id.textViewDialogeWarnTwoBtn);
        messaggiodialog.setText(messaggio);

        Button buttonSi = dialogAttenzione.findViewById(R.id.buttonSiDialogWarnTwoBtn);
        Button buttonNo = dialogAttenzione.findViewById(R.id.buttonNoDialogWarnTwoBtn);
        dialogAttenzione.show();

        buttonSi.setOnClickListener(view -> {
            onBackPressed();
            dialogAttenzione.dismiss();
        });
        buttonNo.setOnClickListener(view -> {
            dialogAttenzione.dismiss();
        });
    }

    private void mostraDialogOkOneBtn(String messaggio) {
        Dialog dialogOk = new Dialog(this);
        dialogOk.setContentView(R.layout.dialog_ok_one_button);

        TextView textViewMess = dialogOk.findViewById(R.id.textViewDialogOkTwoBtn);
        textViewMess.setText(messaggio);
        Button buttonOk = dialogOk.findViewById(R.id.okDialog);
        buttonOk.setOnClickListener(view -> {
            onBackPressed();
            dialogOk.dismiss();
        });

        dialogOk.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        categoriaSelezionata = nomiCategoria.get(i);
        fabAggiungiCategoria.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



    private void mostraDialogErrorOneBtn(String messaggio) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_err_one_btn);

        TextView textViewmess = dialog.findViewById(R.id.textViewErrorMessageDialogErrorOneBtn);
        textViewmess.setText(messaggio);

        Button buttonOk = dialog.findViewById(R.id.buttonOkDialogErrorOneBtn);
        buttonOk.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public void setNomiCategoria(List<String> nomiCategoria) {
        this.nomiCategoria = nomiCategoria;
    }

    @Override
    public void disabilitaFabInserisciTraduzione() {
        fabAggiungiLingua.setVisibility(View.INVISIBLE);
        TextView textViewLingua = findViewById(R.id.textViewLingua);
        textViewLingua.setText("  Lingua corrente  ");


    }
    @Override
    public void elementoGiaPresenteNellaCategoria(){
        Toast.makeText(this, "Elemento già presente nella categoria", Toast.LENGTH_LONG).show();
    }

    @Override
    public void elementoAggiuntoAdUnaCategoria(String nome){
        Toast.makeText(this, "Elemento aggiunto alla categoria " + nome , Toast.LENGTH_LONG).show();
    }

    @Override
    public void elementoModificatoCorrettamente(){
        mostraDialogOkOneBtn("Elemento modificato correttamente");
    }

    @Override
    public void erroreModifica(){
        mostraDialogErrorOneBtn("Errore nella connessione con il server");
    }

    @Override
    public void mostraErrori(String errore){
        if(errore.contains("nome")){
            textInputLayoutDescrizione.setError("Nome elemento non valido");
        }
        else if(errore.contains("prezzo")){
            textInputLayoutDescrizione.setError("Prezzo elemento non valido");
        }
        else{
            textInputLayoutDescrizione.setError("Desrizione non valida");
        }

    }

    @Override
    public Context getContext() {
        return getContext();
    }

}
