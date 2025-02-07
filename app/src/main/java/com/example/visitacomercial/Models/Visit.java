package com.example.visitacomercial.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;
import java.util.Objects;

@Entity(tableName = "visits")
public class Visit
{
    @PrimaryKey(autoGenerate = true)
    public Long id;

    public String clientCnpj;
    public Date date;
    public int satisfaction;
    public double orderValue;
    public String notes;

    public Visit(Long id, String clientCnpj, Date date, int satisfaction, double orderValue, String notes)
    {
        this.id = id;
        this.clientCnpj = clientCnpj;
        this.date = date;
        this.satisfaction = satisfaction;
        this.orderValue = orderValue;
        this.notes = notes;
    }

    public Visit() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientCnpj() {
        return clientCnpj;
    }

    public void setClientCnpj(String clientCnpj) {
        this.clientCnpj = clientCnpj;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(int satisfaction) {
        this.satisfaction = satisfaction;
    }

    public double getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(double orderValue) {
        this.orderValue = orderValue;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visit visit = (Visit) o;
        return Objects.equals(id, visit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", cnpjCliente='" + clientCnpj + '\'' +
                ", data=" + date +
                ", satisfacao=" + satisfaction +
                ", valorPedido=" + orderValue +
                ", observacao='" + notes + '\'' +
                '}';
    }
}