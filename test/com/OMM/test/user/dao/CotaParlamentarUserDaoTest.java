package com.OMM.test.user.dao;

import junit.framework.Assert;
import android.test.ActivityInstrumentationTestCase2;

import com.OMM.application.user.dao.CotaParlamentarUserDao;
import com.OMM.application.user.dao.ParlamentarUserDao;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.view.GuiMain;

public class CotaParlamentarUserDaoTest extends
		ActivityInstrumentationTestCase2<GuiMain> {

	private GuiMain gMain;
	private Parlamentar parlamentarA,parlamentarB;
	private CotaParlamentar cotaA,cotaB;
	private ParlamentarUserDao daoParlamentar;
	private CotaParlamentarUserDao cotaDao;
	
	public CotaParlamentarUserDaoTest()
	{
		this("CotaParlamentarUserDaoTest");
	}
	public CotaParlamentarUserDaoTest(String name) {
		super(GuiMain.class);
		setName(name);
	}

	public void setUp() throws Exception {
		super.setUp();
		gMain=getActivity();
		parlamentarA =new Parlamentar();
		parlamentarB=new Parlamentar();
		
		cotaA=new CotaParlamentar();
		cotaB=new CotaParlamentar();
		
		cotaDao=CotaParlamentarUserDao.getInstance(gMain);
		daoParlamentar= ParlamentarUserDao.getInstance(gMain);
				
		//parlamentar A
				parlamentarA.setId(777);
				parlamentarA.setNome("Parlamentar Teste Insert");
				parlamentarA.setSeguido(0);
				
				cotaA.setIdParlamentar(777);
				cotaA.setCod(770);
				cotaA.setAno(2013);
				
				//Parlamentar B
				parlamentarB.setId(7770);
				parlamentarB.setNome("Parlamentar Teste Dao Cota");
				parlamentarB.setSeguido(0);
				
				cotaB.setIdParlamentar(7770);
				cotaB.setCod(7700);
				cotaB.setAno(2013);
				
				
				daoParlamentar.insertParlamentar(parlamentarA);	
				daoParlamentar.insertParlamentar(parlamentarB);
				
				cotaDao.insertFollowed(parlamentarB, cotaB);
		
		
		
	}

	

public void testGetInstance() {
		
		CotaParlamentarUserDao parlamentarDao1 = CotaParlamentarUserDao.getInstance(gMain);
		CotaParlamentarUserDao parlamentarDao2 = CotaParlamentarUserDao.getInstance(gMain);
		
		assertSame(parlamentarDao1,parlamentarDao2);
	}



	public void testInsertFollowed() 
	{
		
		
		Assert.assertEquals(true,cotaDao.insertFollowed(parlamentarA, cotaA));
		
	}

	public void testDeleteParlamentar() 
	{
		
		
		Assert.assertEquals(true,cotaDao.deleteParlamentar(parlamentarB));
	}

	public void testGetCotasByIdParlamentar() 
	{
		cotaDao.getCotasByIdParlamentar(7770);
	}

}
