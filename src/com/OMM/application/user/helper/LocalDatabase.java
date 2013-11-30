package com.OMM.application.user.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDatabase extends SQLiteOpenHelper {

	private static String dbName = "OMM.db";
	private static String tabela_parlamentar = "CREATE TABLE [PARLAMENTAR] ([ID_PARLAMENTAR] VARCHAR(10),[NOME_PARLAMENTAR] VARCHAR(40),[PARTIDO] VARCHAR(25), [UF] VARCHAR(2),[SEGUIDO] BOOLEAN,[FOTO] VARBINARY);";
	private static String tabela_cota = "CREATE TABLE [COTA] (  [ID_COTA] VARCHAR(10),[ID_PARLAMENTAR] VARCHAR(10),[NUM_SUBCOTA] INT(10),[DESCRICAO] VARCHAR(40) ,  [MES] INTEGER,  [ANO] INTEGER, [VALOR] DOUBLE);";
	
	private static int version = 1;

	public LocalDatabase(Context context) {
		super(context, dbName, null, version);
	}
	public LocalDatabase(Context context, int version,String dbName) {
		super(context, dbName, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {

		database.execSQL(tabela_parlamentar);
		database.execSQL(tabela_cota);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		if(newVersion ==2)
		{
			db.execSQL("CREATE TABLE [PARLAMENTAR] ([ID_PARLAMENTAR] VARCHAR(10),[NOME_PARLAMENTAR] VARCHAR(40),[PARTIDO] VARCHAR(25), [UF] VARCHAR(2),[SEGUIDO] BOOLEAN,[FOTO] VARBINARY);");
			 
		}

	}

}
