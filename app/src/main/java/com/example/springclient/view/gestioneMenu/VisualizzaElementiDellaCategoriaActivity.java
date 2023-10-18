package com.example.springclient.view.gestioneMenu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.entity.Categoria;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.presenter.ElementoMenuPresenter;
import com.example.springclient.view.adapters.IRecycleViewElementoMenu;
import com.example.springclient.view.adapters.RecycleViewAdapterGestioneElementoMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class VisualizzaElementiDellaCategoriaActivity extends AppCompatActivity implements IRecycleViewElementoMenu {

    private TextInputLayout textInputLayoutPrezzo;
    private TextInputLayout textInputLayoutDescrizione;
    private TextInputLayout textInputLayoutAllergeni;
    private FloatingActionButton fabModifica;

    private List<ElementoMenu> elementiMenu;
    private Categoria categoria;
    private RecyclerView recyclerView;
    private int elementoSelezionato = -1;
    private ElementoMenuPresenter elementoMenuPresenter;
    private  RecycleViewAdapterGestioneElementoMenu adapterElementoMenu;
    private ItemTouchHelper.SimpleCallback simpleCallback;
    private String nome;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //verifica l'acrtivity prececente
        elementiMenu = (List<ElementoMenu>) getIntent().getSerializableExtra("elementi");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        nome = getIntent().getStringExtra("nomeCategoria");
        getSupportActionBar().setTitle(nome);
        setContentView(R.layout.activity_visualizza_cagtegoria_gestione_menu);
        elementoMenuPresenter = new ElementoMenuPresenter(this);

        simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN
                | ItemTouchHelper.START | ItemTouchHelper.END, 0) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                Collections.swap(elementiMenu, fromPosition, toPosition);
                recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        };

        initializeComponents();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_overflow_visualizza_elem_menu_inserimento_nel_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.riordinare:
                Intent intent = new Intent(this, RiordinaElementiActivity.class);
                intent.putExtra("elementiMenu",(Serializable) elementiMenu);
                startActivity(intent);
                break;
            case R.id.lingue:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeComponents(){
        textInputLayoutPrezzo = findViewById(R.id.textInputLayoutPrezzoInserimNelMenu);
        textInputLayoutDescrizione  = findViewById(R.id.textInputLayoutDescrizioneInserimNelMenu);
        textInputLayoutAllergeni = findViewById(R.id.textInputLayoutAllergeniInserimNelMenu);
        fabModifica = findViewById(R.id.fabModificaInserNelMenu);
        setElementiMenuRecycleView();

        fabModifica.setOnClickListener(view -> {
            Intent intentModElemento = new Intent(this, ModificaElementoActivity.class);
            intentModElemento.putExtra("elementoMenu", elementoSelezionato);
            startActivity(intentModElemento);
        });



    }

    private void setTextInputLayoutText(TextInputLayout textInputLayout, String text) {
        EditText editText = textInputLayout.getEditText();
        editText.setText(text);
    }


    public void setElementiMenuRecycleView(){
        RecyclerView recyclerViewPiatti = findViewById(R.id.recycleViewPiattiCategoriaInserimentoNelMenu);
        adapterElementoMenu = new RecycleViewAdapterGestioneElementoMenu(this, elementiMenu, this);
        recyclerViewPiatti.setAdapter(adapterElementoMenu);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewPiatti.setLayoutManager(linearLayoutManager);
        recyclerViewPiatti.setItemAnimator(null);

        //Dovrebbe implementare la funzione drug and drop
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerViewPiatti.addItemDecoration(dividerItemDecoration);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewPiatti);

    }

    public void setParameters(ElementoMenu elementoMenu){
        setTextInputLayoutText(textInputLayoutPrezzo, String.valueOf(elementoMenu.getPrezzo()));
        //Attenzione controllare il to string default di List
       // setTextInputLayoutText(textInputLayoutAllergeni, elementoMenu.getElencoAllergeni().toString());
        setTextInputLayoutText(textInputLayoutDescrizione, elementoMenu.getDescrizione());
    }

    private ElementoMenu getElementoMenu(){
        return elementiMenu.get(elementoSelezionato);
    }

    @Override
    public void onItemClick(int position) {
        elementoSelezionato = position;
        setParameters(elementiMenu.get(position));
    }

    @Override
    public void onButtonDeleted(int position) {
        elementoSelezionato = position;
        elementoMenuPresenter.rimuoviElemento(elementiMenu.get(position).getId().toString());
    }

    public void cancellaElemento(){
        elementiMenu.remove(elementoSelezionato);
        adapterElementoMenu.notifyItemChanged(elementoSelezionato);
    }

    public void mostraTraduzione(ElementoMenu elementoMenu){
        Log.d("Traduzione: ", elementoMenu.getNome() + " " + elementoMenu.getDescrizione());
    }

}
