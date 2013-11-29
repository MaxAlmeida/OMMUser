package com.OMM.test.user.model;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import android.test.AndroidTestCase;

import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;

public class ParlamentarTest extends AndroidTestCase {
	
	Parlamentar parlamentarEmpty;
	Parlamentar parlamentarFull;
	
	public void setUp(){
				
		this.parlamentarEmpty = new Parlamentar();	
		this.parlamentarFull = new Parlamentar(2323 ,"Reguffe","PT","DF",1);	
		
	}
		
	public void testConstructorParlamenta(){

		
	}
	

	public void testSetSeguido(){
			
		this.parlamentarEmpty.setSeguido(1);
		Assert.assertEquals(1, parlamentarEmpty.getIsSeguido());
	}
	
	public void testSetId(){
		
		this.parlamentarEmpty.setId(2323);
		Assert.assertEquals(2323, parlamentarEmpty.getId());
	}
	
	public void testSetNome(){
		
		this.parlamentarEmpty.setNome("Reguffe");
		Assert.assertEquals("Reguffe", parlamentarEmpty.getNome());
		
	}
	
	public void testSetPartido(){
		
		this.parlamentarEmpty.setPartido("PT");
		Assert.assertEquals("PT", parlamentarEmpty.getPartido());
		
	}
	
	public void testSetUf(){
		
		this.parlamentarEmpty.setUf("DF");
		Assert.assertEquals("DF", parlamentarEmpty.getUf());
		
	}
	
	public void testSetValor(){
		
		this.parlamentarEmpty.setValor(26.00);
		Assert.assertEquals(26.00, parlamentarEmpty.getValor());		
	}
	
	public void testSetCotas(){
		
		CotaParlamentar cota = new CotaParlamentar();
		List<CotaParlamentar> cotas = new ArrayList<CotaParlamentar>();
		cotas.add(cota);
		this.parlamentarEmpty.setCotas(cotas);
		Assert.assertEquals(cotas, parlamentarEmpty.getCotas());		
		
	}
	
	public void testSetFoto(){
		
		this.parlamentarEmpty.setFoto(null);
		Assert.assertNull(this.parlamentarEmpty.getFoto());
	}
}