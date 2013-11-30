package com.OMM.test.user.exceptions;

import android.test.AndroidTestCase;



public class RequestFailedException extends AndroidTestCase {
public void testInstance(){
		
		RequestFailedException exception = new RequestFailedException();
		assertEquals(RequestFailedException.class, exception.getClass());
		
	}

}
