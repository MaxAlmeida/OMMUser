package com.OMM.test.user.controller;

import com.OMM.application.user.controller.ServerUpdatesController;
import com.OMM.application.user.dao.ServerUpdatesDao;

import android.content.Context;
import android.test.AndroidTestCase;

public class ServerUpdatesControllerTest extends AndroidTestCase {

	private Context context = null;
	private ServerUpdatesController controller = null;
	private ServerUpdatesDao dao = null;

	public void setUp() throws Exception {
		super.setUp();

		this.context = getContext();
		this.controller = ServerUpdatesController.getInstance(context);
		this.dao = ServerUpdatesDao.getInstance(context);
	}

	public void testGetInstance() {
		ServerUpdatesController testedController = ServerUpdatesController
				.getInstance(context);
		
		assertSame(controller, testedController);
	}
	
	public void testGetUrl() {
		ServerUpdatesDao testedDao = ServerUpdatesDao.getInstance(context);
		
		String testedUrlHost = testedDao.getUrlServer();
		String urlHostDao = dao.getUrlServer();
		
		assertEquals(urlHostDao, testedUrlHost);
	}
}
