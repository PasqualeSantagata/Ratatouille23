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
import com.example.springclient.model.entity.Categoria;

import java.util.List;

public class RecycleViewAdapterCategoria extends RecyclerView.Adapter<RecycleViewAdapterCategoria.MyViewHolder> {

    private final IRecycleViewEventi recycleViewEventi;
    Context context;
    List<Categoria> categoriaList;

    public RecycleViewAdapterCategoria(Context context, List<Categoria> categoriaList, IRecycleViewEventi recycleViewEventi) {
        this.context = context;
        this.categoriaList = categoriaList;
        this.recycleViewEventi = recycleViewEventi;
    }

    @NonNull
    @Override
    public RecycleViewAdapterCategoria.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recycle_view_categoria, parent, false);

        return new RecycleViewAdapterCategoria.MyViewHolder(view, recycleViewEventi);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapterCategoria.MyViewHolder holder, int position) {

        if (categoriaList.get(position).getNome() == null || categoriaList.get(position).getNome().isEmpty()) {
            holder.textViewNomeCategoria.setVisibility(View.INVISIBLE);
        } else {
            holder.textViewNomeCategoria.setText(categoriaList.get(position).getNome());
        }

        holder.imageViewCategoria.setImageBitmap(categoriaList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return categoriaList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewNomeCategoria;
        ImageView imageViewCategoria;

        public MyViewHolder(@NonNull View itemView, IRecycleViewEventi recycleViewEventi) {
            super(itemView);
            textViewNomeCategoria = itemView.findViewById(R.id.textViewRecycleViewNomeCategoria);
            imageViewCategoria = itemView.findViewById(R.id.imageViewRecycleViewCategoria);

            itemView.setOnClickListener(view -> {
                if (recycleViewEventi != null) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        recycleViewEventi.onItemClickRecyclerView(pos);
                    }
                }

            });


        }
    }

}

