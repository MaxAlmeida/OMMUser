package com.OMM.application.user.controller;

import java.util.Iterator;
import java.util.List;

import android.content.Context;

import com.OMM.application.user.dao.CotaParlamentarUserDao;
import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.TransmissionException;
import com.OMM.application.user.helper.JSONHelper;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;
import com.google.gson.JsonSyntaxException;

public class CotaParlamentarUserController {

	private static CotaParlamentarUserController instance;

	private CotaParlamentarUserDao cotaParlamentarDao;
	private Context context;

	private CotaParlamentarUserController(Context context) {
		this.context = context;
		this.cotaParlamentarDao = CotaParlamentarUserDao.getInstance(context);

	}

	public static CotaParlamentarUserController getInstance(Context context) {

		if (instance == null) {

			instance = new CotaParlamentarUserController(context);
		}

		return instance;
	}

	public List<CotaParlamentar> convertJsonToCotaParlamentar(String jsonCota)
			throws TransmissionException, NullCotaParlamentarException {
		List<CotaParlamentar> cotas = null;
		try {

			cotas = JSONHelper.listCotaParlamentarFromJSON(jsonCota);

		} catch (NullPointerException npe) {

			throw new NullCotaParlamentarException();
		} catch (JsonSyntaxException jse) {

			throw new TransmissionException();
		}
		if (cotas == null) {
			throw new NullCotaParlamentarException();
		}
		return cotas;
	}

	public boolean persistCotaDB(Parlamentar parlamentar)
			throws NullParlamentarException {
		if(parlamentar != null) {
			
			boolean result = true;
	
			List<CotaParlamentar> cotas = parlamentar.getCotas();
			Iterator<CotaParlamentar> iterator = cotas.iterator();
	
			while (iterator.hasNext()) {
	
				boolean temporary = cotaParlamentarDao.insertFollowed(iterator.next());
	
				result = result & temporary;
			}
	
			return result;
		} else {
			throw new NullParlamentarException();
		}
	}

	public boolean deleteCota(Parlamentar parlamentar) {
		return cotaParlamentarDao.deleteCotasFromParlamentar(parlamentar.getId());
	}

	public List<CotaParlamentar> getCotasByIdParlamentar(int idParlamentar){
		 return cotaParlamentarDao.getCotasByIdParlamentar(idParlamentar);
	}
}
