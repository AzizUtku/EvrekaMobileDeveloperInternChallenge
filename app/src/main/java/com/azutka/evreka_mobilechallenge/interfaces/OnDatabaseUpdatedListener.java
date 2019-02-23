package com.azutka.evreka_mobilechallenge.interfaces;

import com.azutka.evreka_mobilechallenge.models.CurrencyLog;

import java.util.ArrayList;

public interface OnDatabaseUpdatedListener {

    void onDatabaseUpdated(ArrayList<CurrencyLog> logs);

}