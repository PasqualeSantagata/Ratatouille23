package com.example.springclient.view.statoOrdinazioni;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class HomeStatoOrdinazione extends AppCompatActivity {

    private RecyclerView recyclerViewOrdinazioniCorrenti;
    private RecyclerView recyclerViewOrdinazioniPrenotate;
    private RecyclerView recyclerViewOrdinazioniEvase;
    private StompClient stompClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_stato_ordinazioni);
        getSupportActionBar().setTitle("STATO DELLE ORDINAZIONI");
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






}
