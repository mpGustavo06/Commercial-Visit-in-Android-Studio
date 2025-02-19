package com.example.visitacomercial.Control;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.room.Room;
import com.example.visitacomercial.Database.AppDatabase;
import com.example.visitacomercial.Models.Cidade;
import com.example.visitacomercial.Models.Cliente;

import java.util.ArrayList;

public class ClienteDAO implements LifecycleOwner
{
    private AppDatabase database;
    private ClienteAPIService clienteAPIService;
    private Lifecycle lifecycle;

    private ArrayList<Cliente> clientesList = null;

    public ClienteDAO(Context context, Lifecycle lifecycle)
    {
        database = Room.databaseBuilder(context, AppDatabase.class, "app_database").build();
        clienteAPIService = database.clienteService();

        this.lifecycle = lifecycle;

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean inserirCliente(Cliente newCliente)
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
            return true;
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            Log.d("INSERT.CLI.ERROR", "ERRO: "+e.getMessage());
            return false;
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
    public void alterarCliente(Cliente clienteUpdate)
    {
        try
        {
            new Thread(() -> {
                clienteAPIService.update(clienteUpdate);

                Log.d("UPDATE.CLI.SUCCESS", "Cliente alterado(a) com sucesso. " + clienteUpdate);
            }).start();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            Log.d("UPDATE.CLI.ERROR", "ERRO: "+e.getMessage());
        }
    }

    //--------------------------------------------------------------------------------------------//
    public ArrayList<Cliente> getClientes()
    {
        Log.d("GET.CLIENTES", "INICIO");

        clienteAPIService.getClients().observe(this, clientes -> {
            if (clientes != null)
            {
                clientesList = new ArrayList<>(clientes);
                Log.d("GET.CLIENTES.SUCCESS", "Clientes obtidos com sucesso.");
            }
            else
            {
                Log.d("GET.CLIENTES.ERROR", "Erro ao obter clientes.");
                return;
            }
        });

        Log.d("GET.CLIENTES", "FIM: "+clientesList);
        return clientesList;
    }

    //--------------------------------------------------------------------------------------------//
    public ArrayList<Cliente> getClientesPorCidade(Long ibge)
    {
        Log.d("GET.CLIENTES.IBGE", "INICIO");

        clienteAPIService.getClientPerCity(ibge).observe(this, clientes -> {
            if (clientes != null)
            {
                clientesList = new ArrayList<>(clientes);
                Log.d("GET.CLIENTES.SUCCESS", "Clientes da cidade obtidos com sucesso.");
            }
            else
            {
                Log.d("GET.CLIENTES.ERROR", "Erro ao obter clientes da cidade.");
                return;
            }
        });

        Log.d("GET.CLIENTES.IBGE", "FIM: "+clientesList);
        return clientesList;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycle;
    }
}
