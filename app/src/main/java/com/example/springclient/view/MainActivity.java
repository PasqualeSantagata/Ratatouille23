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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

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

public class MainActivity extends AppCompatActivity implements AutenticazioneContract.ViewContract {
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
        getWindow().setStatusBarColor(0xffF68967);

        String messaggio = getIntent().getStringExtra("uscitaForzata");
        if(messaggio != null){
            Toast.makeText(this, messaggio, Toast.LENGTH_LONG).show();
        }

        autenticazionePresenter = new AutenticazionePresenter(this);
        recuperoCredenzialiPresenter = new RecuperoCredenzialiPresenter(this);

        inizializzaComponenti();
    }

    @Override
    public void inizializzaComponenti() {
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

       textViewPasswordDimenticata.setOnClickListener(view -> autenticazionePresenter.avviaRecuperoPassword());
    }

    @Override
    public void tornaIndietro() {

    }

    @Override
    public void mostraPorgressBar() {

    }

    @Override
    public void nascondiProgressBar() {

    }

    @Override
    public boolean isVisibile() {
        return getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED);
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

    private void disabilitaErrori(){
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
        mostraDialogOkOneBtn(dialogPrimoAcesso, "Devi cambiare password, clicca ok e riceverai una mail per aggiornarla", view -> {
            recuperoCredenzialiPresenter.avviaAggiornaPassword(email);
            progressBar.setVisibility(View.INVISIBLE);
            dialogPrimoAcesso.dismiss();
        });
    }


    public void disabilitaPorogressBar(){
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void avviaDashboardAdmin(){
        progressBar.setVisibility(View.GONE);
        Intent dashIntent = new Intent(this, DashboardAdminActivity.class);
        startActivity(dashIntent);

    }

    @Override
    public void impossibileContattareIlServer(String messaggio) {
        Dialog dialogAttenzione = new Dialog(this);
        mostraDialogErroreOneBtn(dialogAttenzione, messaggio, view -> dialogAttenzione.dismiss());
    }

    @Override
    public void avviaDashboardSupervisore(String ruolo) {
        progressBar.setVisibility(View.GONE);
        Intent avviaGestioneMenu = new Intent(this, StartGestioneMenuActivity.class);
        avviaGestioneMenu.putExtra("ruolo", ruolo);
        startActivity(avviaGestioneMenu);

    }

    @Override
    public void forzaUscita(){
       Intent resetMainActivity = new Intent(this, MainActivity.class);
       resetMainActivity.putExtra("uscitaForzata", "Sessione scaduta");
       startActivity(resetMainActivity);
    }


    @Override
    public void avviaDashboardAddettoSala() {
        progressBar.setVisibility(View.GONE);
        Intent avviaOrdinazione =  new Intent(this, StartNuovaOrdinazioneActivity.class);
        startActivity(avviaOrdinazione);
    }

    @Override
    public void avviaDashboardAddettoCucina(String email) {
        progressBar.setVisibility(View.GONE);
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