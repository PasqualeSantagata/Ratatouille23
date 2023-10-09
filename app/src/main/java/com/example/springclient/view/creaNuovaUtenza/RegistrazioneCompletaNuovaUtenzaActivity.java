package com.example.springclient.view.creaNuovaUtenza;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;

public class RegistrazioneCompletaNuovaUtenzaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("REGISTRAZIONE COMPLETATA");
        setContentView(R.layout.activity_registrazione_completa_nuova_utenza);
    }
}
