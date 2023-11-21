package com.example.springclient.view.gestioneMenu;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.contract.BaseAllergeniDialog;
import com.example.springclient.contract.ModificaElementoContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.presenter.ModificaElementoPresenter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class NuovoElementoNuovaLinguaActivity extends AppCompatActivity implements ModificaElementoContract.ViewAggiungiLingua, BaseAllergeniDialog {

    private TextInputLayout textInputLayoutNome;
    private TextInputLayout textInputLayoutDescrizione;
    private TextInputLayout textInputLayoutPrezzo;
    private TextInputEditText textInputEditTextLingua;
    private Button inserisciAllergeniButton;
    private ElementoMenu elemento;
    private String lingua;
    private CheckBox checkBoxArachidi, checkBoxAnidrideSolforosa, checkBoxCrostacei, checkBoxFruttaGuscio,
            checkBoxGlutine, checkBoxLatte, checkBoxLupini, checkBoxMolluschi, checkBoxPesce,
            checkBoxSedano, checkBoxSenape, checkBoxSesamo, checkBoxSoia, checkBoxUova;
    private List<String> allergeni;
    private List<CheckBox> checkBoxes;
    private Button buttonOkDialog;
    private Button okButton;
    private Button indietroButton;
    private ModificaElementoContract.Presenter modificaElementoPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("NUOVO ELEMENTO DEL MENU");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_aggiungi_elem_in_nuova_lingua_gestione_menu);
        elemento = (ElementoMenu) getIntent().getSerializableExtra("elemento");
        lingua = getIntent().getStringExtra("lingua");
        modificaElementoPresenter = new ModificaElementoPresenter(this);
        initializeComponents();
    }


    @Override
    public void initializeComponents() {
        //Text
        textInputLayoutNome = findViewById(R.id.textInputLayout8);
        textInputLayoutDescrizione = findViewById(R.id.textInputLayoutDescrizioneNuovaLinguaGestioneMenu);
        textInputLayoutPrezzo = findViewById(R.id.textInputLayoutPrezzoNovaLingua);
        textInputEditTextLingua = findViewById(R.id.textInputEditTextLinguaCorrenteInserisciElemSecondaLingua);
        textInputLayoutPrezzo.getEditText().setText(elemento.getPrezzo().toString());
        textInputLayoutPrezzo.setEnabled(false);
        textInputEditTextLingua.setEnabled(false);
        textInputEditTextLingua.setText(lingua);

        //Buttons
        inserisciAllergeniButton = findViewById(R.id.iserisciAllergeneNuovaLinguaButton);
        okButton = findViewById(R.id.buttonOkElemNuovaLingua);
        indietroButton = findViewById(R.id.buttonIndietroElemNuovaLingua);
        inserisciAllergeniButton.setOnClickListener(view -> {
            allergeni = elemento.getElencoAllergeni();
            dialogAllergeni(this, allergeni, false);
        });
        okButton.setOnClickListener(view -> {
            ElementoMenu elementoMenu = getElemento();
            if(elementoMenu != null) {
                modificaElementoPresenter.aggiungiLingua(elemento.getNome(), elementoMenu);
            }
        });
        indietroButton.setOnClickListener(view -> {
            Intent intentHome = new Intent(this, StartGestioneMenuActivity.class);
            mostraDialogWarningTwoBtn("Sei sicuro di voler tornare indietro? perderai tutti i dati inseriti!", intentHome);

        });
    }

    private ElementoMenu getElemento() {
        String nome, descrizione;
        List<String> allergeni = elemento.getElencoAllergeni();
        nome = textInputLayoutNome.getEditText().getText().toString();
        descrizione = textInputLayoutDescrizione.getEditText().getText().toString();
        ElementoMenu elementoMenuTradotto = null;
        if(checkFields()) {
            elementoMenuTradotto = new ElementoMenu(nome, elemento.getPrezzo(), descrizione, allergeni, lingua);
        }
        return elementoMenuTradotto;
    }

    private boolean checkFields(){
        boolean checked = true;
        String nomeElemento, descrizione;

        nomeElemento = textInputLayoutNome.getEditText().getText().toString();
        descrizione = textInputLayoutDescrizione.getEditText().getText().toString();

        if(nomeElemento.equals("")){
            textInputLayoutNome.setError("Nome non valido");
            checked = false;
        }
        else{
            textInputLayoutNome.setErrorEnabled(false);
        }
        if(descrizione.isEmpty()){
            textInputLayoutDescrizione.setError("Descrizione non valida");
            checked = false;
        }
        else{
            textInputLayoutDescrizione.setErrorEnabled(false);
        }

        return checked;
    }

    private void mostraDialogWarningTwoBtn(String messaggio, Intent intentSi){
        Dialog dialogAttenzione = new Dialog(this);
        dialogAttenzione.setContentView(R.layout.dialog_warning_two_button);

        TextView messaggiodialog = dialogAttenzione.findViewById(R.id.textViewDialogeWarnTwoBtn);
        messaggiodialog.setText(messaggio);

        Button buttonSi = dialogAttenzione.findViewById(R.id.buttonSiDialogWarnTwoBtn);
        Button buttonNo = dialogAttenzione.findViewById(R.id.buttonNoDialogWarnTwoBtn);
        buttonSi.setOnClickListener(view -> {
            if(intentSi != null)
                super.onBackPressed();

            dialogAttenzione.dismiss();
        });
        buttonNo.setOnClickListener(view -> {
            dialogAttenzione.dismiss();
        });

        dialogAttenzione.show();
    }

    public void mostraDialogWarning(String messaggio) {
        Dialog dialogAttenzione = new Dialog(this);
        dialogAttenzione.setContentView(R.layout.dialog_warning_one_button);

        TextView messaggiodialog = dialogAttenzione.findViewById(R.id.textViewMessageDialogueErrorOneBt);
        messaggiodialog.setText(messaggio);

        Button buttonOk = dialogAttenzione.findViewById(R.id.buttonOkDialogueErrorOneBt);
        dialogAttenzione.show();

        buttonOk.setOnClickListener(view -> {
            dialogAttenzione.dismiss();
        });
    }

    @Override
    public void onBackPressed() {
        Intent intentHome = new Intent(this, StartGestioneMenuActivity.class);
        mostraDialogWarningTwoBtn("Sei sicuro di voler annullare l'inserimento? perderai tutti i dati inseriti", intentHome);

    }

    @Override
    public void linguaAggiunta() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_ok_one_button);
        TextView dialogTv = dialog.findViewById(R.id.textViewDialogOkTwoBtn);
        Button okButton = dialog.findViewById(R.id.okDialog);
        okButton.setText("OK");
        dialogTv.setText("Traduzione inserita con successo");
        okButton.setOnClickListener(view -> {
            Intent nuovaLingua = new Intent(this, StartGestioneMenuActivity.class);
            startActivity(nuovaLingua);
        });
        dialog.show();
    }

    @Override
    public Context getContext() {
        return getContext();
    }



}
