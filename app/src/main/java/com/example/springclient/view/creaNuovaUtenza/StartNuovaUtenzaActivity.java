package com.example.springclient.view.creaNuovaUtenza;

import android.app.Dialog;
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

public class StartNuovaUtenzaActivity extends AppCompatActivity implements AdminContract.View.CreaUtenza, AdapterView.OnItemSelectedListener {
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
        inizializzaComponenti();
    }

    private void inizializzaComponenti() {
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
            progressBar.setVisibility(View.VISIBLE);
        });
        indietroButton.setOnClickListener(view -> {
            mostraDialogWarningTwoBtn("Attenzione, tutti i dati inseriti verranno cancellati se torni indietro, continuare?");
        });

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
        } else {
            mostraErroreCampiVuoti();
        }
    }

    @Override
    public void mostraErroreCampiVuoti() {
        progressBar.setVisibility(View.GONE);

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
        progressBar.setVisibility(View.GONE);

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


    public void registrazioneAvvenutaConSuccesso() {
        //TODO da provare
        Dialog dialogRegistrazioneAvvenutaConSuccesso = new Dialog(this);
        dialogRegistrazioneAvvenutaConSuccesso.setContentView(R.layout.dialog_ok_one_button);
        TextView textViewMessaggio = dialogRegistrazioneAvvenutaConSuccesso.findViewById(R.id.textViewDialogOkTwoBtn);
        Button okButton = dialogRegistrazioneAvvenutaConSuccesso.findViewById(R.id.okDialog);

        textViewMessaggio.setText("Utente registrato correttamente!!");
        okButton.setOnClickListener(view -> {
            dialogRegistrazioneAvvenutaConSuccesso.dismiss();
        });

        dialogRegistrazioneAvvenutaConSuccesso.show();
        progressBar.setVisibility(View.GONE);
    }

    public void registrazioneFallita() {
        mostraDialogErrorOneBtn("Errore imprevisto, utente non registrato, ritenta tra poco");
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
}
