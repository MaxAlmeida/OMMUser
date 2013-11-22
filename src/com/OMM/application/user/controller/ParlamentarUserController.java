package com.OMM.application.user.controller;

import java.util.Iterator;
import java.util.List;

import org.apache.http.client.ResponseHandler;

import android.content.Context;

import com.OMM.application.user.dao.ParlamentarUserDao;
import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.NullParlamentarRankingMaioresException;
import com.OMM.application.user.helper.JSONHelper;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.model.ParlamentarRankingMaiores;
import com.OMM.application.user.requests.HttpConnection;
import com.OMM.application.user.requests.MontaURL;

public class ParlamentarUserController {

	private static ParlamentarUserController instance;
	Parlamentar parlamentar;
	private ParlamentarUserDao parlamentarDao;
	private Context context;

	private ParlamentarUserController(Context context) {

		parlamentarDao = ParlamentarUserDao.getInstance(context);
		this.context=context;
	}

	public static ParlamentarUserController getInstance(Context context) {

		if (instance == null) {
			instance = new ParlamentarUserController(context);
		}

		return instance;

	}

	public Parlamentar convertJsonToParlamentar(String jsonParlamentar)
			throws NullParlamentarException {
		try {
			Parlamentar parlamentar = JSONHelper.listParlamentarFromJSON(
					jsonParlamentar).get(0);

			return parlamentar;
			
		} catch (NullPointerException e) {
			
			return null;
		}
	}

	public List<CotaParlamentar> convertJsonToCotaParlamentar(
			String jsonCotaParlamentar) throws NullCotaParlamentarException {

		try {
			List<CotaParlamentar> listCotas = JSONHelper
					.listCotaParlamentarFromJSON(jsonCotaParlamentar);

			return listCotas;
		} catch (NullPointerException e) {
			return null;
		}
	}

	public Parlamentar doRequest(ResponseHandler<String> responseHandler,
			int idParlamentar) throws NullParlamentarException,
			NullCotaParlamentarException {

		String urlParlamentar = MontaURL.mountURLParlamentar(idParlamentar);
		String jsonParlamentar = HttpConnection.requestParlamentar(responseHandler,
				urlParlamentar);
		
		Parlamentar parlamentar = convertJsonToParlamentar(jsonParlamentar);

		String urlCotas = MontaURL.mountURLCota(idParlamentar);
		String jsonCotasParlamentar = HttpConnection.requestCota(responseHandler,
				urlCotas);

		List<CotaParlamentar> cotas = convertJsonToCotaParlamentar(jsonCotasParlamentar);

		parlamentar.setCotas(cotas);

		return parlamentar;
	}

	public List<Parlamentar> getSelected(String nameParlamentar) {

		return parlamentarDao.getSelected(nameParlamentar);
	}

	public List<Parlamentar> getAll() {

		return parlamentarDao.getAll();
	}

	public boolean followedParlamentar(Parlamentar parlamentar) throws NullParlamentarException {

		boolean result = true;

		CeapUserController controllerCeap = CeapUserController.getInstance(context);
		ParlamentarUserDao parlamentarDAO=ParlamentarUserDao.getInstance(context);
		
		result = controllerCeap.persistCotaDB( parlamentar)&parlamentarDAO.updateParlamentar(parlamentar);

		return result;
	}

	public List<Parlamentar> convertJsonToListParlamentar(String jsonParlamentares)
			throws NullParlamentarException {
		
		try {
			
			List<Parlamentar> parlamentares = JSONHelper
					.listParlamentarFromJSON(jsonParlamentares);

			return parlamentares;
			
		} catch (NullPointerException npe) {

			throw new  NullParlamentarException();
		}
	}
	
	public List<ParlamentarRankingMaiores> convertJsonToListParlamentarRankingMaiores(
			String jsonParlamentarRankingMaiores) throws NullParlamentarRankingMaioresException {
		
		try {
			
			List<ParlamentarRankingMaiores> majorRanking = JSONHelper
					.listParlamentarRankingMaioresFromJSON(jsonParlamentarRankingMaiores);

			return majorRanking;
			
		} catch (NullPointerException npe) {

			throw new  NullParlamentarRankingMaioresException();
		}
	}

	public boolean insertAll(ResponseHandler<String> response) throws
	NullParlamentarException {
 
	String urlParlamentares = MontaURL.mountUrlAll(); 
	String jsonParlamentares = HttpConnection.requestParlamentar(response, urlParlamentares); 
	List<Parlamentar> parlamentares = convertJsonToListParlamentar(jsonParlamentares);

	boolean initialized; 
 
	if (parlamentarDao.checkEmptyDB()) {
		Iterator<Parlamentar> iterator = parlamentares.iterator();
		
		while(iterator.hasNext()){ 
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
	
	public List<ParlamentarRankingMaiores> doRequestMajorRanking(ResponseHandler<String> responseHandler
			) throws NullParlamentarRankingMaioresException {

		String urlParlamentarRankingMaiores = MontaURL.mountUrlMajorRanking();
		String jsonParlamentarRankingMaiores = HttpConnection.requestMajorRanking(responseHandler,
				urlParlamentarRankingMaiores);
		
		List<ParlamentarRankingMaiores> majorRanking = 
				convertJsonToListParlamentarRankingMaiores(jsonParlamentarRankingMaiores);

		return majorRanking;
	}
	
}
