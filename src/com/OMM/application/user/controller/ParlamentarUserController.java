package com.OMM.application.user.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.client.ResponseHandler;

import android.content.Context;
import android.util.Log;

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
import com.OMM.application.user.requests.MontaURL;
import com.google.gson.JsonSyntaxException;

public class ParlamentarUserController {

	private static ParlamentarUserController instance;
	private static Parlamentar parlamentar;
	private static List<Parlamentar> parlamentares;
	private ParlamentarUserDao parlamentarDao;
	private Context context;
	private CeapUserController ceapController;

	private ParlamentarUserController(Context context) {
		ceapController = CeapUserController.getInstance(context);
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

	public Parlamentar convertJsonToParlamentar(String jsonParlamentar)
			throws NullParlamentarException, TransmissionException {

		Parlamentar parlamentar = null;

		try {
			parlamentar = JSONHelper.listParlamentarFromJSON(jsonParlamentar)
					.get(0);

		} catch (JsonSyntaxException jse) {
			throw new TransmissionException();
		} catch (NullPointerException e) {

			throw new NullParlamentarException();
		}
		if (parlamentar == null) {
			throw new NullParlamentarException();
		}

		return parlamentar;
	}

	public Parlamentar doRequest(ResponseHandler<String> responseHandler)
			throws NullParlamentarException, NullCotaParlamentarException,
			TransmissionException, ConnectionFailedException, RequestFailedException {

		if (responseHandler == null) {
			throw new TransmissionException();
		}
		int idParlamentar = parlamentar.getId();
		String urlParlamentar = MontaURL.mountURLParlamentar(idParlamentar);
		String jsonParlamentar = HttpConnection.requestParlamentar(
				responseHandler, urlParlamentar);

		parlamentar = convertJsonToParlamentar(jsonParlamentar);

		String urlCotas = MontaURL.mountURLCota(idParlamentar);
		String jsonCotasParlamentar = HttpConnection.requestCota(
				responseHandler, urlCotas);

		List<CotaParlamentar> cotas = ceapController
				.convertJsonToCotaParlamentar(jsonCotasParlamentar);

		parlamentar.setCotas(cotas);

		return parlamentar;
	}

	// TODO: Fazer direcionamento pela controller
	public List<CotaParlamentar> convertJsonToCotaParlamentar(

	String jsonCotaParlamentar) throws NullCotaParlamentarException {

		try {
			List<CotaParlamentar> listCotas = JSONHelper
					.listCotaParlamentarFromJSON(jsonCotaParlamentar);
			return listCotas;

		} catch (NullPointerException e) {

			throw new NullCotaParlamentarException();
		}

	}

	public Parlamentar doRequest(ResponseHandler<String> responseHandler,
			int idParlamentar) throws NullParlamentarException,
			NullCotaParlamentarException, ConnectionFailedException,
			RequestFailedException, TransmissionException {

		String urlParlamentar = MontaURL.mountURLParlamentar(idParlamentar);
		String jsonParlamentar = HttpConnection.requestParlamentar(
				responseHandler, urlParlamentar);

		parlamentar = convertJsonToParlamentar(jsonParlamentar);

		String urlCotas = MontaURL.mountURLCota(idParlamentar);
		String jsonCotasParlamentar = HttpConnection.requestCota(
				responseHandler, urlCotas);

		List<CotaParlamentar> cotas = ceapController
				.convertJsonToCotaParlamentar(jsonCotasParlamentar);

		parlamentar.setCotas(cotas);

		return parlamentar;
	}

	public List<Parlamentar> getByName(String nameParlamentar) {
		parlamentares = parlamentarDao.getSelected(nameParlamentar);
		return parlamentares;
	}

	public List<Parlamentar> getAll() {

		parlamentares = parlamentarDao.getAll();
		return parlamentares;
	}

	public boolean followedParlamentar()
			throws NullParlamentarException {

		boolean result = true;

		CeapUserController controllerCeap = CeapUserController
				.getInstance(context);
		ParlamentarUserDao parlamentarDAO = ParlamentarUserDao
				.getInstance(context);

		result = controllerCeap.persistCotaDB(parlamentar)
				& parlamentarDAO.updateParlamentar(parlamentar);
		return result;
	}

	public List<Parlamentar> convertJsonToListParlamentar(
			String jsonParlamentares) throws NullParlamentarException {

		try
		{

			List<Parlamentar> parlamentares = JSONHelper
					.listParlamentarFromJSON(jsonParlamentares);

			return parlamentares;

		} catch (NullPointerException npe) {

			throw new NullParlamentarException();
		}
	}

	public List<Parlamentar> convertJsonToListParlamentarRankingMaiores(
			String jsonParlamentarRankingMaiores)
			throws NullParlamentarException {

		try {

			List<Parlamentar> majorRanking = JSONHelper
					.listParlamentarRankingMaioresFromJSON(jsonParlamentarRankingMaiores);

			return majorRanking;

		} catch (NullPointerException npe) {

			throw new NullParlamentarException();
		}
	}

	public boolean insertAll(ResponseHandler<String> response)
			throws NullParlamentarException, ConnectionFailedException,
			RequestFailedException {

		String urlParlamentares = MontaURL.mountUrlAll();
		String jsonParlamentares = HttpConnection.requestParlamentar(response,
				urlParlamentares);
		List<Parlamentar> parlamentares = convertJsonToListParlamentar(jsonParlamentares);
		boolean initialized;
		if (parlamentarDao.checkEmptyDB()) {
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
		return parlamentarDao.checkEmptyDB();
	}

	public boolean unFollowedParlamentar() throws NullParlamentarException {

		boolean result = true;

		CeapUserController controllerCeap = CeapUserController
				.getInstance(context);
		ParlamentarUserDao parlamentarDAO = ParlamentarUserDao
				.getInstance(context);

		result = controllerCeap.deleteCota(parlamentar)
				& parlamentarDAO.updateParlamentar(parlamentar);

		return result;
	}

	public List<Parlamentar> doRequestMajorRanking(
			ResponseHandler<String> responseHandler)
			throws NullParlamentarException {

		String urlParlamentarRankingMaiores = MontaURL.mountUrlMajorRanking();
		String jsonParlamentarRankingMaiores = HttpConnection
				.requestMajorRanking(responseHandler,
						urlParlamentarRankingMaiores);

		parlamentares = convertJsonToListParlamentarRankingMaiores(jsonParlamentarRankingMaiores);

		return parlamentares;
	}

	public Parlamentar getParlamentar() {
		return parlamentar;
	}
	
	public Parlamentar getSelected(){
		List<CotaParlamentar>cotas = ceapController.getCotasByIdParlamentar(parlamentar.getId());
		parlamentar.setCotas(cotas);
		return parlamentar;
	
	}
	
}
