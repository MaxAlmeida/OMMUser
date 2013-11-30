package com.OMM.test.user.exceptions;

import android.test.AndroidTestCase;

public class TransmissionException extends AndroidTestCase {
	
public void testInstance(){
		
		TransmissionException exception = new TransmissionException();
		assertEquals(TransmissionException.class, exception.getClass());
		
	}

}
