package com.OMM.test.user.helper;

import android.test.AndroidTestCase;

import com.OMM.application.user.helper.JSONHelper;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;

public class JSONHelperTest extends AndroidTestCase{
	
	
	public void testListParlamentarFromJSON() {
	
		Parlamentar parlamentar = new Parlamentar();
		parlamentar.setNome("PAULO MALUF");
		parlamentar.setId(373);
		
		Parlamentar p2 = JSONHelper.listParlamentarFromJSON("[{\"id\":373,\"nome\":\"PAULO MALUF\",\"partido\":\"PP\",\"uf\":\"SP\"}]").get(0);
		
		assertEquals(parlamentar.getId(), p2.getId());
		assertTrue(parlamentar.getNome().equals(p2.getNome()));
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

}
