package com.OMM.test.user.exceptions;

import org.junit.Test;

import android.test.AndroidTestCase;

import com.OMM.application.user.model.CotaParlamentar;

public class NullCotaParlamentarExceptionTest extends AndroidTestCase {

	@Test
	@SuppressWarnings("null")
	public void testNullCotaParlamentarException() {
		try {
			CotaParlamentar cotaParlamentar = null;
			cotaParlamentar.toString();
			fail("Should have thrown an exception");
		} catch (NullPointerException e) {
			assertTrue(true);
		}

	}

}
