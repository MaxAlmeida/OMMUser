package com.OMM.application.user.controller;

import java.util.List;

import org.apache.http.client.ResponseHandler;

import com.OMM.application.user.helper.JSONHelper;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.requests.HttpConnection;

public class ParlamentarUserController {

	private static ParlamentarUserController instance;
	Parlamentar parlamentar;

	private ParlamentarUserController() {

		parlamentar = new Parlamentar();
	}

	public Parlamentar popularParlamentar(String json) {

		Parlamentar parlamentar = JSONHelper.listaParlamentarFromJSON(json)
				.get(0);

		return parlamentar;
	}

	public static ParlamentarUserController getInstance() {

		if (instance == null) {
			instance = new ParlamentarUserController();
		}

		return instance;

	}

	public Parlamentar fazerRequisicao(ResponseHandler<String> parametro, int idParlamentar) {

		String resposta = HttpConnection.requisicao(parametro, idParlamentar);
		
		Parlamentar parlamentar = popularParlamentar(resposta);
		
		String cotasResposta = HttpConnection.requisicaoCota(parametro, idParlamentar);
		
		List<CotaParlamentar> cotas = JSONHelper.listaCotaParlamentarFromJSON(cotasResposta);
		
		parlamentar.setCotas(cotas);
		return parlamentar;

	}
}
