package com.OMM.test.user.model;


import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.OMM.application.user.model.Parlamentar;

public class ParlamentarTest {

	Parlamentar parlamentar;
	
	@Test
	public void testIdParlamentar() throws Exception {
		parlamentar = new Parlamentar();
		parlamentar.setId(1);
		assertTrue(parlamentar.getId() == 1);
	}
	
	@Test
	public void testNome() throws Exception {
		parlamentar = new Parlamentar();
		parlamentar.setNome("Romario");
		assertTrue(parlamentar.getNome() == "Romario");
	}
	
	@Test
	public void testPartido() throws Exception {
		parlamentar = new Parlamentar();
		parlamentar.setPartido("PSDB");
		assertTrue(parlamentar.getPartido() == "PSDB");
	}
	
	@Test
	public void testUf() throws Exception {
		parlamentar = new Parlamentar();
		parlamentar.setUf("RJ");
		assertTrue(parlamentar.getUf() == "RJ");
	}
	
	@Test
	public void testFoto() throws Exception {
		parlamentar = new Parlamentar();
		parlamentar.setFoto(null);
		assertNull(parlamentar.getFoto());
	}
	
	@Test
	public void testValor(){
		parlamentar = new Parlamentar();
		parlamentar.setValor(5690.45);
		assertTrue(parlamentar.getValor() == 5690.45 );
	}
	
}