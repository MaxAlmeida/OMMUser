package com.OMM.test.user.model;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.OMM.application.user.model.CotaParlamentar;

public class CotaParlamentarTest{
	
	static CotaParlamentar cotaParlamentar;
	
	@BeforeClass
	public static void setUp() throws Exception {
		cotaParlamentar = new CotaParlamentar();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testId() throws Exception {
		cotaParlamentar.setId(1);
		assertTrue(cotaParlamentar.getId() == 1);
	}

	@Test
	public void testAno() throws Exception {
		cotaParlamentar.setAno(2013);
		assertTrue(cotaParlamentar.getAno() == 2013);
	}

	@Test
	public void testSubCota() throws Exception {
		cotaParlamentar.setNumeroSubCota(1);
		assertTrue(cotaParlamentar.getNumeroSubCota() == 1);
	}

	@Test
	public void testValorJaneiro() throws Exception {
		cotaParlamentar.setValorJaneiro(10001);
		assertTrue(cotaParlamentar.getValorJaneiro() == 10001);
	}

	@Test
	public void testValorFevereiro() throws Exception {
		cotaParlamentar.setValorFevereiro(10002);
		assertTrue(cotaParlamentar.getValorFevereiro() == 10002);
	}

	@Test
	public void testValorMarco() throws Exception {
		cotaParlamentar.setValorMarco(10003);
		assertTrue(cotaParlamentar.getValorMarco() == 10003);
	}

	@Test
	public void testValorAbril() throws Exception {
		cotaParlamentar.setValorAbril(10004);
		assertTrue(cotaParlamentar.getValorAbril() == 10004);
	}

	@Test
	public void testValorMaio() throws Exception {
		cotaParlamentar.setValorMaio(10005);
		assertTrue(cotaParlamentar.getValorMaio() == 10005);
	}

	@Test
	public void testValorJunho() throws Exception {
		cotaParlamentar.setValorJunho(10006);
		assertTrue(cotaParlamentar.getValorJunho() == 10006);
	}

	@Test
	public void testValorJulho() throws Exception {
		cotaParlamentar.setValorJulho(10007);
		assertTrue(cotaParlamentar.getValorJulho() == 10007);
	}

	@Test
	public void testValorAgosto() throws Exception {
		cotaParlamentar.setValorAgosto(10008);
		assertTrue(cotaParlamentar.getValorAgosto() == 10008);
	}

	@Test
	public void testValorSetembro() throws Exception {
		cotaParlamentar.setValorSetembro(10009);
		assertTrue(cotaParlamentar.getValorSetembro() == 10009);
	}

	@Test
	public void testValorOutubro() throws Exception {
		cotaParlamentar.setValorOutubro(10010);
		assertTrue(cotaParlamentar.getValorOutubro() == 10010);
	}

	@Test
	public void testValorNovembro() throws Exception {
		cotaParlamentar.setValorNovembro(10011);
		assertTrue(cotaParlamentar.getValorNovembro() == 10011);
	}

	@Test
	public void testValorDezembro() throws Exception {
		cotaParlamentar.setValorDezembro(10012);
		assertTrue(cotaParlamentar.getValorDezembro() == 10012);
	}

	@Test
	public void testDescricao() throws Exception {
		cotaParlamentar.setDescricao("Descricao da cota parlamentar");
		assertTrue(cotaParlamentar.getDescricao() == "Descricao da cota parlamentar");
	}

	@Test
	public void testEspecificacao() throws Exception {
		cotaParlamentar.setEspecificacao("Especificacao da cota parlamentar");
		assertTrue(cotaParlamentar.getEspecificacao() == "Especificacao da cota parlamentar");
	}

}
