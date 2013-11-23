package com.OMM.test.user.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.TransmissionException;
import com.OMM.application.user.model.Parlamentar;

public class ParlamentarUserControllerTest {
	
	ParlamentarUserController controller;
	Parlamentar p;
	@Before
	public void setUp() throws Exception {
		controller = ParlamentarUserController
				.getInstance(null);
	}

	@After
	public void tearDown() throws Exception {
		p = null;
	}

	@Test
	public void testSingleton() {
		ParlamentarUserController controller2 = ParlamentarUserController.getInstance(null);
		assertSame(controller, controller2);
	}
	
	@Test
	public void testConvertJsonToParlamentar() throws NullParlamentarException, TransmissionException{
		p = controller.convertJsonToParlamentar("[{\"id\":373,\"nome\":\"PAULO MALUF\",\"partido\":\"PP\",\"uf\":\"SP\"}]");
		assertEquals(p.getId(),373);
		assertEquals(p.getPartido(),"PP");
	}

	@Test(expected = NullParlamentarException.class)
	public void testConvertJsonToParlamentarNullParlamentarException() throws NullParlamentarException, TransmissionException{
		p = controller.convertJsonToParlamentar(null);
	}
	@Test(expected = TransmissionException.class)
	public void testConvertJsonToParlamentarTransmitionException() throws NullParlamentarException, TransmissionException{
		p = controller.convertJsonToParlamentar("ashausha");
	}
	
	@Test(expected = TransmissionException.class)
	public void doRequestTest() throws NullParlamentarException, NullCotaParlamentarException, TransmissionException{
			p = controller.doRequest(null, 0);	
	}
	
	@Test
	public void testConvertJsonToListParlamentar() throws NullParlamentarException, TransmissionException{
		List<Parlamentar> lp = controller.convertJsonToListParlamentar("[{\"id\":373,\"nome\":\"PAULO MALUF\",\"partido\":\"PP\",\"uf\":\"SP\"}]");
		assertEquals(lp.get(0).getId(),373);
		assertEquals(lp.get(0).getPartido(),"PP");
	}

	
}
