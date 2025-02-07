package com.example.visitacomercial.Control;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.visitacomercial.Models.Client;

import java.util.List;

@Dao
public interface ClientDAO
{
    @Insert
    long insert(Client client);

    @Query("SELECT * FROM clients ORDER BY lastVisit ASC")
    LiveData<List<Client>> getClients();

    @Query("SELECT * FROM clients WHERE ibgeCity = :ibgeCity ORDER BY lastVisit ASC")
    LiveData<List<Client>> getClientPerCity( Long ibgeCity );

    @Query("SELECT * FROM clients WHERE cnpj = :cnpj")
    LiveData<List<Client>> getClientPerCnpj( String cnpj );
}