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

	private CotaParlamentarUserDao() {
		
	}
	
	public static CotaParlamentarUserDao getInstance() {

		if (instance == null) {
			instance = new CotaParlamentarUserDao();
		}

		return instance;

	}
	
	public boolean insertSeguido(Parlamentar po, CotaParlamentar cota) {
		SQLiteDatabase db = new DB(context).getWritableDatabase();
		ContentValues ctv = new ContentValues();

		ctv.put("ID_COTA", cota.getId());
		ctv.put("ID_PARLAMENTAR", cota.getIdParlamentar());
		ctv.put("DESCRICAO", cota.getDescricao());
		// TODO refatorar chamada no banco
		ctv.put("MES", 10);
		ctv.put("ANO", cota.getAno());
		ctv.put("VALOR", cota.getValorOutubro());
		ctv.put("NUM_SUBCOTA", cota.getNumeroSubCota());

		return (db.insert(nome_tabela, null, ctv) > 0);
	}

	public List<CotaParlamentar> getAll(Parlamentar parlamentar) {
		SQLiteDatabase db = new DB(context).getReadableDatabase();
		Cursor rs = db.rawQuery(
				"SELECT DESCRICAO , VALOR FROM COTA WHERE ID_PARLAMENTAR="
						+ parlamentar.getId(), null);
		List<CotaParlamentar> lista = new ArrayList<CotaParlamentar>();
		while (rs.moveToNext()) {
			CotaParlamentar po = new CotaParlamentar();
			po.setDescricao(rs.getString(0));
			po.setValorOutubro(rs.getDouble(1));
			lista.add(po);
		}
		return lista;
	}

	public boolean delete(Parlamentar parlamentar) {
		SQLiteDatabase db = new DB(context).getWritableDatabase();
		return (db.delete(nome_tabela, "ID_PARLAMENTAR=?",
				new String[] { parlamentar.getId() + "" }) > 0);
	}
}