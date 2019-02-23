package com.azutka.evreka_mobilechallenge.models;

public class CurrencyLog {

    //For SQLite
    public static final String TABLE_NAME = "logs";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PATH = "path";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    //Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_PATH + " TEXT,"
                    + COLUMN_DURATION + " TEXT,"
                    + COLUMN_DATE + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";


    private int id;


    private String name;
    private String path;
    private String duration;
    private String date;
    private String timestamp;


    public CurrencyLog(String name, String path, String duration, String date, String timestamp) {
        this.name = name;
        this.path = path;
        this.duration = duration;
        this.date = date;
        this.timestamp = timestamp;
    }

    //Constructor for SQLite
    public CurrencyLog(int id, String name, String path, String duration, String date, String timestamp) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.duration = duration;
        this.date = date;
        this.timestamp = timestamp;
    }

    public CurrencyLog() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
