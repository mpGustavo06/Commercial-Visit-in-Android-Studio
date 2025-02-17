package com.example.visitacomercial.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "cidades")
public class Cidade implements Serializable
{
    @PrimaryKey
    public Long ibge;

    public String name;
    public String ddd;
    public String uf;

    public Cidade(Long l, String s, String s1, String s2)
    {
        this.ibge = l;
        this.name = s;
        this.ddd = s1;
        this.uf = s2;
    }

    public Cidade()
    {}

    //--------------------------------------------------------------------------------------------//
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

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    //--------------------------------------------------------------------------------------------//
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cidade cidade = (Cidade) o;
        return Objects.equals(ibge, cidade.ibge);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ibge);
    }

    @Override
    public String toString() {
        return "Cidade{" +
                "ibge=" + ibge +
                ", name='" + name + '\'' +
                ", ddd='" + ddd + '\'' +
                ", uf='" + uf + '\'' +
                '}';
    }
}