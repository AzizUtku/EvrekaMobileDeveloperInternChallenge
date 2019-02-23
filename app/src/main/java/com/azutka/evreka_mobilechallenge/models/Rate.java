package com.azutka.evreka_mobilechallenge.models;

public class Rate {

    private String name;
    private String code;
    private double ratio;

    public Rate() {
    }

    public Rate(String name, String symbol, double ratio) {
        this.name = name;
        this.code = symbol;
        this.ratio = ratio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
}
