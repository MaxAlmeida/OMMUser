package com.OMM.application.Updates;

import java.util.List;

import org.apache.http.client.ResponseHandler;

import android.content.Context;

import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.controller.UrlHostController;
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

public class DataUpdate {

	private ParlamentarUserController parlamentarController;
	private UrlHostController urlHostController;
	private Context context;

	public DataUpdate(Context context) {
		this.context = context;
		urlHostController = UrlHostController.getInstance(context);
		parlamentarController = ParlamentarUserController.getInstance(context);
	}

	public Parlamentar doRequest(ResponseHandler<String> responseHandler,
			Parlamentar parlamentar) throws TransmissionException,
			ConnectionFailedException, RequestFailedException,
			NullParlamentarException {

		if (responseHandler != null) {
			String url;
			parlamentarController.setParlamentar(parlamentar);
			int idUpdate = parlamentarController.getIdUpdateParlamentar();
			url = MountURL.getIsntance(context, urlHostController)
					.mountUrlParlamentarUpdate(idUpdate, parlamentar.getId());
			String jsonParlamentarUpdate = HttpConnection.request(
					responseHandler, url);

			List<Parlamentar> parlamentares = JSONHelper
					.listParlamentarFromJSON(jsonParlamentarUpdate);
			if (parlamentares.size() != 0) {
				parlamentar = parlamentares.get(0);
				parlamentarController.setParlamentar(parlamentar);
			}
			else{
				//nothing here
			}
		} else {
			throw new TransmissionException();
		}
		return parlamentar;
	}

	public Parlamentar doRequestCota(ResponseHandler<String> responseHandler,
			Parlamentar parlamentar) throws TransmissionException,
			ConnectionFailedException, RequestFailedException,
			NullParlamentarException, NullCotaParlamentarException {

		if (responseHandler != null) {
			String url;
			parlamentarController.setParlamentar(parlamentar);
			int idUpdate = parlamentarController.getIdUpdateParlamentar();
			url = MountURL.getIsntance(context, urlHostController)
					.mountUrlCotaParlamentarUpdate(idUpdate, parlamentar.getId());
			String jsonCotaParlamentarUpdate = HttpConnection.request(
					responseHandler, url);

			List<CotaParlamentar> cotaParlamentar = JSONHelper
					.listCotaParlamentarFromJSON(jsonCotaParlamentarUpdate);
			if (cotaParlamentar.size() != 0) {
				parlamentar.setCotas(cotaParlamentar);
				parlamentarController.setParlamentar(parlamentar);
			}
			else{
				//nothing here
			}
		} else {
			throw new TransmissionException();
		}
		return parlamentar;
	}
	
}
