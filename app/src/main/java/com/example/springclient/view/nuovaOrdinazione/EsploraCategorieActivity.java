package com.example.springclient.view.nuovaOrdinazione;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.contract.MostraCategoriaContract;
import com.example.springclient.entity.Categoria;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.entity.Ordinazione;
import com.example.springclient.entity.Portata;
import com.example.springclient.presenter.MostraCategoriaMenuPresenter;
import com.example.springclient.view.adapters.IRecycleViewEventi;
import com.example.springclient.view.adapters.RecycleViewAdapterCategoria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EsploraCategorieActivity extends AppCompatActivity implements IRecycleViewEventi, MostraCategoriaContract.View {
    private Ordinazione ordinazione;
    private Button buttonIndietro;
    private Button buttonRiepilogo;
    private MostraCategoriaMenuPresenter categoriaPresenter;

    private List<Categoria> categorie;
    private RecycleViewAdapterCategoria adapterCategoria;
    private Categoria categoria;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("CATEGORIE");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_esplora_categorie_nuova_ordinazione);

        categoriaPresenter = new MostraCategoriaMenuPresenter(this);
        //Dettagli ordinazione dalla activity precedente
        ordinazione = (Ordinazione) getIntent().getSerializableExtra("ordinazione");
        //Fetch delle categorie dal server
        categoriaPresenter.getAllCategorie();


    }

    private void creaRecyclerView(){
        RecyclerView recyclerViewCategorie = findViewById(R.id.RecycleViewCategorie);
        adapterCategoria = new RecycleViewAdapterCategoria(this, categorie, this);
        recyclerViewCategorie.setAdapter(adapterCategoria);
        GridLayoutManager horizontal = new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false);
        recyclerViewCategorie.setLayoutManager(horizontal);
    }

    @Override
    public void initializeComponents() {
        buttonIndietro = findViewById(R.id.buttonIndietroCategorieNuovaOrd);
        buttonRiepilogo = findViewById(R.id.buttonRiepilogoCategorieNuovaOrd);

        buttonRiepilogo.setOnClickListener(view -> categoriaPresenter.apriRiepilogo());
       buttonIndietro.setOnClickListener(view -> categoriaPresenter.mostraStartNuovaOrdinazione());

    }
    @Override
    public void apriRiepilogo(){
        if (ordinazione.ordinazioneVuota()){
            Dialog dialog = new Dialog(this);
            mostraDialogWarningOneBtn(dialog,"Ordinazione vuota", view1 -> dialog.dismiss() );
        } else {
            // starta il riepilogo ordinazione non vuota
            Intent intentRiepilogo = new Intent(this, RiepilogoOrdinazioneActivity.class);
            intentRiepilogo.putExtra("ordinazione",ordinazione);
            startActivity(intentRiepilogo);
        }
    }

    @Override
    public void tornaIndietro() {
        onBackPressed();
    }

    @Override
    public void mostraVisualizzaElementiDellaCategoria() {
        Intent intentVisualizzaCategoria = new Intent(this, VisualizzaMenuCategoriaActivity.class);
        //Setta la lista degli elementi menu in base alla categoria selezionata, caricandola da db
        List<ElementoMenu> elementi = categoria.getElementi();
        List<Portata> portata = new ArrayList<>();
        for(ElementoMenu e: elementi){
            portata.add(new Portata(e,false));
        }
        intentVisualizzaCategoria.putExtra("elementi", (Serializable) portata);
        intentVisualizzaCategoria.putExtra("nomeCategoria", categoria.getNome());
        intentVisualizzaCategoria.putExtra("ordinazione", ordinazione);

        if(!elementi.isEmpty()){
            startActivity(intentVisualizzaCategoria);
        }else{
            Toast.makeText(this,"Categoria attualmente vuota",Toast.LENGTH_SHORT).show();
        }
    }



    /*
            la foto viene recuperata in due passaggi in caso la connessione fosse lenta
        */
    @Override
    public void setCategorie(List<Categoria> categorie){
        this.categorie = categorie;
        if(categorie != null && !categorie.isEmpty()){
            for(int i = 0; i<categorie.size(); i++){
                categoriaPresenter.getFotoCategoriaById(categorie.get(i), i);
            }
        } else{
            Dialog dialog = new Dialog(this);
            mostraDialogErroreOneBtn(dialog, "Nessuna categoria da visualizzare", view -> {
                Intent intent = new Intent(this, StartNuovaOrdinazioneActivity.class);
                dialog.dismiss();
                startActivity(intent);
            });

        }
        initializeComponents();
        creaRecyclerView();

    }
    @Override
    public void mostraImmagineCategoria(int posizione){
        adapterCategoria.notifyItemChanged(posizione);
    }

    @Override
    public void caricamentoCategorieFallito() {
        //non viene mostrata la dialog se l'utente è uscito dall'activity mentre le categorie stavano ancora caricando
        //nel caso in cui la connessione al server fallisca
        if(getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            Dialog dialog = new Dialog(this);
            mostraDialogErroreOneBtn(dialog, "Impossibile caricare le categorie", view -> {
                Intent intent = new Intent(this, StartNuovaOrdinazioneActivity.class);
                dialog.dismiss();
                startActivity(intent);
            });
        }
    }


    @Override
    public void onItemClickRecyclerView(int position) {
        categoria = categorie.get(position);
        categoria.ordinaCategoria();
        categoriaPresenter.mostraElementiDellaCategoria();
    }


    @Override
    public void onBackPressed() {
        Dialog dialog = new Dialog(this);
        mostraDialogWarningTwoBtn(dialog, "Tornando indietro perderai la corrente ordinazione",
                view -> {
                    Intent intentLogOut = new Intent(this, StartNuovaOrdinazioneActivity.class);
                    dialog.dismiss();
                    startActivity(intentLogOut);
                    super.onBackPressed();
                },
                view -> dialog.dismiss());
    }

}
