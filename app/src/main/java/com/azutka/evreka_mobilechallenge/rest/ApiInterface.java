package com.azutka.evreka_mobilechallenge.rest;

import com.azutka.evreka_mobilechallenge.models.CurrencyRest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("latest")
    Call<CurrencyRest> getCurrencies(@Query("access_key") String apiKey, @Query("symbols") String symbols, @Query("base") String base);

}
