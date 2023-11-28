package com.example.springclient.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.authentication.AuthRequest;
import com.example.springclient.contract.AutenticazioneContract;
import com.example.springclient.contract.RecuperoCredenzialiContract;
import com.example.springclient.presenter.AutenticazionePresenter;
import com.example.springclient.presenter.RecuperoCredenzialiPresenter;
import com.example.springclient.view.nuovaOrdinazione.StartNuovaOrdinazioneActivity;
import com.example.springclient.view.statoOrdinazioni.HomeStatoOrdinazioneActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import ua.naiksoftware.stomp.StompClient;

public class MainActivity extends AppCompatActivity implements AutenticazioneContract.View {
    private EditText editTextPassword;
    private EditText editTextEmail;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextView textViewPasswordDimenticata;
    private AutenticazioneContract.Presenter autenticazionePresenter;
    private RecuperoCredenzialiContract.Presenter recuperoCredenzialiPresenter;
    private View progressBar;
    private String email;
    private Dialog dialogPrimoAcesso;


    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        autenticazionePresenter = new AutenticazionePresenter(this);
        recuperoCredenzialiPresenter = new RecuperoCredenzialiPresenter(this);

        initializeComponents();
    }

    @Override
    public void initializeComponents() {
       textInputLayoutEmail =  findViewById(R.id.textInputLayoutEmailLogin);
       textInputLayoutPassword =  findViewById(R.id.textInputLayoutPassword);
       textViewPasswordDimenticata = findViewById(R.id.textViewDimenticatoPassword);
       progressBar = findViewById(R.id.mainActivityProgressBar);
       editTextEmail = textInputLayoutEmail.getEditText();
       editTextPassword = textInputLayoutPassword.getEditText();
       Button buttonSave = findViewById(R.id.buttonLoginOk);
       progressBar.setVisibility(View.GONE);

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
    public void tornaIndietro() {

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
            progressBar.setVisibility(View.VISIBLE);
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
        progressBar.setVisibility(View.INVISIBLE);
        textInputLayoutEmail.setError(" ");
        textInputLayoutPassword.setError(" ");
        //Da proivare poichè a me non và ma penso sia un problema del mio emulatore android studio
        CharSequence messaggio = "Credenziali errate";
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), messaggio, Snackbar.LENGTH_LONG);
        snackbar.show();

    }
    @Override
    public void dialgPrimoAccesso(){
        dialogPrimoAcesso = new Dialog(this);
        dialogPrimoAcesso.setContentView(R.layout.dialog_warning_one_button);
        TextView errorMessage = dialogPrimoAcesso.findViewById(R.id.textViewMessageDialogueErrorOneBt);
        errorMessage.setText(R.string.dialog_cambia_pass);
        dialogPrimoAcesso.show();

        Button buttonDialog = dialogPrimoAcesso.findViewById(R.id.buttonOkDialogueErrorOneBt);
        buttonDialog.setOnClickListener(view -> {
            recuperoCredenzialiPresenter.avviaRecuperoPassword(email);
            progressBar.setVisibility(View.GONE);
            dialogPrimoAcesso.dismiss();
        });
    }

    public void disabilitaPorogressBar(){
        progressBar.setVisibility(View.GONE);

    }

    public void cleanFields(){
        editTextPassword.setText("");
        editTextEmail.setText("");
    }
    @Override
    public void avviaDashboardAdmin(){
        progressBar.setVisibility(View.GONE);
        Intent dashIntent = new Intent(this, DashboardAdminActivity.class);
        startActivity(dashIntent);

    }

    public void mostraDialogErrore(String messaggio) {
        Dialog dialogAttenzione = new Dialog(this);
        dialogAttenzione.setContentView(R.layout.dialog_err_one_btn);

        TextView messaggiodialog = dialogAttenzione.findViewById(R.id.textViewErrorMessageDialogErrorOneBtn);
        messaggiodialog.setText(messaggio);

        Button buttonOk = dialogAttenzione.findViewById(R.id.buttonOkDialogErrorOneBtn);
        dialogAttenzione.show();

        buttonOk.setOnClickListener(view -> {
            dialogAttenzione.dismiss();
        });
    }

    @Override
    public void avviaDashboardSupervisore() {

    }

    @Override
    public void avviaDashboardAddettoSala() {
        Intent avviaOrdinazione =  new Intent(this, StartNuovaOrdinazioneActivity.class);
        startActivity(avviaOrdinazione);
    }

    @Override
    public void avviaDashboardAddettoCucina(String email) {
        Intent avviaCuoco = new Intent(this, HomeStatoOrdinazioneActivity.class);
        avviaCuoco.putExtra("email", email);
        startActivity(avviaCuoco);
    }

    @Override
    public void avviaRecuperoPassword(){
        Intent passwordDimenticataIntent = new Intent(this, PasswordDimenticataActivity.class);
        startActivity(passwordDimenticataIntent);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();
    }

}