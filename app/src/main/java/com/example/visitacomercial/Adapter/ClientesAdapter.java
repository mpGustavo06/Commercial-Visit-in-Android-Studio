package com.example.visitacomercial.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.visitacomercial.Models.Cliente;
import com.example.visitacomercial.R;

import java.util.ArrayList;
import java.util.List;

public class ClientesAdapter extends ArrayAdapter<Cliente>
{
    private Context context;
    private List<Cliente> clientes;

    public ClientesAdapter(Context context, ArrayList<Cliente> clientesList)
    {
        super(context, 0, clientesList);
        this.context = context;
        this.clientes = clientesList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cliente, parent, false);
        }

        Cliente currentItem = clientes.get(position);

        TextView cliente = convertView.findViewById(R.id.tvClienteNome);
        TextView ultimaVisita = convertView.findViewById(R.id.tvClienteUltimaVisita);

        cliente.setText(currentItem.getFantasyName());
        ultimaVisita.setText((CharSequence) currentItem.getLastVisit());

        Log.d("CDD.ADP.ITEM.END", "CIDADE: "+currentItem.toString());

        return convertView;
    }
}