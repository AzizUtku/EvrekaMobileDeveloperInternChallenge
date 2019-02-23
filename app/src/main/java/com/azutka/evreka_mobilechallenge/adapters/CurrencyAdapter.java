package com.azutka.evreka_mobilechallenge.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.azutka.evreka_mobilechallenge.R;
import com.azutka.evreka_mobilechallenge.models.Rate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CurrencyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Rate> mRates;
    private Context mContext;

    public CurrencyAdapter(Context context, List<Rate> rates) {
        this.mContext = context;
        this.mRates = rates;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View currencyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_currency, parent, false);
        CurrencyHolder holder = new CurrencyHolder(currencyView);
        return holder;


        /*if(viewType == 0){

            View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_header, parent,false);
            HeaderHolder holder = new HeaderHolder(headerView);
            return holder;

        } else {

            View collectionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_currency, parent, false);
            CurrencyHolder holder = new CurrencyHolder(collectionView);
            return holder;

        }*/


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Rate rate = mRates.get(position);

        CurrencyHolder currencyHolder = (CurrencyHolder) holder;
        currencyHolder.setCodes(rate.getCode());
        currencyHolder.setName(rate.getName());
        currencyHolder.setRate(rate.getRatio());

        /*if(position > 0) {

            Rate rate = mRates.get(position);

            CurrencyHolder currencyHolder = (CurrencyHolder) holder;
            currencyHolder.setCodes(rate.getSymbol());
            currencyHolder.setName(rate.getName());
            currencyHolder.setRate(rate.getRatio());

        }*/
    }

    @Override
    public int getItemCount() {
        return mRates.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class CurrencyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.currencies_txt_codes)
        TextView mTxtCodes;

        @BindView(R.id.currencies_txt_name)
        TextView mTxtName;

        @BindView(R.id.currencies_txt_rate)
        TextView mTxtRate;

        private CurrencyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setCodes(String code){
            mTxtCodes.setText("EUR/" + code);
        }

        public void setName(String name){
            mTxtName.setText(name);
        }

        public void setRate(double rate){
            mTxtRate.setText(String.format("%.4f", rate));
        }


    }


    public class HeaderHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.row_recycler_txt_info)
        TextView mTxtInfo;

        public HeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


}
