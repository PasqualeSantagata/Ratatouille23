package com.example.springclient.view.statoOrdinazioni;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.entity.Ordinazione;
import com.example.springclient.presenter.OrdinazionePresenter;
import com.example.springclient.entity.Portata;
import com.example.springclient.view.adapters.IRecycleViewOrdinazioniCorrenti;
import com.example.springclient.view.adapters.IRecycleViewOrdinazioniEvase;
import com.example.springclient.view.adapters.IRecycleViewOrdinazioniPrenotate;
import com.example.springclient.view.adapters.RecycleViewAdapterOrdinazioniCorrenti;
import com.example.springclient.view.adapters.RecycleViewAdapterOrdinazioniEvase;
import com.example.springclient.view.adapters.RecycleViewAdapterOrdinazioniPrenotate;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class HomeStatoOrdinazione extends AppCompatActivity implements IRecycleViewOrdinazioniCorrenti, IRecycleViewOrdinazioniPrenotate, IRecycleViewOrdinazioniEvase {

    private RecyclerView recyclerViewOrdinazioniCorrenti;
    private RecyclerView recyclerViewOrdinazioniPrenotate;
    private RecyclerView recyclerViewOrdinazioniEvase;
    private StompClient stompClient;
    private OrdinazionePresenter ordinazionePresenter;
    private List<Ordinazione> ordinazioniSospese;
    private List<Portata> listStatoOrdinazione;
    RecycleViewAdapterOrdinazioniCorrenti adapterCorrenti;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_stato_ordinazioni);
        getSupportActionBar().setTitle("STATO DELLE ORDINAZIONI");
        ordinazionePresenter = new OrdinazionePresenter(this);
        ordinazionePresenter.getOrdinazioniSospese();

        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://10.0.2.2:8080/ordinazione-endpoint/websocket");
        stompClient.connect();

        stompClient.topic("/topic/ricevi-ordinazione")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ordinazione-> {
                    Log.d("WS2:", "Received " + ordinazione.getPayload());
                });

        /*
        *  grazie all'oggetto StatoDellOrdinazione abbiamo una corrispondenza tra gli elementi che compongono un' ordinazione
        *  e gli elementi che vengono mostrati sul tablet del cuoco. Gli elementi che sono mostrati a schermo sono identificati
        *  dall'id dell'ordinazione, ad ogni evasione del singolo piatto si marca il corrispondente nella lista di tipo stato
        *  dellOrdinazione come evaso = true e si fa un ciclo che controlla se tutti gli ordini sono stati evasi. Se questo avviene
        *  si marca come evasa l'intera ordinazione e la si aggiorna nel db. A questo punto possono essere recuperate tutte le ordinazioni
        *  non evase quando questa activity viene caricata.
        *
        * */

    }

    public void setOrdinazioniSospese(List<Ordinazione> ordinazioniSospese){
        this.ordinazioniSospese = ordinazioniSospese;
        initializeComponents();
    }

   /* private List<Portata> generaOrdinazioniSospese(List<Ordinazione> ordinazioneList){

    }*/


    public void initializeComponents() {
        recyclerViewOrdinazioniCorrenti = findViewById(R.id.recyclerViewOrdiniDaEvadereStatoOrdinazioni);
        recyclerViewOrdinazioniPrenotate = findViewById(R.id.recyclerViewOrdiniPrenotatiStatoOrdinazioni);
        recyclerViewOrdinazioniEvase = findViewById(R.id.recycleViewOrdiniEvasiStatoOrdinazioni);
        setDatiRecycleViewOrdinazioniCorrenti(recyclerViewOrdinazioniCorrenti, generaOrdinazioniSospese(ordinazioniSospese));

        //Reecuperare i dati e usare setDatiRecycleView per impostarle

    }

    //gli che vengono presi mentre sono in coda

    /* 1 aggiorna l'elemento della lista statoDellOrdinazione
     *
     *
     *
     */
    public void setDatiRecycleViewOrdinazioniCorrenti(RecyclerView recyclerView, List<Ordinazione> ordinazione){
        adapterCorrenti = new RecycleViewAdapterOrdinazioniCorrenti(this, this, ordinazione);
        recyclerView.setAdapter(adapterCorrenti);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void setDatiRecycleViewOrdinazioniPrenotate(RecyclerView recyclerView, List<Portata> ordinazione){
    /*    RecycleViewAdapterOrdinazioniPrenotate adapter = new RecycleViewAdapterOrdinazioniPrenotate(this, this, ordinazione);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager); */
    }

    public void setDatiRecycleViewOrdinazioniEvase(RecyclerView recyclerView, List<Portata> ordinazione){
        RecycleViewAdapterOrdinazioniEvase adapter = new RecycleViewAdapterOrdinazioniEvase(this, this, ordinazione);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onItemClickOrdinazioniCorrenti(int position) {

    }

    @Override
    public void onGreenButtonClickOrdinazioniCorrenti(int position) {


    }

    @Override
    public void onRedButtonClickOrdinazioniCorrenti(int position) {

    }

    @Override
    public void onItemClickOrdinazioniEvase(int position) {

    }

    @Override
    public void onItemClickOrdinazioniPrenotate(int position) {

    }

    @Override
    public void onGreenButtonClickOrdinazioniPrenotate(int position) {

    }

    @Override
    public void onRedButtonClickOrdinazioniPrenotate(int position) {

    }
}





