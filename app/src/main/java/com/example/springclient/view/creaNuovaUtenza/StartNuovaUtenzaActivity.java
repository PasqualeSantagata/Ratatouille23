package com.example.springclient.view.creaNuovaUtenza;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.contract.AdminContract;
import com.example.springclient.entity.Ruolo;
import com.example.springclient.entity.Utente;
import com.example.springclient.presenter.AdminPresenter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.List;

public class StartNuovaUtenzaActivity extends AppCompatActivity implements AdminContract.View, AdapterView.OnItemSelectedListener {
    private TextInputLayout textInputNome;
    private TextInputLayout textInputCognome;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;
    private Spinner spinnerRuoli;
    private ProgressBar progressBar;
    private AdminContract.Presenter adminPresenter;
    private Button okButton;
    private Button indietroButton;
    private List<String> ruoli;
    private String ruolo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuova_utenza);
        adminPresenter = new AdminPresenter(this);
        initializeComponents();
    }


    @Override
    public void initializeComponents() {
        textInputNome = findViewById(R.id.textInputLayoutNomeCreaUtenza);
        textInputEmail = findViewById(R.id.textInputLayoutEmailCreaUtenza);
        textInputCognome = findViewById(R.id.textInputLayoutCognomeCreaUtenza);
        textInputPassword = findViewById(R.id.textInputLayoutPasswordCreaUtenza);
        spinnerRuoli = findViewById(R.id.spinnerTipoUtenzaCreaUtenza);
        spinnerRuoli.setOnItemSelectedListener(this);
        ruoli = Arrays.asList(getResources().getStringArray(R.array.array_ruoli));
        ArrayAdapter adapterRuoli = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ruoli);
        adapterRuoli.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRuoli.setAdapter(adapterRuoli);
        progressBar = findViewById(R.id.progressBarInserimentoNuovoUtente);
        progressBar.setVisibility(View.GONE);

        okButton = findViewById(R.id.buttonOkCreaUtenza);
        indietroButton = findViewById(R.id.buttonIndietroNuovaUtenza);
        okButton.setOnClickListener(view -> {
            disabilitaErrori();
            raccogliDati();

        });
        indietroButton.setOnClickListener(view -> {
            onBackPressed();
        });

    }

    @Override
    public void tornaIndietro() {

    }

    @Override
    public void onBackPressed() {
        Dialog dialogAttenzione = new Dialog(this);
        mostraDialogWarningTwoBtn(dialogAttenzione,
                "Attenzione, tutti i dati inseriti verranno cancellati se torni indietro, continuare?",
                view -> {
                    dialogAttenzione.dismiss();
                    super.onBackPressed();
                },
                view -> dialogAttenzione.dismiss());
    }

    @Override
    public void raccogliDati() {
        String nome, cognome, email, password;
        nome = textInputNome.getEditText().getText().toString();
        cognome = textInputCognome.getEditText().getText().toString();
        email = textInputEmail.getEditText().getText().toString();
        password = textInputPassword.getEditText().getText().toString();

        if (campiValidi(nome, cognome, email, password, ruolo)) {
            Utente nuovoUtente = new Utente(nome, cognome, email, password);
            switch (ruolo) {
                case "Addetto alla cucina":
                    nuovoUtente.setRuolo(Ruolo.ADDETTO_CUCINA);
                    break;
                case "Addetto alla sala":
                    nuovoUtente.setRuolo(Ruolo.ADDETTO_SALA);
                    break;
                case "Supervisore":
                    nuovoUtente.setRuolo(Ruolo.SUPERVISORE);
            }
            adminPresenter.registraUtente(nuovoUtente);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            mostraErroreCampiVuoti();
        }
    }

    @Override
    public void mostraErroreCampiVuoti() {
        progressBar.setVisibility(View.INVISIBLE);

        String nome, cognome, email, password;
        nome = textInputNome.getEditText().getText().toString();
        cognome = textInputCognome.getEditText().getText().toString();
        email = textInputEmail.getEditText().getText().toString();
        password = textInputPassword.getEditText().getText().toString();
        if (nome.equals("")) {
            textInputNome.setError("Inserire nome");
        }
        if (cognome.equals("")) {
            textInputCognome.setError("Inserire cognome");
        }
        if (email.equals("")) {
            textInputEmail.setError("Inserire email");
        }
        if (password.equals("")) {
            textInputPassword.setError("Inserire password");
        }
        CharSequence messaggio = "Inserisci tutti i campi";
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), messaggio, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void mostraErrore(String messaggio) {
        progressBar.setVisibility(View.INVISIBLE);

        String lowMessaggio = messaggio.toLowerCase();
        if (lowMessaggio.contains("nome") && !lowMessaggio.contains("cog")) {
            textInputNome.setError(messaggio);
        } else if (lowMessaggio.contains("cog")) {
            textInputCognome.setError(messaggio);
        } else if (lowMessaggio.contains("email")) {
            textInputEmail.setError(messaggio);
        } else if (lowMessaggio.contains("password")) {
            textInputPassword.setError(messaggio);
        }
    }

    @Override
    public void disabilitaErrori() {
        textInputNome.setErrorEnabled(false);
        textInputCognome.setErrorEnabled(false);
        textInputEmail.setErrorEnabled(false);
        textInputPassword.setErrorEnabled(false);
    }

    @Override
    public boolean campiValidi(String... valori) {
        for (String s : valori) {
            if (s == null || s.equals(""))
                return false;
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ruolo = ruoli.get(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void registrazioneAvvenutaConSuccesso() {
        Dialog dialog = new Dialog(this);
        mostraDialogOkOneBtn(dialog,"Utente registrato correttamente", view -> dialog.dismiss());
        cancellaCampi();
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void registrazioneFallita() {
        Dialog dialog = new Dialog(this);
        mostraDialogErroreOneBtn(dialog, "Errore imprevisto, utente non registrato, ritenta tra poco", view -> dialog.dismiss());
    }

    public void cancellaCampi(){
        textInputNome.getEditText().setText("");
        textInputCognome.getEditText().setText("");
        textInputEmail.getEditText().setText("");
        textInputPassword.getEditText().setText("");
    }


}
