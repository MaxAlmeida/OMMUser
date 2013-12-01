package com.OMM.test.user.helper;

import java.io.File;

import junit.framework.Assert;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.OMM.application.user.helper.LocalDatabase;

@SuppressLint( "NewApi" )
public class LocalDatabaseTest extends AndroidTestCase
{

	private LocalDatabase db;
	private Context context;
	private SQLiteDatabase database;

	@SuppressLint("SdCardPath")
	protected void setUp( )
	{

		this.context = getContext();

		// Apagar o banco para testar o metodo onCreate
		@SuppressWarnings("unused")
		SQLiteDatabase database = new LocalDatabase(context)
				.getWritableDatabase();
		File file = new File(
				"/data/data/com.OMM.application.user/databases/OMM.db");
		SQLiteDatabase.deleteDatabase(file);
	}

	@SuppressLint("SdCardPath")
	public void testOnCreateSQLiteDatabase( )
	{
		db = new LocalDatabase(context);
		database = new LocalDatabase(context).getWritableDatabase();

		Assert.assertEquals(db.getClass(), LocalDatabase.class);
		Assert.assertEquals(database.getClass(), SQLiteDatabase.class);
		Assert.assertEquals(database.getPath(),
				"/data/data/com.OMM.application.user/databases/OMM.db");
	}

	@SuppressLint("SdCardPath")
	public void testOnUpgradeSQLiteDatabaseIntInt( )
	{

		SQLiteDatabase database = new LocalDatabase(context, 2, "OMM2.db")
				.getWritableDatabase();
		Assert.assertEquals(database.getPath(),
				"/data/data/com.OMM.application.user/databases/OMM2.db");

	}

}
