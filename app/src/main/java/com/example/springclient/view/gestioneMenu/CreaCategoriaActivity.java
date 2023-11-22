package com.example.springclient.view.gestioneMenu;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.contract.CreaCategoriaContract;
import com.example.springclient.entity.Categoria;
import com.example.springclient.presenter.CreaCategoriaPresenter;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;

public class CreaCategoriaActivity extends AppCompatActivity implements CreaCategoriaContract.View {
    private ImageView imageViewAggiungiImmagine;
    private TextView textViewAggiungiImmagine;
    private TextInputLayout textInputLayoutNomeCategoria;
    private Button buttonOk;
    private Button buttonIndietro;
    private CreaCategoriaContract.Presenter creaCategoriaPresenter;
    private byte[] immagineCategoria = null;
    private Bitmap immagine;
    private File immagineFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_categoria_gestione_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().setTitle("CREA CATEGORIA");
        creaCategoriaPresenter = new CreaCategoriaPresenter(this);
        initializeComponents();
    }

    @Override
    public void initializeComponents() {
        imageViewAggiungiImmagine = findViewById(R.id.imageViewCategoriaCreaCategoriaGestioneMenu);
        textInputLayoutNomeCategoria = findViewById(R.id.TextInputLayoutNomeCategoriaCreaCategoriaGestioneMenu);
        textViewAggiungiImmagine = findViewById(R.id.textViewAggiungiImgCreaCategoriaGestioneMenu);
        buttonIndietro = findViewById(R.id.buttonIndietroCreaCategoriaGestioneMenu);
        buttonOk = findViewById(R.id.buttonOkCreaCategoriaGestioneMenu);
        immagineCategoria = (byte[]) getIntent().getSerializableExtra("immagine");


        if (immagineCategoria != null) {
            immagine = BitmapFactory.decodeByteArray(immagineCategoria, 0, immagineCategoria.length);
            imageViewAggiungiImmagine.setImageBitmap(immagine);
        }

        buttonOk.setOnClickListener(view -> {

            if (controllaCampi()) {
                String nomeCategoria = textInputLayoutNomeCategoria.getEditText().getText().toString();
                Categoria categoria = new Categoria(nomeCategoria);
                if (immagineCategoria != null) {
                    immagineFile = new File(getApplicationContext().getFilesDir(), nomeCategoria + ".jpeg");
                    OutputStream os;
                    try {
                        os = Files.newOutputStream(immagineFile.toPath());
                        immagine.compress(Bitmap.CompressFormat.JPEG, 100, os);
                        os.flush();
                        os.close();
                    } catch (Exception ignored) {

                    }
                }
                creaCategoriaPresenter.salavaCategoria(categoria);
            }

        });

        buttonIndietro.setOnClickListener(view -> {
            onBackPressed();
        });

        imageViewAggiungiImmagine.setOnClickListener(view -> {
            Intent intent = new Intent(this, SelezionaImmagineCategoriaActivity.class);
            startActivity(intent);
        });
    }

    public boolean controllaCampi() {
        String nomeCategoria = textInputLayoutNomeCategoria.getEditText().getText().toString();
        if (nomeCategoria.equals("")) {
            textInputLayoutNomeCategoria.setError("Inserisci un nome");
            return false;
        }
        return true;
    }


    @Override
    public void salvaImmagine(Long id) {
        creaCategoriaPresenter.aggiungiFotoCategoria(id.toString(), immagineFile);
    }
    @Override
    public void continuaInserimento() {
        Intent intent = new Intent(this, HomeNuovoElementoActivity.class);
        startActivity(intent);
    }
    @Override
    public Context getContext(){
        return getContext();
    }
    @Override
    public void onBackPressed() {
        Dialog dialog = new Dialog(this);
        mostraDialogWarningTwoBtn(dialog, "Attenzione, tutti i dati inseriti verranno cancellati se torni indietro, continuare?",
                view -> {
                    Intent intent = new Intent(this, HomeNuovoElementoActivity.class);
                    startActivity(intent);
                    super.onBackPressed();

                }, view -> dialog.dismiss());

    }
}
