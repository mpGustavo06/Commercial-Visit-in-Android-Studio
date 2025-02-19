package com.example.visitacomercial.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Color;

import com.example.visitacomercial.MainActivity;
import com.example.visitacomercial.Models.Cliente;
import com.example.visitacomercial.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ClientesAdapter extends ArrayAdapter<Cliente>
{
    private Context context;
    private List<Cliente> clientes;
    private int selectedPosition = -1;

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

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Cliente currentItem = clientes.get(position);

        Log.d("ITEM.ADAPTER.CLIENTE", "ITEM: "+currentItem.toString());

        TextView cliente = convertView.findViewById(R.id.tvClienteNome);
        TextView ultimaVisita = convertView.findViewById(R.id.tvClienteUltimaVisita);
        Button buttonCallCliente = convertView.findViewById(R.id.buttonCallCliente);
        ImageView imageViewIndicador = convertView.findViewById(R.id.imageViewIndicador);

        cliente.setText(currentItem.getFantasyName());

        if (currentItem.getLastVisit() == null || currentItem.getLastVisit().getTime() == 0)
        {
            ultimaVisita.setText("Nenhuma visita realizada.");
        }
        else
        {
            ultimaVisita.setText(formato.format(currentItem.getLastVisit()));
        }
        ultimaVisita.setText(formato.format(currentItem.getLastVisit()));

        if ( visitadoNosUltimos7Dias( currentItem.getLastVisit() ))
        {
            imageViewIndicador.setVisibility(View.VISIBLE);
        }
        else
        {
            imageViewIndicador.setVisibility(View.GONE);
        }

        buttonCallCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String telefone = currentItem.getPhone();
                Log.d("CDD.ADP.ITEM.TEL", "TELEFONE: "+telefone);

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + telefone));
                context.startActivity(intent);
            }
        });

        Log.d("CDD.ADP.ITEM.END", "CIDADE: "+currentItem.toString());

        return convertView;
    }

    private boolean visitadoNosUltimos7Dias(Date ultimaVisita)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date data7DiasAtras = calendar.getTime();

        return ultimaVisita != null && ultimaVisita.after(data7DiasAtras);
    }
}