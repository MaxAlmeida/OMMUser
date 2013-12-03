package com.OMM.test.user.model;

import junit.framework.Assert;
import android.test.AndroidTestCase;

import com.OMM.application.user.model.CotaParlamentar;

public class CotaParlamentarTest extends AndroidTestCase {

	private CotaParlamentar cota;

	public void setUp() {
		this.cota = new CotaParlamentar();
	}

	public void testCod() {
		this.cota.setCod(373);
		Assert.assertEquals(373, cota.getCod());
	}

	public void testIdParlamentar() {
		this.cota.setIdParlamentar(373);
		Assert.assertEquals(373, cota.getIdParlamentar());
	}

	public void testMes() {
		this.cota.setMes(12);
		Assert.assertEquals(12, cota.getMes());
	}

	public void testAno() {
		this.cota.setAno(2012);
		Assert.assertEquals(2012, cota.getAno());
	}

	public void testNumeroSubCota() {
		this.cota.setNumeroSubCota(1);
		Assert.assertEquals(1, cota.getNumeroSubCota());
	}

	public void testDescricao() {
		this.cota.setDescricao("");
		Assert.assertEquals("", cota.getDescricao());
	}

	public void testValo() {
		this.cota.setValor(100.0);
		Assert.assertEquals(100.0, cota.getValor());
	}
}
