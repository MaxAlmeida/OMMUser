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

	public static ParlamentarUserController getInstance() {

		if (instance == null) {
			instance = new ParlamentarUserController();
		}

		return instance;

	}

	public Parlamentar popularParlamentar(String jsonParlamentar)
			throws NullParlamentarException {
		try {
			Parlamentar parlamentar = JSONHelper.listaParlamentarFromJSON(
					jsonParlamentar).get(0);

			return parlamentar;
		} catch (NullPointerException e) {
			return null;
		}
	}

	public List<CotaParlamentar> popularCotaParlamentar(String jsonCotaParlamentar){
	
		List<CotaParlamentar> listaCotas = null;
		
		return listaCotas;
		
	}
	
	public Parlamentar fazerRequisicao(ResponseHandler<String> responseHandler,
			int idParlamentar) throws NullParlamentarException {

		String urlParlamentar = MontaURL.montaURLParlamentar(idParlamentar);
		String resposta = HttpConnection.requisicaoParlamentar(responseHandler,
				urlParlamentar);
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
