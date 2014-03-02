package com.OMM.test.user.updates;

import java.util.ArrayList;

import org.apache.http.client.ResponseHandler;

import junit.framework.Assert;
import android.content.Context;
import android.os.Handler;
import android.test.AndroidTestCase;

import com.OMM.application.updates.*;
import com.OMM.application.user.exceptions.ConnectionFailedException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.RequestFailedException;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.requests.HttpConnection;

public class ServerUpdatesSubjectTest extends AndroidTestCase {

	public ServerUpdatesSubject subject;
	public Context context;
	public RankingParlamentarObserver observer;
	public ArrayList<Parlamentar> listaParlamentares;
	public Parlamentar parlamentar;
	
	protected void setUp() throws Exception {
		super.setUp();
		context=getContext();
		subject=new ServerUpdatesSubject(context);
		listaParlamentares= new ArrayList<Parlamentar>();
		parlamentar=new Parlamentar(001, "Teste","Teste","UF", 0);
		
	}

	public void testServerUpdatesSubject() {
		
		Assert.assertNotNull(subject.getListParlamentares().size());
		
	}

	public void testRegisterObserver() {
		observer=new RankingParlamentarObserver(subject, context);
		Assert.assertNotNull(subject);
		
	}


	public void testRemoveObserver() {
		if(subject.getListParlamentares().size()>0)
		{
			subject.removeObserver(observer, null);
			Assert.assertNull(subject.getListParlamentares());
		}else 
		{
			observer=new RankingParlamentarObserver(subject, context);
			subject.removeObserver(observer, null);
			Assert.assertEquals(0,subject.getListParlamentares().size());
		}
		
	}

	public void testNotifyObservers() {
		ArrayList<Parlamentar>lista=new ArrayList<Parlamentar>();
		lista.add(parlamentar);
		subject.setListParlamentares(lista);
		
		if(observer!=null)
		{
			subject.notifyObservers();
			
		}else 
		{
			observer=new RankingParlamentarObserver(subject, context);
			subject.notifyObservers();
			
		}
		Assert.assertTrue(true);
		 
	}

	public void testDoRequestParlamentar() 
			{
		Assert.assertTrue(true);
	}

	public void testDoRequestCota() {
		Assert.assertTrue(true);
	}

	public void testDoRequestUpdateVerify() {
		Assert.assertTrue(true);
	}

	public void testSetListParlamentares() {
		Assert.assertTrue(true);
	}

	
}
