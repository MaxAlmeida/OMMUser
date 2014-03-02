package com.OMM.application.user.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.helper.LocalDatabase;
import com.OMM.application.user.model.Parlamentar;

public class ParlamentarUserDao {

	private static String tabelaParlamentar = "PARLAMENTAR";
	private static String[] colunasParlamentar = { "ID_PARLAMENTAR,ID_ATUALIZACAO,NOME_PARLAMENTAR,PARTIDO,UF,SEGUIDO,VALOR,RANKING_POS" };
	private static ParlamentarUserDao instance;
	private static LocalDatabase database;
	private static SQLiteDatabase sqliteDatabase;

	@SuppressWarnings("static-access")
	private ParlamentarUserDao(Context context) {
		this.database = new LocalDatabase(context);
	}

	public static ParlamentarUserDao getInstance(Context context) {

		if (instance == null) {
			instance = new ParlamentarUserDao(context);
		}

		return instance;
	}

	public boolean checkEmptyLocalDatabase() {

		sqliteDatabase = database.getWritableDatabase();
		boolean result = false;

		List<Parlamentar> parlamentares = new ArrayList<Parlamentar>();

		parlamentares = getAll();

		if (parlamentares.isEmpty()) {
			result = true;
		}
		sqliteDatabase.close();
		return result;
	}

	public boolean insertParlamentar(Parlamentar parlamentar) {

		sqliteDatabase = database.getWritableDatabase();
		ContentValues content = new ContentValues();

		content.put("ID_PARLAMENTAR", parlamentar.getId());
		content.put("NOME_PARLAMENTAR", parlamentar.getNome());
		content.put("SEGUIDO", parlamentar.getIsSeguido());
		content.put("PARTIDO", parlamentar.getPartido());
		content.put("UF", parlamentar.getUf());
		content.put("VALOR", parlamentar.getValor());
		content.put("RANKING_POS", parlamentar.getMajorRankingPos());
		content.put("ID_ATUALIZACAO", parlamentar.getIdUpdate());
		boolean result = (sqliteDatabase.insert(tabelaParlamentar, null,
				content) > 0);
		sqliteDatabase.close();
		return result;
	}

	public boolean deleteParlamentar(Parlamentar parlamentar) {

		sqliteDatabase = database.getWritableDatabase();
		boolean result = (sqliteDatabase.delete(tabelaParlamentar,
				"ID_PARLAMENTAR=?", new String[] { parlamentar.getId() + "" }) > 0);
		sqliteDatabase.close();
		return result;
	}

	public boolean setSeguidoParlamentar(Parlamentar parlamentar)
			throws NullParlamentarException {

		if (parlamentar != null) {
			sqliteDatabase = database.getWritableDatabase();
			ContentValues content = new ContentValues();
			content.put("SEGUIDO", parlamentar.getIsSeguido());
			boolean result = (sqliteDatabase.update(tabelaParlamentar, content,
					"ID_PARLAMENTAR=?",
					new String[] { parlamentar.getId() + "" }) > 0);
			sqliteDatabase.close();
			return result;
		} else {
			throw new NullParlamentarException();
		}
	}

	public boolean updateParlamentar(Parlamentar parlamentar)
			throws NullParlamentarException {
		if (parlamentar != null) {
			sqliteDatabase = database.getWritableDatabase();
			ContentValues content = new ContentValues();
			content.put("VALOR", parlamentar.getValor());
			content.put("RANKING_POS", parlamentar.getMajorRankingPos());
			content.put("ID_ATUALIZACAO", parlamentar.getIdUpdate());

			boolean result = (sqliteDatabase.update(tabelaParlamentar, content,
					"ID_PARLAMENTAR=?",
					new String[] { parlamentar.getId() + "" }) > 0);

			sqliteDatabase.close();
			return result;

		} else {
			throw new NullParlamentarException();
		}
	}

	public Parlamentar getById(Integer ID_PARLAMENTAR) {

		sqliteDatabase = database.getReadableDatabase();
		Cursor cursor = sqliteDatabase.query(tabelaParlamentar,
				colunasParlamentar, "ID_PARLAMENTAR=?",
				new String[] { ID_PARLAMENTAR.toString() }, null, null, null);

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
			parlamentar.setValor(cursor.getDouble(cursor
					.getColumnIndex("VALOR")));
			parlamentar.setMajorRankingPos(cursor.getInt(cursor
					.getColumnIndex("RANKING_POS")));
			parlamentar.setIdUpdate(cursor.getInt(cursor
					.getColumnIndex("ID_ATUALIZACAO")));
		}
		sqliteDatabase.close();
		return parlamentar;
	}

	public List<Parlamentar> getAll() {

		sqliteDatabase = database.getReadableDatabase();
		Cursor cursor = sqliteDatabase.rawQuery(
				"SELECT * FROM PARLAMENTAR order by VALOR DESC", null);

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
			parlamentar.setValor(cursor.getDouble(cursor
					.getColumnIndex("VALOR")));
			parlamentar.setMajorRankingPos(cursor.getInt(cursor
					.getColumnIndex("RANKING_POS")));
			parlamentar.setIdUpdate(cursor.getInt(cursor
					.getColumnIndex("ID_ATUALIZACAO")));
			listParlamentares.add(parlamentar);
		}
		sqliteDatabase.close();
		return listParlamentares;
	}

	public List<Parlamentar> getSelectedByName(String nameParlamentar) {

		sqliteDatabase = database.getReadableDatabase();
		Cursor cursor = sqliteDatabase.rawQuery(
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
			parlamentar.setValor(cursor.getDouble(cursor
					.getColumnIndex("VALOR")));
			parlamentar.setMajorRankingPos(cursor.getInt(cursor
					.getColumnIndex("RANKING_POS")));
			listParlamentar.add(parlamentar);
			parlamentar.setIdUpdate(cursor.getInt(cursor
					.getColumnIndex("ID_ATUALIZACAO")));
		}
		sqliteDatabase.close();
		return listParlamentar;
	}

	public List<Parlamentar> getAllSelected() {
		sqliteDatabase = database.getReadableDatabase();
		Cursor cursor = sqliteDatabase.rawQuery(
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
			parlamentar.setValor(cursor.getDouble(cursor
					.getColumnIndex("VALOR")));
			parlamentar.setMajorRankingPos(cursor.getInt(cursor
					.getColumnIndex("RANKING_POS")));
			parlamentar.setIdUpdate(cursor.getInt(cursor
					.getColumnIndex("ID_ATUALIZACAO")));
			listParlamentar.add(parlamentar);
		}
		sqliteDatabase.close();
		return listParlamentar;
	}

	public List<Integer> getAllSelectedIds() {
		sqliteDatabase = database.getReadableDatabase();
		Cursor cursor = sqliteDatabase.rawQuery(
				"SELECT ID_PARLAMENTAR FROM PARLAMENTAR WHERE SEGUIDO IN(1)",
				null);

		List<Integer> parlamentaresIds = new ArrayList<Integer>();

		while (cursor.moveToNext()) {
			Integer integer = cursor.getInt(cursor
					.getColumnIndex("ID_PARLAMENTAR"));
			parlamentaresIds.add(integer);
		}
		sqliteDatabase.close();
		return parlamentaresIds;

	}

	public int getIdUpdateParlamentar(Integer idParlamentar) {
		sqliteDatabase = database.getReadableDatabase();
		Cursor cursor = sqliteDatabase.query(tabelaParlamentar,
				colunasParlamentar, "ID_PARLAMENTAR=?",
				new String[] { idParlamentar.toString() }, null, null, null);

		if (cursor.moveToFirst()) {

			int idUpdate = cursor.getInt(cursor
					.getColumnIndex("ID_ATUALIZACAO"));
			sqliteDatabase.close();

			return idUpdate;
		}
		sqliteDatabase.close();
		return 0;
	}
}
