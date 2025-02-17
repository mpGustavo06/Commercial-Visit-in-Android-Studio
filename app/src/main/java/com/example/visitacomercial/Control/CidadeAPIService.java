package com.example.visitacomercial.Control;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.visitacomercial.Models.Cidade;

import java.util.List;

@Dao
public interface CidadeAPIService
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Cidade cidade);

    @Delete
    int delete(Cidade cidade);

    @Update
    int update(Cidade cidade);

    @Query("SELECT * FROM cidades")
    LiveData<List<Cidade>> getCities();

    @Query("SELECT * FROM cidades WHERE ibge = :ibge LIMIT 1")
    LiveData<Cidade> getCityPerIbge(Long ibge);
}