package com.example.springclient.view.creaNuovaUtenza;

import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.entity.Role;
import com.example.springclient.entity.Utente;
import com.example.springclient.presenter.UtentePresenter;
import com.example.springclient.view.adapters.SpinnerAdapterRuoli;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;

public class StartNuovaUtenza extends AppCompatActivity {
    private TextInputLayout textInputNome;
    private TextInputLayout textInputCognome;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;
    private Spinner spinnerRuoli;
    private BaseAdapter spinnerRuoliAdapter;
    private UtentePresenter utentePresenter;
    private Button okButton;
    private Button indietroButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuova_utenza);
        utentePresenter = new UtentePresenter(this);
        inizializzaComponenti();
    }

    private void inizializzaComponenti(){
        textInputNome = findViewById(R.id.textInputLayoutNomeCreaUtenza);
        textInputEmail = findViewById(R.id.textInputLayoutEmailCreaUtenza);
        textInputCognome = findViewById(R.id.textInputLayoutCognomeCreaUtenza);
        textInputPassword = findViewById(R.id.textInputLayoutPasswordCreaUtenza);
        spinnerRuoli = findViewById(R.id.spinnerTipoUtenzaCreaUtenza);
        spinnerRuoliAdapter = new SpinnerAdapterRuoli(this, Arrays.asList(getResources().getStringArray(R.array.array_ruoli)));
        okButton = findViewById(R.id.buttonOkCreaUtenza);
        indietroButton = findViewById(R.id.buttonIndietroNuovaUtenza);
        okButton.setOnClickListener(view -> {
            Utente utente = raccogliDati();
            utentePresenter.saveUtente(utente);
        });
        spinnerRuoli.setAdapter(spinnerRuoliAdapter);


    }

    private Utente raccogliDati(){
        String nome, cognome, email, password, ruolo;
        nome = textInputNome.getEditText().getText().toString();
        cognome = textInputCognome.getEditText().getText().toString();
        email = textInputEmail.getEditText().getText().toString();
        password = textInputPassword.getEditText().getText().toString();
        ruolo = spinnerRuoli.getSelectedItem().toString();
        Utente nuovoUtente = new Utente(nome, cognome, email, password);
        switch (ruolo){
            case "Addetto alla cucina":
                nuovoUtente.setRole(Role.ADDETTO_CUCINA);
                break;
            case "Addetto alla sala":
                nuovoUtente.setRole(Role.ADDETTO_SALA);
                break;
            case "Supervisore":
                nuovoUtente.setRole(Role.SUPERVISORE);
        }
        return nuovoUtente;
    }

}
