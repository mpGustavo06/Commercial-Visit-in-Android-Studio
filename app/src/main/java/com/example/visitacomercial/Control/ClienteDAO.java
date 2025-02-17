package com.example.visitacomercial.Control;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.room.Room;

import com.example.visitacomercial.Database.AppDatabase;
import com.example.visitacomercial.Models.Cidade;
import com.example.visitacomercial.Models.Cliente;

public class ClienteDAO
{
    private AppDatabase database;
    private ClienteAPIService clienteAPIService;

    public ClienteDAO(Context context)
    {
        database = Room.databaseBuilder(context, AppDatabase.class, "app_database").build();
        clienteAPIService = database.clienteDAO();
    }

    public void inserirCliente(Cliente newCliente)
    {
        try
        {
            new Thread()
            {
                public void run()
                {
                    Looper.prepare();
                    clienteAPIService.insert(newCliente);
                    Looper.loop();
                }
            }.start();
            Log.d("INSERT.CLI.SUCCESS", "Cliente inserido com sucesso.");
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            Log.d("INSERT.CLI.ERROR", "ERRO: "+e.getMessage());
        }
    }

    //--------------------------------------------------------------------------------------------//
    public void deletarCliente(Cliente clienteDelete)
    {
        try
        {
            new Thread()
            {
                public void run()
                {
                    Looper.prepare();
                    clienteAPIService.delete(clienteDelete);
                    Looper.loop();
                }
            }.start();
            Log.d("DELETE.CLI.SUCCESS", "Cliente deletado com sucesso.");
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            Log.d("DELETE.CLI.ERROR", "ERRO: "+e.getMessage());
        }
    }

    //--------------------------------------------------------------------------------------------//
    public void alterarCidade(Cliente clienteUpdate)
    {
        try
        {
            new Thread()
            {
                public void run()
                {
                    Looper.prepare();
                    clienteAPIService.update(clienteUpdate);
                    Looper.loop();
                }
            }.start();
            Log.d("UPDATE.CLI.SUCCESS", "Cliente alterado(a) com sucesso.");
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            Log.d("UPDATE.CLI.ERROR", "ERRO: "+e.getMessage());
        }
    }
}
