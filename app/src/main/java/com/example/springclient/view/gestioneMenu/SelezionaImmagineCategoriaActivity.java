package com.example.springclient.view.gestioneMenu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.entity.Categoria;
import com.example.springclient.view.adapters.IRecycleViewCategoria;
import com.example.springclient.view.adapters.RecycleViewAdapterCategoria;

import java.util.ArrayList;
import java.util.List;

public class SelezionaImmagineCategoriaActivity extends AppCompatActivity implements IRecycleViewCategoria {
    private RecyclerView recyclerViewImmagini;
    private RecycleViewAdapterCategoria adapterCategoria;
    private List<Categoria> categorieList ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleziona_immagine_categoria_gestione_menu);
        getSupportActionBar().setTitle("SELEZIONA IMMAGINE CATEGORIA");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        categorieList = new ArrayList<>();
        setImmaginiCategoriaDefault();


        initializeComponents();
    }

    private void setImmaginiCategoriaDefault() {
        int[] immagini = {R.drawable.categoria_dessert, R.drawable.categoria_bevande, R.drawable.categoria_primi,
                R.drawable.categoria_pizza, R.drawable.categoria_secondi, R.drawable.categoria_sushi};

        for (int i : immagini){
            categorieList.add(new Categoria(BitmapFactory.decodeResource(getResources(), i)));
        }

    }

    private void initializeComponents() {
        recyclerViewImmagini = findViewById(R.id.recyclerViewSelezImgCategoriaGestioneMenu);

        setImmaginiRecycleView();
    }

    public void setImmaginiRecycleView(){
        adapterCategoria = new RecycleViewAdapterCategoria(this, categorieList, this);
        recyclerViewImmagini.setAdapter(adapterCategoria);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewImmagini.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onItemClick(int position) {
        Bitmap image = categorieList.get(position).getImage();
        Intent intent = new Intent(this, CreaCategoriaActivity.class);
        intent.putExtra("immagine", image);
        startActivity(intent);
    }
}
