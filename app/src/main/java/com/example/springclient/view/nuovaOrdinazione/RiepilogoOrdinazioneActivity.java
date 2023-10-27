package com.example.springclient.view.nuovaOrdinazione;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.contract.OrdinazioneContract;
import com.example.springclient.entity.Ordinazione;
import com.example.springclient.entity.Portata;
import com.example.springclient.presenter.ElementoMenuPresenter;
import com.example.springclient.presenter.OrdinazionePresenter;
import com.example.springclient.view.adapters.IRecycleViewElementoMenu;
import com.example.springclient.view.adapters.RecycleViewAdapterRiepilogoOrdinazione;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RiepilogoOrdinazioneActivity extends AppCompatActivity implements IRecycleViewElementoMenu, Serializable {

    private Button buttonIndietro;
    private Button buttonOk;

    private Ordinazione ordinazione;
    private List<Portata> portate;
    private RecycleViewAdapterRiepilogoOrdinazione adapterElementoMenu;
    private ElementoMenuPresenter elementoMenuPresenter;
    private OrdinazioneContract.Presenter presenterOrdinazione;
    private int indiceElementoSelezionato = -1;
    private Portata elementoSelezionato;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("RIEPILOGO ORDINAZIONE");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_riepilogo_ordinazione_nuova_ordinazione);
        //Dettagli ordinazione dalla activity precedente
        ordinazione = (Ordinazione) getIntent().getSerializableExtra("ordinazione");
        elementoMenuPresenter = new ElementoMenuPresenter(this);

        portate = new ArrayList<>();
        for (Portata o : ordinazione.getElementiOrdinati()) {
            portate.add(o);
        }
        presenterOrdinazione = new OrdinazionePresenter(this);

        initializeComponents();
    }


    private void initializeComponents() {
        //Recycler view
        RecyclerView recyclerViewRiepilogo = findViewById(R.id.RecyclerViewRiepilogoOrdinazione);
        adapterElementoMenu = new RecycleViewAdapterRiepilogoOrdinazione(this, this, portate);
        recyclerViewRiepilogo.setAdapter(adapterElementoMenu);
        GridLayoutManager horizontal = new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false);
        recyclerViewRiepilogo.setLayoutManager(horizontal);

        //Buttons
        buttonOk = findViewById(R.id.buttonOkRiepilogo);
        buttonIndietro = findViewById(R.id.buttonIndietroRiepilogo);
        buttonOk.setOnClickListener(view -> {
            presenterOrdinazione.savePortate(portate);
        });
        buttonIndietro.setOnClickListener(view -> onBackPressed());

    }

    public void salvaOrdinazione(List<Portata> portateOrdinazione) {
        ordinazione.setElementiOrdinati(portateOrdinazione);
        presenterOrdinazione.aggiungiOrdinazione(ordinazione);
    }

    public void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_popup_elem_riepilogo, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(menuItem -> onPopupMenuClick(menuItem));
        popupMenu.show();
    }

    private boolean onPopupMenuClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_elimina_elem_riepilogo:
                //Deve elminare l'elemento
                portate.remove(indiceElementoSelezionato);
                //notifica l'adapter della recycler view
                adapterElementoMenu.notifyItemChanged(indiceElementoSelezionato);
                break;
        }
        return true;
    }

    public void dialogOrdinazioneAvvvenutaConSuccesso() {
        Dialog ordinazioneAvvenuta = new Dialog(this);
        ordinazioneAvvenuta.setContentView(R.layout.dialog_ok_one_button);
        TextView ordinazioneAvvenutaTv = ordinazioneAvvenuta.findViewById(R.id.textViewDialogEmailInviata);
        ordinazioneAvvenutaTv.setText("Ordinazione inviata con successo");
        Button okButton = ordinazioneAvvenuta.findViewById(R.id.okDialog);
        ordinazioneAvvenuta.show();
        okButton.setOnClickListener(view -> {
            ordinazioneAvvenuta.dismiss();
            Intent loginActivity = new Intent(this, StartNuovaOrdinazioneActivity.class);
            startActivity(loginActivity);
        });
    }


    @Override
    public void onItemClickRecyclerViewPortata(int position) {
        //Aprire menu di android per far elminiare una certa pietanza dall'ordinaizone
        indiceElementoSelezionato = position;
        elementoSelezionato = portate.get(position);


    }

    @Override
    public void onBackPressed() {
        Intent intentEsploraCategorie = new Intent(this, EsploraCategorieActivity.class);
        intentEsploraCategorie.putExtra("ordinazione", ordinazione);
        startActivity(intentEsploraCategorie);
        super.onBackPressed();
    }


}

