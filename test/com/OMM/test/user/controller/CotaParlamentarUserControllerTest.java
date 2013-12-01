package com.OMM.test.user.controller;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.test.AndroidTestCase;
import com.OMM.application.user.controller.CotaParlamentarUserController;
import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.TransmissionException;
import com.OMM.application.user.helper.JSONHelper;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;

public class CotaParlamentarUserControllerTest extends AndroidTestCase {

	private Parlamentar parlamentar;
	private CotaParlamentar cota;
	private CotaParlamentarUserController controller;
	private Context context;
	
	public void setUp() throws Exception
	{
		super.setUp();
		this.context = getContext();
		parlamentar = new Parlamentar();
		cota = new CotaParlamentar();
		controller  = CotaParlamentarUserController.getInstance(context);
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

		CotaParlamentarUserController controller1 = CotaParlamentarUserController.getInstance(context);
		CotaParlamentarUserController controller2 = CotaParlamentarUserController.getInstance(context);		
		assertSame(controller1, controller2);
	}

	public void testPersistCotaLocalDatabase() throws NullParlamentarException{
		
		assertTrue(controller.persistCotasOnLocalDatabase(parlamentar));
		
		
	}

	public void testDeleteCota() throws NullParlamentarException{
	   
		Parlamentar parlamentarDeleteCota = new Parlamentar();
		parlamentarDeleteCota.setId(114);
		List<CotaParlamentar> list = new ArrayList<CotaParlamentar>();
		CotaParlamentar cota = new CotaParlamentar();
		cota.setIdParlamentar(114);
		list.add(cota);
		parlamentarDeleteCota.setCotas(list);
		
		controller.persistCotasOnLocalDatabase(parlamentarDeleteCota);	
		assertTrue(controller.deleteCota(parlamentarDeleteCota));	
	}
	
	public void testGetCotasByIdParlamentar() throws NullParlamentarException {
		
		Parlamentar parlamentarDeleteCota = new Parlamentar();
		parlamentarDeleteCota.setId(114);
		List<CotaParlamentar> list = new ArrayList<CotaParlamentar>();
		CotaParlamentar cota = new CotaParlamentar();
		cota.setIdParlamentar(114);
		list.add(cota);
		parlamentarDeleteCota.setCotas(list);
		
		controller.persistCotasOnLocalDatabase(parlamentarDeleteCota);
		List<CotaParlamentar> listResult = controller.getCotasByIdParlamentar(114);
		
		assertSame(listResult.get(0).getIdParlamentar(), list.get(0).getIdParlamentar());
	}
	
		public void testGetCotasByIdParlamentarExceptions() 
				throws NullParlamentarException{

			Parlamentar parlamentarDeleteCota = null;
			
			try {
				controller.persistCotasOnLocalDatabase(parlamentarDeleteCota);
				
				fail("Exception not launched");
			} catch(NullParlamentarException e) {
				
			}
		}
}
