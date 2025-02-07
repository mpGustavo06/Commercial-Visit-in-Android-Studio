package com.example.visitacomercial.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Objects;

@Entity(tableName = "cities")
public class City
{
    @PrimaryKey
    public Long ibge;

    public String name;
    public String ddd;

    public City(Long ibge, String name, String ddd)
    {
        this.ibge = ibge;
        this.name = name;
        this.ddd = ddd;
    }

    public City() { }

    public Long getIbge() {
        return ibge;
    }

    public void setIbge(Long ibge) {
        this.ibge = ibge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(ibge, city.ibge);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ibge);
    }

    @Override
    public String toString() {
        return "City{" +
                "ibge=" + ibge +
                ", name='" + name + '\'' +
                ", ddd='" + ddd + '\'' +
                '}';
    }
}