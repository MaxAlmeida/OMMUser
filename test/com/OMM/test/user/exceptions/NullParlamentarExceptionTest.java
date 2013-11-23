package com.OMM.test.user.exceptions;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.OMM.application.user.exceptions.NullParlamentarException;

public class NullParlamentarExceptionTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInstance() {
		NullParlamentarException e = new NullParlamentarException();
		assertEquals(e.getClass(), NullParlamentarException.class);
	}

}
