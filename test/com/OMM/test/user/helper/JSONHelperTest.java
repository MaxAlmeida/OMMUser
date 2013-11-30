package com.OMM.test.user.helper;

import java.util.List;

import junit.framework.Assert;

import android.test.AndroidTestCase;

import com.OMM.application.user.helper.JSONHelper;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;

public class JSONHelperTest extends AndroidTestCase{
	
	
	public void testListParlamentarFromJSON() {
	
		Parlamentar parlamentar = new Parlamentar();
		parlamentar.setNome("PAULO MALUF");
		parlamentar.setId(373);
		
		Parlamentar parlamentar2 = JSONHelper.listParlamentarFromJSON("[{\"id\":373,\"nome\":\"PAULO MALUF\",\"partido\":\"PP\",\"uf\":\"SP\"}]").get(0);
		
		assertEquals(parlamentar.getId(), parlamentar2.getId());
		assertTrue(parlamentar.getNome().equals(parlamentar2.getNome()));
}

	
	public void testListCotaParlamentarFromJSON() {

		CotaParlamentar cota = new CotaParlamentar();
		cota.setCod(144068);
		cota.setIdParlamentar(373);

		CotaParlamentar cota2 = JSONHelper
				.listCotaParlamentarFromJSON(
						"[{\"cod\":144068,\"idParlamentar\":373,\"mes\":7,\"ano\":2013,\"numeroSubCota\":3,\"descricao\":\"COMBUST\",\"valor\":150.0}]")
				.get(0);

		assertEquals(cota.getCod(), cota2.getCod());

	}
	
	public void testListParlamentarRankingMaioresFromJSON() {
		
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
	}

}
