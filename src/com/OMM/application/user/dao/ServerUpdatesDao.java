package com.OMM.application.user.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.OMM.application.user.helper.LocalDatabase;

public class ServerUpdatesDao {

	private static ServerUpdatesDao instance = null;
	private LocalDatabase database;
	private String table_name = "URL_SERVER";
	private SQLiteDatabase sqliteDatabase;
	private String defaultUrl= "env-7461835.jelasticlw.com.br/akan_desenvol";
	
	private ServerUpdatesDao(Context context) {
		this.database = new LocalDatabase(context);
	}

	public static ServerUpdatesDao getInstance(Context context) {

		if (instance == null)
			instance = new ServerUpdatesDao(context);
		return instance;

	}

	public boolean insertUrlServer(String url_server) {
		truncateTable();
		sqliteDatabase = database.getWritableDatabase();
		boolean result = false;
		ContentValues content = new ContentValues();

		content.put("url", url_server);
		result = (sqliteDatabase.insert(table_name, null, content) > 0);

		sqliteDatabase.close();

		return result;
	}

	public String getUrlServer() {
		String urlServer = null;
		sqliteDatabase = database.getReadableDatabase();
		Cursor cursor = null;
		cursor = sqliteDatabase.rawQuery("SELECT URL FROM URL_SERVER", null);

		if (cursor.getCount()==0) {
			insertUrlServer(defaultUrl);
			return defaultUrl;
		}
		while (cursor.moveToNext()) {
			urlServer = cursor.getString(0);
		}
		
		database.close();
		
		Log.i( "IP CAPTURADO NA ServerUpdatesDao", urlServer );
		
		return urlServer;
	}

	private boolean truncateTable() {
		boolean result = false;
		sqliteDatabase = database.getWritableDatabase();
		result = (sqliteDatabase.delete(table_name, "", null) > 0);
		sqliteDatabase.close();
		return result;

	}

	public long getLastIdUpdate() {
		sqliteDatabase = database.getWritableDatabase();

		Cursor cursor = sqliteDatabase.rawQuery(
				"SELECT * FROM UPDATE order by ID_UPDATE DESC", null);
		long result = cursor.getLong(0);
		
		sqliteDatabase.close();
		return result;
	}

}
