package com.OMM.test.user.exceptions;

import android.test.AndroidTestCase;

public class TransmissionExceptionTest extends AndroidTestCase {

	public void testInstance() {

		TransmissionExceptionTest exception = new TransmissionExceptionTest();
		assertEquals(TransmissionExceptionTest.class, exception.getClass());

	}

}
