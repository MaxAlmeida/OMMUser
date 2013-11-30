package com.OMM.test.user.dao;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import android.test.ActivityInstrumentationTestCase2;

import com.OMM.application.user.dao.ParlamentarUserDao;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.view.GuiMain;

public class ParlamentarUserDaoTest extends
		ActivityInstrumentationTestCase2<GuiMain> {

	private ParlamentarUserDao parlamentarDao;
	private GuiMain gMain;
	private Parlamentar parlamentarA,parlamentarB, parlamentarC;
	
	
	public ParlamentarUserDaoTest()
	{
		this("ParlamentarUserDaoTest");
	}
	public ParlamentarUserDaoTest(String name) {
		super(GuiMain.class);
		setName(name);
	}

	protected void setUp() throws Exception 
	{
		super.setUp();
		gMain = getActivity();
		parlamentarDao=ParlamentarUserDao.getInstance(gMain);
		parlamentarA =new Parlamentar();
		parlamentarB=new Parlamentar();
		parlamentarC=new Parlamentar();
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
		ParlamentarUserDao obj1=ParlamentarUserDao.getInstance(gMain);
		ParlamentarUserDao obj2=ParlamentarUserDao.getInstance(gMain);
		Assert.assertSame(obj1, obj2);
	}

	public void testCheckEmptyDB() 
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

	public void testUpdateParlamentar() 
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
		lista = parlamentarDao.getSelected("Parlamentar");
		Assert.assertNotNull(lista);
	}

	public void testGetAllSelected() {
		
		List<Parlamentar> lista=new ArrayList<Parlamentar>();
		lista = parlamentarDao.getAllSelected();
		Assert.assertNotNull(lista);
	}

}
