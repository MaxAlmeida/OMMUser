package com.OMM.test.user.requests;

import junit.framework.Assert;

import com.OMM.application.user.requests.MountURL;

import android.test.AndroidTestCase;

public class MountURLTest extends AndroidTestCase{
	
	private String IP;
	
	public void setUp(){
		
		this.IP = MountURL.IP;
		
	}
	
	public void testMountUTL(){
		
		MountURL mountURL = new MountURL();
		assertNotNull(mountURL);
		
	}
	
	public void testMountURLCota(){
		
		
		String urlParlamentar = "http://"+ IP + ":8080/OlhaMinhaMesada/cota?id=373";
		
		Assert.assertEquals(urlParlamentar, MountURL.mountURLCota(373));
	}
	
	public void testMountURLParlamentar(){
		
		
		String urlParlamentar = "http://"+ IP + ":8080/OlhaMinhaMesada/parlamentar?id=373";
		
		Assert.assertEquals(urlParlamentar, MountURL.mountURLParlamentar(373));
	}
	
	public void testMountUrlAll(){
		
		String urlAll = "http://"+ IP + ":8080/OlhaMinhaMesada/parlamentares";
		
		Assert.assertEquals(urlAll, MountURL.mountUrlAll());
		
	}
	
	public void testMountUrlMajorRanking(){
		
		String urlMajorRanking = "http://" + IP + ":8080/OlhaMinhaMesada/rankingMaiores";
		
		Assert.assertEquals(urlMajorRanking, MountURL.mountUrlMajorRanking());
		
	}
	
}