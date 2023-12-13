package com.example.springclient.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.model.entity.Portata;

import java.util.List;

public class RecycleViewAdapterPortata extends RecyclerView.Adapter<RecycleViewAdapterPortata.MyViewHolder> {
    IRecycleViewEventi recycleViewEventi;
    Context context;
    List<Portata> listaElementiMenu;

    public RecycleViewAdapterPortata(Context context, List<Portata> listaElementiMenu, IRecycleViewEventi recycleViewEventi) {
        this.context = context;
        this.listaElementiMenu = listaElementiMenu;
        this.recycleViewEventi = recycleViewEventi;
    }

    @NonNull
    @Override
    public RecycleViewAdapterPortata.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recycle_view_elementi_menu, parent,false);

        return new RecycleViewAdapterPortata.MyViewHolder(view, recycleViewEventi);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textViewNome.setText(listaElementiMenu.get(position).getElementoMenu().getNome());
        holder.textViewPrezzo.setText(listaElementiMenu.get(position).getElementoMenu().getPrezzo().toString() + "€");

    }

    @Override
    public int getItemCount() {
        return listaElementiMenu.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewNome;
        TextView textViewPrezzo;

        public MyViewHolder(@NonNull View itemView, IRecycleViewEventi recycleViewElementoMenuInterface) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.textViewNomeRecycleViewDeleteBtn);
            textViewPrezzo = itemView.findViewById(R.id.textViewPrezzoElemMenu);

            itemView.setOnClickListener(view -> {
                if(recycleViewElementoMenuInterface != null){
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION){
                        recycleViewElementoMenuInterface.onItemClickRecyclerView(pos);
                    }
                }
            });

        }
    }


}
