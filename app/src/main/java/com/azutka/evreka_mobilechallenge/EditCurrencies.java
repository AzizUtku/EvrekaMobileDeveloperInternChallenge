package com.azutka.evreka_mobilechallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.azutka.evreka_mobilechallenge.adapters.CurrencyEditAdapter;
import com.azutka.evreka_mobilechallenge.adapters.LogAdapter;
import com.azutka.evreka_mobilechallenge.fragments.CurrenciesFragment;
import com.azutka.evreka_mobilechallenge.models.Currency;
import com.azutka.evreka_mobilechallenge.models.Rate;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditCurrencies extends AppCompatActivity {

    @BindView(R.id.edit_currencies_recycler)
    RecyclerView recyclerList;

    @BindView(R.id.edit_currencies_toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView txtToolbarTitle;

    private ArrayList<Currency> mCurrencies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_currencies);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        txtToolbarTitle.setText(R.string.edit_currencies);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerList.setLayoutManager(layoutManager);

        mCurrencies = new ArrayList<>();
        fillCurrencies();

        recyclerList.setAdapter(new CurrencyEditAdapter(getApplicationContext(), mCurrencies, CurrenciesFragment.onSettingsUpdatedListener));
        synchronized (recyclerList) {
            recyclerList.notifyAll();
        }
    }

    private void fillCurrencies(){
        mCurrencies.add(new Currency(getString(R.string.c_turkish), "TRY"));
        mCurrencies.add(new Currency(getString(R.string.c_us), "USD"));
        mCurrencies.add(new Currency(getString(R.string.c_british), "GBP"));
        mCurrencies.add(new Currency(getString(R.string.c_canadian), "CAD"));
        mCurrencies.add(new Currency(getString(R.string.c_japanese), "JPY"));
        mCurrencies.add(new Currency(getString(R.string.c_russian), "RUB"));
        mCurrencies.add(new Currency(getString(R.string.c_indian), "INR"));
        mCurrencies.add(new Currency(getString(R.string.c_swiss), "CHF"));
        mCurrencies.add(new Currency(getString(R.string.c_saudi), "SAR"));
        mCurrencies.add(new Currency(getString(R.string.c_ukrainian), "UAH"));
    }
}
