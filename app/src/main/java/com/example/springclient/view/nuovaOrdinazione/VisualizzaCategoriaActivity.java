package com.example.springclient.view.nuovaOrdinazione;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.contract.ElementoMenuContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.entity.Ordinazione;
import com.example.springclient.entity.Portata;
import com.example.springclient.presenter.ElementoMenuPresenter;
import com.example.springclient.view.adapters.IRecycleViewElementoMenu;
import com.example.springclient.view.adapters.RecycleViewAdapterElementoMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class VisualizzaCategoriaActivity extends AppCompatActivity implements IRecycleViewElementoMenu, ElementoMenuContract.View {
    //impostare categoria dall'intent
    private Button buttonIndietro;
    private Button buttonRiepilogo;
    private TextInputLayout textInputLayoutPrezzo;
    private TextInputLayout textInputLayoutAllergeni;
    private TextInputLayout textInputLayoutDescrizione;
    private TextInputLayout textInputLayoutNota;
    private FloatingActionButton fabAggiungiAdOrdinazione;
    private List<Portata> elementiMenu;
    private Ordinazione ordinazione;
    private int elementoSelezionato = -1;

    private ElementoMenuPresenter elementoMenuPresenter;

    //boolean categoriaInclusa; serve a sapere quali categorie ci sono per sapere quali mostrare nel riepilogo,
    // non so se sarà utile quindi lo mantengo commentato per ora
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        elementiMenu = (List<Portata>) getIntent().getSerializableExtra("elementi");
        ordinazione = (Ordinazione) getIntent().getSerializableExtra("ordinazione");
        String nome = getIntent().getStringExtra("nomeCategoria");
        getSupportActionBar().setTitle(nome);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_visualizza_categoria_nuova_ordinazione);

        elementoMenuPresenter = new ElementoMenuPresenter(this);

        initializeComponents();

    }

    public void initializeComponents() {
        buttonIndietro = findViewById(R.id.buttonIndietroVisuaCatNuovaOrdinazione);
        buttonRiepilogo = findViewById(R.id.buttonRiepilogoVisualCatNuovaOrdinazione);

        textInputLayoutPrezzo = findViewById(R.id.textInputLayoutPrezzoVisuaCatNuovaOrdinazione);
        textInputLayoutAllergeni = findViewById(R.id.textInputLayoutAllergeniVisuaCatNuovaOrdinazione);
        textInputLayoutDescrizione = findViewById(R.id.textInputLayoutDescrizioneVisuaCatNuovaOrdinazione);

        fabAggiungiAdOrdinazione = findViewById(R.id.fabAggiungiAdOrdinazione);

        //Setto la recycle view
        setElementiMenuRecycleView(elementiMenu);

        fabAggiungiAdOrdinazione.setOnClickListener(view -> {
            if(elementoSelezionato > -1) {
                ordinazione.aggiungiPiatto(new Portata(getElementoMenu(),false));
            }
        });

        buttonIndietro.setOnClickListener(view -> {
            onBackPressed();
        });

        buttonRiepilogo.setOnClickListener(view -> {
            Intent intentRiepilogo = new Intent(this, RiepilogoOrdinazioneActivity.class);
            intentRiepilogo.putExtra("ordinazione",ordinazione);
            startActivity(intentRiepilogo);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options_visualizza_elem_ordinazione, menu);
        return true;
    }

    private boolean b;
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(b==true){
            menu.findItem(R.id.item_lingue).setEnabled(false);
            menu.findItem(R.id.item_lingua_base).setEnabled(true);
        }else{
            menu.findItem(R.id.item_lingue).setEnabled(true);
            menu.findItem(R.id.item_lingua_base).setEnabled(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_lingue_ordinazione :
                if(elementoSelezionato != -1) {
                    elementoMenuPresenter.restituisciTraduzione(elementiMenu.get(elementoSelezionato).getId().toString());
                    invalidateOptionsMenu();
                    b = true;
                }
                else{
                    Toast.makeText(this, "Seleziona un elemento per visualizzarne la traduzione", Toast.LENGTH_LONG).show();
                }

            case R.id.item_lingua_base_ordinazione:
                if(elementoSelezionato != -1) {
                    setParameters(getElementoMenu());
                    invalidateOptionsMenu();
                    b = false;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void mostraTraduzione(ElementoMenu elementoMenu) {
        setParameters(elementoMenu);
    }

    private void setTextInputLayoutText(TextInputLayout textInputLayout, String text) {
        EditText editText = textInputLayout.getEditText();
        editText.setText(text);
    }

    public void setParameters(ElementoMenu elementoMenu){
        setTextInputLayoutText(textInputLayoutPrezzo, String.valueOf(elementoMenu.getPrezzo()));
        setTextInputLayoutText(textInputLayoutDescrizione, elementoMenu.getDescrizione());
    }

    public void setElementiMenuRecycleView(List<Portata> listaElementiMenu){
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
    public void onItemClickRecyclerViewPortata(int position) {
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
}
