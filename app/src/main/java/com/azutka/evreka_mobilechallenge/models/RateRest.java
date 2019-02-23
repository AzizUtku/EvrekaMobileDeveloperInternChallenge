package com.azutka.evreka_mobilechallenge.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RateRest implements Serializable {

    //For flexibility it includes currencies more than three.
    //Users can list any of them.
    @SerializedName("TRY")
    public double _try;
    @SerializedName("USD")
    public double usd;
    @SerializedName("GBP")
    public double gbp;
    @SerializedName("CAD")
    public double cad;
    @SerializedName("JPY")
    public double jpy;
    @SerializedName("RUB")
    public double rub;
    @SerializedName("INR")
    public double inr;
    @SerializedName("CHF")
    public double chf;
    @SerializedName("SAR")
    public double sar;
    @SerializedName("UAH")
    public double uah;

    public RateRest(double _try, double usd, double gbp, double cad, double jpy, double rub, double inr, double chf, double sar, double uah) {
        this._try = _try;
        this.usd = usd;
        this.gbp = gbp;
        this.cad = cad;
        this.jpy = jpy;
        this.rub = rub;
        this.inr = inr;
        this.chf = chf;
        this.sar = sar;
        this.uah = uah;
    }

    public double get_try() {
        return _try;
    }

    public void set_try(double _try) {
        this._try = _try;
    }

    public double getUsd() {
        return usd;
    }

    public void setUsd(double usd) {
        this.usd = usd;
    }

    public double getGbp() {
        return gbp;
    }

    public void setGbp(double gbp) {
        this.gbp = gbp;
    }

    public double getCad() {
        return cad;
    }

    public void setCad(double cad) {
        this.cad = cad;
    }

    public double getJpy() {
        return jpy;
    }

    public void setJpy(double jpy) {
        this.jpy = jpy;
    }

    public double getRub() {
        return rub;
    }

    public void setRub(double rub) {
        this.rub = rub;
    }

    public double getInr() {
        return inr;
    }

    public void setInr(double inr) {
        this.inr = inr;
    }

    public double getChf() {
        return chf;
    }

    public void setChf(double chf) {
        this.chf = chf;
    }

    public double getSar() {
        return sar;
    }

    public void setSar(double sar) {
        this.sar = sar;
    }

    public double getUah() {
        return uah;
    }

    public void setUah(double uah) {
        this.uah = uah;
    }

    @Override
    public String toString() {
        return "RateRest{" +
                "_try=" + _try +
                ", usd=" + usd +
                ", gbp=" + gbp +
                ", cad=" + cad +
                ", jpy=" + jpy +
                ", rub=" + rub +
                ", inr=" + inr +
                ", chf=" + chf +
                ", sar=" + sar +
                ", uah=" + uah +
                '}';
    }
}