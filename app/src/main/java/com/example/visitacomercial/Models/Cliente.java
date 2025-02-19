package com.example.visitacomercial.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity(tableName = "clientes")
public class Cliente implements Serializable
{
    @PrimaryKey
    @NonNull
    public String cnpj;

    public String socialReason;
    public String fantasyName;
    public String phone;
    public String contactPerson;
    public String email;
    public Date lastVisit;
    public String zipCode;
    public String street;
    public String district;
    public String number;
    public Long ibgeCity;

    public Cliente()
    {
        this.lastVisit = new Date();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getSocialReason() {
        return socialReason;
    }

    public void setSocialReason(String socialReason) {
        this.socialReason = socialReason;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public void setFantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Date lastVisit) {
        this.lastVisit = lastVisit;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getIbgeCity() {
        return ibgeCity;
    }

    public void setIbgeCity(Long ibgeCity) {
        this.ibgeCity = ibgeCity;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(cnpj, cliente.cnpj);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cnpj);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "cnpj='" + cnpj + '\'' +
                ", socialReason='" + socialReason + '\'' +
                ", fantasyName='" + fantasyName + '\'' +
                ", phone='" + phone + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", email='" + email + '\'' +
                ", lastVisit=" + lastVisit +
                ", zipCode='" + zipCode + '\'' +
                ", street='" + street + '\'' +
                ", district='" + district + '\'' +
                ", number='" + number + '\'' +
                ", ibgeCity=" + ibgeCity +
                '}';
    }
}