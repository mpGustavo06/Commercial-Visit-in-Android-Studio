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

import java.util.ArrayList;

public class CidadeDAO implements LifecycleOwner {
    private AppDatabase database;
    private CidadeAPIService cidadeAPIService;
    private Lifecycle lifecycle;
    private boolean cidadeExiste = true;

    private ArrayList<Cidade> cidadesList = null;

    public CidadeDAO(Context context, Lifecycle lifecycle)
    {
        database = Room.databaseBuilder(context, AppDatabase.class, "app_database").build();
        cidadeAPIService = database.cidadeService();

        this.lifecycle = lifecycle;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean inserirCidade(Cidade newCidade)
    {
        try
        {
            new Thread()
            {
                public void run()
                {
                    Looper.prepare();
                    cidadeAPIService.insert(newCidade);
                    Looper.loop();
                }
            }.start();
            Log.d("INSERT.CTY.SUCCESS", "Cidade inserida com sucesso.");
            return true;
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            Log.d("INSERT.CTY.ERROR", "ERRO: "+e.getMessage());
            return false;
        }
    }

    //--------------------------------------------------------------------------------------------//
    public void deletarCidade(Cidade cidadeDelete)
    {
        try
        {
            new Thread()
            {
                public void run()
                {
                    Looper.prepare();
                    cidadeAPIService.delete(cidadeDelete);
                    Looper.loop();
                }
            }.start();
            Log.d("DELETE.CTY.SUCCESS", "Cidade deletada com sucesso.");
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            Log.d("DELETE.CTY.ERROR", "ERRO: "+e.getMessage());
        }
    }

    //--------------------------------------------------------------------------------------------//
    public void alterarCidade(Cidade cidadeUpdate)
    {
        try
        {
            new Thread()
            {
                public void run()
                {
                    Looper.prepare();
                    cidadeAPIService.update(cidadeUpdate);
                    Looper.loop();
                }
            }.start();
            Log.d("UPDATE.CTY.SUCCESS", "Cidade alterada com sucesso.");
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            Log.d("UPDATE.CTY.ERROR", "ERRO: "+e.getMessage());
        }
    }

    //--------------------------------------------------------------------------------------------//
    public ArrayList<Cidade> getCidades()
    {
        Log.d("GET.CIDADES", "INICIO");

        cidadeAPIService.getCities().observe(this, cidades -> {
            if (cidades != null)
            {
                cidadesList = new ArrayList<>(cidades);
                Log.d("GET.CIDADES.SUCCESS", "Cidades obtidas com sucesso.");
            }
            else
            {
                Log.d("GET.CTY.ERROR", "Erro ao obter cidades.");
                throw new IllegalStateException("Erro ao obter cidades.");
            }
        });

        Log.d("GET.CIDADES", "FIM: "+cidadesList);
        return cidadesList;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean verificarCidadeExiste(Long ibge)
    {
        Log.d("CTY.EXIST.STATUS", "INICIO:" + ibge);

        cidadeAPIService.getCityPerIbge(ibge).observe( this, cidade -> {
            if (cidade != null)
            {
                Log.d("CTY.EXIST.SUCESS", "Cidade encontrada: " + cidade.getName() + " - IBGE: " + cidade.getIbge());
                cidadeExiste = true;
            }
            else
            {
                Log.d("CTY.EXIST.ERROR", "Cidade não encontrada para o IBGE: " + ibge);
                cidadeExiste = false;
            }
        });

        Log.d("CTY.EXIST.STATUS", "Cidade existe: " + cidadeExiste);
        return cidadeExiste;
    }

    //--------------------------------------------------------------------------------------------//
    @NonNull
    @Override
    public Lifecycle getLifecycle()
    {
        return lifecycle;
    }
}