package com.OMM.test.user.requests;

import android.test.AndroidTestCase;

import com.OMM.application.user.requests.MontaURL;

public class MontaURLTest  extends AndroidTestCase {
	
	
	public void testMountURLCota() {
		
	 String url = MontaURL.mountURLCota(0);
		
		assertEquals("http://" + MontaURL.IP + ":8080/OlhaMinhaMesada/cota?id="+0,url);
		
		
		
	}
	
	public void testMountURLParlamentar(){
		
		 String url = MontaURL.mountURLParlamentar(0);
		
		 assertEquals("http://" + MontaURL.IP + ":8080/OlhaMinhaMesada/parlamentar?id="+0,url);
		
	}
	
	public void testMountUrlAll(){
		
		String url = MontaURL.mountUrlAll();
		
		assertEquals("http://" + MontaURL.IP + ":8080/OlhaMinhaMesada/parlamentares",url);
		
	}
	
	public void testMountUrlMajorRanking(){
		String url = MontaURL.mountUrlMajorRanking();
		
		assertEquals("http://" + MontaURL.IP + ":8080/OlhaMinhaMesada/rankingMaiores",url);
		
	}
}