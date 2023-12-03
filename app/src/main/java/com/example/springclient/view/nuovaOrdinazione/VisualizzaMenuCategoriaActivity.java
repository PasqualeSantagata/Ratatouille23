package com.example.springclient.view.nuovaOrdinazione;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.contract.BaseAllergeniDialog;
import com.example.springclient.contract.OrdinazioneContract;
import com.example.springclient.contract.VisualizzElementiContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.entity.Ordinazione;
import com.example.springclient.entity.Portata;
import com.example.springclient.presenter.OrdinazionePresenter;
import com.example.springclient.presenter.VisualizzElementiPresenter;
import com.example.springclient.view.adapters.IRecycleViewEventi;
import com.example.springclient.view.adapters.RecycleViewAdapterElementoMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;
import java.util.List;

public class VisualizzaMenuCategoriaActivity extends AppCompatActivity implements IRecycleViewEventi, VisualizzElementiContract.View, BaseAllergeniDialog,
        OrdinazioneContract.ElementiOrdinazioneView {
    private Button buttonIndietro;
    private Button buttonRiepilogo;
    private TextInputLayout textInputLayoutPrezzo;
    private TextInputLayout textInputLayoutDescrizione;
    private TextInputLayout textInputLayoutNota;
    private FloatingActionButton fabAggiungiAdOrdinazione;
    private List<Portata> elementiMenu;
    private Ordinazione ordinazione;
    private int elementoSelezionato = -1;
    private TextView textViewAllergeni;
    private boolean valoreSelezionatoBurgerMenu;
    private List<String> allergeni;
    private VisualizzElementiContract.Presenter visualizzaElementiPresenter;
    private OrdinazioneContract.Presenter viewElementiOrdinazionePresenter;
    private String nomeCategoria;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        elementiMenu = (List<Portata>) getIntent().getSerializableExtra("elementi");
        ordinazione = (Ordinazione) getIntent().getSerializableExtra("ordinazione");
        nomeCategoria = getIntent().getStringExtra("nomeCategoria");
        getSupportActionBar().setTitle(nomeCategoria);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_visualizza_categoria_nuova_ordinazione);
        progressBar = findViewById(R.id.progressBarVisualizzaMenuCategoria);
        progressBar.setVisibility(View.INVISIBLE);
        viewElementiOrdinazionePresenter = new OrdinazionePresenter(this);
        visualizzaElementiPresenter = new VisualizzElementiPresenter(this);
        inizializzaComponenti();
    }
    @Override
    public void inizializzaComponenti() {
        buttonIndietro = findViewById(R.id.buttonIndietroVisuaCatNuovaOrdinazione);
        buttonRiepilogo = findViewById(R.id.buttonRiepilogoVisualCatNuovaOrdinazione);

        textInputLayoutPrezzo = findViewById(R.id.textInputLayoutPrezzoVisuaCatNuovaOrdinazione);
        textInputLayoutDescrizione = findViewById(R.id.textInputLayoutDescrizioneVisuaCatNuovaOrdinazione);
        textInputLayoutNota= findViewById(R.id.textInputLayoutBreveNota);
        textViewAllergeni= findViewById(R.id.textViewAllergeniVisualCatNuovaOrd);

        fabAggiungiAdOrdinazione = findViewById(R.id.fabAggiungiAdOrdinazione);

        //Setto la recycle view
        setElementiMenuRecycleView(elementiMenu);

        fabAggiungiAdOrdinazione.setOnClickListener(view -> {
            if(elementoSelezionato > -1) {
                Portata portata = new Portata(getElementoMenu(),false);
                portata.setBreveNota(textInputLayoutNota.getEditText().getText().toString());
                ordinazione.aggiungiPiatto(portata);
                Toast.makeText(this,"Aggiunto ad ordinazione",Toast.LENGTH_SHORT).show();
            }
        });

        buttonIndietro.setOnClickListener(view -> viewElementiOrdinazionePresenter.tornaEsploraCategorie());

        buttonRiepilogo.setOnClickListener(view -> {
            if(ordinazione != null && ordinazione.getElementiOrdinati().size() != 0) {
                visualizzaElementiPresenter.mostraRiepilogo();
            }
            else{
                Dialog dialogAttenzione = new Dialog(this);
                mostraDialogWarningOneBtn(dialogAttenzione, "Attenzione l'ordinazione non ha elementi", view1 -> dialogAttenzione.dismiss());
            }
        });

        textViewAllergeni.setOnClickListener(view -> {
            if(elementoSelezionato != -1) {
                allergeni = elementiMenu.get(elementoSelezionato).getElementoMenu().getElencoAllergeni();
                dialogAllergeni(this, allergeni, true);
            }else{
                Toast.makeText(this,"Seleziona un elemento",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void tornaIndietro() {
        onBackPressed();
    }

    @Override
    public void mostraPorgressBar() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void nascondiProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public boolean isVisibile() {
        return getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options_visualizza_elem_ordinazione, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(valoreSelezionatoBurgerMenu){
            menu.findItem(R.id.item_lingue_ordinazione).setEnabled(false);
            menu.findItem(R.id.item_lingua_base_ordinazione).setEnabled(true);
        }else{
            menu.findItem(R.id.item_lingue_ordinazione).setEnabled(true);
            menu.findItem(R.id.item_lingua_base_ordinazione).setEnabled(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_lingue_ordinazione :
                if(elementoSelezionato != -1) {
                    visualizzaElementiPresenter.restituisciTraduzione(elementiMenu.get(elementoSelezionato).getElementoMenu().getId().toString());
                    invalidateOptionsMenu();
                    valoreSelezionatoBurgerMenu = true;
                }
                else{
                    Toast.makeText(this, "Seleziona un elemento per visualizzarne la traduzione", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.item_lingua_base_ordinazione:
                if(elementoSelezionato != -1) {
                    setParameters(getElementoMenu());
                    invalidateOptionsMenu();
                    valoreSelezionatoBurgerMenu = false;
                }
                break;

            case R.id.item_riordina_elem:
                viewElementiOrdinazionePresenter.mostraFiltraCategoria(nomeCategoria);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void mostraTraduzione(ElementoMenu elementoMenu) {
        setParameters(elementoMenu);
    }

    @Override
    public void traduzioneAssente() {
        Toast.makeText(this, "Traduzione non disponibile", Toast.LENGTH_LONG).show();
    }



    private void setTextInputLayoutText(TextInputLayout textInputLayout, String text) {
        EditText editText = textInputLayout.getEditText();
        editText.setText(text);
    }

    private void setParameters(ElementoMenu elementoMenu){
        setTextInputLayoutText(textInputLayoutPrezzo, String.valueOf(elementoMenu.getPrezzo()));
        setTextInputLayoutText(textInputLayoutDescrizione, elementoMenu.getDescrizione());
    }

    private void setElementiMenuRecycleView(List<Portata> listaElementiMenu){
        RecyclerView recyclerViewPiatti = findViewById(R.id.recycleViewElemMenuNuovaOrdinazione);
        RecycleViewAdapterElementoMenu adapterElementoMenu = new RecycleViewAdapterElementoMenu(this, listaElementiMenu, this);
        recyclerViewPiatti.setAdapter(adapterElementoMenu);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewPiatti.setLayoutManager(linearLayoutManager);
    }

    private ElementoMenu getElementoMenu(){
        return elementiMenu.get(elementoSelezionato).getElementoMenu();
    }

    @Override
    public void onItemClickRecyclerView(int position) {
        elementoSelezionato = position;
        setParameters(elementiMenu.get(position).getElementoMenu());

    }

    @Override
    public void onBackPressed() {
        Intent intentEsploraCategorie = new Intent(this, EsploraCategorieActivity.class);
        intentEsploraCategorie.putExtra("ordinazione",ordinazione);
        startActivity(intentEsploraCategorie);
        super.onBackPressed();
    }



    @Override
    public void mostraRiepilogo() {
        Intent intentRiepilogo = new Intent(this, RiepilogoOrdinazioneActivityView.class);
        intentRiepilogo.putExtra("ordinazione", ordinazione);
        startActivity(intentRiepilogo);
    }

    @Override
    public void mostraFiltraCategoria(List<ElementoMenu> portataList) {

    }


    @Override
    public void mostraModifica(ElementoMenu elementoMenu) {

    }

    @Override
    public void impossibileComunicareConServer(String messaggio) {
        Dialog dialog = new Dialog(this);
        mostraDialogErroreOneBtn(dialog, messaggio, view -> dialog.dismiss());
    }

    @Override
    public void impossibileRimuovereElemento(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setElementi(List<ElementoMenu> elementoMenuList) {

    }

    @Override
    public void rimuoviElemento() {

    }

    @Override
    public void mostraFiltraCategoriaMenu(List<Portata> portataList) {
        Intent intent = new Intent(this, FiltraCategoriaNuovaOrdinazioneActivity.class);
        intent.putExtra("elementiMenu", (Serializable) portataList);
        intent.putExtra("ordinazione", ordinazione);
        intent.putExtra("nomeCategoria", nomeCategoria);
        startActivity(intent);

    }

    @Override
    public void impossibileFiltrareElementi(String s) {
        Dialog dialog = new Dialog(this);
        mostraDialogWarningOneBtn(dialog, s, view -> dialog.dismiss());
    }


}
