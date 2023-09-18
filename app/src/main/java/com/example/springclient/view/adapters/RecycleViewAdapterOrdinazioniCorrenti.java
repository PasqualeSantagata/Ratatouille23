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
import com.example.springclient.entity.Ordinazione;
import com.example.springclient.entity.Portata;

import java.util.List;

public class RecycleViewAdapterOrdinazioniCorrenti extends RecyclerView.Adapter<RecycleViewAdapterOrdinazioniCorrenti.MyViewHolder> {
    private final IRecycleViewOrdinazioniCorrenti iRecycleViewOrdinazioniCorrenti;
    Context context;
    List<Ordinazione> ordinazioni;

    public RecycleViewAdapterOrdinazioniCorrenti(IRecycleViewOrdinazioniCorrenti iRecycleViewOrdinazioniCorrenti, Context context, List<Ordinazione> ordinazioni) {
        this.iRecycleViewOrdinazioniCorrenti = iRecycleViewOrdinazioniCorrenti;
        this.context = context;
        this.ordinazioni = ordinazioni;

    }

    @NonNull
    @Override
    public RecycleViewAdapterOrdinazioniCorrenti.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recycle_view_stato_ordinazioni_correnti, parent,false);

        return new RecycleViewAdapterOrdinazioniCorrenti.MyViewHolder(view, iRecycleViewOrdinazioniCorrenti);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapterOrdinazioniCorrenti.MyViewHolder holder, int position) {
        for(Portata p: ordinazioni.get(position).getElementiOrdinati()){
            holder.textViewSala.setText(ordinazioni.get(position).getSala().toString());
            holder.textViewNota.setText(ordinazioni.get(position).getBreveNota());
            holder.textViewTavolo.setText(ordinazioni.get(position).getTavolo().toString());
            holder.textViewTempo.setText(p.getElementoMenu().getTempoPreparazione());
            holder.textViewNomePiatto.setText(p.getElementoMenu().getNome());

            holder.buttonAvanti.setOnClickListener(view -> {
                if (iRecycleViewOrdinazioniCorrenti != null){
                    if(position != RecyclerView.NO_POSITION){
                        iRecycleViewOrdinazioniCorrenti.onGreenButtonClickOrdinazioniCorrenti(position);
                    }
                }
            });
            holder.buttonIndietro.setOnClickListener(view -> {
                if (iRecycleViewOrdinazioniCorrenti != null){
                    if(position != RecyclerView.NO_POSITION){
                        iRecycleViewOrdinazioniCorrenti.onRedButtonClickOrdinazioniCorrenti(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return ordinazioni.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTavolo;
        TextView textViewSala;
        TextView textViewNomePiatto;
        TextView textViewTempo;
        TextView textViewNota;
        ImageButton buttonIndietro;
        ImageButton buttonAvanti;


        public MyViewHolder(@NonNull View itemView, IRecycleViewOrdinazioniCorrenti iRecycleViewOrdinazioniCorrenti) {
            super(itemView);
            buttonIndietro = itemView.findViewById(R.id.buttonIndietroRecycleViewStatoOrdinazioni);
            buttonAvanti = itemView.findViewById(R.id.buttonAvantiRecycleViewStatoOrdinazioni);
            textViewNomePiatto = itemView.findViewById(R.id.textViewNomePiattoRecycleViewStatoOrdinazioni);
            textViewTempo = itemView.findViewById(R.id.textViewTempoRecycleViewStatoOrdinazioni);
            textViewNota = itemView.findViewById(R.id.textViewNotaRecycleViewStatoOrdinazioni);
            textViewTavolo = itemView.findViewById(R.id.textViewTavoloRecycleViewStatoOrdinazioni);
            textViewSala = itemView.findViewById(R.id.textViewSalaRecycleViewStatoOrdinazioni);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (iRecycleViewOrdinazioniCorrenti != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            iRecycleViewOrdinazioniCorrenti.onItemClickOrdinazioniCorrenti(pos);
                        }
                    }
                }
            });



        }
    }
}
