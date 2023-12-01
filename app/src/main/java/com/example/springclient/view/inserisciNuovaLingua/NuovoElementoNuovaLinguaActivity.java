package com.example.springclient.view.inserisciNuovaLingua;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import com.example.springclient.R;
import com.example.springclient.contract.BaseAllergeniDialog;
import com.example.springclient.contract.InserisciNuovaLinguaContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.presenter.InserisciNuovaLinguaPresenter;
import com.example.springclient.view.StartGestioneMenuActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class NuovoElementoNuovaLinguaActivity extends AppCompatActivity implements InserisciNuovaLinguaContract.NuovoElementoNuovaLinguaView, BaseAllergeniDialog {

    private TextInputLayout textInputLayoutNome;
    private TextInputLayout textInputLayoutDescrizione;
    private TextInputLayout textInputLayoutPrezzo;
    private TextInputEditText textInputEditTextLingua;
    private Button inserisciAllergeniButton;
    private ElementoMenu elemento;
    private String lingua;
    private List<String> allergeni;
    private Button okButton;
    private Button indietroButton;
    private InserisciNuovaLinguaContract.Presenter inserisciNuovaLinguaPresenter;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("NUOVO ELEMENTO DEL MENU");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_aggiungi_elem_in_nuova_lingua_gestione_menu);
        progressBar = findViewById(R.id.progressBarNuovoElementoNuovaLingua);
        progressBar.setVisibility(View.INVISIBLE);
        elemento = (ElementoMenu) getIntent().getSerializableExtra("elemento");
        lingua = getIntent().getStringExtra("lingua");
        inserisciNuovaLinguaPresenter = new InserisciNuovaLinguaPresenter(this);
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
                inserisciNuovaLinguaPresenter.aggiungiLingua(elemento.getNome(), elementoMenu);
            }
        });
        indietroButton.setOnClickListener(view -> onBackPressed());
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

    @Override
    public void onBackPressed() {
        Dialog dialog = new Dialog(this);
        mostraDialogWarningTwoBtn(dialog, "Sei sicuro di voler annullare l'inserimento? perderai tutti i dati inseriti",
                view -> super.onBackPressed(),
                view -> dialog.dismiss());

    }

    @Override
    public void impossibileComunicareServer(String messaggio){
        Dialog dialog = new Dialog(this);
        mostraDialogErroreOneBtn(dialog, messaggio, view -> dialog.dismiss());
    }

    @Override
    public void linguaAggiuntaConSuccesso() {
        Dialog dialog = new Dialog(this);
        mostraDialogOkOneBtn(dialog, "Traduzione inserita con successo", view -> {
            Intent nuovaLingua = new Intent(this, StartGestioneMenuActivity.class);
            startActivity(nuovaLingua);
        });
    }

    @Override
    public void mostraErroreInserimentoElemento(String errore) {
        if (errore.toLowerCase().contains("nome")) {
            textInputLayoutNome.setError(errore);
        }else{
            Toast.makeText(this, errore, Toast.LENGTH_LONG).show();
        }

    }



}
