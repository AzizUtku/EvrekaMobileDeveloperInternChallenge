package com.azutka.evreka_mobilechallenge.fragments;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.azutka.evreka_mobilechallenge.MainActivity;
import com.azutka.evreka_mobilechallenge.R;
import com.azutka.evreka_mobilechallenge.adapters.CurrencyAdapter;
import com.azutka.evreka_mobilechallenge.adapters.LogAdapter;
import com.azutka.evreka_mobilechallenge.database.DatabaseHelper;
import com.azutka.evreka_mobilechallenge.interfaces.OnDatabaseUpdatedListener;
import com.azutka.evreka_mobilechallenge.models.CurrencyLog;
import com.azutka.evreka_mobilechallenge.models.Rate;
import com.azutka.evreka_mobilechallenge.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LogsFragment extends Fragment {

    private View mView;

    Unbinder unbinder;

    @BindView(R.id.fragment_logs_recycler)
    RecyclerView recyclerList;

    @BindView(R.id.fragment_logs_txt_info)
    TextView txtInfo;

    private ArrayList<CurrencyLog> mLogs = new ArrayList<>();

    public LogsFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_logs, container, false);
        unbinder = ButterKnife.bind(this, mView);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerList.setLayoutManager(layoutManager);

        MainActivity.databaseHelper.setOnTaskCompletedListener(new OnDatabaseUpdatedListener() {
            @Override
            public void onDatabaseUpdated(ArrayList<CurrencyLog> logs) {
                mLogs = logs;
                updateRecycler();
            }
        });

        mLogs = MainActivity.databaseHelper.getAllLogs();

        updateRecycler();


        return mView;
    }



    private void updateRecycler(){
        if(mLogs.size() > 0){
            txtInfo.setVisibility(View.GONE);
            recyclerList.setAdapter(new LogAdapter(getContext(), mLogs));
            synchronized (recyclerList) {
                recyclerList.notifyAll();
            }
        } else {
            txtInfo.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



}
