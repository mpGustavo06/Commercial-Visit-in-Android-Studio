package com.example.visitacomercial.Control;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.visitacomercial.Models.Visit;

import java.util.List;

@Dao
public interface VisitDAO
{
    @Insert
    long insert(Visit visit);

    @Query("SELECT * FROM visits")
    LiveData<List<Visit>> getVisits();

    @Query("SELECT * FROM visits WHERE clientCnpj = :clientCnpj ORDER BY date DESC")
    LiveData<List<Visit>> getVisitPerClient( String clientCnpj );
}