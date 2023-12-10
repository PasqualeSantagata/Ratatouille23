package com.example.springclient.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.entity.Portata;

import java.util.List;

public class RecycleViewAdapterOrdinazioniPrenotate extends RecyclerView.Adapter<RecycleViewAdapterOrdinazioniPrenotate.MyViewHolder>{

    private final IRecycleViewOrdinazioniPrenotate iRecycleViewOrdinazioniPrenotate;
    Context context;
    List<Portata> ordinazioniPrenotate;


    public RecycleViewAdapterOrdinazioniPrenotate(IRecycleViewOrdinazioniPrenotate iRecycleViewOrdinazioniPrenotate, Context context, List<Portata> ordinazioniPrenotate) {
        this.iRecycleViewOrdinazioniPrenotate = iRecycleViewOrdinazioniPrenotate;
        this.context = context;
        this.ordinazioniPrenotate = ordinazioniPrenotate;
    }

    @NonNull
    @Override
    public RecycleViewAdapterOrdinazioniPrenotate.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recycle_view_stato_ordinazioni_prenotate, parent,false);

        return new RecycleViewAdapterOrdinazioniPrenotate.MyViewHolder(view, iRecycleViewOrdinazioniPrenotate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapterOrdinazioniPrenotate.MyViewHolder holder, int position) {
        //info ordinazione
        holder.textViewSala.setText(ordinazioniPrenotate.get(position).getOrdinazione().getSala().toString());
        holder.textViewTavolo.setText(ordinazioniPrenotate.get(position).getOrdinazione().getTavolo().toString());
        holder.textViewNumOrdinazione.setText(ordinazioniPrenotate.get(position).getOrdinazione().getId().toString());
        //info singoli piatti della stessa ordinazione
        holder.textViewTempo.setText(ordinazioniPrenotate.get(position).getOrdinazione().getOrarioOrdinazione());
        holder.textViewNomePiatto.setText(ordinazioniPrenotate.get(position).getElementoMenu().getNome());
        holder.textViewNota.setText(ordinazioniPrenotate.get(position).getBreveNota());
    }


    @Override
    public int getItemCount() {
        return ordinazioniPrenotate.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTavolo;
        TextView textViewSala;
        TextView textViewNomePiatto;
        TextView textViewTempo;
        TextView textViewNota;
        ImageButton buttonAvanti;
        TextView textViewNumOrdinazione;

        public MyViewHolder(@NonNull View itemView, IRecycleViewOrdinazioniPrenotate iRecycleViewOrdinazioniPrenotate) {
            super(itemView);
            textViewNomePiatto = itemView.findViewById(R.id.textViewNomePiattoRecycleViewStatoOrdinazioniPrenotate);
            textViewNota = itemView.findViewById(R.id.textViewNotaRecycleViewStatoOrdinazioniPrenotate);
            textViewSala = itemView.findViewById(R.id.textViewSalaRecycleViewOrdinazioniPrenotate);
            textViewTavolo = itemView.findViewById(R.id.textViewTavoloRecycleViewOrdinazioniPrenotate);
            textViewTempo = itemView.findViewById(R.id.textViewTempoRecycleViewOrdinazioniPrenotate);
            buttonAvanti = itemView.findViewById(R.id.buttonAvantiRecycleViewOrdinazioniPrenotate);
            textViewNumOrdinazione = itemView.findViewById(R.id.numeroOrdinazionePrenotata);

            itemView.setOnClickListener(view -> {
                if (iRecycleViewOrdinazioniPrenotate != null){
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION){
                        iRecycleViewOrdinazioniPrenotate.onItemClickOrdinazioniPrenotate(pos);
                    }
                }
            });

            buttonAvanti.setOnClickListener(view -> {
                if (iRecycleViewOrdinazioniPrenotate != null) {
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        iRecycleViewOrdinazioniPrenotate.onGreenButtonClickOrdinazioniPrenotate(getAdapterPosition());
                    }
                }
            });

        }
    }

}
