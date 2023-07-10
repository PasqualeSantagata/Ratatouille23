package com.example.springclient.view.inserimentoNelMenu;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;

public class ElementoInseritoCorrettamente extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("ELEMENTO INSERITO CORRETTAMENTE!");
        setContentView(R.layout.activity_elem_inserito_inserimento_nel_menu);
    }
}
