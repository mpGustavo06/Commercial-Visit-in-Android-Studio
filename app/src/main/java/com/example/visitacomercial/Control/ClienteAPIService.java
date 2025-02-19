package com.example.visitacomercial.Control;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.visitacomercial.Models.Cliente;

import java.util.List;

@Dao
public interface ClienteAPIService
{
    @Insert
    long insert(Cliente cliente);

    @Delete
    int delete(Cliente cliente);

    @Update
    int update(Cliente cliente);

    @Query("SELECT * FROM clientes ORDER BY lastVisit ASC")
    LiveData<List<Cliente>> getClients();

    @Query("SELECT * FROM clientes WHERE ibgeCity = :ibge ORDER BY lastVisit ASC")
    LiveData<List<Cliente>> getClientPerCity(Long ibge );
}