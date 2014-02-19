package com.OMM.test.user.requests;

import org.apache.http.client.ResponseHandler;

import android.test.AndroidTestCase;

import com.OMM.application.user.controller.UrlHostController;
import com.OMM.application.user.exceptions.ConnectionFailedException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.RequestFailedException;
import com.OMM.application.user.helper.JSONHelper;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.requests.HttpConnection;
import com.OMM.application.user.requests.MountURL;

public class HttpConnectionTest extends AndroidTestCase {

	private ResponseHandler<String> response;

	public void setUp() {
		response = HttpConnection.getResponseHandler();
	}

	public void testRequest() throws ConnectionFailedException,
			RequestFailedException, NullParlamentarException {
		MountURL urlServer = MountURL.getIsntance(getContext(),
				UrlHostController.getInstance(getContext()));

		String url = urlServer.mountURLParlamentar(373);
		String json = HttpConnection.request(response, url);
		String result = "[{\"id\":373,\"nome\":\"PAULO MALUF\",\"partido\":\"PP\",\"uf\":\"SP\"}]";
		Parlamentar pResult = JSONHelper.listParlamentarFromJSON(result).get(0);
		Parlamentar pJson = JSONHelper.listParlamentarFromJSON(json).get(0);

		assertEquals(pResult.getId(), pJson.getId());
		assertEquals(pResult.getNome(), pJson.getNome());
	}

	public void testHttpConnectionInstance() {
		HttpConnection connection = new HttpConnection();
		assertEquals(HttpConnection.class, connection.getClass());
	}
}