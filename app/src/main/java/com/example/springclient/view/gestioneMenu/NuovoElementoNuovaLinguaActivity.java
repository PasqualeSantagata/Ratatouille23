package com.example.springclient.view.gestioneMenu;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.presenter.ElementoMenuPresenter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class NuovoElementoNuovaLinguaActivity extends AppCompatActivity {

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
    private ElementoMenuPresenter elementoMenuPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("NUOVO ELEMENTO DEL MENU");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_aggiungi_elem_in_nuova_lingua_gestione_menu);
        elemento = (ElementoMenu) getIntent().getSerializableExtra("elemento");
        lingua = getIntent().getStringExtra("lingua");
        elementoMenuPresenter = new ElementoMenuPresenter(this);

        initializeComponents();
    }

    private void initializeComponents() {
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
            dialogAllergeni();
        });
        okButton.setOnClickListener(view -> {
            ElementoMenu elementoMenu = getElemento();
            if(elementoMenu != null) {
                elementoMenuPresenter.aggiungiLingua(elemento.getId().toString(), elementoMenu);
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

    public void listenerAllergeni(){
        allergeni = elemento.getElencoAllergeni();
        if(allergeni == null)
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
            c.setClickable(false);
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

        buttonOkDialog = dialogAllergeni.findViewById(R.id.buttonOkTabellaAllergeniDialog);
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
        listenerAllergeni();
        dialogAllergeni.show();

        buttonOkDialog.setOnClickListener(view -> {
            dialogAllergeni.dismiss();
        });
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

    @Override
    public void onBackPressed() {
        Intent intentHome = new Intent(this, StartGestioneMenuActivity.class);
        mostraDialogWarningTwoBtn("Sei sicuro di voler annullare l'inserimento? perderai tutti i dati inseriti", intentHome);

    }

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
}
