package com.OMM.test.user.requests;

import junit.framework.Assert;

import com.OMM.application.user.requests.MontaURL;

import android.test.AndroidTestCase;

public class MontaURLTest extends AndroidTestCase{
	
	private String IP;
	
	public void setUp(){
		
		this.IP = MontaURL.IP;
		
	}
	
	public void testMountURLCota(){
		
		
		String urlParlamentar = "http://"+ IP + ":8080/OlhaMinhaMesada/cota?id=373";
		
		Assert.assertEquals(urlParlamentar, MontaURL.mountURLCota(373));
	}
	
	public void testMountURLParlamentar(){
		
		
		String urlParlamentar = "http://"+ IP + ":8080/OlhaMinhaMesada/parlamentar?id=373";
		
		Assert.assertEquals(urlParlamentar, MontaURL.mountURLParlamentar(373));
	}
	
}