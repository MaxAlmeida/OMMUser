package com.OMM.test.user.controller;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.OMM.application.user.controller.CotaParlamentarUserController;
import com.OMM.application.user.dao.CotaParlamentarUserDao;
import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.TransmissionException;
import com.OMM.application.user.helper.JSONHelper;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.view.GuiMain;


public class CotaParlamentarUserControllerTest extends ActivityInstrumentationTestCase2<GuiMain> {

	private Parlamentar parlamentar;
	private CotaParlamentar cota;
	private CotaParlamentarUserController controller;
	private GuiMain guiMain;
	

	public CotaParlamentarUserControllerTest(String nome)
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
		controller  = CotaParlamentarUserController.getInstance(guiMain);
		parlamentar.setId(112);
		parlamentar.setPartido("PT");
		parlamentar.setNome("Tiririca");
		
		cota.setCod(1110);
		cota.setIdParlamentar(114);
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

		CotaParlamentarUserController controller1 = CotaParlamentarUserController.getInstance(guiMain);
		CotaParlamentarUserController controller2 = CotaParlamentarUserController.getInstance(guiMain);
		
		assertSame(controller1, controller2);
	}
//TODO Verificar teste no junit 3 com exceção

public void testConvertJsonToCotaParlamentar() throws NullCotaParlamentarException,TransmissionException {
		
		String json = "[{\"cod\":144068,\"idParlamentar\":373,\"mes\":7,\"ano\":2013,\"numeroSubCota\":3,\"descricao\":\"COMBUST\",\"valor\":150.0}]";
		List<CotaParlamentar> list1 = JSONHelper.listCotaParlamentarFromJSON(json);
		List<CotaParlamentar> list2 = controller.convertJsonToCotaParlamentar(json);
		
		Assert.assertEquals(list1.get(0).getIdParlamentar(), list2.get(0).getIdParlamentar());
		
	}

public void testPersistCotaDB() throws NullParlamentarException{
	
	Assert.assertTrue(controller.persistCotaDB(parlamentar));
	
	
}

public void deleteCota(){

	Assert.assertFalse(controller.deleteCota(parlamentar));
	
}

}
