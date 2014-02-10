package com.OMM.application.user.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDatabase extends SQLiteOpenHelper {

	private static String dbName = "OMM.db";
	private static String tabela_parlamentar = "CREATE TABLE [PARLAMENTAR] ([ID_PARLAMENTAR] VARCHAR(10),[NOME_PARLAMENTAR] VARCHAR(40),[PARTIDO] VARCHAR(25), [UF] VARCHAR(2), [VALOR] DOUBLE, [SEGUIDO] BOOLEAN,[FOTO] VARBINARY, [RANKING_POS] INT);";
	private static String tabela_cota = "CREATE TABLE [COTA] (  [ID_COTA] VARCHAR(10),[ID_PARLAMENTAR] VARCHAR(10),[NUM_SUBCOTA] INT(10),[DESCRICAO] VARCHAR(40) ,  [MES] INTEGER,  [ANO] INTEGER, [VALOR] DOUBLE);";
	private static String tabela_URL_SERVER = "CREATE TABLE [URL_SERVER] (  [URL] VARCHAR(1000));";

	private static int version = 1;

	public LocalDatabase(Context context) {
		super(context, dbName, null, version);
	}

	public LocalDatabase(Context context, int version, String dbName) {
		super(context, dbName, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {

		ContentValues content = new ContentValues();

		database.execSQL(tabela_parlamentar);
		database.execSQL(tabela_cota);
		database.execSQL(tabela_URL_SERVER);
		// content.put("URL", "env-6198716.jelastic.websolute.net.br");
		// database.insert(tabela_URL_SERVER, null, content);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		// implementar para novas versões do banco do app.

	}

}
