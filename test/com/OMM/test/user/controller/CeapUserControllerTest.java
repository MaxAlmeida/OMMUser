package com.OMM.test.user.controller;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import android.test.ActivityInstrumentationTestCase2;

import com.OMM.application.user.controller.CeapUserController;
import com.OMM.application.user.dao.CotaParlamentarUserDao;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.view.GuiMain;


public class CeapUserControllerTest extends ActivityInstrumentationTestCase2<GuiMain> {

	private Parlamentar parlamentar;
	private CotaParlamentar cota;
	private CeapUserController controller;
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
		controller  = CeapUserController.getInstance(guiMain);
		parlamentar.setId(111);
		parlamentar.setPartido("PT");
		parlamentar.setNome("Tiririca");
		
		cota.setCod(1110);
		cota.setIdParlamentar(111);
		cota.setDescricao("Alimentacao");
		cota.setMes(6);
		cota.setAno(2013);
		cota.setValor(5000.00);
		cota.setNumeroSubCota(3);
		List<CotaParlamentar> lista = new ArrayList<CotaParlamentar>();
		parlamentar.setCotas(lista);
	}
	
	
	public void testGetInstanceCota() 
	{

		CeapUserController controller1 = CeapUserController.getInstance(guiMain);
		CeapUserController controller2 = CeapUserController.getInstance(guiMain);
		
		assertSame(controller1, controller2);
	}


	



}
