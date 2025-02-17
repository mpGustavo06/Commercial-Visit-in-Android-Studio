package com.example.visitacomercial.Database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import android.content.Context;

import com.example.visitacomercial.Control.CidadeAPIService;
import com.example.visitacomercial.Control.ClienteAPIService;
import com.example.visitacomercial.Control.VisitaAPIService;
import com.example.visitacomercial.Models.Cidade;
import com.example.visitacomercial.Models.Cliente;
import com.example.visitacomercial.Models.Visita;
import com.example.visitacomercial.Utils.DateConverter;

@Database(entities = {Cliente.class, Cidade.class, Visita.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase
{
    private static AppDatabase INSTANCE;

    public abstract ClienteAPIService clienteDAO();
    public abstract CidadeAPIService cidadeDAO();
    public abstract VisitaAPIService visitaDAO();

    public static AppDatabase getAppDatabase(Context context)
    {
        if (INSTANCE == null)
        {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "clientes-database")
                        .allowMainThreadQueries()
                        .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance()
    {
        INSTANCE = null;
    }
}