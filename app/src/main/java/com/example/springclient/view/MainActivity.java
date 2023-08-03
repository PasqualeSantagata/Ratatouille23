package com.example.springclient.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.authentication.AuthRequest;
import com.example.springclient.contract.AutenticazioneContract;
import com.example.springclient.presenter.AutenticazionePresenter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity implements AutenticazioneContract.View {
    private EditText editTextPassword;
    private EditText editTextEmail;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextView textViewPasswordDimenticata;
    private AutenticazioneContract.Presenter autenticazionePresenter;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        autenticazionePresenter = new AutenticazionePresenter(this);
        initializeComponents();

    }
    private void initializeComponents() {
       textInputLayoutEmail =  findViewById(R.id.textInputLayoutEmailLogin);
       textInputLayoutPassword =  findViewById(R.id.textInputLayoutPassword);
       textViewPasswordDimenticata = findViewById(R.id.textViewDimenticatoPassword);
       editTextEmail = textInputLayoutEmail.getEditText();
       editTextPassword = textInputLayoutPassword.getEditText();
       Button buttonSave = findViewById(R.id.buttonLoginOk);


       buttonSave.setOnClickListener(view -> {
           String password;
           email = String.valueOf(editTextEmail.getText());
           password = String.valueOf(editTextPassword.getText());
           disabilitaErrori();
           verificaCredenziali(email, password);
       });

       textViewPasswordDimenticata.setOnClickListener(view -> {
           avviaRecuperoPassword();
       });
    }
    @Override
    public void verificaCredenziali(String email, String password){
        if(email.equals("")){
            textInputLayoutEmail.setError("Inserisci la mail");
        }
        if(password.equals("")){
            textInputLayoutPassword.setError("Inserisci la password");
        }
        if(!email.equals("") && !password.equals("") ){
            autenticazionePresenter.logInUtente(new AuthRequest(email, password));
            SharedPreferences sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
            sharedPreferences.edit().putString("email", email).apply();
        }
    }

    public void disabilitaErrori(){
        textInputLayoutEmail.setErrorEnabled(false);
        textInputLayoutPassword.setErrorEnabled(false);
    }
    @Override
    public void loginErrore(){
        textInputLayoutEmail.setError(" ");
        textInputLayoutPassword.setError(" ");
        //Da proivare poichè a me non và ma penso sia un problema del mio emulatore android studio
        CharSequence messaggio = "Credenziali errate";
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), messaggio, Snackbar.LENGTH_LONG);
        snackbar.show();

    }
    @Override
    public void dialgPrimoAccesso(){
        Dialog dialogPrimoAcesso = new Dialog(this);
        dialogPrimoAcesso.setContentView(R.layout.dialog_error_one_button);
        TextView errorMessage = dialogPrimoAcesso.findViewById(R.id.textViewMessageDialogueErrorOneBt);
        errorMessage.setText(R.string.dialog_cambia_pass);
        dialogPrimoAcesso.show();

        Button buttonDialog = dialogPrimoAcesso.findViewById(R.id.buttonOkDialogueErrorOneBt);
        buttonDialog.setOnClickListener(view -> {
           // utentePresenter.reimpostaPassword(email);
            dialogPrimoAcesso.dismiss();
        });
    }


    public void cleanFields(){
        editTextPassword.setText("");
        editTextEmail.setText("");
    }
    @Override
    public void avviaDashboardAdmin(){
        Intent dashIntent = new Intent(this, DashboardAdmin.class);
        startActivity(dashIntent);

    }

    @Override
    public void avviaDashboardSupervisore() {

    }

    @Override
    public void avviaDashboardAddettoSala() {

    }

    @Override
    public void avviaDashboardAddettoCucina() {

    }

    @Override
    public void avviaRecuperoPassword(){
        Intent passwordDimenticataIntent = new Intent(this, PasswordDimenticataActivity.class);
        startActivity(passwordDimenticataIntent);
    }






}