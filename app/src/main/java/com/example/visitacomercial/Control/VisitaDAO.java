package com.example.visitacomercial.Control;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.room.Room;

import com.example.visitacomercial.Database.AppDatabase;
import com.example.visitacomercial.Models.Visita;

public class VisitaDAO implements LifecycleOwner
{
    private AppDatabase database;
    private VisitaAPIService visitaAPIService;
    private Lifecycle lifecycle;

    public VisitaDAO(Context context, Lifecycle lifecycle)
    {
        database = Room.databaseBuilder(context, AppDatabase.class, "app_database").build();
        visitaAPIService = database.visitaService();

        this.lifecycle = lifecycle;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean inserirVisita(Visita newVisita)
    {
        try
        {
            new Thread()
            {
                public void run()
                {
                    Looper.prepare();
                    visitaAPIService.insert(newVisita);
                    Looper.loop();
                }
            }.start();
            Log.d("INSERT.VIS.SUCCESS", "Visita inserida com sucesso.");
            return true;
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            Log.d("INSERT.VIS.ERROR", "ERRO: "+e.getMessage());
            return false;
        }
    }

    //--------------------------------------------------------------------------------------------//


    ////////////////////////////////////////////////////////////////////////////////////////////////
    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return null;
    }
}
