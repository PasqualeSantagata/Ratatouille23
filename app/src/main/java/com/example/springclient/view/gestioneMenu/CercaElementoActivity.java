package com.example.springclient.view.gestioneMenu;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.presenter.ElementoMenuPresenter;
import com.example.springclient.view.adapters.IRecycleViewElementoMenu;
import com.example.springclient.view.adapters.RecycleViewAdapterGestioneElementoMenu;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class CercaElementoActivity extends AppCompatActivity implements IRecycleViewElementoMenu {

    private Button buttonInditero;
    private Button buttonCerca;
    private RecyclerView recyclerViewElementi;
    private RecycleViewAdapterGestioneElementoMenu adapter;
    private TextInputLayout textInputLayoutRicercaNome;
    private List<ElementoMenu> elementoMenuList;
    private ElementoMenuPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerca_elemento_gestione_menu);
        getSupportActionBar().setTitle("CERCA ELEMENTO MENU");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        presenter = new ElementoMenuPresenter(this);
       // elementoMenuList = presenter.getAllElementiMenu(); non cosi ma la list è da riempire

        initializeComponents();
    }

    private void initializeComponents() {
        buttonInditero = findViewById(R.id.buttonIndietroCercaElemento);
        buttonCerca = findViewById(R.id.buttonCercaElemento);
        textInputLayoutRicercaNome = findViewById(R.id.textInputLayoutCerca);

        recyclerViewElementi = findViewById(R.id.recyclerViewElementiRicercati);
        adapter = new RecycleViewAdapterGestioneElementoMenu(this, elementoMenuList, this);
        recyclerViewElementi.setAdapter(adapter);

        GridLayoutManager horizontal = new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false);
        recyclerViewElementi.setLayoutManager(horizontal);



    }


    @Override
    public void onItemClick(int position) {

    }
}
