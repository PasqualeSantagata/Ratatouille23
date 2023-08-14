package com.example.springclient.view.creaNuovaUtenza;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.contract.AdminContract;
import com.example.springclient.entity.Ruolo;
import com.example.springclient.entity.Utente;
import com.example.springclient.view.adapters.SpinnerAdapterRuoli;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;

public class CreaNuovaUtenza extends AppCompatActivity {

    private Spinner spinnerRuolo;
    private SpinnerAdapterRuoli spinnerAdapterRuoli;

    private TextInputLayout textInputLayoutNome;
    private TextInputLayout textInputLayoutCognome;
    private TextInputLayout textInputLayoutEmail;

    private Button buttonOk;
    private Button buttonIndietro;

    private Utente utente;

    private AdminContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("NUOVA UTENZA");
        setContentView(R.layout.activity_nuova_utenza);
        initializeComponents();
       //presenter = new UtentePresenter(this);
    }

    public void initializeComponents() {
        spinnerRuolo = findViewById(R.id.spinnerTipoUtenzaCreaUtenza);
        BaseAdapter adapter = new SpinnerAdapterRuoli(this, Arrays.asList(getResources().getStringArray(R.array.array_ruoli)));
        spinnerRuolo.setAdapter(adapter);

        buttonOk.setOnClickListener(view -> {
            if(checkIncorrectFields()){
                Dialog dialog = new Dialog(this);
                TextView errorMessage = findViewById(R.id.textViewMessageDialogueErrorOneBt);
                errorMessage.setText(R.string.dialog_campi_errati);
                dialog.setContentView(R.layout.dialog_error_one_button);
                dialog.show();
                findViewById(R.id.buttonOkDialogueErrorOneBt).setOnClickListener(view1 -> {dialog.dismiss();});

            }else{
                utente = new Utente(textInputLayoutNome.getEditText().toString(), textInputLayoutCognome.getEditText().toString(),
                        textInputLayoutEmail.getEditText().toString(),"pass"); //aggiungere il recupero del ruolo

                //manca anche il campo password
                String role = spinnerRuolo.getSelectedItem().toString();
                switch (role){
                    case "Addetto alla cucina":
                        utente.setRuolo(Ruolo.ADDETTO_CUCINA);
                        break;
                    case "Addetto alla sala":
                        utente.setRuolo(Ruolo.ADDETTO_SALA);
                        break;
                    case "Supervisore":
                        utente.setRuolo(Ruolo.SUPERVISORE);
                }
                presenter.registraUtente(utente);
            }
        });


    }

    private boolean checkIncorrectFields(){
        boolean bool = false;
        String nome, cognome, email;
        nome = textInputLayoutNome.getEditText().toString();
        cognome = textInputLayoutCognome.getEditText().toString();
        email = textInputLayoutEmail.getEditText().toString();
        if (nome.isEmpty()) {
            textInputLayoutNome.setError("Nome non valido");
            bool = true;
        }
        if (cognome.isEmpty()){
            textInputLayoutCognome.setError("Cognome non valido");
            bool = true;
        }
        if(email.isEmpty() || !email.matches("^(.+)@(\\S+) $.")){
            textInputLayoutEmail.setError("Email non valida");
            bool = true;
        }

        int selectedItemOfMySpinner = spinnerRuolo.getSelectedItemPosition();
        String actualPositionOfMySpinner = (String) spinnerRuolo.getItemAtPosition(selectedItemOfMySpinner);
        if (actualPositionOfMySpinner.isEmpty()) {
            setSpinnerError(spinnerRuolo,"Seleziona un ruolo");
            bool = true;
        }
        return bool;
    }

    //TODO Da provare
    private void setSpinnerError(Spinner spinner, String error){
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("errore, spinner vuoto"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(error); // actual error message
            spinner.performClick(); // to open the spinner list if error is found.

        }
    }

}
