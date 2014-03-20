package com.OMM.application.user.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.OMM.application.user.helper.LocalDatabase;

public class UrlHostDao {

	private static UrlHostDao instance = null;
	private LocalDatabase database;
	private String table_name = "URL_SERVER";
	private SQLiteDatabase sqliteDatabase;
	private String defaultUrl = "192.168.1.4:8080/OlhaMinhaMesada";
	//private String defaultUrl="env-6198716.jelastic.websolute.net.br";
	private UrlHostDao(Context context) {
		this.database = new LocalDatabase(context);
	}

	public static UrlHostDao getInstance(Context context) {

		if (instance == null)
			instance = new UrlHostDao(context);
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
		return urlServer;
	}

	private boolean truncateTable() {
		boolean result = false;
		sqliteDatabase = database.getWritableDatabase();
		result = (sqliteDatabase.delete(table_name, "", null) > 0);
		sqliteDatabase.close();
		return result;

	}

}
