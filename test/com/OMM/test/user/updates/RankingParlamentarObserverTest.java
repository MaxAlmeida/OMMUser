package com.OMM.test.user.updates;

import java.util.ArrayList;

import junit.framework.Assert;
import android.content.Context;
import android.test.AndroidTestCase;

import com.OMM.application.user.dao.*;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.updates.RankingParlamentarObserver;
import com.OMM.application.user.updates.ServerUpdatesSubject;

public class RankingParlamentarObserverTest extends AndroidTestCase {

	public ServerUpdatesSubject subject;
	public RankingParlamentarObserver observer;
	public Context context;
	public Parlamentar parlamentarTest;
	public ParlamentarUserDao dao;
	
	protected void setUp() throws Exception {
		super.setUp();
		context=getContext();
		subject=new ServerUpdatesSubject(context);
		observer=new RankingParlamentarObserver(subject, context);
		dao=ParlamentarUserDao.getInstance(context);
		
		parlamentarTest=new Parlamentar(999,"Teste","Teste","DF",0);
		dao.insertParlamentar(parlamentarTest);
		
		
	}

	

	public void testUpdate() {
		ArrayList<Parlamentar> lista=new ArrayList<Parlamentar>();
		parlamentarTest.setValor(10);
		lista.add(parlamentarTest);
		
		subject.setListParlamentares(lista);
		subject.notifyObservers();
		
		Assert.assertEquals(10.0, dao.getById(999).getValor());
		
	}
	@Override
	protected void tearDown() throws Exception {
		 
		super.tearDown();
		dao.deleteParlamentar(parlamentarTest);
	}

}
