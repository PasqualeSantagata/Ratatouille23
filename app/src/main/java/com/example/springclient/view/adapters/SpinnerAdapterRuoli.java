package com.example.springclient.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.springclient.R;

import java.util.List;

public class SpinnerAdapterRuoli extends BaseAdapter {

    Context context;
    List<String> ruoli;

    public SpinnerAdapterRuoli(Context context, List<String> ruoli) {
        this.context = context;
        this.ruoli = ruoli;
    }

    @Override
    public int getCount() {
        if(ruoli.isEmpty())
            return 0;
        else
            return ruoli.size();
    }

    @Override
    public Object getItem(int i) {
        return ruoli.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View localView = LayoutInflater.from(context).inflate(R.layout.item_spinner_ruolo, viewGroup, false);

        TextView textView = localView.findViewById(R.id.item_ruolo);

        textView.setText(ruoli.get(i).toString());

        return localView;
    }
}
