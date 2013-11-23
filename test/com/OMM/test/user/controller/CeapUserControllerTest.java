package com.OMM.test.user.controller;

import static org.junit.Assert.assertSame;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.OMM.application.user.controller.CeapUserController;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.TransmissionException;
import com.OMM.application.user.model.CotaParlamentar;

public class CeapUserControllerTest {

	CeapUserController controller;
	
	@Before
	public void setUp() throws Exception {
		controller = CeapUserController.getInstance(null);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSingleton() {
		CeapUserController c2 = CeapUserController.getInstance(null);
		assertSame(controller, c2);
	}
	
	@Test(expected = NullParlamentarException.class) 
	public void testException() throws NullParlamentarException, TransmissionException{
			List<CotaParlamentar> convert = controller.convertJsonToCotaParlamentar("null");
	}

	
}
