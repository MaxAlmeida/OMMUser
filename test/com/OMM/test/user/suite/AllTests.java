package com.OMM.test.user.suite;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.OMM.test.user.controller.*;
import com.OMM.test.user.dao.*;
import com.OMM.test.user.exceptions.*;
import com.OMM.test.user.helper.*;
import com.OMM.test.user.model.*;
import com.OMM.test.user.requests.*;

public class AllTests
{
	public static Test suite( )
	{
		TestSuite suite = new TestSuite(AllTests.class.getName());
		// $JUnit-BEGIN$
		suite.addTestSuite(CotaParlamentarUserControllerTest.class);
		suite.addTestSuite(ParlamentarUserControllerTest.class);
		suite.addTestSuite(CotaParlamentarUserDaoTest.class);
		suite.addTestSuite(ParlamentarUserDaoTest.class);
		suite.addTestSuite(ConnectionFailedExceptionTest.class);
		suite.addTestSuite(NullParlamentarExceptionTest.class);
		suite.addTestSuite(NullCotaParlamentarExceptionTest.class);
		suite.addTestSuite(NullParlamentarRankingMaioresExceptionTest.class);
		suite.addTestSuite(RequestFailedExceptionTest.class);
		suite.addTestSuite(TransmissionExceptionTest.class);
		suite.addTestSuite(JSONHelperTest.class);
		suite.addTestSuite(LocalDatabaseTest.class);
		suite.addTestSuite(ParlamentarTest.class);
		suite.addTestSuite(CotaParlamentarTest.class);
		suite.addTestSuite(HttpConnectionTest.class);
		suite.addTestSuite(MountURLTest.class);
		// $JUnit-END$
		return suite;
	}
}
