package com.example.springclient.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.springclient.R;
import com.example.springclient.contract.RegisterUtenteContract;
import com.example.springclient.entity.Allergene;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.presenter.AllergenePresenter;
import com.example.springclient.presenter.ElementoMenuPresenter;
import com.example.springclient.presenter.RegisterPresenter;
import com.example.springclient.entity.Utente;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity implements RegisterUtenteContract.View {

    private EditText inputEditTextNome;
    private EditText inputEditTextCognome;
    EditText EditTextPassword;
    EditText EditTextEmail;

    private RegisterPresenter registerPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*registerPresenter = new RegisterPresenter(this);

        presenter.saveElementoMenu(e2);

        initializeComponents();*/

    }

    private void initializeComponents() {
       inputEditTextNome =  findViewById(R.id.editTextTextPersonName2);
       inputEditTextCognome =  findViewById(R.id.editTextTextPersonName);
       EditTextPassword =  findViewById(R.id.editTextTextPassword);
       EditTextEmail =  findViewById(R.id.editTextTextEmailAddress);

       Button buttonSave = findViewById(R.id.button);



       buttonSave.setOnClickListener(view -> {
           String nome, cognome, email, password;
           LocalDate dob;

           nome = String.valueOf(inputEditTextNome.getText());
           cognome = String.valueOf(inputEditTextCognome.getText());
           email = String.valueOf(EditTextEmail.getText());
           password = String.valueOf(EditTextPassword.getText());


           Utente utente = new Utente();
           utente.setNome(nome);
           utente.setCognome(cognome);
           utente.setEmail(email);
           utente.setPassword(password);



           registerPresenter.saveUtente(utente);
           cleanFields();



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