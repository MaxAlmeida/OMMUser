package com.OMM.test.user.exceptions;

import android.test.AndroidTestCase;

import com.OMM.application.user.model.CotaParlamentar;

public class NullCotaParlamentarExceptionTest extends AndroidTestCase {

	public void testNullCotaParlamentarException() throws Exception {
		try {
			CotaParlamentar cotaParlamentar = null;
			cotaParlamentar.toString();
			fail("CotaParlamentarException is null");
		} catch (NullPointerException e) {
			assertTrue(true);
		}

	}

}
