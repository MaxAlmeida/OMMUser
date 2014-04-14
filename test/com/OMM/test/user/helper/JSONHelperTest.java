package com.OMM.test.user.helper;

import android.test.AndroidTestCase;

import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.helper.JSONHelper;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;

public class JSONHelperTest extends AndroidTestCase {

	public void testJSONHelper() {

		JSONHelper jsonHelper = new JSONHelper();
		assertNotNull(jsonHelper);
	}

	public void testInstance() {
		JSONHelper helper = new JSONHelper();

		assertEquals(JSONHelper.class, helper.getClass());
	}

	public void testListParlamentarFromJSON() throws NullParlamentarException {

		Parlamentar parlamentar = new Parlamentar();
		parlamentar.setNome("PAULO MALUF");
		parlamentar.setId(373);

		Parlamentar parlamentar2 = JSONHelper
				.listParlamentarFromJSON(
						"[{\"id\":373,\"nome\":\"PAULO MALUF\",\"partido\":\"PP\",\"uf\":\"SP\"}]")
				.get(0);

		assertEquals(parlamentar.getId(), parlamentar2.getId());
		assertTrue(parlamentar.getNome().equals(parlamentar2.getNome()));
		assertNotNull(JSONHelper
				.listParlamentarFromJSON("[{\"id\":373,\"nome\":\"PAULO MALUF\",\"partido\":\"PP\",\"uf\":\"SP\"}]"));
	}

	public void testListParlamentarFromJSONNullCotaParlamentarException()
			throws NullParlamentarException {

		try {
			JSONHelper.listParlamentarFromJSON("[{}]").get(0);

		} catch (NullParlamentarException ncpe) {

		}
	}

	public void testListCotaParlamentarFromJSON()
			throws NullCotaParlamentarException {

		CotaParlamentar cota = new CotaParlamentar();
		cota.setCod(144068);
		cota.setIdParlamentar(373);

		CotaParlamentar cota2 = JSONHelper
				.listCotaParlamentarFromJSON(
						"[{\"cod\":144068,\"idParlamentar\":373,\"mes\":7,\"ano\":2013,\"numeroSubCota\":3,\"descricao\":\"COMBUST\",\"valor\":150.0}]")
				.get(0);
		assertEquals(cota.getCod(), cota2.getCod());

	}

	public void testListCotaParlamentarFromJSONNullCotaParlamentarException()
			throws NullCotaParlamentarException {

		try {
			JSONHelper.listCotaParlamentarFromJSON("[{}]").get(0);
		} catch (NullCotaParlamentarException ncpe) {

		}
	}
	
	public void testUpdateFromJSON() {
		int updateId = JSONHelper.updateFromJSON("[1]");
		assertEquals(1, updateId);
	}
		
}
