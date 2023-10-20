package com.example.springclient.view.creaNuovaUtenza;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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

    private void inizializzaComponenti(){
        textInputNome = (TextInputLayout) findViewById(R.id.textInputLayoutNomeCreaUtenza);
        textInputEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmailCreaUtenza);
        textInputCognome = (TextInputLayout) findViewById(R.id.textInputLayoutCognomeCreaUtenza);
        textInputPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPasswordCreaUtenza);
        spinnerRuoli = findViewById(R.id.spinnerTipoUtenzaCreaUtenza);
        spinnerRuoli.setOnItemSelectedListener(this);
        ruoli = Arrays.asList(getResources().getStringArray(R.array.array_ruoli));
        ArrayAdapter adapterRuoli = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ruoli);
        adapterRuoli.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRuoli.setAdapter(adapterRuoli);

        okButton = findViewById(R.id.buttonOkCreaUtenza);
        indietroButton = findViewById(R.id.buttonIndietroNuovaUtenza);
        okButton.setOnClickListener(view -> {
            disabilitaErrori();
            raccogliDati();
        });

    }
    @Override
    public void raccogliDati(){
        String nome, cognome, email, password;
        nome = textInputNome.getEditText().getText().toString();
        cognome = textInputCognome.getEditText().getText().toString();
        email = textInputEmail.getEditText().getText().toString();
        password = textInputPassword.getEditText().getText().toString();

        if(campiValidi(nome, cognome, email, password, ruolo)) {
            Utente nuovoUtente = new Utente(nome, cognome, email, password);
            switch (ruolo){
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
        }
        else{
            mostraErroreCampiVuoti();
        }
    }
    @Override
    public void mostraErroreCampiVuoti(){
        String nome, cognome, email, password;
        nome = textInputNome.getEditText().getText().toString();
        cognome = textInputCognome.getEditText().getText().toString();
        email = textInputEmail.getEditText().getText().toString();
        password = textInputPassword.getEditText().getText().toString();
        if(nome == null || nome.equals("")) {
            textInputNome.setError("Inserire nome");
        }
        if(cognome == null || cognome.equals("")){
            textInputCognome.setError("Inserire cognome");
        }
        if(email == null || email.equals("")) {
            textInputEmail.setError("Inserire email");
        }
        if(password == null || password.equals("")) {
            textInputPassword.setError("Inserire password");
        }
        CharSequence messaggio = "Inserisci tutti i campi";
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), messaggio, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
    @Override
    public void mostraErrore(String messaggio){
        String lowMessaggio = messaggio.toLowerCase();
        if(lowMessaggio.contains("nome") && !lowMessaggio.contains("cog")) {
            textInputNome.setError(messaggio);
        }
        else if(lowMessaggio.contains("cog")){
            textInputCognome.setError(messaggio);
        }
        else if(lowMessaggio.contains("email")){
            textInputEmail.setError(messaggio);
        }
        else if(lowMessaggio.contains("password")){
            textInputPassword.setError(messaggio);
        }
    }
    @Override
    public void disabilitaErrori(){
        textInputNome.setErrorEnabled(false);
        textInputCognome.setErrorEnabled(false);
        textInputEmail.setErrorEnabled(false);
        textInputPassword.setErrorEnabled(false);
    }
    @Override
    public boolean campiValidi(String... valori){
        for(String s: valori){
            if(s == null || s.equals(""))
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
}
