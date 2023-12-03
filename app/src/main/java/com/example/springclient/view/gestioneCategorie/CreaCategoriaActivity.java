package com.example.springclient.view.gestioneCategorie;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import com.example.springclient.R;
import com.example.springclient.contract.CreaCategoriaContract;
import com.example.springclient.entity.Categoria;
import com.example.springclient.presenter.CreaCategoriaPresenter;
import com.example.springclient.view.inserisciElemento.HomeNuovoElementoActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;

public class CreaCategoriaActivity extends AppCompatActivity implements CreaCategoriaContract.CreaCategoriaView {
    private ImageView imageViewAggiungiImmagine;
    private TextView textViewAggiungiImmagine;
    private TextInputLayout textInputLayoutNomeCategoria;
    private Button buttonOk;
    private Button buttonIndietro;
    private CreaCategoriaContract.Presenter creaCategoriaPresenter;
    private byte[] immagineCategoria = null;
    private Bitmap immagine;
    private File immagineFile;
    private String nomeCategoria;
    private EditText editTextNome;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_categoria_gestione_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().setTitle("CREA CATEGORIA");
        nomeCategoria = getIntent().getStringExtra("nomeCategoria");
        creaCategoriaPresenter = new CreaCategoriaPresenter(this);
        inizializzaComponenti();
    }

    @Override
    public void inizializzaComponenti() {
        imageViewAggiungiImmagine = findViewById(R.id.imageViewCategoriaCreaCategoriaGestioneMenu);
        textInputLayoutNomeCategoria = findViewById(R.id.TextInputLayoutNomeCategoriaCreaCategoriaGestioneMenu);
        textViewAggiungiImmagine = findViewById(R.id.textViewAggiungiImgCreaCategoriaGestioneMenu);
        buttonIndietro = findViewById(R.id.buttonIndietroCreaCategoriaGestioneMenu);
        buttonOk = findViewById(R.id.buttonOkCreaCategoriaGestioneMenu);
        immagineCategoria = (byte[]) getIntent().getSerializableExtra("immagine");
        editTextNome = textInputLayoutNomeCategoria.getEditText();
        progressBar = findViewById(R.id.progressBarCreaCategoria);
        progressBar.setVisibility(View.INVISIBLE);

        if (immagineCategoria != null) {
            immagine = BitmapFactory.decodeByteArray(immagineCategoria, 0, immagineCategoria.length);
            imageViewAggiungiImmagine.setImageBitmap(immagine);
        }

        buttonOk.setOnClickListener(view -> {

            if (controllaCampi()) {
                String nomeCategoria = textInputLayoutNomeCategoria.getEditText().getText().toString();
                Categoria categoria = new Categoria(nomeCategoria);
                if (immagineCategoria != null) {
                    immagineFile = new File(getApplicationContext().getFilesDir(), nomeCategoria + ".PNG");
                    OutputStream os;
                    try {
                        os = Files.newOutputStream(immagineFile.toPath());
                        immagine.compress(Bitmap.CompressFormat.JPEG, 100, os);
                        os.flush();
                        os.close();
                    } catch (Exception ignored) {
                        Toast.makeText(this, "Errore nel caricamento della foto: "+ignored.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                if(immagineCategoria == null){
                    Dialog dialog = new Dialog(this);
                    mostraDialogWarningOneBtn(dialog, "Inserisci una foto per la categoria", view1 -> dialog.dismiss());
                }else {
                    creaCategoriaPresenter.salavaCategoria(categoria);
                }
            }

        });
        if(nomeCategoria!=null && !nomeCategoria.equals("")){
            editTextNome.setText(nomeCategoria);
        }

        buttonIndietro.setOnClickListener(view -> creaCategoriaPresenter.tornaHomeNuovoElemento());
        imageViewAggiungiImmagine.setOnClickListener(view -> creaCategoriaPresenter.mostraScegliFoto());
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
    public void erroreSalvataggioCategoria() {
        Dialog dialog = new Dialog(this);
        mostraDialogErroreOneBtn(dialog, "Impossibile comunicare con il server", view -> dialog.dismiss());
    }

    @Override
    public void mostraScegliFoto() {
        Intent intent = new Intent(this, SelezionaImmagineCategoriaActivity.class);
        intent.putExtra("nomeCategoria", editTextNome.getText().toString());
        startActivity(intent);
    }

    @Override
    public void impossibileContattareIlServer(String messaggio) {
        Dialog dialog = new Dialog(this);
        mostraDialogErroreOneBtn(dialog, messaggio, view -> dialog.dismiss());
    }

    @Override
    public void categoriaGiaEsistente() {
        textInputLayoutNomeCategoria.setError("Nome categoria non valido");
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
