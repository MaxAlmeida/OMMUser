package com.OMM.test.user.controller;

import org.apache.http.client.ResponseHandler;

import android.content.Context;
import android.test.AndroidTestCase;

import com.OMM.application.user.controller.ServerUpdatesController;
import com.OMM.application.user.exceptions.ConnectionFailedException;
import com.OMM.application.user.exceptions.RequestFailedException;
import com.OMM.application.user.helper.JSONHelper;
import com.OMM.application.user.requests.HttpConnection;
import com.OMM.application.user.requests.MountURL;

public class ServerUpdatesControllerTest extends AndroidTestCase {

	private Context context = null;
	private ServerUpdatesController controller = null;
	private ResponseHandler<String> response = null;

	public void setUp() throws Exception {
		super.setUp();

		this.context = getContext();
		this.controller = ServerUpdatesController.getInstance(context);
		this.response = HttpConnection.getResponseHandler();
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetInstance() {
		ServerUpdatesController testedController = ServerUpdatesController
				.getInstance(context);

		assertSame(controller, testedController);
	}

	public void testGetAndInsertUrl() {
		String url = "www.akan.com";

		boolean testedResult = controller.insertUrlServer(url);
		String testedUrl = controller.getUrl();

		assertEquals(true, testedResult);
		assertEquals(url, testedUrl);
	}

	public void testGetExistUpdates() throws ConnectionFailedException,
			RequestFailedException {

		MountURL url = MountURL.getIsntance(context, controller);
		String actualUrl = url.mountUrlExistsUpdate();

		String jsonCodUpdate = HttpConnection.request(response, actualUrl);

		int updateCode = JSONHelper.updateFromJSON(jsonCodUpdate);
		int testedUpdateCode = controller.getExistsUpdates(response);

		assertEquals(updateCode, testedUpdateCode);
	}

	public void testRequestNewUrl() throws ConnectionFailedException,
			RequestFailedException {
		
		MountURL url = MountURL.getIsntance(context, controller);
		String actualUrl = url.mountUrlExistsUpdate();

		String jsonCodUpdate = HttpConnection.request(response, actualUrl);

		int updateCode = controller.getExistsUpdates(response);

		String request = JSONHelper.newRequestUrl(jsonCodUpdate);
		String testedRequest = controller.requestNewUrl(response, updateCode);

		assertEquals(request, testedRequest);
	}
}
