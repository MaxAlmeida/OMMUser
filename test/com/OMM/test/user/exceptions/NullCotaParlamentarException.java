package com.OMM.test.user.exceptions;

import android.test.AndroidTestCase;

public class NullCotaParlamentarException extends AndroidTestCase {
	
public void testInstance(){
		
		NullParlamentarRankingMaioresException exception = new NullParlamentarRankingMaioresException();
		assertEquals(NullParlamentarRankingMaioresException.class, exception.getClass());
		
	}

}
