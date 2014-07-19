package com.OMM.application.user.updates;

import java.util.Iterator;
import java.util.List;

import org.apache.http.client.ResponseHandler;

import android.content.Context;

import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.controller.ServerUpdatesController;
import com.OMM.application.user.exceptions.ConnectionFailedException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.RequestFailedException;
import com.OMM.application.user.helper.JSONHelper;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.requests.HttpConnection;
import com.OMM.application.user.requests.MountURL;

public class ObserverParlamentar implements ObserverUpdates {

	private Context context;
	private ParlamentarUserController parlamentarController;

	public ObserverParlamentar(Context context,
			GetServerUpdates serverUpdatesSubject) {
		this.context = context;
		parlamentarController = ParlamentarUserController.getInstance(context);
		serverUpdatesSubject.registerObserver(this);
	}

	@Override
	public void update(long idUpdate) throws ConnectionFailedException,
			RequestFailedException, NullParlamentarException {
		String update = String.valueOf(idUpdate);

		if (update.charAt(1) == '2') {
			ResponseHandler<String> responseHandler = HttpConnection
					.getResponseHandler();
			if (responseHandler != null) {
				String url;
				long idParlamentarUpdate = parlamentarController
						.getIdUpdateParlamentar();
				url = MountURL.getIsntance(context,
						ServerUpdatesController.getInstance(context))
						.mountUrlParlamentarUpdate(idParlamentarUpdate);
				String jsonParlamentarUpdate;
				jsonParlamentarUpdate = HttpConnection.request(responseHandler,
						url);
				List<Parlamentar> listParlamentares = JSONHelper
						.listParlamentarFromJSON(jsonParlamentarUpdate);
				Iterator<Parlamentar> iterator = listParlamentares.iterator();
				while (iterator.hasNext()) {
					parlamentarController.updateParlamentar(iterator.next());
				}
			}
		}
	}
}
