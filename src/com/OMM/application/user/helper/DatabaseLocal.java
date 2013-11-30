package com.OMM.application.user.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseLocal extends SQLiteOpenHelper {

	private static String dbName = "OMM.db";
	private static String tabela_parlamentar = "CREATE TABLE [PARLAMENTAR] ([ID_PARLAMENTAR] VARCHAR(10),[NOME_PARLAMENTAR] VARCHAR(40),[PARTIDO] VARCHAR(25), [UF] VARCHAR(2),[SEGUIDO] BOOLEAN,[FOTO] VARBINARY);";
	private static String tabela_cota = "CREATE TABLE [COTA] (  [ID_COTA] VARCHAR(10),[ID_PARLAMENTAR] VARCHAR(10),[NUM_SUBCOTA] INT(10),[DESCRICAO] VARCHAR(40) ,  [MES] INTEGER,  [ANO] INTEGER, [VALOR] DOUBLE);";
	
	private static int version = 1;

	public DatabaseLocal(Context context) {
		super(context, dbName, null, version);
	}
	public DatabaseLocal(Context context, int version,String dbName) {
		super(context, dbName, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		/**
		 * Esse eh executado quando instalada aplicacao
		 */
		database.execSQL(tabela_parlamentar);
		database.execSQL(tabela_cota);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		/*
		 * Eh executado quando o usuario ja tem a aplicacao instalada e deseja
		 * instalar uma versao superior, com esse metodo vc consegue fazer
		 * melhor a atualizacao do banco da nova versao sem que o usuario perca
		 * as informa��es ja persistidas no celular por exemplo , suponha que o
		 * usuario deseja atualizar a versao 1 para 2 do aplicativo e nessa nova
		 * versao nao existe mais a tabela log e foi criada a tabela clientes
		 * ... veja como fica o corpo do metodo if(oldVersion==1) {
		 * if(newVersion ==2) { db.execSQL("DROP TABLE logs");
		 * db.execSQL("CREATE TABLE cliente...."); } }
		 */
		
		if(newVersion ==2)
		{
			db.execSQL("CREATE TABLE [PARLAMENTAR] ([ID_PARLAMENTAR] VARCHAR(10),[NOME_PARLAMENTAR] VARCHAR(40),[PARTIDO] VARCHAR(25), [UF] VARCHAR(2),[SEGUIDO] BOOLEAN,[FOTO] VARBINARY);");
			 
		}

	}

}
