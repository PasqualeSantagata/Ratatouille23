package com.example.springclient.view.gestioneMenu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.view.adapters.IRecycleViewElementoMenu;
import com.example.springclient.view.adapters.RecycleViewAdapterGestioneElementoMenu;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class RiordinaElementiActivity extends AppCompatActivity implements IRecycleViewElementoMenu {
    private RecyclerView recyclerViewElementi;
    private RecycleViewAdapterGestioneElementoMenu adapterElementoMenu;
    private Button buttonOk;
    private Button buttonIndietro;
    private List<ElementoMenu> elementiMenu;

    private ItemTouchHelper.SimpleCallback simpleCallback;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riordina_elementi_menu_gestione_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        elementiMenu = (List<ElementoMenu>) getIntent().getSerializableExtra("elementiMenu");
        //abilita drag and drop recycle view
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

    private void initializeComponents() {
        buttonOk = findViewById(R.id.buttonOkRiordinaElementiGestioneMenu);
        buttonIndietro = findViewById(R.id.buttonIndietroRiordinaElementiGestioneMenu);
        setElementiMenuRecycleView();

        buttonOk.setOnClickListener(view -> {
            Intent intent = new Intent(this, VisualizzaElementiDellaCategoriaInserimentoNelMenuActivity.class);
            //TODO Salva il nuovo ordine del menu
            startActivity(intent);
        });

        buttonIndietro.setOnClickListener(view -> {
            Intent intent = new Intent(this, VisualizzaElementiDellaCategoriaInserimentoNelMenuActivity.class);
            intent.putExtra("elementi", (Serializable) elementiMenu);
            startActivity(intent);
        });

    }


    public void setElementiMenuRecycleView(){
        RecyclerView recyclerViewPiatti = findViewById(R.id.recyclerViewElementiMenuRiordinaElementiGestioneMenu);
        adapterElementoMenu = new RecycleViewAdapterGestioneElementoMenu(this, elementiMenu, this);
        recyclerViewPiatti.setAdapter(adapterElementoMenu);

        GridLayoutManager horizontal = new GridLayoutManager(this, 3, RecyclerView.HORIZONTAL, false);

        recyclerViewPiatti.setLayoutManager(horizontal);
        recyclerViewPiatti.setItemAnimator(null);

        //Dovrebbe implementare la funzione drug and drop
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerViewPiatti.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewPiatti);

    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onButtonDeleted(int position) {
        IRecycleViewElementoMenu.super.onButtonDeleted(position);
    }


}
