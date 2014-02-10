package com.OMM.application.user.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.OMM.application.user.helper.LocalDatabase;

public class ServerListenerDao {

	private static ServerListenerDao instance=null;
	private LocalDatabase database;
	private String table_name="URL_SERVER";
	private SQLiteDatabase sqliteDatabase;
	
	private ServerListenerDao(Context context) 
	{
		this.database= new LocalDatabase(context);
	}
	
	public static ServerListenerDao getInstance(Context context)
	{
		 
		if (instance ==null) instance =new ServerListenerDao(context);
		return instance;
		
	}
	public boolean insertUrlServer(String url_server)
	{
		truncateTable();
		sqliteDatabase = database.getWritableDatabase();
		boolean result=false;
		ContentValues content= new ContentValues();
		
		content.put("url", url_server);
		result = (sqliteDatabase.insert(table_name, null, content) > 0);
		
		sqliteDatabase.close();
		
		return result;
	}
	
	public String getUrlServer()
	{
		String urlServer=null;
		sqliteDatabase =database.getReadableDatabase();
		Cursor cursor=null;
		cursor=sqliteDatabase.rawQuery("SELECT URL FROM URL_SERVER", null);
				
		
		while(cursor.moveToNext())
		{
			urlServer=cursor.getString(0);
		}
		database.close();
		return urlServer;
	}
	
	private boolean truncateTable()
	{
		boolean result=false;
		sqliteDatabase=database.getWritableDatabase();
		result=(sqliteDatabase.delete(table_name, "", null)>0);
		sqliteDatabase.close();
		return result;
		
	}

}
