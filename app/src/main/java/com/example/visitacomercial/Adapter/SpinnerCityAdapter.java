package com.example.visitacomercial.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.visitacomercial.Models.Cidade;
import com.example.visitacomercial.R;

import java.util.List;

public class SpinnerCityAdapter extends ArrayAdapter<Cidade>
{
    private Context context;
    private List<Cidade> cities;

    public SpinnerCityAdapter(@NonNull Context context, List<Cidade> cities)
    {
        super(context, 0, cities);
        this.context = context;
        this.cities = cities;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner_city, parent, false);
        }

        Cidade currentItem = cities.get(position);

        TextView cityView = convertView.findViewById(R.id.tvSpinnerCidade);
        TextView dddView = convertView.findViewById(R.id.tvSpinnerDdd);

        String city = currentItem.getName();
        String ddd = currentItem.getDdd();

        cityView.setText( city );
        dddView.setText( "( "+ddd+" )" );

        Log.d("SPINNER.CDD.ITEM.END", "CIDADE: "+currentItem.toString());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner_city, parent, false);
        }

        TextView cityView = convertView.findViewById(R.id.tvSpinnerCidade);
        TextView dddView = convertView.findViewById(R.id.tvSpinnerDdd);

        cityView.setText(cities.get(position).getName());
        dddView.setText("( "+cities.get(position).getDdd()+" )");

        return convertView;
    }

    public void updateData(List<Cidade> newCities)
    {
        cities.clear();
        cities.addAll(newCities);
        notifyDataSetChanged();
    }
}
