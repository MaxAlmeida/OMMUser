package com.OMM.test.user.controller;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.test.AndroidTestCase;
import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.dao.ParlamentarUserDao;
import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.TransmissionException;
import com.OMM.application.user.helper.JSONHelper;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;

public class ParlamentarUserControllerTest extends AndroidTestCase{
	
	private Context context;
	private Parlamentar parlamentar;
	private ParlamentarUserController controller;
	private ParlamentarUserDao dao;
	public void setUp() throws Exception{
		super.setUp();		
		context = getContext();
		controller = ParlamentarUserController.getInstance(context);
		dao = ParlamentarUserDao.getInstance(context);
		parlamentar = new Parlamentar();
		parlamentar.setNome("TIRIRICA");
		parlamentar.setId(0);
		parlamentar.setSeguido(0);
		dao.insertParlamentar(parlamentar);
	}
	public void tearDown() throws Exception{
		super.tearDown();
		dao.deleteParlamentar(parlamentar);
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
	
	public void testGetByName(){
		
		String nameParlamentar = "TIRIRICA";
	List<Parlamentar> listParlamentar = controller.getByName(nameParlamentar);
	
	assertEquals(nameParlamentar, listParlamentar.get(0).getNome());
				
	}
	
	public void testGetAll(){

		List<Parlamentar> listParlamentar = controller.getAll();
		
		assertNotNull(listParlamentar);
		
		}
	
	public void testFollowedParlamentar() throws NullCotaParlamentarException, NullParlamentarException{
		
		Parlamentar parlamentar = controller.getByName("TIRIRICA").get(0);
		List<CotaParlamentar> list = new ArrayList<CotaParlamentar>();
		CotaParlamentar cota = new CotaParlamentar();
		cota.setIdParlamentar(parlamentar.getId());
		parlamentar.setSeguido(1);
		parlamentar.setCotas(list);
		controller.setParlamentar(parlamentar);
		
		assertTrue(controller.followedParlamentar());
	}
	
	public void testUnFollowedParlamentar() throws NullCotaParlamentarException, NullParlamentarException {
		
		Parlamentar parlamentar = controller.getByName("TIRIRICA").get(0);
		List<CotaParlamentar> list = new ArrayList<CotaParlamentar>();
		CotaParlamentar cota = new CotaParlamentar();
		cota.setIdParlamentar(parlamentar.getId());
		parlamentar.setCotas(list);
		parlamentar.setSeguido(1);
		controller.setParlamentar(parlamentar);
		controller.followedParlamentar();
		parlamentar.setSeguido(0);
		controller.setParlamentar(parlamentar);
		assertTrue(controller.unFollowedParlamentar());
	}
	
	public void testFollowedParlamentarException() 
			throws NullCotaParlamentarException, NullParlamentarException {
		
		try {
			
			controller.setParlamentar(null);
			controller.followedParlamentar();

			fail("Exceptions not launched");
			
		} catch (NullParlamentarException npe) {
		}
	}
	
	public void testDoRequest(){
		//ResponseHandler<String> response = Mockito.mock(ResponseHandler.class);
		//TODO later.
		
	}
	
}
