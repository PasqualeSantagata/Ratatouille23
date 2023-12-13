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
import com.example.springclient.model.entity.ElementoMenu;

import java.util.List;

public class RecycleViewAdapterElementoMenu extends RecyclerView.Adapter<RecycleViewAdapterElementoMenu.MyViewHolder> {
    IRecycleViewEventi recycleViewEventi;
    Context context;
    List<ElementoMenu> listaElementiMenu;

    public RecycleViewAdapterElementoMenu(Context context, List<ElementoMenu> listaElementiMenu, IRecycleViewEventi recycleViewEventi) {
        this.context = context;
        this.listaElementiMenu = listaElementiMenu;
        this.recycleViewEventi = recycleViewEventi;
    }

    @NonNull
    @Override
    public RecycleViewAdapterElementoMenu.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recycle_view_elementi_menu_delete_btn, parent,false);

        return new RecycleViewAdapterElementoMenu.MyViewHolder(view, recycleViewEventi);
    }
    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapterElementoMenu.MyViewHolder holder, int position) {
        holder.textViewNome.setText(listaElementiMenu.get(position).getNome());
        holder.textViewPrezzo.setText(listaElementiMenu.get(position).getPrezzo().toString() + "€");
    }

    @Override
    public int getItemCount() {
        return listaElementiMenu.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewNome;
        TextView textViewPrezzo;
        ImageView cancellaElementoImageView;

        public MyViewHolder(@NonNull View itemView, IRecycleViewEventi iRecycleViewEventi) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.textViewNomeRecycleViewDeleteBtn);
            cancellaElementoImageView = itemView.findViewById(R.id.cancellaElementoImageViewRecycleViewDeleteBtn);
            textViewPrezzo = itemView.findViewById(R.id.textViewPrezzoRecycleViewDeleteBtn);


            itemView.setOnClickListener(view -> {
                if(iRecycleViewEventi != null){
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION){
                        iRecycleViewEventi.onItemClickRecyclerView(pos);
                    }
                }
            });
            cancellaElementoImageView.setOnClickListener(view -> {
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
