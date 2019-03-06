package com.azutka.evreka_mobilechallenge.fragments;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.azutka.evreka_mobilechallenge.EditCurrencies;
import com.azutka.evreka_mobilechallenge.MainActivity;
import com.azutka.evreka_mobilechallenge.R;
import com.azutka.evreka_mobilechallenge.adapters.CurrencyAdapter;
import com.azutka.evreka_mobilechallenge.database.DatabaseHelper;
import com.azutka.evreka_mobilechallenge.interfaces.OnSettingsUpdatedListener;
import com.azutka.evreka_mobilechallenge.models.CurrencyLog;
import com.azutka.evreka_mobilechallenge.models.CurrencyRest;
import com.azutka.evreka_mobilechallenge.models.Rate;
import com.azutka.evreka_mobilechallenge.models.RateRest;
import com.azutka.evreka_mobilechallenge.rest.ApiClient;
import com.azutka.evreka_mobilechallenge.rest.ApiInterface;
import com.azutka.evreka_mobilechallenge.utils.Constants;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CurrenciesFragment extends Fragment {

    private static final String TAG = "CurrenciesFragment";

    private String api_key;

    private View mView;

    private ArrayList<Rate> mRates = new ArrayList<>();

    private SharedPreferences mSharedPreferences;

    Unbinder unbinder;

    @BindView(R.id.fragment_currencies_recycler)
    RecyclerView recycler;

    @BindView(R.id.fragment_currencies_txt_time)
    TextView txtTime;

    @BindView(R.id.fragment_currencies_txt_info)
    TextView txtInfo;

    private Handler mHandlerForIcon = new Handler();
    private Runnable mRunnableForIcon;

    private Handler mHandlerRecord = new Handler();
    private Runnable mRunnableRecord;

    private StringBuilder mSBLog = new StringBuilder();
    private long startedTimeStamp;

    public static OnSettingsUpdatedListener onSettingsUpdatedListener;


    private boolean isRecording = false;
    private String mDate;
    private MenuItem mMenuItem;

    public CurrenciesFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_currencies, container, false);

        unbinder = ButterKnife.bind(this, mView);

        api_key = getString(R.string.api_key);

        mSharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_CURRENCY_SETTINGS,Context.MODE_PRIVATE);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            RateRest rates = (RateRest) bundle.getSerializable("rates");
            parseRates(rates);
            txtTime.setText(bundle.getString("time"));
        }

        //getData();

        onSettingsUpdatedListener = new OnSettingsUpdatedListener() {
            @Override
            public void onSettingsUpdated() {
                getData();
            }
        };

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(layoutManager);

        if(mRates.size() > 0){
            recycler.setAdapter(new CurrencyAdapter(getContext(), mRates));
            synchronized (recycler) {
                recycler.notifyAll();
            }
        }

        mRunnableRecord = new Runnable() {
            @Override
            public void run() {
                getData();
                mHandlerRecord.postDelayed(this, 2000);
            }
        };

        mHandlerRecord.postDelayed(mRunnableRecord,2000);

        return mView;
    }

    @OnClick(R.id.fragment_currencies_fab_edit)
    public void onButtonClick(View view) {
        Intent editIntent = new Intent(getContext(), EditCurrencies.class);
        startActivity(editIntent);
    }

    private void parseRates(RateRest rateRest){
        //mRates.add(null);

        mRates = new ArrayList<>();

        if(mSharedPreferences.getBoolean("TRY", true)){
            mRates.add(new Rate(getString(R.string.c_turkish), "TRY", rateRest.get_try()));
        }
        if(mSharedPreferences.getBoolean("USD", true)){
            mRates.add(new Rate(getString(R.string.c_us), "USD", rateRest.getUsd()));
        }
        if(mSharedPreferences.getBoolean("GBP", true)){
            mRates.add(new Rate(getString(R.string.c_british), "GBP", rateRest.getGbp()));
        }
        if(mSharedPreferences.getBoolean("CAD", false)){
            mRates.add(new Rate(getString(R.string.c_canadian), "CAD", rateRest.getCad()));
        }
        if(mSharedPreferences.getBoolean("JPY", false)){
            mRates.add(new Rate(getString(R.string.c_japanese), "JPY", rateRest.getJpy()));
        }
        if(mSharedPreferences.getBoolean("RUB", false)){
            mRates.add(new Rate(getString(R.string.c_russian), "RUB", rateRest.getRub()));
        }
        if(mSharedPreferences.getBoolean("INR", false)){
            mRates.add(new Rate(getString(R.string.c_indian), "INR", rateRest.getInr()));
        }
        if(mSharedPreferences.getBoolean("CHF", false)){
            mRates.add(new Rate(getString(R.string.c_swiss), "CHF", rateRest.getChf()));
        }
        if(mSharedPreferences.getBoolean("SAR", false)){
            mRates.add(new Rate(getString(R.string.c_saudi), "SAR", rateRest.getSar()));
        }
        if(mSharedPreferences.getBoolean("UAH", false)){
            mRates.add(new Rate(getString(R.string.c_ukrainian), "UAH", rateRest.getUah()));
        }

        if(mRates.isEmpty()){
            txtInfo.setVisibility(View.VISIBLE);
        } else {
            txtInfo.setVisibility(View.GONE);
        }

        recycler.setAdapter(new CurrencyAdapter(getContext(), mRates));
        synchronized (recycler) {
            recycler.notifyAll();
        }

        if(isRecording) {
            for(Rate rate : mRates) {
                mSBLog.append(mDate)
                        .append(" ")
                        .append(rate.getName())
                        .append(" ")
                        .append(rate.getCode())
                        .append(": ")
                        .append(rate.getRatio())
                        .append("\n");
            }
        }
    }

    private void getData(){

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault());
        mDate = sdf.format(new Date());

        Call<CurrencyRest> call = apiService.getCurrencies(api_key,"TRY,USD,GBP,CAD,JPY,RUB,INR,CHF,SAR,UAH","EUR");
        call.enqueue(new Callback<CurrencyRest>() {
            @Override
            public void onResponse(Call<CurrencyRest> call, Response<CurrencyRest> response) {
                RateRest rateRest = response.body().getRates();
                if(rateRest != null) {
                    parseRates(rateRest);
                    txtTime.setText(mDate);
                } else {
                    txtTime.setText(R.string.error);
                }



            }

            @Override
            public void onFailure(Call<CurrencyRest>call, Throwable t) {
                // CurrencyLog error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    private void startRecording(final MenuItem menuItem){

        if(getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            mMenuItem = menuItem;
        } else {
            startedTimeStamp = System.currentTimeMillis();
            isRecording = true;
            menuItem.setIcon(getActivity().getDrawable(R.drawable.ic_stop));
            final boolean[] isRed = {false};

            getData();

            mRunnableForIcon = new Runnable(){
                @Override
                public void run() {

                    if(!isRed[0]){
                        menuItem.getIcon().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorDarkRed),
                                PorterDuff.Mode.MULTIPLY);
                        isRed[0] = true;
                    } else {
                        menuItem.getIcon().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorWhite),
                                PorterDuff.Mode.MULTIPLY);
                        isRed[0] = false;
                    }
                    mHandlerForIcon.postDelayed(this, 1000);
                }

            };

            mHandlerForIcon.postDelayed(mRunnableForIcon, 1000);
        }






    }

    private void stopRecording(MenuItem menuItem){
        isRecording = false;
        menuItem.setIcon(getActivity().getDrawable(R.drawable.ic_record));
        mHandlerForIcon.removeCallbacks(mRunnableForIcon);

        saveLog();

        mSBLog.setLength(0);

    }

    private void saveLog(){
        long timestamp = System.currentTimeMillis() / 1000;
        String name = String.valueOf(timestamp) + "_log.txt";
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + name;
        String duration = String.valueOf( (timestamp*1000 - startedTimeStamp) / 1000) + getString(R.string.seconds);


        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), name);
        FileOutputStream fileOutputStream = null;
        try {

            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(mSBLog.toString().getBytes());
            fileOutputStream.close();

            MainActivity.databaseHelper.insertLog(new CurrencyLog(name ,path, duration, mDate, String.valueOf((timestamp))));
            Snackbar snackbar = Snackbar.make(recycler,getString(R.string.info_saved) + path, Snackbar.LENGTH_LONG);
            snackbar.show();

        } catch(Exception e){
            Log.e(TAG, "saveLog: ", e );
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        try {
            MenuItem menuItem = menu.findItem(R.id.menu_main_log);

            menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if(isRecording) {
                        stopRecording(menuItem);
                    } else {
                        startRecording(menuItem);
                    }
                    return false;
                }
            });


        } catch (Exception e){
            Log.e(TAG, "onCreateOptionsMenu: ",e );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 100:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startRecording(mMenuItem);
                } else {
                    Snackbar snackbar = Snackbar.make(recycler, R.string.missing_write_permission, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
        }
    }

}
