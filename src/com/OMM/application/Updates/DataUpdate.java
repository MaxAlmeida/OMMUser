package com.OMM.application.Updates;
//TODO apagar essa classe
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ResponseHandler;

import android.content.Context;

import com.OMM.application.user.controller.CotaParlamentarUserController;
import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.controller.UrlHostController;
import com.OMM.application.user.exceptions.ConnectionFailedException;
import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.RequestFailedException;
import com.OMM.application.user.helper.JSONHelper;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.requests.HttpConnection;
import com.OMM.application.user.requests.MountURL;

public class DataUpdate {

	private ParlamentarUserController parlamentarController;
	private CotaParlamentarUserController cotaParlamentarController;
	private UrlHostController urlHostController;
	private Context context;

	public DataUpdate(Context context) {
		this.context = context;
		urlHostController = UrlHostController.getInstance(context);
		parlamentarController = ParlamentarUserController.getInstance(context);
		cotaParlamentarController = CotaParlamentarUserController
				.getInstance(context);
	}

	public List<Parlamentar> doRequestParlamentar(
			ResponseHandler<String> responseHandler)
			throws ConnectionFailedException,
			RequestFailedException, NullParlamentarException {

		List<Parlamentar> parlamentares = new ArrayList<Parlamentar>();

		if (responseHandler != null) {
			String url;
			int idUpdate = parlamentarController.getIdUpdateParlamentar();
			url = MountURL.getIsntance(context, urlHostController)
					.mountUrlParlamentarUpdate(idUpdate);
			String jsonParlamentarUpdate = HttpConnection.request(
					responseHandler, url);

			parlamentares = JSONHelper
					.listParlamentarFromJSON(jsonParlamentarUpdate);
			parlamentarController.setParlamentares(parlamentares);
		}
		return parlamentares;
	}

	public List<CotaParlamentar> doRequestCota(
			ResponseHandler<String> responseHandler)
			throws ConnectionFailedException, RequestFailedException,
			NullParlamentarException, NullCotaParlamentarException {

		List<CotaParlamentar> cotaParlamentar = new ArrayList<CotaParlamentar>();

		if (responseHandler != null) {
			String url;

			ArrayList<Integer> parlamentaresIds = (ArrayList<Integer>) parlamentarController
					.getAllSelectedIds();
			int idUpdate = parlamentarController.getLastIdUpdate();
			url = MountURL.getIsntance(context, urlHostController)
					.mountUrlCotaParlamentarUpdate(idUpdate, parlamentaresIds);
			String jsonCotaParlamentarUpdate = HttpConnection.request(
					responseHandler, url);

			cotaParlamentar = JSONHelper
					.listCotaParlamentarFromJSON(jsonCotaParlamentarUpdate);
			if (cotaParlamentar.size() != 0) {
				cotaParlamentarController
						.persistCotasOnLocalDatabase(cotaParlamentar);
			} else {
				// nothing here
			}
		}
		return cotaParlamentar;
	}

	public boolean doRequestUpdateVerify(ResponseHandler<String> responseHandler)
			throws ConnectionFailedException, RequestFailedException {

		int idUpdate = parlamentarController.getLastIdUpdate();

		String url = MountURL.getIsntance(context, urlHostController)
				.mountUrlExistsUpdate();
		String jsonUpdateVerify = HttpConnection.request(responseHandler, url);
		int serverIdUpdate = JSONHelper.updateFromJSON(jsonUpdateVerify);

		if (serverIdUpdate > idUpdate) {
			return true;
		} else {
			return false;
		}
	}

}
