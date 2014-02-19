package com.OMM.test.user.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;
import android.content.Context;
import android.test.AndroidTestCase;

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

		// Parlamentar B
		parlamentarB.setId(7770);
		parlamentarB.setNome("Parlamentar Teste Dao Cota");
		parlamentarB.setSeguido(1);
		parlamentarB.setPartido("PU");
		parlamentarB.setUf("DF");

		// Parlamentar C
		parlamentarC.setId(888);
		parlamentarC.setNome("Parlamentar Teste Dao Cota");
		parlamentarC.setSeguido(1);
		parlamentarC.setPartido("PU");
		parlamentarC.setUf("DF");

		// if(parlamentarDao.checkEmptyLocalDatabase()){
		parlamentarDao.insertParlamentar(parlamentarB);
		parlamentarDao.insertParlamentar(parlamentarC);
		// }
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

		Iterator<Parlamentar> iterator = parlamentarDao.getAll().iterator();

		while (iterator.hasNext()) {
			Parlamentar p = iterator.next();
			parlamentarDao.deleteParlamentar(p);

		}

		assertTrue(parlamentarDao.checkEmptyLocalDatabase());
	}

	public void testInsertParlamentar() {
		assertTrue(parlamentarDao.insertParlamentar(parlamentarA));
	}

	public void testDeleteParlamentar() {
		assertTrue(parlamentarDao.deleteParlamentar(parlamentarB));
	}

	public void testUpdateParlamentar() throws NullParlamentarException {
		parlamentarA.setSeguido(1);
		assertTrue(parlamentarDao.setSeguidoParlamentar(parlamentarA));
	}

	public void testGetById() {
		Parlamentar parlamentar;
		parlamentar = parlamentarDao.getById(777);
		assertNotNull(parlamentar);
		assertSame(parlamentar.getClass(), Parlamentar.class);
	}

	public void testGetAll() {
		List<Parlamentar> lista = new ArrayList<Parlamentar>();
		lista = parlamentarDao.getAll();
		assertNotNull(lista);
		assertSame(lista.get(0).getClass(), Parlamentar.class);

	}

	public void testGetAllSelected() {

		List<Parlamentar> lista = new ArrayList<Parlamentar>();
		lista = parlamentarDao.getAllSelected();
		assertNotNull(lista);
		assertNotNull(lista.get(0));
		assertSame(lista.get(0).getClass(), Parlamentar.class);

	}

	public void getSelectedByName() {
		List<Parlamentar> lista = new ArrayList<Parlamentar>();
		lista = parlamentarDao.getSelectedByName("Parlamentar");
		assertNotNull(lista);
		assertNotNull(lista.get(0));
		assertSame(lista.get(0).getClass(), Parlamentar.class);
	}

}
