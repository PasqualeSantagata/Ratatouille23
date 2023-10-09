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
    private StompClient stompClient;
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
/*
        ElementoMenu e1 = new ElementoMenu(1L,"Spaghetti", 10.0f, "La pasta di tutti i giorni", null, "Italiano");
        List<ElementoMenu> elementoMenuList = new ArrayList<>();
        elementoMenuList.add(e1);
        Ordinazione o = new Ordinazione(3,4,2);
        o.setElementiOrdinati(elementoMenuList);

        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://10.0.2.2:8080/ordinazione-endpoint/websocket");
        stompClient.connect();
        String elemento = new Gson().toJson(o);


        stompClient.lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {

                case OPENED:
                    Log.d("SOCKET: ", "Stomp connection opened");
                    break;

                case ERROR:
                    Log.e("SOCKET: ", "Error", lifecycleEvent.getException());
                    break;

                case CLOSED:
                    Log.d("SOCKET: ", "Stomp connection closed");
                    break;
            }
        });

        stompClient.send("/app/invia-ordinazione", elemento).subscribe();
        stompClient.topic("/topic/ricevi-ordinazione")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ordinazione-> {
                    Log.d("WS:", "Received " + ordinazione.getPayload());
                });*/

        initializeComponents();
    }
    private void initializeComponents() {
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
        progressBar.setVisibility(View.GONE);
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
        dialogPrimoAcesso.setContentView(R.layout.dialog_error_one_button);
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

    @Override
    public void avviaDashboardSupervisore() {

    }

    @Override
    public void avviaDashboardAddettoSala() {
        Intent avviaOrdinazione =  new Intent(this, StartNuovaOrdinazioneActivity.class);
        startActivity(avviaOrdinazione);
    }

    @Override
    public void avviaDashboardAddettoCucina() {
        Intent avviaCuoco = new Intent(this, HomeStatoOrdinazioneActivity.class);
        startActivity(avviaCuoco);
    }

    @Override
    public void avviaRecuperoPassword(){
        Intent passwordDimenticataIntent = new Intent(this, PasswordDimenticataActivity.class);
        startActivity(passwordDimenticataIntent);
    }

    @Override
    protected void onDestroy() {
        stompClient.disconnect();
        super.onDestroy();
    }







}