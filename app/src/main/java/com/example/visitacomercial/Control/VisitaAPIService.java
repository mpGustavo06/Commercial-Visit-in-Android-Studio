package com.example.visitacomercial.Control;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.visitacomercial.Models.Visita;

import java.util.List;

@Dao
public interface VisitaAPIService
{
    @Insert
    long insert(Visita visita);

    @Delete
    int delete(Visita visita);

    @Update
    int update(Visita visita);

    @Query("SELECT * FROM visitas")
    LiveData<List<Visita>> getVisits();

    @Query("SELECT * FROM visitas WHERE clientCnpj = :clientCnpj ORDER BY date DESC")
    LiveData<List<Visita>> getVisitPerClient(String clientCnpj );
}