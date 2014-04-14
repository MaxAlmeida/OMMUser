package com.OMM.application.user.controller;

import org.apache.http.client.ResponseHandler;

import android.content.Context;

import com.OMM.application.user.dao.ServerUpdatesDao;
import com.OMM.application.user.exceptions.ConnectionFailedException;
import com.OMM.application.user.exceptions.RequestFailedException;
import com.OMM.application.user.helper.JSONHelper;
import com.OMM.application.user.requests.HttpConnection;
import com.OMM.application.user.requests.MountURL;

/*
 * classe criada para verificar se existe 
 * atualizacoes disponiveis no servidor 
 * eh um racunho ainda ... revisar ela toda
 * 
 */
public class ServerUpdatesController {

	private static ServerUpdatesController instance = null;
	private ServerUpdatesDao urlHostDao = null;
	private static MountURL url = null;

	private ServerUpdatesController(Context context) {
		urlHostDao = ServerUpdatesDao.getInstance(context);

	}

	public static ServerUpdatesController getInstance(Context context) {
		if (instance == null)
			instance = new ServerUpdatesController(context);

		url = MountURL.getIsntance(context, instance);
		return instance;

	}

	public String getUrl() {
		return urlHostDao.getUrlServer();
	}

	public boolean insertUrlServer(String url_server) {
		return urlHostDao.insertUrlServer(url_server);
	}

	public int getExistsUpdates(ResponseHandler<String> response)
			throws ConnectionFailedException, RequestFailedException {

		String url = ServerUpdatesController.url.mountUrlExistsUpdate();
		String jsonCodUpdate = HttpConnection.request(response, url);

		return JSONHelper.updateFromJSON(jsonCodUpdate);

	}

	public String requestNewUrl(ResponseHandler<String> response, int cod)
			throws NullPointerException, ConnectionFailedException,
			RequestFailedException {
		String url = ServerUpdatesController.url.mountUrlRequestUpdates(cod);
		String jsonNewUrlServer = HttpConnection.request(response, url);

		return JSONHelper.newRequestUrl(jsonNewUrlServer);
	}
}
