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

public class RecycleViewAdapterElementoMenu extends RecyclerView.Adapter<RecycleViewAdapterElementoMenu.MyViewHolder> {
    Context context;
    List<ElementoMenu> listaElementiMenu;

    public RecycleViewAdapterElementoMenu(Context context, List<ElementoMenu> listaElementiMenu) {
        this.context = context;
        this.listaElementiMenu = listaElementiMenu;
    }

    @NonNull
    @Override
    public RecycleViewAdapterElementoMenu.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recycle_view_elementi_menu, parent,false);

        return new RecycleViewAdapterElementoMenu.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textViewNome.setText(listaElementiMenu.get(position).getNome());
        holder.textViewTempoPreparazione.setText(listaElementiMenu.get(position).getTempoPreparazione());
        holder.textViewQuantita.setText(String.valueOf(listaElementiMenu.get(position).getQuantita()));
    }

    @Override
    public int getItemCount() {
        return listaElementiMenu.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewNome;
        TextView textViewTempoPreparazione;
        TextView textViewQuantita;
        ImageView imageViewInfo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.textViewNome);
            textViewTempoPreparazione = itemView.findViewById(R.id.textViewTempoPreparazione);
            textViewQuantita = itemView.findViewById(R.id.textViewQuantita);
            imageViewInfo = itemView.findViewById(R.id.imageViewInfoRecycleView);
        }
    }


}
