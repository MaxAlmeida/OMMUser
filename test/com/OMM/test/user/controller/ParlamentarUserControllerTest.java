package com.OMM.test.user.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.client.ResponseHandler;

import android.content.Context;
import android.test.AndroidTestCase;
import android.util.Log;

import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.dao.ParlamentarUserDao;
import com.OMM.application.user.exceptions.ConnectionFailedException;
import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.RequestFailedException;
import com.OMM.application.user.helper.JSONHelper;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.requests.HttpConnection;

public class ParlamentarUserControllerTest extends AndroidTestCase {

	private Context context;
	private ResponseHandler<String> response;
	private Parlamentar parlamentar;
	private ParlamentarUserController controller;
	private ParlamentarUserDao dao;

	public void setUp() throws Exception {
		super.setUp();
		response = HttpConnection.getResponseHandler();
		context = getContext();
		controller = ParlamentarUserController.getInstance(context);
		dao = ParlamentarUserDao.getInstance(context);
		parlamentar = new Parlamentar();
		parlamentar.setNome("TIRIRICA");
		parlamentar.setId(0);
		parlamentar.setSeguido(0);
		parlamentar.setIdUpdate(2);
		dao.insertParlamentar(parlamentar);
	}

	public void tearDown() throws Exception {
		super.tearDown();
		dao.deleteParlamentar(parlamentar);
	}

	public void testGetInstance() {

		ParlamentarUserController controller2 = ParlamentarUserController
				.getInstance(context);
		assertSame(controller, controller2);
	}

	public void testGetParlametares() {
		List<Parlamentar> list = new ArrayList<Parlamentar>();
		Parlamentar parlamentar2 = new Parlamentar();
		parlamentar2.setId(0);
		list.add(parlamentar2);
		controller.setParlamentares(list);
		assertEquals(list.get(0).getId(), controller.getParlamentares().get(0)
				.getId());
	}

	public void testGetParlamentar() {
		parlamentar = new Parlamentar();
		parlamentar.setId(1);
		controller.setParlamentar(parlamentar);
		assertEquals(parlamentar.getId(), controller.getParlamentar().getId());
	}

	public void testGetByName() {

		String nameParlamentar = "TIRIRICA";
		List<Parlamentar> listParlamentar = controller
				.getByName(nameParlamentar);

		assertEquals(nameParlamentar, listParlamentar.get(0).getNome());

	}

	public void testGetAll() {

		List<Parlamentar> listParlamentar = controller.getAll();

		assertNotNull(listParlamentar);

	}

	public void testFollowedParlamentar() throws NullCotaParlamentarException,
			NullParlamentarException {

		Parlamentar parlamentar = controller.getByName("TIRIRICA").get(0);
		List<CotaParlamentar> list = new ArrayList<CotaParlamentar>();
		CotaParlamentar cota = new CotaParlamentar();
		cota.setIdParlamentar(parlamentar.getId());
		parlamentar.setSeguido(1);
		parlamentar.setCotas(list);
		controller.setParlamentar(parlamentar);

		assertTrue(controller.followedParlamentar());
	}

	public void testUnFollowedParlamentar()
			throws NullCotaParlamentarException, NullParlamentarException {

		Parlamentar parlamentar = controller.getByName("TIRIRICA").get(0);
		List<CotaParlamentar> list = new ArrayList<CotaParlamentar>();
		CotaParlamentar cota = new CotaParlamentar();
		cota.setIdParlamentar(parlamentar.getId());
		list.add(cota);
		parlamentar.setCotas(list);
		parlamentar.setSeguido(1);
		controller.setParlamentar(parlamentar);
		controller.followedParlamentar();
		parlamentar.setSeguido(0);
		controller.setParlamentar(parlamentar);
		assertTrue(controller.unfollowedParlamentar());
	}

	public void testFollowedParlamentarNullParlamentrarException()
			throws NullCotaParlamentarException, NullParlamentarException {

		try {
			controller.setParlamentar(null);
			controller.followedParlamentar();

			fail("Exceptions not launched");

		} catch (NullParlamentarException npe) {
		}
	}

	public void testFollowedParlamentarNullCotaParlamentrarException()
			throws NullCotaParlamentarException, NullParlamentarException {

		try {
			Parlamentar parlamentar = controller.getByName("TIRIRICA").get(0);
			List<CotaParlamentar> list = null;
			parlamentar.setSeguido(1);
			parlamentar.setCotas(list);
			controller.setParlamentar(parlamentar);

			controller.followedParlamentar();
			fail("Exceptions not launched");

		} catch (NullCotaParlamentarException npe) {
		}
	}

	public void testUnFollowedNullParlamentarException()
			throws NullParlamentarException, NullCotaParlamentarException {
		try {
			controller.setParlamentar(null);
			controller.unfollowedParlamentar();

			fail("Exceptions no launched");

		} catch (NullParlamentarException npe) {

		}
	}

	public void testDoRequest() throws ConnectionFailedException,
			RequestFailedException, NullParlamentarException,
			NullCotaParlamentarException {
		Parlamentar p = new Parlamentar();
		p.setId(373);
		controller.setParlamentar(p);
		Parlamentar pJson = controller.doRequest(response);
		String result = "[{\"id\":373,\"nome\":\"PAULO MALUF\",\"partido\":\"PP\",\"uf\":\"SP\"}]";
		Parlamentar pResult = JSONHelper.listParlamentarFromJSON(result).get(0);

		assertEquals(pResult.getId(), pJson.getId());
	}

	public void testGetSelected() {

		assertNotNull(controller.getSelected());
	}

	public void testInsertAllFalse() throws NullParlamentarException,
			ConnectionFailedException, RequestFailedException {

		ResponseHandler<String> response = HttpConnection.getResponseHandler();
		assertFalse(controller.insertAll(response));

	}

	public void testInsertAllTrue() throws NullParlamentarException,
			ConnectionFailedException, RequestFailedException {

		controller.getAll();
		Iterator<Parlamentar> iterator = controller.getAll().iterator();

		while (iterator.hasNext()) {
			Parlamentar p = iterator.next();
			dao.deleteParlamentar(p);

		}

		ResponseHandler<String> response = HttpConnection.getResponseHandler();
		assertTrue(controller.insertAll(response));

	}

	public void testGetAllSelected() {
		List<Parlamentar> lista = new ArrayList<Parlamentar>();
		lista = controller.getAllSelected();
		assertNotNull(lista);

	}

	public void testCheckEmptyDBFalse() {
		assertFalse(controller.checkEmptyDB());
	}

	public void testCheckEmptyDBTrue() {

		List<Parlamentar> list = controller.getAll();
		Iterator<Parlamentar> iterator = list.iterator();

		while (iterator.hasNext()) {
			Parlamentar p = iterator.next();
			dao.deleteParlamentar(p);
		}

		assertTrue(controller.checkEmptyDB());
	}
	
	public void testGetIdUpdateParlamentar(){
		
		List<Parlamentar> list = null;
		int idUpdate = -1;
		list = controller.getAll();
		
		if(list==null){
			fail("banco vazio");
		}
		else{
			Log.i("IDUPDATE",list.get(0).getIdUpdate()+"");
			controller.setParlamentar(list.get(0));
			idUpdate = controller.getIdUpdateParlamentar();
			if(idUpdate<0){
				assertTrue(false);
			}
			else{
				assertTrue(true);
			}
		}
	}
	
	public void testUpdateParlamentar(){
		parlamentar = new Parlamentar();
		parlamentar.setNome("TIRIRICA");
		parlamentar.setId(0);
		parlamentar.setSeguido(0);
		parlamentar.setIdUpdate(3);
		parlamentar.setValor(300.0f);
		parlamentar.setMajorRankingPos(3);
		
		controller.updateParlamentar(parlamentar);
		
		Parlamentar p = dao.getById(0);
		Log.i("testUpdateParlamentar","idUpdate "+ p.getIdUpdate()+"");
		Log.i("testUpdateParlamentar","valor "+ p.getValor()+"");
		Log.i("testUpdateParlamentar","ranking "+ p.getMajorRankingPos()+"");
		assertEquals(parlamentar.getIdUpdate(), p.getIdUpdate());
		assertEquals(parlamentar.getValor(), p.getValor(), 0.0f);
		assertEquals(parlamentar.getMajorRankingPos(), p.getMajorRankingPos());
		
	}
	
	public void testGetAllSelectedIds(){
		parlamentar = new Parlamentar();
		parlamentar.setNome("MACACO");
		parlamentar.setId(0);
		parlamentar.setSeguido(1);
		parlamentar.setIdUpdate(3);
		parlamentar.setValor(300.0f);
		parlamentar.setMajorRankingPos(2);
		
		dao.insertParlamentar(parlamentar);
		List<Parlamentar> list = dao.getAllSelected();
		List<Integer> ids = new ArrayList<Integer>();
		
		Iterator<Parlamentar> iterator = list.iterator();
		while(iterator.hasNext()){
			Integer i = iterator.next().getId();
			ids.add(i);
			Log.i("SelectedIds ","id "+i);
		}
		
		ArrayList<Integer> selectedIds = (ArrayList<Integer>)controller.getAllSelectedIds();
		
		assertEquals(ids, selectedIds);
	}
	public void testLastIdUpdate(){
		parlamentar = new Parlamentar();
		parlamentar.setNome("MACACO");
		parlamentar.setId(0);
		parlamentar.setSeguido(1);
		parlamentar.setIdUpdate(3);
		parlamentar.setValor(300.0f);
		parlamentar.setMajorRankingPos(2);
		
		dao.insertParlamentar(parlamentar);
		
		List<Parlamentar> list = dao.getAll();
		int idUpdate = 0;

		Iterator<Parlamentar> iterator = list.iterator();
		while (iterator.hasNext()) {
			Parlamentar p = iterator.next();
			if (p.getIdUpdate() > idUpdate) {
				idUpdate = p.getIdUpdate();
			}
		}
		Log.i("LastIdUpdate","lastIdUpdate "+idUpdate);
		assertEquals(idUpdate, controller.getLastIdUpdate());
	}
}
