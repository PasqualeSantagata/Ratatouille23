package com.example.springclient.view;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.contract.RecuperoCredenzialiContract;
import com.example.springclient.presenter.RecuperoCredenzialiPresenter;
import com.google.android.material.textfield.TextInputLayout;

public class PasswordDimenticataActivity extends AppCompatActivity implements RecuperoCredenzialiContract.View {

    private TextInputLayout emailTextInputLayout;
    private Button buttonOk;
    private Button buttonIndietro;
    private RecuperoCredenzialiContract.Presenter credenzialiPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_password_dimenticata);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        credenzialiPresenter = new RecuperoCredenzialiPresenter(this);
        initializeComponents();

    }
    private void initializeComponents(){
        emailTextInputLayout = findViewById(R.id.textInputLayoutEmailPassDimenticata);
        buttonOk = findViewById(R.id.buttonOkPassDimenticata);
        buttonIndietro = findViewById(R.id.buttonIndietroPassDimenticata);
        buttonOk.setOnClickListener(view -> {
            emailTextInputLayout.setErrorEnabled(false);
            String email = String.valueOf(emailTextInputLayout.getEditText().getText());
            verificaEmail(email);
        });
    }

    @Override
    public void verificaEmail(String email){
        if(email == null || email.equals("")){
            emailTextInputLayout.setError("Inserisci la mail");
            return;
        }
        credenzialiPresenter.avviaRecuperoPassword(email);
    }

    @Override
    public void emailErrata(){
        emailTextInputLayout.setError("Email non trovata");
    }




}
