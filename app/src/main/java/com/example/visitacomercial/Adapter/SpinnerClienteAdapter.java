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

import com.example.visitacomercial.Models.Cliente;
import com.example.visitacomercial.R;

import java.util.List;

public class SpinnerClienteAdapter extends ArrayAdapter<Cliente>
{
    private Context context;
    private List<Cliente> clientes;

    public SpinnerClienteAdapter(@NonNull Context context, List<Cliente> clientes)
    {
        super(context, 0, clientes);
        this.context = context;
        this.clientes = clientes;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner_cliente, parent, false);
        }

        Cliente currentItem = clientes.get(position);

        TextView tvNomeFantasia = convertView.findViewById(R.id.tvSpinnerNomeFantasia);
        TextView tvCnpj = convertView.findViewById(R.id.tvSpinnerCnpj);

        String nameFantasia = currentItem.getFantasyName();
        String cnpj = currentItem.getCnpj();

        tvNomeFantasia.setText( nameFantasia );
        tvCnpj.setText( cnpj );

        Log.d("SPN.CLIENTE.ITEM.END", "CLIENTE: "+currentItem.toString());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner_cliente, parent, false);
        }

        TextView tvNomeFantasia = convertView.findViewById(R.id.tvSpinnerNomeFantasia);
        TextView tvCnpj = convertView.findViewById(R.id.tvSpinnerCnpj);

        tvNomeFantasia.setText( clientes.get(position).getFantasyName() );
        tvCnpj.setText( clientes.get(position).getCnpj() );

        return convertView;
    }
}
