package com.example.visitacomercial.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

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
    public abstract ClienteAPIService clienteService();
    public abstract CidadeAPIService cidadeService();
    public abstract VisitaAPIService visitaService();
}