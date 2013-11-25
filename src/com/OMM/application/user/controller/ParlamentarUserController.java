package com.OMM.application.user.controller;

import java.util.Iterator;
import java.util.List;

import org.apache.http.client.ResponseHandler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.OMM.application.user.dao.ParlamentarUserDao;
import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.helper.DB;
import com.OMM.application.user.exceptions.NullParlamentarException;
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
	
	public Parlamentar getParlamentar() {
		return this.parlamentar;
	}
	
	public void setParlamentar(Parlamentar parlamentar) {
		this.parlamentar = parlamentar;
	}
	
	public List<Parlamentar> getParlamentares() {
		return this.parlamentares;
	}
	
	public void setParlamentares(List<Parlamentar> parlamentares) {
		this.parlamentares = parlamentares;
	}
	
	private ParlamentarUserController(Context context) {
		ceapController = CeapUserController.getInstance(context);
		parlamentarDao = ParlamentarUserDao.getInstance(context);
		this.context = context;
	}

	public static ParlamentarUserController getInstance(Context context) {

		if (instance == null) {
			instance = new ParlamentarUserController(context);
		}

		return instance;

	}

	public Parlamentar convertJsonToParlamentar(String jsonParlamentar)
			throws NullParlamentarException, TransmissionException {

		Parlamentar parlamentar = null;

		try {
			parlamentar = JSONHelper.listParlamentarFromJSON(jsonParlamentar)
					.get(0);		
			
		} catch (JsonSyntaxException jse) {
			throw new TransmissionException();
		}catch (NullPointerException e) {

			throw new NullParlamentarException();
		}
		if(parlamentar == null){
			throw new NullParlamentarException();
		}
		
		return parlamentar;
	}

	public Parlamentar doRequest(ResponseHandler<String> responseHandler,
			int idParlamentar) throws NullParlamentarException,
			NullCotaParlamentarException, TransmissionException {
		
		if(responseHandler == null){
			throw new TransmissionException();
		}
		
		String urlParlamentar = MontaURL.mountURLParlamentar(idParlamentar);
		String jsonParlamentar = HttpConnection.requestParlamentar(
				responseHandler, urlParlamentar);

		Parlamentar parlamentar = convertJsonToParlamentar(jsonParlamentar);

		String urlCotas = MontaURL.mountURLCota(idParlamentar);
		String jsonCotasParlamentar = HttpConnection.requestCota(
				responseHandler, urlCotas);

		List<CotaParlamentar> cotas = ceapController.convertJsonToCotaParlamentar(jsonCotasParlamentar);

		parlamentar.setCotas(cotas);

		return parlamentar;
	}

	public List<Parlamentar> getSelected(String nameParlamentar) {

		return parlamentarDao.getSelected(nameParlamentar);
	}

	public List<Parlamentar> getAll() {

		return parlamentarDao.getAll();
	}

	public boolean followedParlamentar()
			throws NullParlamentarException {

		boolean result = true;
		
		result = ceapController.persistCotaDB(parlamentar)
				& parlamentarDao.updateParlamentar(parlamentar);

		return result;
	}

	public List<Parlamentar> convertJsonToListParlamentar(
			String jsonParlamentares) throws NullParlamentarException {

		try {

			List<Parlamentar> parlamentares = JSONHelper
					.listParlamentarFromJSON(jsonParlamentares);

			return parlamentares;

		} catch (NullPointerException npe) {

			throw new NullParlamentarException();
		}
	}
	
	public List<Parlamentar> convertJsonToListParlamentarRankingMaiores(
			String jsonParlamentarRankingMaiores) throws NullParlamentarException {
		
		try {
			
			List<Parlamentar> majorRanking = JSONHelper
					.listParlamentarRankingMaioresFromJSON(jsonParlamentarRankingMaiores);

			return majorRanking;
			
		} catch (NullPointerException npe) {

			throw new  NullParlamentarException();
		}
	}

	public boolean insertAll(ResponseHandler<String> response)
			throws NullParlamentarException {

		String urlParlamentares = MontaURL.mountUrlAll();
		String jsonParlamentares = HttpConnection.requestParlamentar(response,
				urlParlamentares);
		List<Parlamentar> parlamentares = convertJsonToListParlamentar(jsonParlamentares);

		boolean initialized;

		if (parlamentarDao.checkEmptyDB()) {
			Iterator<Parlamentar> iterator = parlamentares.iterator();

			while (iterator.hasNext()) {
				parlamentarDao.insertParlamentar(iterator.next());
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

		CeapUserController controllerCeap = CeapUserController.getInstance(context);
		ParlamentarUserDao parlamentarDAO=ParlamentarUserDao.getInstance(context);
		
		result = controllerCeap.deleteCota( parlamentar)&parlamentarDAO.updateParlamentar(parlamentar);

		return result;
	}
	
	public List<Parlamentar> doRequestMajorRanking(ResponseHandler<String> responseHandler
			) throws NullParlamentarException {

		String urlParlamentarRankingMaiores = MontaURL.mountUrlMajorRanking();
		String jsonParlamentarRankingMaiores = HttpConnection.requestMajorRanking(responseHandler,
				urlParlamentarRankingMaiores);
		
		List<Parlamentar> majorRanking = 
				convertJsonToListParlamentarRankingMaiores(jsonParlamentarRankingMaiores);

		return majorRanking;
	}
	
}
