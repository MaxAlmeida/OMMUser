package com.OMM.test.user.dao;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import android.test.ActivityInstrumentationTestCase2;

import com.OMM.application.user.dao.ParlamentarUserDao;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.view.GuiMain;

public class ParlamentarUserDaoTest extends ActivityInstrumentationTestCase2<GuiMain> {
	
	
	private ParlamentarUserDao parlamentarDao;
	private GuiMain gMain;
	
	private CotaParlamentar cota;
	
	public ParlamentarUserDaoTest(String nome )
	{
		super(GuiMain.class);
		setName(nome);
	}
	public ParlamentarUserDaoTest ()
	{
		this("ParlamentarUserDaoTest");
	}
	public void setUp() throws Exception
	{
		super.setUp();
		gMain = getActivity();
		parlamentarDao=ParlamentarUserDao.getInstance(gMain);
		Parlamentar parlamentar=new Parlamentar();
		 
		
	}
	public void testInsertDao()
	{
		Parlamentar parlamentar=new Parlamentar();
		parlamentar.setId(111);
		parlamentar.setNome("Pangare");
		parlamentar.setPartido("PU");
		parlamentar.setSeguido(0);
		parlamentar.setUf("DF");
		Assert.assertTrue(parlamentarDao.insertParlamentar(parlamentar));
		
		
	}
	public void testGetbyIdDAO()
	{
		Parlamentar parlamentar=new Parlamentar();
		parlamentar.setId(112);
		parlamentar.setNome("Fernando da fila do banco");
		parlamentar.setPartido("PU");
		parlamentar.setSeguido(0);
		parlamentar.setUf("DF");
		
		Assert.assertTrue(parlamentarDao.insertParlamentar(parlamentar));
		
		Assert.assertNotNull(parlamentarDao.getById(112));
		
		
	}
	public void testDeleteParlamentaDAO()
	{
		Parlamentar parlamentar=new Parlamentar();
		parlamentar.setId(113);
		parlamentar.setNome("Justin Biber");
		parlamentar.setPartido("PU");
		parlamentar.setSeguido(0);
		parlamentar.setUf("DF");
		Assert.assertTrue(parlamentarDao.insertParlamentar(parlamentar));
		
		Assert.assertTrue(parlamentarDao.deleteParlamentar(parlamentar));
		
	}
	public void testUpdateParlamentarDAO()
	{
		Parlamentar parlamentar=new Parlamentar();
		parlamentar.setId(114);
		parlamentar.setNome("Genu");
		parlamentar.setPartido("PU");
		parlamentar.setSeguido(0);
		parlamentar.setUf("DF");
		Assert.assertTrue(parlamentarDao.insertParlamentar(parlamentar));
		
		Assert.assertTrue(parlamentarDao.updateParlamentar(parlamentar));
		
	}
	public void testGetListAll()
	{
		Parlamentar parlamentar=new Parlamentar();
		parlamentar.setId(115);
		parlamentar.setNome("Xeroso");
		parlamentar.setPartido("PU");
		parlamentar.setSeguido(0);
		List<Parlamentar>lista =new ArrayList<Parlamentar>();
		lista=parlamentarDao.getAll();
		Assert.assertNotNull(lista);
		
	}
	public void testGetAllSelected()
	{
		Parlamentar parlamentar=new Parlamentar();
		parlamentar.setId(116);
		parlamentar.setNome("Genu");
		parlamentar.setPartido("PU");
		parlamentar.setSeguido(1);
		List<Parlamentar>lista =new ArrayList<Parlamentar>();
		lista=parlamentarDao.getAllSelected();
		Assert.assertNotNull(lista);
	}
	public void testCheckEmptyDB() 
	{
		Parlamentar parlamentar=new Parlamentar();
		parlamentar.setId(117);
		parlamentar.setNome("Panrangole");
		parlamentar.setPartido("PU");
		parlamentar.setSeguido(0);
		parlamentar.setUf("DF");
		Assert.assertTrue(parlamentarDao.insertParlamentar(parlamentar));
		Assert.assertFalse(parlamentarDao.checkEmptyDB());
	}
	public void testGetSelected()
	{
		Parlamentar parlamentar=new Parlamentar();
		parlamentar.setId(118);
		parlamentar.setNome("Ratinho");
		parlamentar.setPartido("PU");
		parlamentar.setSeguido(1);
		List<Parlamentar>lista =new ArrayList<Parlamentar>();
		lista=parlamentarDao.getSelected(parlamentar.getNome());
		Assert.assertNotNull(lista);
		
		
	}
	public void testGetInstance()
	{
		ParlamentarUserDao obj1=ParlamentarUserDao.getInstance(gMain);
		ParlamentarUserDao obj2=ParlamentarUserDao.getInstance(gMain);
		Assert.assertSame(obj1, obj2);
	}
	public void tearDown()
	{
		parlamentarDao.dropTable();
	}

}
