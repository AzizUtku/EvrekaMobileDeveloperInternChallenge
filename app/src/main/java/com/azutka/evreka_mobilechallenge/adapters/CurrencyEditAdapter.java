package com.azutka.evreka_mobilechallenge.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.azutka.evreka_mobilechallenge.R;
import com.azutka.evreka_mobilechallenge.interfaces.OnDatabaseUpdatedListener;
import com.azutka.evreka_mobilechallenge.interfaces.OnSettingsUpdatedListener;
import com.azutka.evreka_mobilechallenge.models.Currency;
import com.azutka.evreka_mobilechallenge.models.CurrencyLog;
import com.azutka.evreka_mobilechallenge.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CurrencyEditAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Currency> mCurrencyList;
    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private OnSettingsUpdatedListener mOnSettingsUpdatedListener;

    public CurrencyEditAdapter(Context context, List<Currency> currencyList, OnSettingsUpdatedListener onSettingsUpdatedListener) {
        this.mContext = context;
        this.mCurrencyList = currencyList;
        mOnSettingsUpdatedListener = onSettingsUpdatedListener;
        mSharedPreferences = mContext.getSharedPreferences(Constants.SHARED_PREFERENCES_CURRENCY_SETTINGS,Context.MODE_PRIVATE);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View currencyEditView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_currency_edit, parent, false);
        CurrencySettingsHolder holder = new CurrencySettingsHolder(currencyEditView);
        return holder;



    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Currency currency = mCurrencyList.get(position);

        CurrencySettingsHolder currencySettingsHolder = (CurrencySettingsHolder) holder;

        String code = currency.getCode();
        currencySettingsHolder.setName(currency.getName());
        currencySettingsHolder.setCode(code);
        if(code.equals("TRY") || code.equals("USD") || code.equals("GBP")){
            currencySettingsHolder.setIsPresent(mSharedPreferences.getBoolean(code,true));
        } else {
            currencySettingsHolder.setIsPresent(mSharedPreferences.getBoolean(code,false));
        }



    }

    @Override
    public int getItemCount() {
        return mCurrencyList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class CurrencySettingsHolder extends RecyclerView.ViewHolder {


        private boolean mIsPresent = true;
        private String mCode;

        @BindView(R.id.currency_edit_txt_name)
        TextView txtName;

        @BindView(R.id.currency_edit_txt_code)
        TextView txtCode;

        @BindView(R.id.currency_edit_btn_add)
        Button btnAdd;

        @OnClick(R.id.currency_edit_btn_add)
        public void onButtonClick(View view) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            if(mIsPresent) {
                editor.putBoolean(mCode, false);
                editor.apply();
                setIsPresent(false);
            } else {
                editor.putBoolean(mCode, true);
                editor.apply();
                setIsPresent(true);
            }

            if(mOnSettingsUpdatedListener != null){
                mOnSettingsUpdatedListener.onSettingsUpdated();
            }
        }


        private CurrencySettingsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setName(String name){
            txtName.setText(name);
        }

        public void setCode(String code){
            mCode = code;
            txtCode.setText(code);
        }

        public void setIsPresent(boolean isPresent) {
            mIsPresent = isPresent;
            if(isPresent) {
                btnAdd.setText(R.string.remove);
            } else {
                btnAdd.setText(R.string.add);
            }
        }

    }

}
