package com.example.springclient.view.nuovaOrdinazione;

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
import com.example.springclient.entity.Ordinazione;
import com.example.springclient.entity.Portata;
import com.example.springclient.presenter.CategoriaMenuPresenter;
import com.example.springclient.view.MainActivity;
import com.example.springclient.view.adapters.IRecycleViewCategoria;
import com.example.springclient.view.adapters.RecycleViewAdapterCategoria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EsploraCategorieActivity extends AppCompatActivity implements IRecycleViewCategoria, CategoriaContract.View {
    private Ordinazione ordinazione;
    private Button buttonIndietro;
    private Button buttonRiepilogo;
    private CategoriaMenuPresenter categoriaPresenter;

    private List<Categoria> categorie;
    private RecycleViewAdapterCategoria adapterCategoria;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("CATEGORIE");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_esplora_categorie_nuova_ordinazione);

        categoriaPresenter = new CategoriaMenuPresenter(this);
        //Dettagli ordinazione dalla activity precedente
        ordinazione = (Ordinazione) getIntent().getSerializableExtra("ordinazione");
        //Fetch delle categorie dal server
        categoriaPresenter.getAllCategorie();
    }

    public void initializeComponents() {

        RecyclerView recyclerViewCategorie = findViewById(R.id.RecycleViewCategorie);
        adapterCategoria = new RecycleViewAdapterCategoria(this, categorie, this);
        recyclerViewCategorie.setAdapter(adapterCategoria);
        GridLayoutManager horizontal = new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false);
        recyclerViewCategorie.setLayoutManager(horizontal);


        buttonIndietro = findViewById(R.id.buttonIndietroCategorieNuovaOrd);
        buttonRiepilogo = findViewById(R.id.buttonRiepilogoCategorieNuovaOrd);

        buttonRiepilogo.setOnClickListener(view -> {
            if (ordinazione.ordinazioneVuota()){
                mostraDialogWarning(getString(R.string.dialog_ord_vuota));

            } else {
                // starta il riepilogo ordinazione non vuota
                Intent intentRiepilogo = new Intent(this, RiepilogoOrdinazioneActivity.class);
                intentRiepilogo.putExtra("ordinazione",ordinazione);
                startActivity(intentRiepilogo);
            }

        });
       buttonIndietro.setOnClickListener(view -> {
           onBackPressed();
       });


    }


    private void mostraDialogWarning(String messaggio){
        Dialog dialogAttenzione = new Dialog(this);
        dialogAttenzione.setContentView(R.layout.dialog_warning_one_button);

        TextView messaggiodialog = dialogAttenzione.findViewById(R.id.textViewMessageDialogueErrorOneBt);
        messaggiodialog.setText(messaggio);

        Button buttonOk = dialogAttenzione.findViewById(R.id.buttonOkDialogueErrorOneBt);
        dialogAttenzione.show();

        buttonOk.setOnClickListener(view -> {
            dialogAttenzione.dismiss();
        });

    }

    @Override
    public void setNomiCategorie(List<String> nomiCategorie) {

    }

    /*
    la foto viene recuperata in due passaggi in caso la connessione fosse lenta
     */
    public void setCategorie(List<Categoria> categorie){
        this.categorie = categorie;
        if(categorie != null && !categorie.isEmpty()){
            for(int i = 0; i<categorie.size(); i++){
                categoriaPresenter.getFotoCategoriaById(categorie.get(i), i);
            }
        } else{
            dialogNessunaCategoria();
        }
        initializeComponents();
    }
    @Override
    public void notifyAdapter(int posizione){
        adapterCategoria.notifyItemChanged(posizione);
    }

    @Override
    public void onItemClick(int position) {
        Intent intentVisualizzaCategoria = new Intent(this, VisualizzaCategoriaActivity.class);
        //Setta la lista degli elementi menu in base alla categoria selezionata, caricandola da db
        List<ElementoMenu> elementi = categorie.get(position).getElementi();
        List<Portata> portata = new ArrayList<>();
        for(ElementoMenu e: elementi){
            portata.add(new Portata(e,false));
        }
        intentVisualizzaCategoria.putExtra("elementi", (Serializable) portata);
        intentVisualizzaCategoria.putExtra("nomeCategoria", categorie.get(position).getNome());

        //Ordinazione con le info collezionate in precedenza
        intentVisualizzaCategoria.putExtra("ordinazione", ordinazione);
        startActivity(intentVisualizzaCategoria);
    }

    @Override
    public void onBackPressed() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_warning_two_button);
        TextView errorMessage = dialog.findViewById(R.id.textViewDialogeWarnTwoBtn);
        errorMessage.setText("Tornando indietro perderai la corrente ordinazione");
        dialog.show();

        Button buttonNo = dialog.findViewById(R.id.buttonNoDialogWarnTwoBtn);
        Button buttonSi = dialog.findViewById(R.id.buttonSiDialogWarnTwoBtn);

        buttonNo.setOnClickListener(view1 -> {
            dialog.dismiss();
        });

        buttonSi.setOnClickListener(view1 -> {
            Intent intentLogOut = new Intent(this, StartNuovaOrdinazioneActivity.class);
            dialog.dismiss();
            startActivity(intentLogOut);
            super.onBackPressed();
        });
    }

    private void dialogNessunaCategoria(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_warning_one_button);
        TextView errorMessage = dialog.findViewById(R.id.textViewMessageDialogueErrorOneBt);
        Button ok = dialog.findViewById(R.id.buttonOkDialogueErrorOneBt);
        errorMessage.setText("Nessuna categoria da visualizzare");
        ok.setOnClickListener(view1 -> {
            Intent intent = new Intent(this, StartNuovaOrdinazioneActivity.class);
            dialog.dismiss();
            startActivity(intent);
            dialog.dismiss();
        });
        dialog.show();
    }
}
