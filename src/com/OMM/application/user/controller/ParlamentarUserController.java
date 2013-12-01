package com.OMM.application.user.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.client.ResponseHandler;

import android.content.Context;

import com.OMM.application.user.dao.ParlamentarUserDao;
import com.OMM.application.user.exceptions.ConnectionFailedException;
import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.RequestFailedException;
import com.OMM.application.user.exceptions.TransmissionException;
import com.OMM.application.user.helper.JSONHelper;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.requests.HttpConnection;
import com.OMM.application.user.requests.MountURL;

public class ParlamentarUserController {

	private static ParlamentarUserController instance;
	private static Parlamentar parlamentar;
	private static List<Parlamentar> parlamentares;
	private ParlamentarUserDao parlamentarDao;
	private Context context;
	private CotaParlamentarUserController ceapController;

	private ParlamentarUserController(Context context) {
		ceapController = CotaParlamentarUserController.getInstance(context);
		parlamentarDao = ParlamentarUserDao.getInstance(context);
		this.context = context;
		parlamentares = new ArrayList<Parlamentar>();
	}

	public static ParlamentarUserController getInstance(Context context) {

		if (instance == null) {
			instance = new ParlamentarUserController(context);
		}

		return instance;

	}

	public void setParlamentar(Parlamentar parlamentar) {
		ParlamentarUserController.parlamentar = parlamentar;
	}

	public List<Parlamentar> getParlamentares() {
		return ParlamentarUserController.parlamentares;
	}

	public void setParlamentares(List<Parlamentar> parlamentares) {
		ParlamentarUserController.parlamentares = parlamentares;
	}

	public Parlamentar doRequest(ResponseHandler<String> responseHandler)
			throws NullParlamentarException, NullCotaParlamentarException,
			TransmissionException, ConnectionFailedException, RequestFailedException {

		if (responseHandler == null) {
			throw new TransmissionException();
		}
		
		int idParlamentar = parlamentar.getId();
		String urlParlamentar = MountURL.mountURLParlamentar(idParlamentar);
		String jsonParlamentar = HttpConnection.requestParlamentar(
				responseHandler, urlParlamentar);

		parlamentar = JSONHelper.listParlamentarFromJSON(jsonParlamentar).get(0);

		String urlCotas = MountURL.mountURLCota(idParlamentar);
		String jsonCotasParlamentar = HttpConnection.requestCota(
				responseHandler, urlCotas);

		List<CotaParlamentar> cotas = JSONHelper
				.listCotaParlamentarFromJSON(jsonCotasParlamentar);

		parlamentar.setCotas(cotas);

		return parlamentar;
	}
		public List<Parlamentar> getByName(String nameParlamentar) {
		parlamentares = parlamentarDao.getSelectedByName(nameParlamentar);
		return parlamentares;
	}

	public List<Parlamentar> getAll() {

		parlamentares = parlamentarDao.getAll();
		return parlamentares;
	}

	public boolean followedParlamentar()
			throws NullCotaParlamentarException, NullParlamentarException{

		boolean result = true;

		if(parlamentar != null && parlamentar.getCotas() != null){
			CotaParlamentarUserController controllerCeap = CotaParlamentarUserController
					.getInstance(context);
			parlamentar.setSeguido(1);
			result = controllerCeap.persistCotasOnLocalDatabase(parlamentar.getCotas())
					&& parlamentarDao.updateParlamentar(parlamentar);
			return result;
		}
		else if ( parlamentar == null) {
			throw new NullParlamentarException();
		}else {
			
			throw new NullCotaParlamentarException();
		}
	}

	public boolean insertAll(ResponseHandler<String> response)
			throws NullParlamentarException, ConnectionFailedException,
			RequestFailedException, TransmissionException {

		String urlParlamentares = MountURL.mountUrlAll();
		String jsonParlamentares = HttpConnection.requestParlamentar(response,
				urlParlamentares);
		List<Parlamentar> parlamentares = JSONHelper.listParlamentarFromJSON(jsonParlamentares);
		boolean initialized;
		if (parlamentarDao.checkEmptyLocalDatabase()) {
			Iterator<Parlamentar> iterator = parlamentares.iterator();
			
			while (iterator.hasNext()) {
				Parlamentar p = iterator.next();
				p.setSeguido(0);
				parlamentarDao.insertParlamentar(p);
			}

			initialized = true;
		} else {
			initialized = false;
		}

		return initialized;
	}

	public List<Parlamentar> getAllSelected() {

		return parlamentarDao.getAllSelected();
	}

	public boolean checkEmptyDB() {
		return parlamentarDao.checkEmptyLocalDatabase();
	}

	public boolean unFollowedParlamentar() throws NullParlamentarException {

		boolean result = true;
		parlamentar.setSeguido(0);
		CotaParlamentarUserController controllerCeap = CotaParlamentarUserController
				.getInstance(context);

		result = controllerCeap.deleteCota(parlamentar.getId())
				&& parlamentarDao.updateParlamentar(parlamentar);

		return result;
	}

	public List<Parlamentar> doRequestMajorRanking(
			ResponseHandler<String> responseHandler)
			throws NullParlamentarException, ConnectionFailedException, RequestFailedException, TransmissionException {

		String urlParlamentarRankingMaiores = MountURL.mountUrlMajorRanking();
		String jsonParlamentarRankingMaiores = HttpConnection
				.requestMajorRanking(responseHandler,
						urlParlamentarRankingMaiores);

		parlamentares =JSONHelper.listParlamentarRankingMaioresFromJSON(jsonParlamentarRankingMaiores);

		return parlamentares;
	}

	public Parlamentar getParlamentar() {
		return parlamentar;
	}
	
	public Parlamentar getSelected(){
		parlamentar = parlamentarDao.getById(parlamentar.getId());
		List<CotaParlamentar>cotas = ceapController.getCotasByIdParlamentar(parlamentar.getId());
		parlamentar.setCotas(cotas);
		return parlamentar;
	}
	
}
