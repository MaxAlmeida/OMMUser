package com.OMM.test.user.controller;

import java.util.ArrayList;
import java.util.List;

import android.test.ActivityInstrumentationTestCase2;

import com.OMM.application.user.dao.CotaParlamentarUserDao;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.view.GuiMain;


public class CeapUserControllerTest extends ActivityInstrumentationTestCase2<GuiMain> {

	private Parlamentar parlamentar;
	private CotaParlamentar cota;
	private CotaParlamentarUserDao dao;
	private GuiMain guiMain;
	

	public CeapUserControllerTest(String nome)
	{
		super(GuiMain.class);
		setName(nome);
	}
	
	public void setUp() throws Exception
	{
		super.setUp();
		guiMain=getActivity();
		parlamentar = new Parlamentar();
		cota = new CotaParlamentar();
		dao  = CotaParlamentarUserDao.getInstance(guiMain);
		parlamentar.setId(111);
		parlamentar.setPartido("Sparta");
		parlamentar.setNome("Ramon");
		
		cota.setCod(1110);
		cota.setIdParlamentar(111);
		cota.setDescricao("=D");
		List<CotaParlamentar> lista = new ArrayList<CotaParlamentar>();
		parlamentar.setCotas(lista);
	}
	
	
	public void testGetInstance() 
	{

		CotaParlamentarUserDao dao1=CotaParlamentarUserDao.getInstance(guiMain);
		CotaParlamentarUserDao dao2=CotaParlamentarUserDao.getInstance(guiMain);
		
		assertSame(dao1, dao2);
	}

	
	public void testInsertFollowed() {
		assertEquals(true, dao.insertFollowed(parlamentar, cota));
	}

	
	public void testGetAllCotas() {
		
		List<CotaParlamentar> listaCotas=new ArrayList<CotaParlamentar>();
		listaCotas=dao.getAllCotas(parlamentar);
		assertNotNull(listaCotas);
	
	}

	
	public void testDeleteParlamentar() {
		
		assertTrue(dao.deleteParlamentar(parlamentar));
		
	}

}
