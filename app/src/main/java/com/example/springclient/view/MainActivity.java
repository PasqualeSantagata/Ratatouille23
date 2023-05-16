package com.example.springclient.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.authentication.AuthRequest;
import com.example.springclient.contract.UtenteContract;
import com.example.springclient.presenter.UtentePresenter;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity implements UtenteContract.View {
    private EditText editTextPassword;
    private EditText editTextEmail;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextView textViewPasswordDimenticata;
    private UtentePresenter utentePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        utentePresenter = new UtentePresenter(this);
        initializeComponents();
    }
    private void initializeComponents() {
       textInputLayoutEmail =  findViewById(R.id.textInputLayoutEmailLogin);
       textInputLayoutPassword =  findViewById(R.id.textInputLayoutPassword);
       //CREDO debba fare la lambda expr per avviare poi l'activity di recupero password
       textViewPasswordDimenticata = findViewById(R.id.textViewDimenticatoPassword);
       editTextEmail = textInputLayoutEmail.getEditText();
       editTextPassword = textInputLayoutPassword.getEditText();
       Button buttonSave = findViewById(R.id.buttonLoginOk);

       buttonSave.setOnClickListener(view -> {
           String nome, cognome, email, password;

           email = String.valueOf(editTextEmail.getText());
           password = String.valueOf(editTextPassword.getText());

           utentePresenter.logInUtente(new AuthRequest(email, password));

       });
    }

    public void loginError(){
        textInputLayoutEmail.setError(" ");
        textInputLayoutPassword.setError(" ");
        Toast t = Toast.makeText(getApplicationContext(),"Attenzione: credenziali errate!", Toast.LENGTH_SHORT);
        t.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
        t.show();
    }

    @Override
    public void cleanFields(){
        editTextPassword.setText("");
        editTextEmail.setText("");
    }



}