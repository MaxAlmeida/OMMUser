package com.OMM.test.user.dao;

import android.test.mock.MockApplication;

import com.OMM.application.user.dao.CotaParlamentarUserDao;

public class CeapUserDaoTest {
	
	MockApplication mc = new MockApplication();
	public CotaParlamentarUserDao dao = CotaParlamentarUserDao.getInstance(mc); 
}
