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

public class RecycleViewAdapterRiepilogoOrdinazione extends RecyclerView.Adapter<RecycleViewAdapterRiepilogoOrdinazione.MyViewHolder>{
    IRecycleViewElementoMenu recycleViewElementoMenuInterface;
    Context context;
    List<Portata> listaElementiMenu;

    public RecycleViewAdapterRiepilogoOrdinazione(IRecycleViewElementoMenu recycleViewElementoMenuInterface, Context context, List<Portata> listaElementiMenu) {
        this.recycleViewElementoMenuInterface = recycleViewElementoMenuInterface;
        this.context = context;
        this.listaElementiMenu = listaElementiMenu;
    }

    @NonNull
    @Override
    public RecycleViewAdapterRiepilogoOrdinazione.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recycle_view_elemento_menu_riepilogo_ordinazione, parent,false);

        return new RecycleViewAdapterRiepilogoOrdinazione.MyViewHolder(view, recycleViewElementoMenuInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapterRiepilogoOrdinazione.MyViewHolder holder, int position) {
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
            textViewNome = itemView.findViewById(R.id.textViewNomePiattoRiepilogoOrdinazione);
            imageViewInfo = itemView.findViewById(R.id.imageViewInfoElemRiepilogoOrdinazione);

            imageViewInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recycleViewElementoMenuInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            recycleViewElementoMenuInterface.onItemClickRecyclerViewPortata(pos);
                        }
                    }
                }
            });

        }
    }

}
