package com.OMM.test.user.helper;

import java.util.List;

import junit.framework.Assert;

import android.test.AndroidTestCase;

import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.TransmissionException;
import com.OMM.application.user.helper.JSONHelper;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;

public class JSONHelperTest extends AndroidTestCase{
	
	
	public void testJSONHelper( ){
		
		JSONHelper jsonHelper = new JSONHelper();
		assertNotNull(jsonHelper);	
	}
	
	public void testInstance() {
		JSONHelper helper = new JSONHelper();
		
		assertEquals(JSONHelper.class, helper.getClass());	
	}
	
	public void testListParlamentarFromJSON() throws TransmissionException, NullParlamentarException {
	
		Parlamentar parlamentar = new Parlamentar();
		parlamentar.setNome("PAULO MALUF");
		parlamentar.setId(373);
		
		Parlamentar parlamentar2 = JSONHelper.listParlamentarFromJSON("[{\"id\":373,\"nome\":\"PAULO MALUF\",\"partido\":\"PP\",\"uf\":\"SP\"}]").get(0);
		
		assertEquals(parlamentar.getId(), parlamentar2.getId());
		assertTrue(parlamentar.getNome().equals(parlamentar2.getNome()));
		assertNotNull(JSONHelper.listParlamentarFromJSON("[{\"id\":373,\"nome\":\"PAULO MALUF\",\"partido\":\"PP\",\"uf\":\"SP\"}]"));
	}
	
	public void testListParlamentarFromJSONNullCotaParlamentarException() throws NullParlamentarException, 
	TransmissionException {
	
		try {
			JSONHelper.listParlamentarFromJSON("[{}]").get(0);
			
		} catch(NullParlamentarException ncpe) {
			
		}
	}
	
	public void testListParlamentarFromJSONTransmissionException() 
			throws NullParlamentarException, TransmissionException {
		
		try {
			JSONHelper.listParlamentarFromJSON("Teste");
		} catch (TransmissionException te) {
			
		}		
	}
	
	public void testListCotaParlamentarFromJSON() throws NullCotaParlamentarException, TransmissionException {

		CotaParlamentar cota = new CotaParlamentar();
		cota.setCod(144068);
		cota.setIdParlamentar(373);

		CotaParlamentar cota2 = JSONHelper
				.listCotaParlamentarFromJSON(
						"[{\"cod\":144068,\"idParlamentar\":373,\"mes\":7,\"ano\":2013,\"numeroSubCota\":3,\"descricao\":\"COMBUST\",\"valor\":150.0}]")
				.get(0);
		assertEquals(cota.getCod(), cota2.getCod());

	}
	
	public void testListCotaParlamentarFromJSONNullCotaParlamentarException() throws NullCotaParlamentarException, 
		TransmissionException {
		
		try {
			JSONHelper.listCotaParlamentarFromJSON("[{}]").get(0);
		} catch(NullCotaParlamentarException ncpe) {
			
		}
	}
	
	public void testListCotaParlamentarFromJSONTransmissionException() 
			throws NullCotaParlamentarException, TransmissionException {
		
		try {
			JSONHelper.listCotaParlamentarFromJSON("Teste");
		} catch (TransmissionException te) {
			
		}		
	}
	
	public void testListParlamentarRankingMaioresFromJSON() 
			throws NullParlamentarException, TransmissionException {
		
		try {
			Parlamentar parlamentarFirst = new Parlamentar();
			parlamentarFirst.setNome("MOREIRA MENDES");
			parlamentarFirst.setValor(369922.75);
			
			Parlamentar parlamentarSecond = new Parlamentar();
			parlamentarSecond.setNome("URZENI ROCHA");
			parlamentarSecond.setValor(368762.90);
			
			List<Parlamentar> ranking = JSONHelper.listParlamentarRankingMaioresFromJSON("[{\"id\":49,\"valor\":369922.75,\"nome\":\"MOREIRA MENDES\",\"partido\":\"PSD\",\"uf\":\"RO\"},{\"id\":616,\"valor\":368762.90,\"nome\":\"URZENI ROCHA\",\"partido\":\"PSD\",\"uf\":\"RR\"}]");
			
			Parlamentar parlamentarFirstJson = ranking.get(0);
			Parlamentar parlamentarSecondJson = ranking.get(1);
			
			Assert.assertEquals(parlamentarFirst.getNome(), parlamentarFirstJson.getNome());
			Assert.assertEquals(parlamentarSecond.getNome(), parlamentarSecondJson.getNome());
			
		} catch(NullParlamentarException e) {
					 
		} catch(TransmissionException e) {
			 
		}
	}
	
	public void testListParlamentarRankingMaioresFromJSONNullParlamentarException()
	throws NullCotaParlamentarException, TransmissionException {
		
		try {
			Parlamentar parlamentarFirst = new Parlamentar();
			parlamentarFirst.setNome("MOREIRA MENDES");
			parlamentarFirst.setValor(369922.75);
			
			Parlamentar parlamentarSecond = new Parlamentar();
			parlamentarSecond.setNome("URZENI ROCHA");
			parlamentarSecond.setValor(368762.90);
			
			List<Parlamentar> ranking = JSONHelper.listParlamentarRankingMaioresFromJSON(null);
			
			Parlamentar parlamentarFirstJson = ranking.get(0);
			Parlamentar parlamentarSecondJson = ranking.get(1);
			
			Assert.assertEquals(parlamentarFirst.getNome(), parlamentarFirstJson.getNome());
			Assert.assertEquals(parlamentarSecond.getNome(), parlamentarSecondJson.getNome());
			fail("Exception ");
			
		} catch(NullParlamentarException e) {
		 
		} catch(TransmissionException e) {
			 
		}	
	}
	
	public void testListParlamentarRankingMaioresFromJSONTransmissionException()
			throws NullCotaParlamentarException, TransmissionException {
		
				try {
					Parlamentar parlamentarFirst = new Parlamentar();
					parlamentarFirst.setNome("MOREIRA MENDES");
					parlamentarFirst.setValor(369922.75);
					
					Parlamentar parlamentarSecond = new Parlamentar();
					parlamentarSecond.setNome("URZENI ROCHA");
					parlamentarSecond.setValor(368762.90);
					
					List<Parlamentar> ranking = JSONHelper.listParlamentarRankingMaioresFromJSON("[{\"id\";49,\"valor\";369922.75,\"nome\";\"MOREIRA MENDES\",\"partido\":\"PSD\",\"uf\":\"RO\"},{\"id\":616,\"valor\":368762.90,\"nome\":\"URZENI ROCHA\",\"partido\":\"PSD\",\"uf\":\"RR\"}]");
					
					Parlamentar parlamentarFirstJson = ranking.get(0);
					Parlamentar parlamentarSecondJson = ranking.get(1);
					
					Assert.assertEquals(parlamentarFirst.getNome(), parlamentarFirstJson.getNome());
					Assert.assertEquals(parlamentarSecond.getNome(), parlamentarSecondJson.getNome());
					fail("Exception ");
					
				} catch(NullParlamentarException e) {
					 
				} catch(TransmissionException e) {
					 
				}
			}
}
