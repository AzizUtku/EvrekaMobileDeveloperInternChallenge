package com.azutka.evreka_mobilechallenge.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.azutka.evreka_mobilechallenge.interfaces.OnDatabaseUpdatedListener;
import com.azutka.evreka_mobilechallenge.models.CurrencyLog;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "logs_db";

	private OnDatabaseUpdatedListener mOnDatabaseUpdatedListener;

	public void setOnTaskCompletedListener(OnDatabaseUpdatedListener onTaskCompletedListener){
		this.mOnDatabaseUpdatedListener = onTaskCompletedListener;
	}

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CurrencyLog.CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + CurrencyLog.TABLE_NAME);
		onCreate( db);
	}

	public long insertLog(CurrencyLog currencyLog){

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(CurrencyLog.COLUMN_NAME, currencyLog.getName());
		values.put(CurrencyLog.COLUMN_PATH, currencyLog.getPath());
		values.put(CurrencyLog.COLUMN_DURATION, currencyLog.getDuration());
		values.put(CurrencyLog.COLUMN_DATE, currencyLog.getDate());
		values.put(CurrencyLog.COLUMN_TIMESTAMP, currencyLog.getTimestamp());

		long id = db.insert(CurrencyLog.TABLE_NAME, null, values);

		db.close();

		if(mOnDatabaseUpdatedListener != null){
			mOnDatabaseUpdatedListener.onDatabaseUpdated(getAllLogs());
		}

		return id;
	}

	public ArrayList<CurrencyLog> getAllLogs() {

		ArrayList<CurrencyLog> listCurrencies = new ArrayList<>();

		String selectQuery = "SELECT  * FROM " + CurrencyLog.TABLE_NAME + " ORDER BY " +
				CurrencyLog.COLUMN_TIMESTAMP + " DESC";

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(selectQuery, null);

		if(cursor.moveToFirst()){

			do {

				CurrencyLog currencyLog = new CurrencyLog( cursor.getInt(cursor.getColumnIndex(CurrencyLog.COLUMN_ID)),
						cursor.getString(cursor.getColumnIndex(CurrencyLog.COLUMN_NAME)),
						cursor.getString(cursor.getColumnIndex(CurrencyLog.COLUMN_PATH)),
						cursor.getString(cursor.getColumnIndex(CurrencyLog.COLUMN_DURATION)),
						cursor.getString(cursor.getColumnIndex(CurrencyLog.COLUMN_DATE)),
						cursor.getString(cursor.getColumnIndex(CurrencyLog.COLUMN_TIMESTAMP))
				);

				listCurrencies.add(currencyLog);

			} while( cursor.moveToNext());

		}

		cursor.close();
		db.close();

		return listCurrencies;
	}




}
