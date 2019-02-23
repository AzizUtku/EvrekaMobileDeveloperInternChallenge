package com.azutka.evreka_mobilechallenge;

import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.azutka.evreka_mobilechallenge.database.DatabaseHelper;
import com.azutka.evreka_mobilechallenge.fragments.CurrenciesFragment;
import com.azutka.evreka_mobilechallenge.fragments.LogsFragment;
import com.azutka.evreka_mobilechallenge.models.RateRest;
import com.azutka.evreka_mobilechallenge.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    private RateRest mRateRest;

    @BindView(R.id.main_toolbar)
    Toolbar toolbar;

    @BindView(R.id.main_bottom_nav)
    BottomNavigationView bottomNav;

    private Fragment mCurrentFragment;

    //Fragments
    private CurrenciesFragment currenciesFragment;
    private LogsFragment logsFragment;

    public static DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //Set toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(getDrawable(R.drawable.ic_logo_vector));

        databaseHelper = new DatabaseHelper(getApplicationContext());

        currenciesFragment = new CurrenciesFragment();
        logsFragment = new LogsFragment();

        mRateRest = (RateRest) getIntent().getSerializableExtra("rates");

        Bundle bundle = new Bundle();
        bundle.putSerializable("rates", mRateRest);
        bundle.putString("time", getIntent().getStringExtra("time"));
        currenciesFragment.setArguments(bundle);

        mCurrentFragment = currenciesFragment;
        setFragment( currenciesFragment,"currencies");

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.menu_nav_currencies:
                        switchFragment(currenciesFragment,"currencies");
                        return true;
                    case R.id.menu_nav_logs:
                        switchFragment(logsFragment,"logs");
                        return true;
                    default:
                        switchFragment(currenciesFragment,"currencies");
                        return true;

                }
            }
        });


    }

    private void switchFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            fragmentTransaction.add(R.id.main_frame, fragment, tag);
        }

        fragmentTransaction.hide(mCurrentFragment);
        fragmentTransaction.show(fragment);
        mCurrentFragment = fragment;
        fragmentTransaction.commit();
    }

    private void setFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment, tag);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        //Set menu
        switch(item.getItemId()){
            case R.id.menu_main_about:
                AlertDialog.Builder dialogAbout = new AlertDialog.Builder(this);
                dialogAbout.setTitle(getString(R.string.about));
                dialogAbout.setMessage(getString(R.string.about_message) + Constants.version);
                dialogAbout.setPositiveButton(R.string.okay, null );
                dialogAbout.show();
                break;
            default:
                break;
        }

        return true;
    }


}
