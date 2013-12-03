package com.OMM.test.user.exceptions;

import android.test.AndroidTestCase;

public class ConnectionFailedExceptionTest extends AndroidTestCase {

	public void testInstance() {

		ConnectionFailedExceptionTest exception = new ConnectionFailedExceptionTest();
		assertEquals(ConnectionFailedExceptionTest.class, exception.getClass());

	}

}
