package com.example.springclient.view.inserimentoNelMenu;

import android.os.Bundle;
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
import com.example.springclient.view.adapters.IRecycleViewElementoMenu;
import com.example.springclient.view.adapters.RecycleViewAdapterElementoMenu;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Collections;
import java.util.List;

public class VisualizzaElementoMenu extends AppCompatActivity implements IRecycleViewElementoMenu {

    private TextInputLayout textInputLayoutPrezzo;
    private TextInputLayout textInputLayoutDescrizione;
    private TextInputLayout textInputLayoutAllergeni;

    private List<ElementoMenu> elementiMenu;
    private Categoria categoria;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().setTitle("PRIMI"); CATEGORIA PASSATA DALLA ACTIVITY PRECEDENTE (ESPLORA CATEGORIE)
        setContentView(R.layout.activity_visualizza_elem_menu_inserimento_nel_menu);

        initializeComponents();

    }

    private void initializeComponents(){
        textInputLayoutPrezzo = findViewById(R.id.textInputLayoutPrezzoInserimNelMenu);
        textInputLayoutDescrizione  = findViewById(R.id.textInputLayoutDescrizioneInserimNelMenu);
        textInputLayoutAllergeni = findViewById(R.id.textInputLayoutAllergeniInserimNelMenu);


    }

    private void setTextInputLayoutText(TextInputLayout textInputLayout, String text) {
        EditText editText = textInputLayout.getEditText();
        editText.setText(text);
    }


    public void setElementiMenuRecycleView(List<ElementoMenu> listaElementiMenu){
        RecyclerView recyclerViewPiatti = findViewById(R.id.RecyclerViewPiattiNuovaOrdinazione);
        RecycleViewAdapterElementoMenu adapterElementoMenu = new RecycleViewAdapterElementoMenu(this, listaElementiMenu, this);
        recyclerViewPiatti.setAdapter(adapterElementoMenu);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewPiatti.setLayoutManager(linearLayoutManager);

        //Dovrebbe implementare la funzione drug and drop
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerViewPiatti.addItemDecoration(dividerItemDecoration);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewPiatti);

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {

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

    public void setParameters(ElementoMenu elementoMenu){
        setTextInputLayoutText(textInputLayoutPrezzo, String.valueOf(elementoMenu.getPrezzo()));
        //Attenzione controllare il to string default di List
        setTextInputLayoutText(textInputLayoutAllergeni, elementoMenu.getElencoAllergeni().toString());
        setTextInputLayoutText(textInputLayoutDescrizione, elementoMenu.getDescrizione());
    }

    @Override
    public void onItemClick(int position) {

    }
}
