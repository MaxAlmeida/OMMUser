package com.OMM.test.user.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;
import android.content.Context;
import android.test.AndroidTestCase;
import android.util.Log;

import com.OMM.application.user.dao.ParlamentarUserDao;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.model.Parlamentar;

public class ParlamentarUserDaoTest extends AndroidTestCase {

	private Context context;
	private ParlamentarUserDao parlamentarDao;
	private Parlamentar parlamentarA, parlamentarB, parlamentarC;

	protected void setUp() {
		this.context = getContext();
		parlamentarDao = ParlamentarUserDao.getInstance(context);
		parlamentarA = new Parlamentar();
		parlamentarB = new Parlamentar();
		parlamentarC = new Parlamentar();

		// Parlamentar A
		parlamentarA.setId(777);
		parlamentarA.setNome("Parlamentar Teste Insert");
		parlamentarA.setSeguido(0);
		parlamentarA.setPartido("PU");
		parlamentarA.setUf("DF");
		parlamentarA.setMinorRankingPos(1);
		parlamentarA.setMajorRankingPos(3);
		parlamentarA.setValor(10);
		// Parlamentar B
		parlamentarB.setId(7770);
		parlamentarB.setNome("Parlamentar Teste Dao Cota");
		parlamentarB.setSeguido(1);
		parlamentarB.setPartido("PU");
		parlamentarB.setUf("DF");
		parlamentarB.setMinorRankingPos(2);
		parlamentarB.setMajorRankingPos(2);
		parlamentarB.setValor(20);

		// Parlamentar C
		parlamentarC.setId(888);
		parlamentarC.setNome("Parlamentar Teste Dao Cota");
		parlamentarC.setSeguido(1);
		parlamentarC.setPartido("PU");
		parlamentarC.setUf("DF");
		parlamentarC.setMinorRankingPos(3);
		parlamentarC.setMajorRankingPos(1);
		parlamentarC.setValor(30);
		
		parlamentarDao.insertParlamentar(parlamentarB);
		parlamentarDao.insertParlamentar(parlamentarC);
		parlamentarDao.insertParlamentar(parlamentarA);
	}

	protected void tearDown(){
		try{
		parlamentarDao.deleteParlamentar(parlamentarB);
		parlamentarDao.deleteParlamentar(parlamentarC);
		parlamentarDao.deleteParlamentar(parlamentarA);}
		catch(Exception e){
			
		}
	}
	
	public void testGetInstance() {
		ParlamentarUserDao parlamentarDao1 = ParlamentarUserDao
				.getInstance(context);
		ParlamentarUserDao parlamentarDao2 = ParlamentarUserDao
				.getInstance(context);
		Assert.assertSame(parlamentarDao1, parlamentarDao2);
	}

	public void testCheckEmptyLocalDatabase() {
		assertFalse(parlamentarDao.checkEmptyLocalDatabase());
	}

	public void testCheckEmptyLocalDatabaseTrue() {

		Iterator<Parlamentar> iterator = parlamentarDao.getMajorRanking().iterator();

		while (iterator.hasNext()) {
			Parlamentar p = iterator.next();
			parlamentarDao.deleteParlamentar(p);

		}

		assertTrue(parlamentarDao.checkEmptyLocalDatabase());
	}

	public void testInsertParlamentar() {
		assertTrue(parlamentarDao.insertParlamentar(parlamentarB));
	}

	public void testDeleteParlamentar() {
		assertTrue(parlamentarDao.deleteParlamentar(parlamentarB));
	}

	public void testsetSeguidoParlamentar() throws NullParlamentarException {
		parlamentarA.setSeguido(1);
		assertTrue(parlamentarDao.setSeguidoParlamentar(parlamentarA));
	}

	public void testGetById() {
		Parlamentar parlamentar;
		parlamentar = parlamentarDao.getById(777);
		assertNotNull(parlamentar);
		assertSame(parlamentar.getClass(), Parlamentar.class);
	}

	public void testGetMajorRanking() {
		List<Parlamentar> lista = new ArrayList<Parlamentar>();
		lista = parlamentarDao.getMajorRanking();
		assertNotNull(lista);
		assertEquals(lista.get(0).getMajorRankingPos(),1);
		assertEquals(lista.get(lista.size()-1).getMajorRankingPos(),3);

	}

	public void testGetAllSelected() {

		List<Parlamentar> lista = new ArrayList<Parlamentar>();
		lista = parlamentarDao.getAllSelected();
		assertNotNull(lista);
		assertNotNull(lista.get(0));
		assertSame(lista.get(0).getClass(), Parlamentar.class);

	}
	
	public void testGetMinorRanking() {
		List<Parlamentar> lista = new ArrayList<Parlamentar>();
		lista = parlamentarDao.getMinorRanking();
		assertNotNull(lista);
		assertEquals(lista.get(0).getMinorRankingPos(), 1);
		assertEquals(lista.get(lista.size()-1).getMinorRankingPos(), 3);
	}

	public void testGetSelectedByName() {
		List<Parlamentar> lista = new ArrayList<Parlamentar>();
		lista = parlamentarDao.getSelectedByName("Parlamentar");
		assertNotNull(lista);
		assertNotNull(lista.get(0));
		assertSame(lista.get(0).getClass(), Parlamentar.class);
	}

	public void testUpdateParlamentar() throws NullParlamentarException{
		// Parlamentar A
		parlamentarA.setId(777);
		parlamentarA.setSeguido(0);
		parlamentarA.setPartido("PU");
		parlamentarA.setUf("DF");
		parlamentarA.setIdUpdate(2);
		parlamentarA.setValor(300.0f);
		
		parlamentarDao.updateParlamentar(parlamentarA);
		
		Parlamentar p = parlamentarDao.getById(parlamentarA.getId());
		
		assertEquals(parlamentarA.getIdUpdate(), p.getIdUpdate());
		assertEquals(parlamentarA.getValor(), p.getValor());
	}
	
	public void testGetAllSelectedIds(){
		List<Parlamentar> list = parlamentarDao.getAllSelected();
		List<Integer> ids = new ArrayList<Integer>();
		Iterator<Parlamentar> i = list.iterator();
		while(i.hasNext()){
			int id = i.next().getId();
			ids.add(id);
			Log.i("DaoAllSelectedIds", "Id "+id);
		}
		assertEquals(ids, parlamentarDao.getAllSelectedIds());
	}
	
	public void testGetIdUpdateParlamentar(){
		
		int idUpdate = parlamentarDao.getIdUpdateParlamentar(parlamentarA.getId());
		
		assertEquals(parlamentarA.getIdUpdate(), idUpdate);
	}
}
