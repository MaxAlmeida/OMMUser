package com.OMM.test.user.controller;

import junit.framework.Assert;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;

import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;

public class ParlamentarUserControllerTest extends AndroidTestCase{
	
	private MockContext context = new MockContext();
	private Parlamentar parlamentar;
	private CotaParlamentar cota;
	private ParlamentarUserController controller = ParlamentarUserController.getInstance(context);
	
	public void testGetInstance() {
		
		ParlamentarUserController controller2 = ParlamentarUserController.getInstance(context);
		assertSame(controller, controller2);
	}	
}