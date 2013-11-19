package com.OMM.test.user.helper;

import static org.junit.Assert.*;

import org.junit.Test;

import android.util.Log;

import com.OMM.application.user.helper.JSONHelper;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;

public class JSONHelperTest {

	@Test
	public void testListParlamentarFromJSON() {
		// Parlamentar parlamentar = new Parlamentar(337, "PAULO MALUF", "PP",
		// "SP", 0, ));
		Parlamentar parlamentar = new Parlamentar();
		parlamentar.setNome("PAULO MALUF");
		parlamentar.setId(373);
		parlamentar.setPartido("PP");
		parlamentar.setUf("SP");
		// ArrayList<Parlamentar> list = new ArrayList<Parlamentar>();
		// list.add(parlamentar);

		assertTrue(parlamentar
				.equals(JSONHelper
						.listParlamentarFromJSON(
								"[{\"id\":373,\"nome\":\"PAULO MALUF\",\"partido\":\"PP\",\"uf\":\"SP\"}]")
						.get(0)));

		// assertEquals(parlamentar.getNome()
		// ,JSONHelper.listParlamentarFromJSON("[{\"id\":373,\"nome\":\"PAULO MALUF\",\"partido\":\"PP\",\"uf\":\"SP\"}]").get(0).getNome());
	}

	@Test
	public void testListCotaParlamentarFromJSON() {

		CotaParlamentar cota = new CotaParlamentar();
		cota.setId(144068);
		cota.setIdParlamentar(373);

		cota = JSONHelper
				.listCotaParlamentarFromJSON(
						"[{\"cod\":144068,\"idParlamentar\":373,\"mes\":7,\"ano\":2013,\"numeroSubCota\":3,\"descricao\":\"COMBUST\",\"valor\":150.0}]")
				.get(0);

		// assertTrue(cota.equals(JSONHelper.listCotaParlamentarFromJSON("[{\"cod\":144068,\"idParlamentar\":373,\"mes\":7,\"ano\":2013,\"numeroSubCota\":3,\"descricao\":\"COMBUST\",\"valor\":150.0}]").get(0)));

	}

}
