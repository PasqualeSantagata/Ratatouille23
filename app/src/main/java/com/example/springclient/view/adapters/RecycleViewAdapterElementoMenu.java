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

public class RecycleViewAdapterElementoMenu extends RecyclerView.Adapter<RecycleViewAdapterElementoMenu.MyViewHolder> {
    IRecycleViewEventi recycleViewEventi;
    Context context;
    List<Portata> listaElementiMenu;

    public RecycleViewAdapterElementoMenu(Context context, List<Portata> listaElementiMenu, IRecycleViewEventi recycleViewEventi) {
        this.context = context;
        this.listaElementiMenu = listaElementiMenu;
        this.recycleViewEventi = recycleViewEventi;
    }

    @NonNull
    @Override
    public RecycleViewAdapterElementoMenu.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recycle_view_elementi_menu, parent,false);

        return new RecycleViewAdapterElementoMenu.MyViewHolder(view, recycleViewEventi);
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
        ImageView imageViewInfo;

        public MyViewHolder(@NonNull View itemView, IRecycleViewEventi recycleViewElementoMenuInterface) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.textViewNomeRecycleViewDeleteBtn);
            textViewPrezzo = itemView.findViewById(R.id.textViewPrezzoElemMenu);
            imageViewInfo = itemView.findViewById(R.id.imageViewInfoRecycleView);

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
