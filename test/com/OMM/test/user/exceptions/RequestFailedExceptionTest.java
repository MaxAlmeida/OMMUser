package com.OMM.test.user.exceptions;

import android.test.AndroidTestCase;

public class RequestFailedExceptionTest extends AndroidTestCase {
	public void testInstance() {

		RequestFailedExceptionTest exception = new RequestFailedExceptionTest();
		assertEquals(RequestFailedExceptionTest.class, exception.getClass());

	}

}
