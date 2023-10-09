package com.example.springclient.view;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.presenter.AutenticazionePresenter;

public class ReimpostaPasswordActivity extends AppCompatActivity {

    private EditText EditTextNuovaPassword;
    private EditText EditTextRipetiPassword;

    private AutenticazionePresenter autenticazionePresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_reimposta_password);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        initializeComponents();
    }

    private void initializeComponents(){
        EditTextNuovaPassword=findViewById(R.id.textInputLayoutEmailPassDimenticata);
        EditTextRipetiPassword=findViewById(R.id.textInputLayoutRipetiPasswordReimpostaPassw);



    }
}
