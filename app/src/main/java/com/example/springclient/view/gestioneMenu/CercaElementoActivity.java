package com.example.springclient.view.gestioneMenu;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

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
    private ImageView imageViewButtonOnItem;
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
        presenter.getAllElementiMenu();

    }

    private void initializeComponents() {
        buttonInditero = findViewById(R.id.buttonIndietroCercaElemento);
        buttonCerca = findViewById(R.id.buttonCercaElemento);
        textInputLayoutRicercaNome = findViewById(R.id.textInputLayoutCerca);
        imageViewButtonOnItem = findViewById(R.id.cancellaElementoImageView);
        //Forse va messo dopo l'istanziazione della recycle view
        imageViewButtonOnItem.setImageResource(R.drawable.ic_info);

        recyclerViewElementi = findViewById(R.id.recyclerViewElementiRicercati);
        adapter = new RecycleViewAdapterGestioneElementoMenu(this, elementoMenuList, this);
        recyclerViewElementi.setAdapter(adapter);

        GridLayoutManager horizontal = new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false);
        recyclerViewElementi.setLayoutManager(horizontal);




    }

    public void setElementi(List<ElementoMenu> elementoMenuList){
        this.elementoMenuList = elementoMenuList;
        initializeComponents();
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onButtonDeleted(int position){

    }
}
