package com.example.springclient.view.inserimentoNelMenu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.view.DashboardAdmin;

public class ElementoInseritoCorrettamente extends AppCompatActivity {
    private Button buttonOk;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("ELEMENTO INSERITO CORRETTAMENTE!");
        setContentView(R.layout.activity_elem_inserito_inserimento_nel_menu);
        buttonOk = findViewById(R.id.buttonOkElemInserito);
        buttonOk.setOnClickListener(view -> {
            Intent dashIntent = new Intent(this, DashboardAdmin.class);
            startActivity(dashIntent);
        });
    }
}
