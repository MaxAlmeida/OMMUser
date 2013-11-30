package com.OMM.test.user.exceptions;

import android.test.AndroidTestCase;

public class ConnectionFailedException extends AndroidTestCase {
	
public void testInstance(){
		
		ConnectionFailedException exception = new ConnectionFailedException();
		assertEquals(ConnectionFailedException.class, exception.getClass());
		
	}

}
