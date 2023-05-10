package com.example.springclient.view;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.apiUtils.AuthRequest;
import com.example.springclient.contract.UtenteContract;
import com.example.springclient.presenter.UtentePresenter;

public class MainActivity extends AppCompatActivity implements UtenteContract.View {
    private EditText inputEditTextNome;
    private EditText inputEditTextCognome;
    private EditText EditTextPassword;
    private EditText EditTextEmail;
    private TextView textViewPasswordDimenticata;
    private UtentePresenter utentePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        utentePresenter = new UtentePresenter(this);
        initializeComponents();
    }
    private void initializeComponents() {
       EditTextPassword =  findViewById(R.id.textInputLayoutPassword);
       EditTextEmail =  findViewById(R.id.textInputLayoutEmailLogin);
       //CREDO debba fare la lambda expr per avviare poi l'activity di recupero password
       textViewPasswordDimenticata = findViewById(R.id.textViewDimenticatoPassword);

       Button buttonSave = findViewById(R.id.buttonLoginOk);

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