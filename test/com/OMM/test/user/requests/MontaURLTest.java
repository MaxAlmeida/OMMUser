package com.OMM.test.user.requests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.requests.MontaURL;

public class MontaURLTest {
	private static final String IP = "192.168.0.102";
	
	Parlamentar parlamentar = new Parlamentar();
	
	@Before
	public void setUp() throws Exception {	
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMontaURLParlamentar( ) {	
		parlamentar.setId(1);
		
      String url = MontaURL.mountURLParlamentar(parlamentar.getId());	
      assertEquals("http://" + IP + ":8080/OlhaMinhaMesada/parlamentar?id=" + parlamentar.getId(), url); 
	}
	
	@Test
	public void testMontaURLCota( ) {
		parlamentar.setId(1);
		String url = MontaURL.mountURLCota(parlamentar.getId());
		
		assertEquals("http://" + IP + ":8080/OlhaMinhaMesada/cota?id=" + parlamentar.getId(), url);		
	}
	
	@Test
	public void testMontaURLAll( ) {	
		String url = MontaURL.mountUrlAll();
		
		assertEquals("http://" + IP + ":8080/OlhaMinhaMesada/parlamentares", url);	
	}

	@Test
	public void testMontaURLMajorRanking( ) {	
		String url = MontaURL.mountUrlMajorRanking();
		
		assertEquals("http://" + IP + ":8080/OlhaMinhaMesada/rankingMaiores", url);
	}
}