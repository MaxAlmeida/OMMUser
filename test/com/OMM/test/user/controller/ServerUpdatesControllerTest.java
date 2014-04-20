package com.OMM.test.user.controller;

import android.content.Context;
import android.test.AndroidTestCase;

import com.OMM.application.user.controller.ServerUpdatesController;

public class ServerUpdatesControllerTest extends AndroidTestCase {

	private Context context = null;
	private ServerUpdatesController controller = null;

	public void setUp() throws Exception {
		super.setUp();

		this.context = getContext();
		this.controller = ServerUpdatesController.getInstance(context);
	}

	public void testGetInstance() {
		ServerUpdatesController testedController = ServerUpdatesController
				.getInstance(context);
		
		assertSame(controller, testedController);
	}
	
	public void testGetAndInsertUrl() {
		String testedUrl = "www.akan.com";
		
		boolean testedResult = controller.insertUrlServer(testedUrl);
		String url = controller.getUrl();
		
		assertEquals(true, testedResult);
		assertEquals(testedUrl, url);
	}
}
