package com.example.springclient.view.gestioneMenu;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.contract.CategoriaContract;
import com.example.springclient.entity.Categoria;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.presenter.CategoriaMenuPresenter;
import com.example.springclient.view.adapters.IRecycleViewCategoria;
import com.example.springclient.view.adapters.RecycleViewAdapterCategoria;

import java.io.Serializable;
import java.util.List;

public class EsploraCategorieMenuActivity extends AppCompatActivity implements CategoriaContract.View, IRecycleViewCategoria {
    private List<Categoria> categorie;
    private CategoriaContract.Presenter categoriaMenuPresenter;
    private RecycleViewAdapterCategoria adapterCategoria;
    private Button indietroButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("CATEGORIE");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_esplora_categorie_nuova_ordinazione);
        categoriaMenuPresenter = new CategoriaMenuPresenter(this);
        categoriaMenuPresenter.getAllCategorie();
        Button riepilogoButton = findViewById(R.id.buttonRiepilogoCategorieNuovaOrd);
        riepilogoButton.setVisibility(View.INVISIBLE);

    }

    public void setCategorie(List<Categoria> categorie){
        this.categorie = categorie;
        if(categorie != null && !categorie.isEmpty()){
            for(int i = 0; i<categorie.size(); i++){
                categoriaMenuPresenter.getFotoCategoriaById(categorie.get(i), i);
            }
        } else{
            dialogNessunaCategoria();
        }
        initializeComponents();
    }

    private void dialogNessunaCategoria(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_warning_one_button);
        TextView errorMessage = dialog.findViewById(R.id.textViewMessageDialogueErrorOneBt);
        Button ok = dialog.findViewById(R.id.buttonOkDialogueErrorOneBt);
        errorMessage.setText("Nessuna categoria da visualizzare");
        ok.setOnClickListener(view1 -> {
            Intent intent = new Intent(this, HomeModificaElemMenuActivity.class);
            dialog.dismiss();
            startActivity(intent);
            dialog.dismiss();
        });
        dialog.show();
    }

    public void initializeComponents() {
        indietroButton = findViewById(R.id.buttonIndietroCategorieNuovaOrd);

        indietroButton.setOnClickListener(view -> {
            onBackPressed();
        });
        RecyclerView recyclerViewCategorie = findViewById(R.id.RecycleViewCategorie);
        adapterCategoria = new RecycleViewAdapterCategoria(this, categorie, this);
        recyclerViewCategorie.setAdapter(adapterCategoria);
        GridLayoutManager horizontal = new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false);
        recyclerViewCategorie.setLayoutManager(horizontal);


    }

    @Override
    public void setNomiCategorie(List<String> nomiCategori) {
        //TODO serve?
    }

    public void notifyAdapter(int posizione){
        adapterCategoria.notifyItemChanged(posizione);
    }


    @Override
    public void onItemClick(int position) {
        Intent intentVisualizzaCategoria = new Intent(this, VisualizzaElementiDellaCategoriaActivity.class);
        //Setta la lista degli elementi menu in base alla categoria selezionata, caricandola da db
        List<ElementoMenu> elementi = categorie.get(position).getElementi();
        intentVisualizzaCategoria.putExtra("elementi", (Serializable) elementi);
        startActivity(intentVisualizzaCategoria);

    }

    @Override
    public void onBackPressed() {
        Intent homeModifica = new Intent(this, HomeModificaElemMenuActivity.class);
        startActivity(homeModifica);
        super.onBackPressed();
    }
}
