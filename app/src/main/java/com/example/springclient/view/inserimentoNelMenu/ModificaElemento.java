package com.example.springclient.view.inserimentoNelMenu;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;

public class ModificaElemento extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("MODIFICA ELEMENTO NEL MENU");
        setContentView(R.layout.activity_modifica_elemento_inserimento_nel_menu);
    }
}
