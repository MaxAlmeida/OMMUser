package com.OMM.test.user.dao;

import android.test.AndroidTestCase;
import android.test.mock.MockApplication;
import android.test.mock.MockContext;

import com.OMM.application.user.dao.CotaParlamentarUserDao;

public class CeapUserDaoTest extends AndroidTestCase{
	
	MockApplication mc = new MockApplication();
	public CotaParlamentarUserDao dao = CotaParlamentarUserDao.getInstance(mc); 
	MockContext mock;
	
	public void setUp(){
		
		this.mock = new MockContext();		
		
	}
	
	public void testGetInstance(){
		
		
		CotaParlamentarUserDao parlamentarDao1 = CotaParlamentarUserDao.getInstance(mock);
		CotaParlamentarUserDao parlamentarDao2 = CotaParlamentarUserDao.getInstance(mock);
		
		assertSame(parlamentarDao1,parlamentarDao2);
	}
	
	
}

