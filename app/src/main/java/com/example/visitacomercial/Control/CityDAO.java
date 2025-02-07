package com.example.visitacomercial.Control;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.visitacomercial.Models.City;

import java.util.List;

@Dao
public interface CityDAO
{
    @Insert
    long insert(City city);

    @Query("SELECT * FROM clients")
    LiveData<List<City>> getCities();

    @Query("SELECT * FROM cities WHERE ibge = :ibge")
    LiveData<List<City>> getCityPerIbge( Long ibge );

    @Query("SELECT * FROM clients WHERE cnpj = :cnpj")
    LiveData<List<City>> getClientPerCnpj( String cnpj );
}