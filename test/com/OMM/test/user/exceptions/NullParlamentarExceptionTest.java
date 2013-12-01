package com.OMM.test.user.exceptions;

import android.test.AndroidTestCase;

import com.OMM.application.user.model.Parlamentar;

public class NullParlamentarExceptionTest extends AndroidTestCase {

	@SuppressWarnings("null")
	public void testNullParlamentarException() throws Exception {
		try {
			Parlamentar parlamentar = null;
			parlamentar.toString();
			fail("CotaParlamentarException is null");
		} catch (NullPointerException e) {
			assertTrue(true);
		}

	}

}
