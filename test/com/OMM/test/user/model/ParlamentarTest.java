package com.OMM.test.user.model;

import static org.junit.Assert.*;
import org.junit.Test;
import com.OMM.application.user.model.Parlamentar;

public class ParlamentarTest {

	Parlamentar parlamentar = new Parlamentar();
	
	@Test
	public void testConstructor(){
		Parlamentar parlamentar2 = new Parlamentar(10, "Someone", "Party", "DF", 0, null);
		assertEquals(parlamentar2.getId(), 10);
	}
	
	@Test
	public void testIdParlamentar() {
		parlamentar.setId(1);
		assertTrue(parlamentar.getId() == 1);
	}
	
	@Test
	public void testNome(){
		parlamentar.setNome("Romario");
		assertTrue(parlamentar.getNome() == "Romario");
	}
	
	@Test
	public void testPartido(){
		parlamentar.setPartido("PSDB");
		assertTrue(parlamentar.getPartido() == "PSDB");
	}
	
	@Test
	public void testUf(){
		parlamentar.setUf("RJ");
		assertTrue(parlamentar.getUf() == "RJ");
	}
	
	@Test
	public void testFoto(){
		parlamentar.setFoto(null);
		assertNull(parlamentar.getFoto());
	}
	
	@Test
	public void testCotas(){
		
		parlamentar.setCotas(null);
		assertEquals(parlamentar.getCotas(),null);
	}
	
	@Test
	public void testSeguido(){
		parlamentar.setSeguido(1);
		assertEquals(parlamentar.isSeguido(), 1);
	}
			
	
	
}