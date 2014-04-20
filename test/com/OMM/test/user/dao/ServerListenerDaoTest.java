package com.OMM.test.user.dao;

import android.content.Context;
import android.test.AndroidTestCase;

import com.OMM.application.user.dao.ServerListenerDao;



public class ServerListenerDaoTest extends AndroidTestCase {

	ServerListenerDao listenerDao;
	private Context context;
	
	protected void setUp() throws Exception {
		this.context = getContext();
		listenerDao = ServerListenerDao.getInstance(context);
		listenerDao.insertUrlServer("testa url");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetInstance(){
		ServerListenerDao dao1 =  ServerListenerDao.getInstance(context);
		ServerListenerDao dao2 = ServerListenerDao.getInstance(context);
		assertSame(dao1, dao2);
		
	}
	
	public void testInsertUrlServer(){
		assertTrue(listenerDao.insertUrlServer("testa url"));
	}
	
	public void testGetUrlServer(){
		assertEquals("testa url", listenerDao.getUrlServer());
	}
}
