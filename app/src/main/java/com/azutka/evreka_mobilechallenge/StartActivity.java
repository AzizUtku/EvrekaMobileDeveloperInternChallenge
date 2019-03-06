package com.azutka.evreka_mobilechallenge;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.azutka.evreka_mobilechallenge.models.CurrencyRest;
import com.azutka.evreka_mobilechallenge.models.RateRest;
import com.azutka.evreka_mobilechallenge.rest.ApiClient;
import com.azutka.evreka_mobilechallenge.rest.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartActivity extends AppCompatActivity {

    private static final String TAG = "StartActivity";
    private String api_key;
    private boolean mIsConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        api_key = getString(R.string.api_key);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
            mIsConnected = true;
            getData();
        } else {
            AlertDialog.Builder dialogAbout = new AlertDialog.Builder(this);
            dialogAbout.setCancelable(false);
            dialogAbout.setTitle(R.string.no_connection);
            dialogAbout.setMessage(R.string.connection_needed);
            dialogAbout.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            dialogAbout.show();
        }


    }

    private void getData(){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault());
        final String date = sdf.format(new Date());

        Call<CurrencyRest> call = apiService.getCurrencies(api_key,"TRY,USD,GBP,CAD,JPY,RUB,INR,CHF,SAR,UAH","EUR");
        call.enqueue(new Callback<CurrencyRest>() {
            @Override
            public void onResponse(Call<CurrencyRest> call, Response<CurrencyRest> response) {
                RateRest rateRest = null;
                if (response.body() != null) {

                    rateRest = response.body().getRates();

                    if(rateRest == null){
                        showAPIError(getString(R.string.exceeded_quota));
                    } else {

                        Log.i(TAG, "onResponse: " + rateRest.toString());

                        Intent mainIntent = new Intent(StartActivity.this, MainActivity.class);
                        mainIntent.putExtra("rates", rateRest);
                        mainIntent.putExtra("time", date);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(mainIntent);
                        finish();
                    }



                } else {

                    showAPIError(getString(R.string.connection_error));

                }
            }

            @Override
            public void onFailure(Call<CurrencyRest>call, Throwable t) {
                // CurrencyLog error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    private void showAPIError(String message){
        AlertDialog.Builder dialogError = new AlertDialog.Builder(StartActivity.this);
        dialogError.setCancelable(false);
        dialogError.setTitle(R.string.api_problem);
        dialogError.setMessage(message);
        dialogError.setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        dialogError.show();
    }

}
