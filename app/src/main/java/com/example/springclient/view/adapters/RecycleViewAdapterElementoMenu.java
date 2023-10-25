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
    IRecycleViewElementoMenu recycleViewElementoMenuInterface;
    Context context;
    List<Portata> listaElementiMenu;

    public RecycleViewAdapterElementoMenu(Context context, List<Portata> listaElementiMenu, IRecycleViewElementoMenu recycleViewElementoMenuInterface) {
        this.context = context;
        this.listaElementiMenu = listaElementiMenu;
        this.recycleViewElementoMenuInterface = recycleViewElementoMenuInterface;
    }

    @NonNull
    @Override
    public RecycleViewAdapterElementoMenu.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recycle_view_elementi_menu, parent,false);

        return new RecycleViewAdapterElementoMenu.MyViewHolder(view, recycleViewElementoMenuInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textViewNome.setText(listaElementiMenu.get(position).getElementoMenu().getNome());

    }

    @Override
    public int getItemCount() {
        return listaElementiMenu.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewNome;

        ImageView imageViewInfo;

        public MyViewHolder(@NonNull View itemView, IRecycleViewElementoMenu recycleViewElementoMenuInterface) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.textViewNome);

            imageViewInfo = itemView.findViewById(R.id.imageViewInfoRecycleView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recycleViewElementoMenuInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            recycleViewElementoMenuInterface.onItemClick(pos);
                        }
                    }
                }
            });

        }
    }


}
