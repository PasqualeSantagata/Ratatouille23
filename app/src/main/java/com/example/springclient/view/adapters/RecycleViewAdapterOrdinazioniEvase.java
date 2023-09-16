package com.example.springclient.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.utils.Portata;
import java.util.List;

public class RecycleViewAdapterOrdinazioniEvase  extends RecyclerView.Adapter<RecycleViewAdapterOrdinazioniEvase.MyViewHolder>{

    private final IRecycleViewOrdinazioniEvase iRecycleViewOrdinazioniEvase;
    Context context;
    List<Portata> ordinazioniEvase;

    public RecycleViewAdapterOrdinazioniEvase(IRecycleViewOrdinazioniEvase iRecycleViewOrdinazioniEvase, Context context, List<Portata> ordinazioniEvase) {
        this.iRecycleViewOrdinazioniEvase = iRecycleViewOrdinazioniEvase;
        this.context = context;
        this.ordinazioniEvase = ordinazioniEvase;
    }

    @NonNull
    @Override
    public RecycleViewAdapterOrdinazioniEvase.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recycle_view_stato_ordinazioni_evase, parent,false);

        return new RecycleViewAdapterOrdinazioniEvase.MyViewHolder(view, iRecycleViewOrdinazioniEvase);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapterOrdinazioniEvase.MyViewHolder holder, int position) {
        holder.textViewNomePiatto.setText(ordinazioniEvase.get(position).getElementoMenu().getNome());
        holder.textViewNota.setText(ordinazioniEvase.get(position).getOrdinazione().getBreveNota());
        holder.textViewTavolo.setText(ordinazioniEvase.get(position).getOrdinazione().getTavolo());
        holder.textViewSala.setText(ordinazioniEvase.get(position).getOrdinazione().getSala());
        holder.textViewTempo.setText(ordinazioniEvase.get(position).getElementoMenu().getTempoPreparazione());

    }

    @Override
    public int getItemCount() {
        return ordinazioniEvase.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTavolo;
        TextView textViewSala;
        TextView textViewNomePiatto;
        TextView textViewTempo;
        TextView textViewNota;

        public MyViewHolder(@NonNull View itemView, IRecycleViewOrdinazioniEvase iRecycleViewOrdinazioniEvase) {
            super(itemView);
            textViewNomePiatto = itemView.findViewById(R.id.textViewNomePiattoRecycleViewOrdinazioniEvase);
            textViewNota = itemView.findViewById(R.id.textViewBreveNotaRecycleViewOrdinazioniEvase);
            textViewSala = itemView.findViewById(R.id.textViewSalaRecycleViewOrdinazioniEvase);
            textViewTavolo = itemView.findViewById(R.id.textViewTavoloRecycleViewOrdinazioniEvase);
            textViewTempo = itemView.findViewById(R.id.textViewTempoRecycleViewOrdinazioniEvase);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (iRecycleViewOrdinazioniEvase != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            iRecycleViewOrdinazioniEvase.onItemClickOrdinazioniEvase(pos);
                        }
                    }
                }
            });

        }
    }
}
