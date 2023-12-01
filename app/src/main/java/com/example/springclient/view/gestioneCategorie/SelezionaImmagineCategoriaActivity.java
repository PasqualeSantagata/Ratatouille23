package com.example.springclient.view.gestioneCategorie;

import static android.graphics.BitmapFactory.decodeResource;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.contract.CreaCategoriaContract;
import com.example.springclient.entity.Categoria;
import com.example.springclient.presenter.CreaCategoriaPresenter;
import com.example.springclient.view.adapters.IRecycleViewEventi;
import com.example.springclient.view.adapters.RecycleViewAdapterCategoria;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SelezionaImmagineCategoriaActivity extends AppCompatActivity implements IRecycleViewEventi, CreaCategoriaContract.ScegliFotoView {
    private RecyclerView recyclerViewImmagini;
    private RecycleViewAdapterCategoria adapterCategoria;
    private Button buttonIndietro;
    private Button buttonAggiungiImmagine;
    private List<Categoria> categorieList ;
    private CreaCategoriaContract.Presenter creaCategoriaPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleziona_immagine_categoria_gestione_menu);
        getSupportActionBar().setTitle("SELEZIONA IMMAGINE CATEGORIA");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        categorieList = new ArrayList<>();
        creaCategoriaPresenter = new CreaCategoriaPresenter(this);
        setImmaginiCategoriaDefault();
        initializeComponents();
    }

    private void setImmaginiCategoriaDefault() {
        //TODO aggiungere nuove imagini
        int[] immagini = {R.drawable.categoria_dessert, R.drawable.categoria_bevande, R.drawable.categoria_primi,
                R.drawable.categoria_pizza, R.drawable.categoria_secondi, R.drawable.categoria_sushi};

        for (int i : immagini){
            categorieList.add(new Categoria(decodeResource(getResources(), i)));
        }

    }
    private void setImmaginiRecycleView(){
        adapterCategoria = new RecycleViewAdapterCategoria(this, categorieList, this);
        recyclerViewImmagini.setAdapter(adapterCategoria);

        GridLayoutManager horizontal = new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false);
        recyclerViewImmagini.setLayoutManager(horizontal);
    }

    private byte[] convertiImmagine(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    @Override
    public void initializeComponents() {
        recyclerViewImmagini = findViewById(R.id.recyclerViewSelezImgCategoriaGestioneMenu);
        //aggiungiImmagineFab = findViewById(R.id.floatingActionButton);
        buttonAggiungiImmagine = findViewById(R.id.buttonAggingiImmagineSelezImmagine);
        buttonIndietro = findViewById(R.id.buttonIndietroSelezImmagine);

        ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    if (uri != null) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                            int w = bitmap.getHeight();
                            int h = bitmap.getHeight();
                            if (h >= 200 && h <= 500 && w >= 200 && w <= 500) {
                                byte[] bytes = convertiImmagine(bitmap);
                                creaCategoriaPresenter.caricaImmagine(bytes);
                            }else{
                                Toast.makeText(this, "Immagine con risoluzione errata, massimo supportato 500x500", Toast.LENGTH_LONG).show();
                            }
                        } catch(IOException e){
                                Toast.makeText(this, "Errore ne caricamento della foto", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(this, "Nessuna foto selezionata", Toast.LENGTH_LONG).show();
                    }
                });

        buttonAggiungiImmagine.setOnClickListener(view -> pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build()));
        setImmaginiRecycleView();

        buttonIndietro.setOnClickListener(view -> creaCategoriaPresenter.annullaInserisciImmagine());
    }

    @Override
    public void tornaIndietro() {
        onBackPressed();
    }

    @Override
    public void mostraPorgressBar() {

    }

    @Override
    public void nascondiProgressBar() {

    }

    @Override
    public boolean isVisibile() {
        return getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED);
    }

    @Override
    public void caricaImmagine(byte[] immagine){
        Intent intent = new Intent(this, CreaCategoriaActivity.class);
        intent.putExtra("immagine", immagine);
        intent.putExtra("nomeCategoria", getIntent().getStringExtra("nomeCategoria"));
        startActivity(intent);
    }

    @Override
    public void onItemClickRecyclerView(int position) {
        Bitmap bitmap = categorieList.get(position).getImage();
        byte[] bytes = convertiImmagine(bitmap);
        caricaImmagine(bytes);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
