package com.example.springclient.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
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
    private View progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_password_dimenticata);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        credenzialiPresenter = new RecuperoCredenzialiPresenter(this);
        initializeComponents();

    }

    @Override
    public Context getContext() {
        return getContext();
    }

    @Override
    public void initializeComponents(){
        emailTextInputLayout = findViewById(R.id.textInputLayoutEmailPassDimenticata);
        buttonOk = findViewById(R.id.buttonOkPassDimenticata);
        buttonIndietro = findViewById(R.id.buttonIndietroPassDimenticata);
        progressBar = findViewById(R.id.passwordDimenticataProgessBar);
        progressBar.setVisibility(View.GONE);
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
        progressBar.setVisibility(View.VISIBLE);
        credenzialiPresenter.avviaRecuperoPassword(email);
    }

    public void emailInviataCorrettamente(){
        Dialog emailInviataCorrettamenteDialog = new Dialog(this);
        emailInviataCorrettamenteDialog.setContentView(R.layout.dialog_ok_one_button);
        Button okButton = emailInviataCorrettamenteDialog.findViewById(R.id.okDialog);
        emailInviataCorrettamenteDialog.show();
        okButton.setOnClickListener(view -> {
                progressBar.setVisibility(View.GONE);
                emailInviataCorrettamenteDialog.dismiss();
                Intent loginActivity = new Intent(this, MainActivity.class);
                startActivity(loginActivity);
        });

    }

    @Override
    public void emailErrata(){
        progressBar.setVisibility(View.GONE);
        emailTextInputLayout.setError("Email non trovata");
    }




}
