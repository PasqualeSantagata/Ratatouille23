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
import com.example.springclient.entity.ElementoMenu;

import java.util.List;

public class RecycleViewAdapterGestioneElementoMenu extends RecyclerView.Adapter<RecycleViewAdapterGestioneElementoMenu.MyViewHolder> {
    IRecycleViewElementoMenu recycleViewElementoMenuInterface;
    Context context;
    List<ElementoMenu> listaElementiMenu;

    public RecycleViewAdapterGestioneElementoMenu(Context context, List<ElementoMenu> listaElementiMenu, IRecycleViewElementoMenu recycleViewElementoMenuInterface) {
        this.context = context;
        this.listaElementiMenu = listaElementiMenu;
        this.recycleViewElementoMenuInterface = recycleViewElementoMenuInterface;
    }

    @NonNull
    @Override
    public RecycleViewAdapterGestioneElementoMenu.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recycle_view_elementi_menu2, parent,false);

        return new RecycleViewAdapterGestioneElementoMenu.MyViewHolder(view, recycleViewElementoMenuInterface);
    }
    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapterGestioneElementoMenu.MyViewHolder holder, int position) {
        holder.textViewNome.setText(listaElementiMenu.get(position).getNome());
    }

    @Override
    public int getItemCount() {
        return listaElementiMenu.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewNome;
        ImageView imageViewInfo;
        ImageView cancellaElementoImageView;

        public MyViewHolder(@NonNull View itemView, IRecycleViewElementoMenu recycleViewElementoMenuInterface) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.textViewNome);
            imageViewInfo = itemView.findViewById(R.id.imageViewInfoRecycleView);
            cancellaElementoImageView = itemView.findViewById(R.id.cancellaElementoImageView);


            itemView.setOnClickListener(view -> {
                if(recycleViewElementoMenuInterface != null){
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION){
                        recycleViewElementoMenuInterface.onItemClickRecyclerViewPortata(pos);
                    }
                }
            });
            cancellaElementoImageView.setOnClickListener(view -> {
                if(recycleViewElementoMenuInterface != null){
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION){
                        recycleViewElementoMenuInterface.onButtonDeleted(pos);
                    }
                }

            });

        }
    }



}
