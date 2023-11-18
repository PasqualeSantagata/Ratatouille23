package com.example.springclient.view.gestioneMenu;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.entity.Categoria;
import com.example.springclient.presenter.CategoriaMenuPresenter;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;

public class CreaCategoriaActivity extends AppCompatActivity {
    private ImageView imageViewAggiungiImmagine;
    private TextView textViewAggiungiImmagine;
    private TextInputLayout textInputLayoutNomeCategoria;
    private Button buttonOk;
    private Button buttonIndietro;
    private CategoriaMenuPresenter categoriaPresenter;
    private byte[] immagineCategoria = null;
    private Bitmap immagine;
    private File immagineFile;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_categoria_gestione_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().setTitle("CREA CATEGORIA");
        categoriaPresenter = new CategoriaMenuPresenter(this);

        initializeComponents();
    }

    private void initializeComponents() {
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
                categoriaPresenter.saveCategoria(categoria);
            }

        });

        buttonIndietro.setOnClickListener(view -> {
            mostraDialogWarningTwoBtn("Attenzione, tutti i dati inseriti verranno cancellati se torni indietro, continuare?");
        });

        imageViewAggiungiImmagine.setOnClickListener(view -> {
            Intent intent = new Intent(this, SelezionaImmagineCategoriaActivity.class);
            startActivity(intent);
        });
    }

    private void mostraDialogWarningTwoBtn(String messaggio) {
        Dialog dialogAttenzione = new Dialog(this);
        dialogAttenzione.setContentView(R.layout.dialog_warning_two_button);

        TextView messaggiodialog = dialogAttenzione.findViewById(R.id.textViewDialogeWarnTwoBtn);
        messaggiodialog.setText(messaggio);

        Button buttonSi = dialogAttenzione.findViewById(R.id.buttonSiDialogWarnTwoBtn);
        Button buttonNo = dialogAttenzione.findViewById(R.id.buttonNoDialogWarnTwoBtn);
        dialogAttenzione.show();

        buttonSi.setOnClickListener(view -> {
            onBackPressed();
            dialogAttenzione.dismiss();
        });
        buttonNo.setOnClickListener(view -> {
            dialogAttenzione.dismiss();
        });
    }

    public void salvaImmagine(Long id) {
        categoriaPresenter.addFotoCategoria(id.toString(), immagineFile);

    }

    public void continuaInserimento() {
        Intent intent = new Intent(this, HomeNuovoElementoActivity.class);
        startActivity(intent);
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
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeNuovoElementoActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
