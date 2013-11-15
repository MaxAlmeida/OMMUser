package com.OMM.application.user.controller;

import java.util.List;

import org.apache.http.client.ResponseHandler;

import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.helper.JSONHelper;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.requests.HttpConnection;
import com.OMM.application.user.requests.MontaURL;

public class ParlamentarUserController {

	private static ParlamentarUserController instance;
	Parlamentar parlamentar;

	private ParlamentarUserController() {

		parlamentar = new Parlamentar();
	}

	public Parlamentar popularParlamentar(String json)
			throws NullParlamentarException {
		try {
			Parlamentar parlamentar = JSONHelper.listaParlamentarFromJSON(json)
					.get(0);

			return parlamentar;
		} catch (NullPointerException e) {
			return null;
		}
	}

	public static ParlamentarUserController getInstance() {

		if (instance == null) {
			instance = new ParlamentarUserController();
		}

		return instance;

	}

	public Parlamentar fazerRequisicao(ResponseHandler<String> responseHandler,
			int idParlamentar) throws NullParlamentarException {

		String urlParlamentar = MontaURL.montaURLParlamentar(idParlamentar);
		String resposta = HttpConnection.requisicao(responseHandler, urlParlamentar);
		Parlamentar parlamentar = popularParlamentar(resposta);

		String urlCotas = MontaURL.montaURLCota(idParlamentar);
		String cotasResposta = HttpConnection.requisicaoCota(responseHandler,
				urlCotas);

		List<CotaParlamentar> cotas = JSONHelper
				.listaCotaParlamentarFromJSON(cotasResposta);

		parlamentar.setCotas(cotas);

		return parlamentar;
	}
}
