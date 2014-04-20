package com.OMM.test.user.controller;

import com.OMM.application.user.controller.ServerUpdatesController;

import android.content.Context;
import android.test.AndroidTestCase;

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
}
