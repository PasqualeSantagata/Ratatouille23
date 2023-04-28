package com.example.springclient.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.springclient.R;
import com.example.springclient.apiUtils.AuthRequest;
import com.example.springclient.contract.UtenteContract;
import com.example.springclient.presenter.UtentePresenter;
import com.example.springclient.entity.Utente;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity implements UtenteContract.View {
    private EditText inputEditTextNome;
    private EditText inputEditTextCognome;
    private EditText EditTextPassword;
    private EditText EditTextEmail;
    private UtentePresenter utentePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        utentePresenter = new UtentePresenter(this);
        initializeComponents();
    }
    private void initializeComponents() {
       inputEditTextNome =  findViewById(R.id.editTextTextPersonName2);
       inputEditTextCognome =  findViewById(R.id.editTextTextPersonName);
       EditTextPassword =  findViewById(R.id.editTextTextPassword);
       EditTextEmail =  findViewById(R.id.editTextTextEmailAddress);

       Button buttonSave = findViewById(R.id.button);

       buttonSave.setOnClickListener(view -> {
           String nome, cognome, email, password;


        /*  nome = String.valueOf(inputEditTextNome.getText());
           cognome = String.valueOf(inputEditTextCognome.getText());
           email = String.valueOf(EditTextEmail.getText());
           password = String.valueOf(EditTextPassword.getText());

           Utente utente = new Utente();
           utente.setNome(nome);
           utente.setCognome(cognome);
           utente.setEmail(email);
           utente.setPassword(password);*/

           email = String.valueOf(EditTextEmail.getText());
           password = String.valueOf(EditTextPassword.getText());


           utentePresenter.logInUtente(new AuthRequest(email, password));

       });
    }

    @Override
    public void cleanFields(){
        inputEditTextNome.setText("");
        inputEditTextCognome.setText("");
        EditTextPassword.setText("");
        EditTextEmail.setText("");
    }



}