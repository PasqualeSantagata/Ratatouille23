package com.example.springclient.view;

import androidx.appcompat.app.AppCompatActivity;
import com.example.springclient.R;
import com.example.springclient.entity.Allergene;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.entity.Utente;
import com.example.springclient.presenter.AllergenePresenter;
import com.example.springclient.presenter.ElementoMenuPresenter;
import com.example.springclient.presenter.RegisterPresenter;

import android.os.Bundle;

public class InserisciElementoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserisci_elemento);

       /* ElementoMenu e1 = new ElementoMenu("spaghetti", 9.0f, "classic");
        ElementoMenu e2 = new ElementoMenu("gamberi", 18f, "classic fish");
        Allergene a1 = new Allergene("farina");
        Allergene a2 = new Allergene("crostacei");

        e1.addAllergene(a1);
        e2.addAllergene(a2);

        AllergenePresenter allergenePresenter = new AllergenePresenter();
        ElementoMenuPresenter presenter = new ElementoMenuPresenter();

        allergenePresenter.saveAllergene(a1);
        allergenePresenter.saveAllergene(a2);
        presenter.saveElementoMenu(e1);
        presenter.saveElementoMenu(e2);*/


    }
}