package com.example.visitacomercial.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;
import java.util.Objects;

@Entity(tableName = "clients")
public class Client
{
    @PrimaryKey
    public String cnpj;

    public String socialReason;
    public String fantasyName;
    public String contact;
    public String phone;
    public String email;
    public Date lastVisit;
    public String zipCode;
    public String street;
    public String district;
    public String number;
    public Long ibgeCity;

    public Client(String cnpj, String socialReason, String fantasyName, String contact, String phone, String email, Date lastVisit, String zipCode, String street, String district, String number, Long ibgeCity)
    {
        this.cnpj = cnpj;
        this.socialReason = socialReason;
        this.fantasyName = fantasyName;
        this.contact = contact;
        this.phone = phone;
        this.email = email;
        this.lastVisit = lastVisit;
        this.zipCode = zipCode;
        this.street = street;
        this.district = district;
        this.number = number;
        this.ibgeCity = ibgeCity;
    }

    public Client() { }

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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(cnpj, client.cnpj);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cnpj);
    }

    @Override
    public String toString() {
        return "Client{" +
                "cnpj='" + cnpj + '\'' +
                ", razaoSocial='" + socialReason + '\'' +
                ", nomeFantasia='" + fantasyName + '\'' +
                ", contato='" + contact + '\'' +
                ", telefone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", ultimaVisita=" + lastVisit +
                ", CEP='" + zipCode + '\'' +
                ", logradouro='" + street + '\'' +
                ", bairro='" + district + '\'' +
                ", numero='" + number + '\'' +
                ", ibgeCidade=" + ibgeCity +
                '}';
    }
}