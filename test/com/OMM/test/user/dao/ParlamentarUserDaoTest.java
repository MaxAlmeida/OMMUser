package com.OMM.test.user.dao;

import java.util.ArrayList;
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
	private Parlamentar parlamentarA,parlamentarB,parlamentarC;
	
	
	protected void setUp() 
	{
		this.context = getContext();
		parlamentarDao=ParlamentarUserDao.getInstance(context);
		parlamentarA =new Parlamentar();
		parlamentarB=new Parlamentar();
		parlamentarC = new Parlamentar();
		
		//Parlamentar A
		
		parlamentarA.setId(777);
		parlamentarA.setNome("Parlamentar Teste Insert");
		parlamentarA.setSeguido(0);
		
		//Parlamentar B
		parlamentarB.setId(7770);
		parlamentarB.setNome("Parlamentar Teste Dao Cota");
		parlamentarB.setSeguido(0);
		
		//Parlamentar C
		parlamentarB.setId(888);
		parlamentarB.setNome("Parlamentar Teste Dao Cota");
		parlamentarB.setSeguido(1);
		
		parlamentarDao.insertParlamentar(parlamentarB);
		parlamentarDao.insertParlamentar(parlamentarC);
		
	}

	public void testGetInstance() 
	{
		ParlamentarUserDao parlamentarDao1=ParlamentarUserDao.getInstance(context);
		ParlamentarUserDao parlamentarDao2=ParlamentarUserDao.getInstance(context);
		Assert.assertSame(parlamentarDao1, parlamentarDao2);
	}

	public void testCheckEmptyLocalDatabase() 
	{
		Assert.assertFalse(parlamentarDao.checkEmptyLocalDatabase());
	}

	public void testInsertParlamentar()
	{
		Assert.assertTrue(parlamentarDao.insertParlamentar(parlamentarA));
	}

	public void testDeleteParlamentar() {
		Assert.assertTrue(parlamentarDao.deleteParlamentar(parlamentarB));
	}

	public void testUpdateParlamentar() throws NullParlamentarException 
	{
		parlamentarA.setSeguido(1);
		Assert.assertTrue(parlamentarDao.updateParlamentar(parlamentarA));
	}

	public void testGetById() 
	{
		Assert.assertSame(parlamentarDao.getById(777).getClass(), Parlamentar.class);
	}

	public void testGetAll() {
		List<Parlamentar> lista=new ArrayList<Parlamentar>();
		lista = parlamentarDao.getAll();
		Assert.assertNotNull(lista);
	}

	public void testGetSelected() {
		List<Parlamentar> lista=new ArrayList<Parlamentar>();
		lista = parlamentarDao.getSelectedByName("Parlamentar");
		Assert.assertNotNull(lista);
	}

	public void testGetAllSelected() {
		
		List<Parlamentar> lista=new ArrayList<Parlamentar>();
		lista = parlamentarDao.getAllSelected();
		Assert.assertNotNull(lista);
	}

}
