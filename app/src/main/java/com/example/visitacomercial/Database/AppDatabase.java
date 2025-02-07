package com.example.visitacomercial.Database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.visitacomercial.Control.CityDAO;
import com.example.visitacomercial.Control.ClientDAO;
import com.example.visitacomercial.Control.VisitDAO;
import com.example.visitacomercial.Models.City;
import com.example.visitacomercial.Models.Client;
import com.example.visitacomercial.Models.Visit;

@Database(entities = {Client.class, City.class, Visit.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase
{
    private static AppDatabase INSTANCE;

    public abstract ClientDAO clientDao();
    public abstract CityDAO cityDao();
    public abstract VisitDAO visitDao();

    public static AppDatabase getAppDatabase( Context context )
    {
        if ( INSTANCE == null )
        {
            INSTANCE =  Room.databaseBuilder( context.getApplicationContext(),
                        AppDatabase.class, "app_database")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build();
        }

        return INSTANCE;
    }
}