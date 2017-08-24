package com.example.sergey.counterpartycrud.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sergey.counterpartycrud.R;
import com.example.sergey.counterpartycrud.entities.Counterparty;

import java.util.ArrayList;

public class CounterpartiesListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<Counterparty> counterparties;

    public CounterpartiesListAdapter(Context context, ArrayList<Counterparty> counterparties) {
        this.counterparties = counterparties;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return counterparties.size();
    }

    @Override
    public Counterparty getItem(int position) {
        return counterparties.get(position);
    }

    @Override
    public long getItemId(int position) {
        return counterparties.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View thisView = view;
        if(thisView == null) {
            thisView = inflater.inflate(R.layout.layout_counterparties_list_item, parent, false);
        }

        Counterparty counterparty = getItem(position);

        if (!counterparty.getPhoto().equals(""))
            ((ImageView) thisView.findViewById(R.id.photoImageViewFromList)).
                setImageBitmap(BitmapFactory.decodeFile(counterparty.getPhoto()));

        ((TextView) thisView.findViewById(R.id.emailTextViewFromList)).setText(counterparty.getEmail());
        ((TextView) thisView.findViewById(R.id.nameTextViewFromList)).setText(counterparty.getName());
        ((TextView) thisView.findViewById(R.id.phoneTextViewFromList)).setText(counterparty.getPhone());

        return thisView;
    }

    public void setCounterparties(ArrayList<Counterparty> counterparties) {
        this.counterparties = counterparties;
    }
}
