package com.OMM.application.user.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.OMM.application.user.helper.DB;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;

public class CotaParlamentarUserDao {

	private static String nome_tabela = "COTA";
	private static Context context;
    private static CotaParlamentarUserDao instance;
	// private static String[] colunas = {
	// "ID_COTA,ID_PARLAMENTAR, NUM_SUBCOTA ,DESCRICAO,MES,ANO,VALOR" };
	// private static Parlamentar parlamentar;

	private CotaParlamentarUserDao(Context context) {
		this.context=context;
		// Empty Constructor
	}
	
	public static CotaParlamentarUserDao getInstance(Context context) {

		if (instance == null)
		{
			instance = new CotaParlamentarUserDao(context);
		}

		return instance;
	}
	
	public boolean insertFollowed(Parlamentar po, CotaParlamentar cota) {
		
		SQLiteDatabase database = new DB(context).getWritableDatabase();
		ContentValues content = new ContentValues();

		content.put("ID_COTA", cota.getId());
		content.put("ID_PARLAMENTAR", cota.getIdParlamentar());
		content.put("DESCRICAO", cota.getDescricao());
		
		content.put("MES", 10);
		content.put("ANO", cota.getAno());
		content.put("VALOR", cota.getValorOutubro());
		content.put("NUM_SUBCOTA", cota.getNumeroSubCota());

		return (database.insert(nome_tabela, null, content) > 0);
	}

	public List<CotaParlamentar> getAllCotas(Parlamentar parlamentar) {
		
		SQLiteDatabase database = new DB(context).getReadableDatabase();
		
		Cursor cursor = database.rawQuery(
				"SELECT DESCRICAO , VALOR FROM COTA WHERE ID_PARLAMENTAR="
						+ parlamentar.getId(), null);
		
		List<CotaParlamentar> listCotas = new ArrayList<CotaParlamentar>();
		
		while (cursor.moveToNext()) {
			
			CotaParlamentar cota = new CotaParlamentar();
			cota.setDescricao(cursor.getString(0));
			cota.setValorOutubro(cursor.getDouble(1));
			
			listCotas.add(cota);
		}
		
		return listCotas;
	}

	public boolean deleteParlamentar(Parlamentar parlamentar) {
		
		SQLiteDatabase database = new DB(context).getWritableDatabase();
		
		return (database.delete(nome_tabela, "ID_PARLAMENTAR=?",
				new String[] { parlamentar.getId() + "" }) > 0);
	}
}