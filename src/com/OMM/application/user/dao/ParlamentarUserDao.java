package com.OMM.application.user.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.OMM.application.user.helper.DB;
import com.OMM.application.user.model.Parlamentar;

public class ParlamentarUserDao {
	
	private static String nome_tabela="PARLAMENTAR";
	private static Context context;
	private static String[] colunas={"ID_PARLAMENTAR,NOME_PARLAMENTAR"};
	private  static ParlamentarUserDao instance;
	
	@SuppressWarnings("static-access")
	private ParlamentarUserDao(Context context)
	{
		this.context=context;
	}
	
	public static ParlamentarUserDao getInstance(Context context) {

		if (instance == null) {
			instance = new ParlamentarUserDao(context);
		}

		return instance;

	}
	
	public boolean insert(Parlamentar po)
{
	SQLiteDatabase db=new DB(context).getWritableDatabase();
	ContentValues ctv = new ContentValues();
	
	ctv.put("ID_PARLAMENTAR", po.getId());
	ctv.put("NOME_PARLAMENTAR", po.getNome());
	ctv.put("SEGUIDO", po.isSeguido());
		
	
	return (db.insert(nome_tabela,null, ctv)>0);
}
	
	public boolean delete(Parlamentar po){
	SQLiteDatabase db=new DB(context).getWritableDatabase();
	return(db.delete(nome_tabela,"ID_PARLAMENTAR=?",new String[]{po.getId()+""})>0);
	}
	
	public boolean update(Parlamentar po){
	SQLiteDatabase db= new DB(context).getWritableDatabase();
	ContentValues ctv = new ContentValues();
	
	ctv.put("SEGUIDO", po.isSeguido());
	
	return(	db.update(nome_tabela, ctv, "ID_PARLAMENTAR=?", new String[]{po.getId()+""})>0);

	}
	
	
	
	public Parlamentar getById(Integer ID_PARLAMENTAR)
	{
		SQLiteDatabase db= new DB(context).getReadableDatabase();
		
		Cursor rs= db.query(nome_tabela, colunas, "ID_PARLAMENTAR=?",new String[]{ID_PARLAMENTAR.toString()},null,null,null);
		
		Parlamentar po=null;
		if(rs.moveToFirst())
		{
			po= new Parlamentar();
			po.setId(Integer.parseInt(rs.getString(rs.getColumnIndex("ID_CLIENTE"))));
			po.setNome(rs.getString(rs.getColumnIndex("NOME_PARLAMENTAR")));
		
			
		}
		return po;
	}
	public List<Parlamentar> getAll()
	{
		SQLiteDatabase db= new DB(context).getReadableDatabase();
		Cursor rs = db.rawQuery("SELECT ID_PARLAMENTAR,NOME_PARLAMENTAR FROM PARLAMENTAR", null);
		List <Parlamentar> lista= new ArrayList<Parlamentar>();
		while(rs.moveToNext())
		{
			Parlamentar po = new Parlamentar();
			po.setId(rs.getInt(0));
			po.setNome(rs.getString(1));
			
			lista.add(po);
			
		}
		return lista;
	}
	/*
	 * Metodo utilizado para realizar o filtro de parlamentares
	 * ele deve ser trabalhado melhor para condição de nao encontrar
	 * um parlamentar
	 */
	public List<Parlamentar> getSelected(String nomeParlamentar)
	{
		
		SQLiteDatabase db= new DB(context).getReadableDatabase();
		Cursor rs = db.rawQuery("SELECT ID_PARLAMENTAR,NOME_PARLAMENTAR FROM PARLAMENTAR WHERE NOME_PARLAMENTAR LIKE '%"+nomeParlamentar+"%'", null);
		List <Parlamentar> lista= new ArrayList<Parlamentar>();
		while(rs.moveToNext())
		{
			Parlamentar po = new Parlamentar();
			po.setId(rs.getInt(0));
			po.setNome(rs.getString(1));
			
			lista.add(po);
			
		}
		
		return lista;
	}
	
	public List<Parlamentar> getAllSelected() {
		SQLiteDatabase db = new DB(context).getReadableDatabase();
		Cursor rs = db
				.rawQuery(
						"SELECT ID_PARLAMENTAR,NOME_PARLAMENTAR FROM PARLAMENTAR WHERE SEGUIDO IN(1)",
						null);
		List<Parlamentar> lista = new ArrayList<Parlamentar>();
		while (rs.moveToNext()) {
			Parlamentar po = new Parlamentar();
			po.setId(rs.getInt(0));
			po.setNome(rs.getString(1));

			lista.add(po);

		}
		return lista;
	}
}
