package com.OMM.application.user.controller;

import java.util.List;

import org.apache.http.client.ResponseHandler;

import android.content.Context;

import com.OMM.application.user.dao.ParlamentarUserDao;
import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.helper.JSONHelper;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.requests.HttpConnection;
import com.OMM.application.user.requests.MontaURL;

public class ParlamentarUserController {

	private static ParlamentarUserController instance;
	Parlamentar parlamentar;
	private ParlamentarUserDao parlamentarDao;
	
	private ParlamentarUserController(Context context) {

		parlamentarDao =  ParlamentarUserDao.getInstance(context);
	}

	public static ParlamentarUserController getInstance(Context context) {

		if (instance == null) {
			instance = new ParlamentarUserController(context);
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

	public List<CotaParlamentar> popularCotaParlamentar(
			String jsonCotaParlamentar) throws NullCotaParlamentarException {

		try{
		List<CotaParlamentar> listaCotas = JSONHelper
				.listaCotaParlamentarFromJSON(jsonCotaParlamentar);
        
		
		return listaCotas;
		} catch (NullPointerException e){
			return null;
		}
	}  

	public Parlamentar fazerRequisicao(ResponseHandler<String> responseHandler,
			int idParlamentar) throws NullParlamentarException, NullCotaParlamentarException {

		String urlParlamentar = MontaURL.montaURLParlamentar(idParlamentar);
		String resposta = HttpConnection.requisicaoParlamentar(responseHandler,
				urlParlamentar);
		Parlamentar parlamentar = popularParlamentar(resposta);

		String urlCotas = MontaURL.montaURLCota(idParlamentar);
		String cotasResposta = HttpConnection.requisicaoCota(responseHandler,
				urlCotas);

		List<CotaParlamentar> cotas = popularCotaParlamentar(cotasResposta);

		parlamentar.setCotas(cotas);

		return parlamentar;
	}
	
     public List<Parlamentar> getSelected(String nomeParlamentar) {
		
		return parlamentarDao.getSelected(nomeParlamentar);
	}
	
	public List<Parlamentar> getAll(){
		
		return parlamentarDao.getAll();
	}

	public boolean followedParlamentar(ResponseHandler<String> response, Parlamentar parlamentar) throws NullParlamentarException{
		
		boolean result = true;
		
		CeapUserController controllerCeap = CeapUserController.getInstance();
		result = controllerCeap.persistCotaDB(response, parlamentar);
		
		return result;
	}
	
	public List<Parlamentar> getAllSelected() {
		
		return parlamentarDao.getAllSelected();
	}
}
