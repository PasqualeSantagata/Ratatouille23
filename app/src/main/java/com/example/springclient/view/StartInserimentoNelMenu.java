package com.example.springclient.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.contract.ElementoMenuContract;
import com.example.springclient.presenter.ElementoMenuPresenter;

public class StartInserimentoNelMenu extends AppCompatActivity {
    private Button buttonIndietro;
    private Button buttonAggiungiElementi;
    private Button buttonModificaElementi;

    ElementoMenuContract.Presenter elementoMenuPresenter = new ElementoMenuPresenter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Elementi del Menù");
        setContentView(R.layout.activity_start_inserimento_nel_menu);


    }

    private void initializeComponents(){
        buttonIndietro = findViewById(R.id.buttonIndietroStartInserMenù);
        buttonAggiungiElementi = findViewById(R.id.buttonAggElemMenù);
        buttonModificaElementi = findViewById(R.id.buttonModElemMenù);

        buttonIndietro.setOnClickListener(view -> {

            PopupWindow popupWindow = new PopupWindow(getApplicationContext());
            EditText tv = new EditText(getApplicationContext());
            tv.setText("Sei sicuro di voler uscire?");
            View view2 = tv;
            popupWindow.setContentView(view2);
        });

        //Intent intent = new Intent(this, MainActivity.class);
    }


}
