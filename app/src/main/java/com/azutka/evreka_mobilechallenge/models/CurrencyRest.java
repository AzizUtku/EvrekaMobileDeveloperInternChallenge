package com.azutka.evreka_mobilechallenge.models;

import com.google.gson.annotations.SerializedName;

public class CurrencyRest {

    @SerializedName("success")
    public boolean success;
    @SerializedName("timestamp")
    public int timestamp;
    @SerializedName("base")
    public String base;
    @SerializedName("date")
    public String date;
    @SerializedName("rates")
    public RateRest rates;

    public CurrencyRest(boolean success, int timestamp, String base, String date, RateRest rates) {
        this.success = success;
        this.timestamp = timestamp;
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public RateRest getRates() {
        return rates;
    }

    public void setRates(RateRest rates) {
        this.rates = rates;
    }

}