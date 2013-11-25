package com.OMM.test.user.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.OMM.application.user.model.CotaParlamentar;

public class CotaParlamentarTest {

	public CotaParlamentar cotaParlamentar = new CotaParlamentar();

	@Test
	public void testConstructor() {
		cotaParlamentar = new CotaParlamentar();
		cotaParlamentar.setCod(15);
				
		assertEquals(cotaParlamentar.getCod(), 15);
	}

	@Test
	public void testId(){
		cotaParlamentar.setCod(1);
		assertTrue(cotaParlamentar.getCod() == 1);
	}

	@Test
	public void testAno(){
		cotaParlamentar.setAno(2013);
		assertTrue(cotaParlamentar.getAno() == 2013);
	}

	@Test
	public void testSubCota(){
		cotaParlamentar.setNumeroSubCota(1);
		assertTrue(cotaParlamentar.getNumeroSubCota() == 1);
	}

	@Test
	public void testDescricao() {
		cotaParlamentar.setDescricao("Descricao da cota parlamentar");
		assertTrue(cotaParlamentar.getDescricao() == "Descricao da cota parlamentar");
	}

	@Test
	public void testIdParlamentar() {
		cotaParlamentar.setIdParlamentar(0);
		assertEquals(cotaParlamentar.getIdParlamentar(), 0);
	}
}