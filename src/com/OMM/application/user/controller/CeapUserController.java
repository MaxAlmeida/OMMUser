package com.OMM.application.user.controller;

import java.util.Iterator;
import java.util.List;

import org.apache.http.client.ResponseHandler;

import com.OMM.application.user.dao.CotaParlamentarUserDao;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.helper.JSONHelper;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.requests.HttpConnection;
import com.OMM.application.user.requests.MontaURL;


public class CeapUserController {
	
	private static CeapUserController instance;
	
	private CeapUserController() {

		
	}
	
	public static CeapUserController getInstance() {

		if (instance == null) {
			instance = new CeapUserController();
		}

		return instance;

	}
	
	private List<CotaParlamentar> popularCotas(String json)
			throws NullParlamentarException {
		try{
			List<CotaParlamentar> cotas = JSONHelper
					.listaCotaParlamentarFromJSON(json);
			return cotas; 
		}
		catch (NullPointerException npe){
		
			throw new NullParlamentarException();
		}						
	}
	
	public boolean persistCotaDB(ResponseHandler<String> response, Parlamentar parlamentar) throws NullParlamentarException {
		
		boolean result = true;
		String url = MontaURL.montaURLCota(parlamentar.getId());
		String json = HttpConnection.requisicaoCota(response, url);
		
		List<CotaParlamentar> cotas = popularCotas(json);
		CotaParlamentarUserDao cotaDao = CotaParlamentarUserDao.getInstance();
		
		Iterator<CotaParlamentar> iterator = cotas.iterator();
		while(iterator.hasNext()){
			boolean temp = cotaDao.insertSeguido(parlamentar, iterator.next());
			result = result & temp;
			
		}
		
		return result;
		
	}

}
