package com.OMM.test.user.helper;



import junit.framework.Assert;
import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.test.ActivityInstrumentationTestCase2;

import com.OMM.application.user.helper.DB;
import com.OMM.application.user.view.GuiMain;

@SuppressLint("NewApi")
public class DBTest extends ActivityInstrumentationTestCase2<GuiMain>{

	private DB db;
	private GuiMain context;
	 
	
	private SQLiteDatabase database;
	
	public DBTest(String name)
	{
		super(GuiMain.class);
		setName(name);
		
	}
	public DBTest ()
	{
		this("DBTest");
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		context=getActivity();
		
		
	}
	

	protected void tearDown() throws Exception {
		super.tearDown();
	}


	public void testOnCreateSQLiteDatabase() 
	{
		 
		
		
		db=new DB(context);
		database= new DB(context).getWritableDatabase();
		
		Assert.assertEquals(db.getClass(),DB.class);
		Assert.assertEquals(database.getClass(),SQLiteDatabase.class);
		Assert.assertEquals(database.getPath(), "/data/data/com.OMM.application.user/databases/OMM.db");
	  
	}

	public void testOnUpgradeSQLiteDatabaseIntInt() {
		db=new DB(context,2);
		Assert.assertEquals(db.getClass(),DB.class);
		 
		
		
	}

}
