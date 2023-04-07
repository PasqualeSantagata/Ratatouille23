package com.example.springclient.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.springclient.R;

import java.util.List;

public class SpinnerAdapterCategorie extends BaseAdapter {

    Context context;
    List<String> categorie;

    public SpinnerAdapterCategorie(Context context, List<String> categorie) {
        this.context = context;
        this.categorie = categorie;
    }

    @Override
    public int getCount() {
        if(categorie.isEmpty())
            return 0;
        else
            return categorie.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View localView = LayoutInflater.from(context).inflate(R.layout.item_spinner_categorie, viewGroup, false);

        TextView textView = localView.findViewById(R.id.item_categoria);

        textView.setText(categorie.get(i).toString());

        return localView;
    }
}
