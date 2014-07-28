package com.OMM.application.user.updates;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ResponseHandler;

import android.content.Context;

import com.OMM.application.user.controller.CotaParlamentarUserController;
import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.controller.ServerUpdatesController;
import com.OMM.application.user.exceptions.ConnectionFailedException;
import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.RequestFailedException;
import com.OMM.application.user.helper.JSONHelper;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.requests.HttpConnection;
import com.OMM.application.user.requests.MountURL;

public class ObserverCota implements ObserverUpdates {

	private ParlamentarUserController parlamentarController;
	private CotaParlamentarUserController cotaParlamentarController;
	private Context context;

	public ObserverCota(GetServerUpdates subject, Context context) {
		this.context = context;
		subject.registerObserver(this);
		cotaParlamentarController = CotaParlamentarUserController
				.getInstance(context);
		parlamentarController = ParlamentarUserController.getInstance(context);
	}

	@Override
	public void update(long idUpdate) throws ConnectionFailedException,
			RequestFailedException, NullCotaParlamentarException {

		String update = String.valueOf(idUpdate);

		if (update.charAt(2) == '3') {
			ResponseHandler<String> responseHandler = HttpConnection
					.getResponseHandler();

			if (responseHandler != null) {
				String url;

				ArrayList<Integer> parlamentaresIds = (ArrayList<Integer>) parlamentarController
						.getAllSelectedIds();
				long idParlamentarUpdate = parlamentarController
						.getLastIdUpdate();
				url = MountURL.getIsntance(context,
						ServerUpdatesController.getInstance(context))
						.mountUrlCotaParlamentarUpdate(idParlamentarUpdate,
								parlamentaresIds);
				String jsonCotaParlamentarUpdate = HttpConnection.request(
						responseHandler, url);

				List<CotaParlamentar> listCotas = JSONHelper
						.listCotaParlamentarFromJSON(jsonCotaParlamentarUpdate);
				if (listCotas.size() != 0) {
					cotaParlamentarController
							.persistCotasOnLocalDatabase(listCotas);
				} else {
					// nothing here
				}
			}

		}
	}
}
