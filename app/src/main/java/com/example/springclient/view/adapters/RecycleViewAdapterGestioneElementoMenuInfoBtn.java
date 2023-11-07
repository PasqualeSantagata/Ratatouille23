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

public class RecycleViewAdapterGestioneElementoMenuInfoBtn extends RecyclerView.Adapter<RecycleViewAdapterGestioneElementoMenuInfoBtn.MyViewHolder>{

    IRecycleViewElementoMenu recycleViewElementoMenuInterface;
    Context context;
    List<ElementoMenu> listaElementiMenu;

    public RecycleViewAdapterGestioneElementoMenuInfoBtn(Context context, List<ElementoMenu> listaElementiMenu, IRecycleViewElementoMenu recycleViewElementoMenuInterface) {
        this.context = context;
        this.listaElementiMenu = listaElementiMenu;
        this.recycleViewElementoMenuInterface = recycleViewElementoMenuInterface;
    }

    @NonNull
    @Override
    public RecycleViewAdapterGestioneElementoMenuInfoBtn.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recycler_view_elementi_menu_ic_info, parent,false);

        return new RecycleViewAdapterGestioneElementoMenuInfoBtn.MyViewHolder(view, recycleViewElementoMenuInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textViewNome.setText(listaElementiMenu.get(position).getNome());
        holder.textViewPrezzo.setText(listaElementiMenu.get(position).getPrezzo().toString());
    }

    @Override
    public int getItemCount() {
        return listaElementiMenu.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewNome;
        TextView textViewPrezzo;
        ImageView imageViewInfo;

        public MyViewHolder(@NonNull View itemView, IRecycleViewElementoMenu recycleViewElementoMenuInterface) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.textViewNomeInfo);
            imageViewInfo = itemView.findViewById(R.id.InfoElementoImageView);
            textViewPrezzo = itemView.findViewById(R.id.textViewPrezzoRecycleViewDeleteBtn);


            itemView.setOnClickListener(view -> {
                if(recycleViewElementoMenuInterface != null){
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION){
                        recycleViewElementoMenuInterface.onItemClickRecyclerViewPortata(pos);
                    }
                }
            });
            imageViewInfo.setOnClickListener(view -> {
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
