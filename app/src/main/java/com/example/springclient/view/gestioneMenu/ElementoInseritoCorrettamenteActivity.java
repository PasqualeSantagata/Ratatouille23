package com.example.springclient.view.gestioneMenu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.view.DashboardAdminActivity;

public class ElementoInseritoCorrettamenteActivity extends AppCompatActivity {
    private Button buttonOk;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("ELEMENTO INSERITO CORRETTAMENTE!");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_elem_inserito_gestione_menu);


        buttonOk = findViewById(R.id.buttonOkElemInserito);
        buttonOk.setOnClickListener(view -> {
            Intent dashIntent = new Intent(this, DashboardAdminActivity.class);
            startActivity(dashIntent);
        });
    }
}
