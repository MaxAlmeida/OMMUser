package com.OMM.test.user.controller;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import android.test.ActivityInstrumentationTestCase2;

import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.dao.ParlamentarUserDao;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.view.GuiMain;

public class ParlamentarUserControllerTest extends ActivityInstrumentationTestCase2<GuiMain>{
	
	private Parlamentar parlamentar;
	private CotaParlamentar cota;
	private ParlamentarUserController parlamentarController;
	private GuiMain guiMain;
	
	public ParlamentarUserControllerTest(String nome){
		
		super(GuiMain.class);
		setName(nome);
	}
	
	public void setUp() throws Exception {
		
		super.setUp();
		guiMain=getActivity();
		parlamentar = new Parlamentar();
		cota = new CotaParlamentar();
		parlamentarController  = ParlamentarUserController.getInstance(guiMain);
		parlamentar.setId(1111);
		parlamentar.setPartido("PT");
		parlamentar.setNome("Tiririca");
		
		cota.setCod(1110);
		cota.setIdParlamentar(1111);
		cota.setNumeroSubCota(88);
		cota.setDescricao("Alimentação");
		List<CotaParlamentar> lista = new ArrayList<CotaParlamentar>();
		parlamentar.setCotas(lista);
	}
	
	public void testGetInstance() {

		ParlamentarUserController controller1 = ParlamentarUserController.getInstance(guiMain);
		ParlamentarUserController controller2 = ParlamentarUserController.getInstance(guiMain);
		
		assertSame(controller1, controller2);
	}
	
	public void testSetParlamentar(){
		
		Parlamentar parlamentarTest = new Parlamentar();
		parlamentarTest.setNome("joao");
		
		parlamentarController.setParlamentar(parlamentarTest);
		
		
		Assert.assertEquals(parlamentarTest.getNome(),parlamentarController.getParlamentar().getNome());
		
	}
	
}