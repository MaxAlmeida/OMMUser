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
	private MountURL url;
	private ServerUpdatesController urlHostController;

	private ParlamentarUserController(Context context) {
		ceapController = CotaParlamentarUserController.getInstance(context);
		parlamentarDao = ParlamentarUserDao.getInstance(context);
		urlHostController = ServerUpdatesController.getInstance(context);

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
			ConnectionFailedException, RequestFailedException {

		if (responseHandler != null) {

			url = MountURL.getIsntance(context, urlHostController);
			int idParlamentar = parlamentar.getId();
			String urlCotas = url.mountURLCota(idParlamentar);
			String jsonCotasParlamentar = HttpConnection.request(
					responseHandler, urlCotas);

			List<CotaParlamentar> cotas = JSONHelper
					.listCotaParlamentarFromJSON(jsonCotasParlamentar);

			parlamentar.setCotas(cotas);

		} else if (parlamentar == null) {
			throw new NullParlamentarException();

		}
		return parlamentar;
	}

	public List<Parlamentar> getByName(String nameParlamentar) {
		parlamentares = parlamentarDao.getSelectedByName(nameParlamentar);
		return parlamentares;
	}

	public List<Parlamentar> getAll() {

		parlamentares = parlamentarDao.getMajorRanking();
		return parlamentares;
	}

	public List<Parlamentar> getMinor() {

		parlamentares = parlamentarDao.getMinorRanking();
		return parlamentares;
	}

	public boolean followedParlamentar() throws NullCotaParlamentarException,
			NullParlamentarException {

		boolean result = true;

		if (parlamentar != null) {
			CotaParlamentarUserController cotaParlamentarUserController = CotaParlamentarUserController
					.getInstance(context);
			parlamentar.setSeguido(1);
			result = parlamentarDao.setSeguidoParlamentar(parlamentar)
					&& cotaParlamentarUserController
							.persistCotasOnLocalDatabase(parlamentar.getCotas());
			return result;

		} else if (parlamentar == null) {
			throw new NullParlamentarException();

		} else {
			throw new NullCotaParlamentarException();
		}
	}

	public boolean insertAll(ResponseHandler<String> response)
			throws NullParlamentarException, ConnectionFailedException,
			RequestFailedException {

		boolean initialized = false;

		if (response != null) {
			url = MountURL.getIsntance(context, urlHostController);
			String urlParlamentares = url.mountUrlAll();

			String jsonParlamentares = HttpConnection.request(response,
					urlParlamentares);
			
			Log.i("VALOR DA VARIÁVEL jsonParlamentares", jsonParlamentares);
			
			List<Parlamentar> parlamentares = JSONHelper
					.listParlamentarFromJSON(jsonParlamentares);

			if (parlamentarDao.checkEmptyLocalDatabase()) {
				Iterator<Parlamentar> iterator = parlamentares.iterator();
				int majorRankingPos = 1;
				while (iterator.hasNext()) {
					Parlamentar p = iterator.next();
					p.setSeguido(0);
					p.setMajorRankingPos(majorRankingPos);
					parlamentarDao.insertParlamentar(p);
					majorRankingPos++;
				}

				initialized = true;

			} else {
				initialized = false;
			}

		} else if (parlamentar == null) {
			throw new NullParlamentarException();
		}

		return initialized;
	}

	public List<Parlamentar> getAllSelected() {

		return parlamentarDao.getAllSelected();
	}

	public boolean checkEmptyDB() {
		return parlamentarDao.checkEmptyLocalDatabase();
	}

	public boolean unfollowedParlamentar() throws NullParlamentarException,
			NullCotaParlamentarException {
		boolean result = true;

		if (parlamentar != null) {
			parlamentar.setCotas(ceapController
					.getCotasByIdParlamentar(parlamentar.getId()));

			parlamentar.setSeguido(0);
			CotaParlamentarUserController controllerCeap = CotaParlamentarUserController
					.getInstance(context);

			result = parlamentarDao.setSeguidoParlamentar(parlamentar)
					&& controllerCeap.deleteCota(parlamentar.getId());

			return result;

		} else if (parlamentar == null) {
			throw new NullParlamentarException();
		} else {

			throw new NullCotaParlamentarException();
		}
	}

	public Parlamentar getParlamentar() {
		return parlamentar;
	}

	public Parlamentar getSelected() {
		parlamentar = parlamentarDao.getById(parlamentar.getId());
		List<CotaParlamentar> cotas = ceapController
				.getCotasByIdParlamentar(parlamentar.getId());
		parlamentar.setCotas(cotas);
		return parlamentar;
	}

	public int getIdUpdateParlamentar() {
		return parlamentarDao.getIdUpdateParlamentar(parlamentar.getId());
	}

	public boolean updateParlamentar(Parlamentar parlamentar) {
		try {
			return parlamentarDao.updateParlamentar(parlamentar);
		} catch (NullParlamentarException e) {

			e.printStackTrace();
		}
		return false;
	}

	public List<Integer> getAllSelectedIds() {
		return parlamentarDao.getAllSelectedIds();
	}

	public int getLastIdUpdate() {
		parlamentares = parlamentarDao.getMajorRanking();
		int idUpdate = 0;

		Iterator<Parlamentar> iterator = parlamentares.iterator();
		while (iterator.hasNext()) {
			Parlamentar p = iterator.next();
			if (p.getIdUpdate() > idUpdate) {
				idUpdate = p.getIdUpdate();
			}
		}
		return idUpdate;
	}

}