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

public class RecycleViewAdapterRiepilogoOrdinazioneDeleteBtn extends RecyclerView.Adapter<RecycleViewAdapterRiepilogoOrdinazioneDeleteBtn.MyViewHolder>{

    private final IRecycleViewElementoMenu recycleViewElementoMenuInterface;
    Context context;
    List<Portata> listaElementiMenu;

    public RecycleViewAdapterRiepilogoOrdinazioneDeleteBtn(IRecycleViewElementoMenu recycleViewElementoMenuInterface, Context context, List<Portata> listaElementiMenu) {
        this.recycleViewElementoMenuInterface = recycleViewElementoMenuInterface;
        this.context = context;
        this.listaElementiMenu = listaElementiMenu;
    }

    @NonNull
    @Override
    public RecycleViewAdapterRiepilogoOrdinazioneDeleteBtn.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recycle_view_elementi_menu_delete_btn, parent,false);

        return new RecycleViewAdapterRiepilogoOrdinazioneDeleteBtn.MyViewHolder(view, recycleViewElementoMenuInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapterRiepilogoOrdinazioneDeleteBtn.MyViewHolder holder, int position) {
        holder.textViewNome.setText(listaElementiMenu.get(position).getElementoMenu().getNome());
        //TODO input type dell'xml è number ma qua è passata la string, dovrebbe workare
        holder.textViewPrezzo.setText(listaElementiMenu.get(position).getElementoMenu().getPrezzo().toString());

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
        ImageView imageViewInfo;
        TextView textViewPrezzo;
        TextView textViewBreveNota;

        public MyViewHolder(@NonNull View itemView, IRecycleViewElementoMenu recycleViewElementoMenuInterface) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.textViewNomeRecycleViewDeleteBtn);
            imageViewInfo = itemView.findViewById(R.id.cancellaElementoImageViewRecycleViewDeleteBtn);
            textViewPrezzo = itemView.findViewById(R.id.textViewPrezzoRecycleViewDeleteBtn);
            textViewBreveNota = itemView.findViewById(R.id.textViewRecycleViewBreveNotaRiepilogo);

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
