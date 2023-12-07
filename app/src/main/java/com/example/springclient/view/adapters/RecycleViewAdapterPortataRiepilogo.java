package com.example.springclient.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.entity.Portata;

import java.util.List;

public class RecycleViewAdapterPortataRiepilogo extends RecyclerView.Adapter<RecycleViewAdapterPortataRiepilogo.MyViewHolder>{

    private final IRecycleViewEventi iRecycleViewEventi;
    Context context;
    List<Portata> listaElementiMenu;

    public RecycleViewAdapterPortataRiepilogo(IRecycleViewEventi iRecycleViewEventi, Context context, List<Portata> listaElementiMenu) {
        this.iRecycleViewEventi = iRecycleViewEventi;
        this.context = context;
        this.listaElementiMenu = listaElementiMenu;
    }

    @NonNull
    @Override
    public RecycleViewAdapterPortataRiepilogo.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recycle_view_elementi_menu_delete_btn, parent,false);

        return new RecycleViewAdapterPortataRiepilogo.MyViewHolder(view, iRecycleViewEventi);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapterPortataRiepilogo.MyViewHolder holder, int position) {
        holder.textViewNome.setText(listaElementiMenu.get(position).getElementoMenu().getNome());
        holder.textViewPrezzo.setText(listaElementiMenu.get(position).getElementoMenu().getPrezzo().toString() + "€");

        //Controllo su nota
        String nota = listaElementiMenu.get(position).getBreveNota();
        if(nota != null && !nota.equals(""))
            holder.textViewBreveNota.setText(listaElementiMenu.get(position).getBreveNota());

    }

    @Override
    public int getItemCount() {
        return listaElementiMenu.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewNome;
        ImageView buttonDelete;
        TextView textViewPrezzo;
        TextView textViewBreveNota;

        public MyViewHolder(@NonNull View itemView, IRecycleViewEventi iRecycleViewEventi) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.textViewNomeRecycleViewDeleteBtn);
            buttonDelete = itemView.findViewById(R.id.cancellaElementoImageViewRecycleViewDeleteBtn);
            textViewPrezzo = itemView.findViewById(R.id.textViewPrezzoRecycleViewDeleteBtn);
            textViewBreveNota = itemView.findViewById(R.id.textViewRecycleViewBreveNotaRiepilogo);

            itemView.setOnClickListener(view -> {
                if(iRecycleViewEventi != null){
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION){
                        iRecycleViewEventi.onItemClickRecyclerView(pos);
                    }
                }
            });
            buttonDelete.setOnClickListener(view -> {
                if(iRecycleViewEventi != null){
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION){
                        iRecycleViewEventi.onButtonEventRecyclerView(pos);

                    }
                }
            });

        }
    }
    
}
