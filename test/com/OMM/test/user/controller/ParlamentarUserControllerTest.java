package com.OMM.test.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ResponseHandler;
import org.mockito.Mockito;

import android.content.Context;
import android.test.AndroidTestCase;

import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.TransmissionException;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;

public class ParlamentarUserControllerTest extends AndroidTestCase{
	
	private Context context;
	private Parlamentar parlamentar;
	private CotaParlamentar cota;
	private ParlamentarUserController controller;
	
	public void setUp() throws Exception{
		super.setUp();		
		context = getContext();
		controller = ParlamentarUserController.getInstance(context);
	}
	
	public void testGetInstance() {
		
		ParlamentarUserController controller2 = ParlamentarUserController.getInstance(context);
		assertSame(controller, controller2);
	}
	
	public void testGetParlametares(){
		List<Parlamentar> list = new ArrayList<Parlamentar>();
		Parlamentar parlamentar2 = new Parlamentar();
		parlamentar2.setId(0);
		list.add(parlamentar2);
		controller.setParlamentares(list);	
		assertEquals(list.get(0).getId(), controller.getParlamentares().get(0).getId());
	}
	
	public void testGetParlamentar(){
		parlamentar = new Parlamentar();
		parlamentar.setId(1);
		controller.setParlamentar(parlamentar);
		assertEquals(parlamentar.getId(),controller.getParlamentar().getId());
	}
	
	public void testConvertJsonToParlamentar() throws NullParlamentarException, TransmissionException{
		
		String json = "[{\"id\":373,\"nome\":\"PAULO MALUF\",\"partido\":\"PP\",\"uf\":\"SP\"}]";	
		Parlamentar parlamentarJson = controller.convertJsonToParlamentar(json);
		
		assertEquals(373,parlamentarJson.getId());
		
	}
	
	public void testConvertJsonToCotaParlamentar() throws NullCotaParlamentarException{
	
		String jsonCotas = "[{\"cod\":144068,\"idParlamentar\":373,\"mes\":7,\"ano\":2013,\"numeroSubCota\":3,\"descricao\":\"COMBUST\",\"valor\":150.0}]";
		
		List<CotaParlamentar> cotas = controller.convertJsonToCotaParlamentar(jsonCotas);
		
		assertEquals(144068,cotas.get(0).getCod());
		
		
		
	}

	public void testGetByName(){
		
		String nameParlamentar = "TIRIRICA";
	List<Parlamentar> listParlamentar = controller.getByName(nameParlamentar);
	
	assertEquals(nameParlamentar, listParlamentar.get(0).getNome());
				
	}
	
	public void testGetAll(){		
		
		}
	public void testDoRequest(){
		//ResponseHandler<String> response = Mockito.mock(ResponseHandler.class);
		//TODO later.
		
	}
	
}