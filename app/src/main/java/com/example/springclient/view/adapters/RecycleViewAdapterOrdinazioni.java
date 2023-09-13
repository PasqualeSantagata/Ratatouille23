package com.example.springclient.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.utils.StatoDellaOrdinazione;

import java.util.List;

public class RecycleViewAdapterOrdinazioni extends RecyclerView.Adapter<RecycleViewAdapterOrdinazioni.MyViewHolder> {
    private final IRecycleViewOrdinazione iRecycleViewOrdinazione;
    Context context;
    List<StatoDellaOrdinazione> ordinazioni;

    public RecycleViewAdapterOrdinazioni(IRecycleViewOrdinazione iRecycleViewOrdinazione, Context context, List<StatoDellaOrdinazione> ordinazioni) {
        this.iRecycleViewOrdinazione = iRecycleViewOrdinazione;
        this.context = context;
        this.ordinazioni = ordinazioni;

    }

    @NonNull
    @Override
    public RecycleViewAdapterOrdinazioni.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recycle_view_stato_ordinazioni, parent,false);

        return new RecycleViewAdapterOrdinazioni.MyViewHolder(view, iRecycleViewOrdinazione);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapterOrdinazioni.MyViewHolder holder, int position) {
        holder.textViewSala.setText(ordinazioni.get(position).getOrdinazione().getSala());
        holder.textViewNota.setText(ordinazioni.get(position).getOrdinazione().getBreveNota());
        holder.textViewTavolo.setText(ordinazioni.get(position).getOrdinazione().getTavolo());
        holder.textViewTempo.setText(ordinazioni.get(position).getElementoMenu().getTempoPreparazione());
        holder.textViewNomePiatto.setText(ordinazioni.get(position).getElementoMenu().getNome());

        //TODO eventi button

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
        Button buttonIndietro;
        Button buttonAvanti;


        public MyViewHolder(@NonNull View itemView, IRecycleViewOrdinazione iRecycleViewOrdinazione) {
            super(itemView);
            buttonIndietro = itemView.findViewById(R.id.buttonAvantiRecycleViewStatoOrdinazioni);
            buttonAvanti = itemView.findViewById(R.id.buttonIndietroRecycleViewStatoOrdinazioni);
            textViewNomePiatto = itemView.findViewById(R.id.textViewNumeroPiattiRecycleViewStatoOrdinazioni);
            textViewTempo = itemView.findViewById(R.id.textViewTempoRecycleViewStatoOrdinazioni);
            textViewNota = itemView.findViewById(R.id.textViewNotaRecycleViewStatoOrdinazioni);
            textViewTavolo = itemView.findViewById(R.id.textViewTavoloRecycleViewStatoOrdinazioni);
            textViewSala = itemView.findViewById(R.id.textViewSalaRecycleViewStatoOrdinazioni);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (iRecycleViewOrdinazione != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            iRecycleViewOrdinazione.onItemClick(pos);
                        }
                    }
                }
            });



        }
    }
}
