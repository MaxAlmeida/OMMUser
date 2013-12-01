package com.OMM.application.user.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.helper.LocalDatabase;
import com.OMM.application.user.model.Parlamentar;

public class ParlamentarUserDao {

	// TODO:Fazer try catch do banco

	private static String nome_tabela = "PARLAMENTAR";
	private static String[] colunas = { "ID_PARLAMENTAR,NOME_PARLAMENTAR,PARTIDO,UF,SEGUIDO" };
	private static ParlamentarUserDao instance;
	private static SQLiteDatabase database;


	@SuppressWarnings("static-access")
	private ParlamentarUserDao(Context context) {
		this.database = new LocalDatabase(context).getWritableDatabase();
	}

	public static ParlamentarUserDao getInstance(Context context) {

		if (instance == null) {
			instance = new ParlamentarUserDao(context);
		}

		return instance;
	}

	public boolean checkEmptyLocalDatabase() {

		boolean result = false;

		List<Parlamentar> parlamentares = new ArrayList<Parlamentar>();

		parlamentares = getAll();

		if (parlamentares.isEmpty()) {
			result = true;
		}

		Log.i("ParlamentarUserDao", "Result : " + result);

		return result;
	}

	public boolean insertParlamentar(Parlamentar parlamentar) {

		ContentValues content = new ContentValues();

		content.put("ID_PARLAMENTAR", parlamentar.getId());
		content.put("NOME_PARLAMENTAR", parlamentar.getNome());
		content.put("SEGUIDO", parlamentar.getIsSeguido());
		content.put("PARTIDO", parlamentar.getPartido());
		content.put("UF", parlamentar.getUf());

		return (database.insert(nome_tabela, null, content) > 0);
	}

	public boolean deleteParlamentar(Parlamentar parlamentar) {

		return (database.delete(nome_tabela, "ID_PARLAMENTAR=?",
				new String[] { parlamentar.getId() + "" }) > 0);
	}

	public boolean updateParlamentar(Parlamentar parlamentar) throws NullParlamentarException {

		if(parlamentar != null){
		ContentValues content = new ContentValues();

		content.put("SEGUIDO", parlamentar.getIsSeguido());

		return (database.update(nome_tabela, content, "ID_PARLAMENTAR=?",
				new String[] { parlamentar.getId() + "" }) > 0);
		}
		else {
			throw new NullParlamentarException();
		}
	}

	public Parlamentar getById(Integer ID_PARLAMENTAR) {

		Cursor cursor = database.query(nome_tabela, colunas,
				"ID_PARLAMENTAR=?", new String[] { ID_PARLAMENTAR.toString() },
				null, null, null);

		Parlamentar parlamentar = new Parlamentar();

		if (cursor.moveToFirst()) {

			parlamentar.setId(Integer.parseInt(cursor.getString(cursor
					.getColumnIndex("ID_PARLAMENTAR"))));
			parlamentar.setNome(cursor.getString(cursor
					.getColumnIndex("NOME_PARLAMENTAR")));
			parlamentar.setSeguido(cursor.getInt(cursor
					.getColumnIndex("SEGUIDO")));
			parlamentar.setPartido(cursor.getString(cursor
					.getColumnIndex("PARTIDO")));
			parlamentar.setUf(cursor.getString(cursor.getColumnIndex("UF")));

		}

		return parlamentar;
	}

	public List<Parlamentar> getAll() {

		Cursor cursor = database.rawQuery("SELECT * FROM PARLAMENTAR", null);
		List<Parlamentar> listParlamentares = new ArrayList<Parlamentar>();

		while (cursor.moveToNext()) {

			Parlamentar parlamentar = new Parlamentar();
			parlamentar.setId(cursor.getInt(cursor
					.getColumnIndex("ID_PARLAMENTAR")));
			parlamentar.setNome(cursor.getString(cursor
					.getColumnIndex("NOME_PARLAMENTAR")));
			parlamentar.setSeguido(cursor.getInt(cursor
					.getColumnIndex("SEGUIDO")));
			parlamentar.setPartido(cursor.getString(cursor
					.getColumnIndex("PARTIDO")));
			parlamentar.setUf(cursor.getString(cursor.getColumnIndex("UF")));
			listParlamentares.add(parlamentar);
		}

		return listParlamentares;
	}

	public List<Parlamentar> getSelectedByName(String nameParlamentar) {

		Cursor cursor = database.rawQuery(
				"SELECT * FROM PARLAMENTAR WHERE NOME_PARLAMENTAR LIKE '%"
						+ nameParlamentar + "%'", null);

		List<Parlamentar> listParlamentar = new ArrayList<Parlamentar>();

		while (cursor.moveToNext()) {

			Parlamentar parlamentar = new Parlamentar();
			parlamentar.setId(cursor.getInt(cursor
					.getColumnIndex("ID_PARLAMENTAR")));
			parlamentar.setNome(cursor.getString(cursor
					.getColumnIndex("NOME_PARLAMENTAR")));
			parlamentar.setSeguido(cursor.getInt(cursor
					.getColumnIndex("SEGUIDO")));
			parlamentar.setPartido(cursor.getString(cursor
					.getColumnIndex("PARTIDO")));
			parlamentar.setUf(cursor.getString(cursor.getColumnIndex("UF")));
			listParlamentar.add(parlamentar);
		}

		return listParlamentar;
	}

	public List<Parlamentar> getAllSelected() {

		Cursor cursor = database.rawQuery(
				"SELECT * FROM PARLAMENTAR WHERE SEGUIDO IN(1)", null);

		List<Parlamentar> listParlamentar = new ArrayList<Parlamentar>();

		while (cursor.moveToNext()) {

			Parlamentar parlamentar = new Parlamentar();
			parlamentar.setId(cursor.getInt(cursor
					.getColumnIndex("ID_PARLAMENTAR")));
			parlamentar.setNome(cursor.getString(cursor
					.getColumnIndex("NOME_PARLAMENTAR")));
			parlamentar.setSeguido(1);
			parlamentar.setPartido(cursor.getString(cursor
					.getColumnIndex("PARTIDO")));
			parlamentar.setUf(cursor.getString(cursor.getColumnIndex("UF")));
			listParlamentar.add(parlamentar);
		}

		return listParlamentar;
	}
}
