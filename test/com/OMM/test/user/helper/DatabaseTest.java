package com.OMM.test.user.helper;



import java.io.File;

import junit.framework.Assert;
import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.test.ActivityInstrumentationTestCase2;

import com.OMM.application.user.helper.DatabaseLocal;
import com.OMM.application.user.view.GuiMain;

@SuppressLint("NewApi")
public class DatabaseTest extends ActivityInstrumentationTestCase2<GuiMain>{

	private DatabaseLocal db;
	private GuiMain context;
	 
	
	private SQLiteDatabase database;
	
	public DatabaseTest(String name)
	{
		super(GuiMain.class);
		setName(name);
		
	}
	public DatabaseTest ()
	{
		this("DBTest");
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		context=getActivity();
		
		//Apagar o banco para testar o metodo onCreate
		SQLiteDatabase database=new DatabaseLocal(context).getWritableDatabase();
		File file= new File("/data/data/com.OMM.application.user/databases/OMM.db");
		database.deleteDatabase(file);
		
		
	}
	

	protected void tearDown() throws Exception {
		super.tearDown();
	}


	public void testOnCreateSQLiteDatabase() 
	{
		 
		
		
		db=new DatabaseLocal(context);
		database= new DatabaseLocal(context).getWritableDatabase();
		
		Assert.assertEquals(db.getClass(),DatabaseLocal.class);
		Assert.assertEquals(database.getClass(),SQLiteDatabase.class);
		Assert.assertEquals(database.getPath(), "/data/data/com.OMM.application.user/databases/OMM.db");
	  
	}

	public void testOnUpgradeSQLiteDatabaseIntInt() 
	{
		
		SQLiteDatabase database=new DatabaseLocal(context,2,"OMM2.db").getWritableDatabase();
		Assert.assertEquals(database.getPath(), "/data/data/com.OMM.application.user/databases/OMM2.db");
		 
		
		
	}

}
