package com.eticaret.repository.entity;

import javax.persistence.*;

@Table(name = "tblproduct")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String ad;
    double fiyat;

    public Product() {
    }

    public Product(String ad, double fiyat) {
        this.ad = ad;
        this.fiyat = fiyat;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public double getFiyat() {
        return fiyat;
    }

    public void setFiyat(double fiyat) {
        this.fiyat = fiyat;
    }
}
