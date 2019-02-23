package com.azutka.evreka_mobilechallenge.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.azutka.evreka_mobilechallenge.R;
import com.azutka.evreka_mobilechallenge.models.CurrencyLog;
import com.azutka.evreka_mobilechallenge.models.Rate;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "LogAdapter";

    private List<CurrencyLog> mLogs;
    private Context mContext;

    public LogAdapter(Context context, List<CurrencyLog> logs) {
        this.mContext = context;
        this.mLogs = logs;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View logView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log, parent, false);
        LogHolder holder = new LogHolder(logView);
        return holder;



    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        CurrencyLog currencyLog = mLogs.get(position);

        LogHolder logHolder = (LogHolder) holder;

        logHolder.setPath(currencyLog.getPath());
        logHolder.setName(currencyLog.getName());
        logHolder.setDate(currencyLog.getDate());
        logHolder.setDuration(currencyLog.getDuration());

    }

    @Override
    public int getItemCount() {
        return mLogs.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class LogHolder extends RecyclerView.ViewHolder {

        private String mPath;

        @BindView(R.id.logs_txt_name)
        TextView txtName;

        @BindView(R.id.logs_txt_date)
        TextView txtDate;

        @BindView(R.id.logs_txt_duration)
        TextView txtDuration;

        @OnClick(R.id.logs_rlt_main)
        public void onButtonClick(View view) {
            try {
                File file = new File(mPath);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri apkURI = FileProvider.getUriForFile(mContext,mContext.getPackageName() + ".provider", file);
                intent.setDataAndType(apkURI, "text/*");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                mContext.startActivity(intent);
            } catch (Exception e) {
                Log.e(TAG, "onButtonClick: ", e );
            }
        }


        private LogHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setName(String name){
            txtName.setText(name);
        }

        public void setDate(String date){
            txtDate.setText(date);
        }

        public void setDuration(String duration){
           txtDuration.setText(duration);
        }

        public void setPath(String path) {
            mPath = path;
        }

    }


}
