package com.OMM.application.user.controller;

import java.util.List;

import android.content.Context;

import com.OMM.application.user.dao.CotaParlamentarUserDao;
import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.model.CotaParlamentar;

public class CotaParlamentarUserController {

	private static CotaParlamentarUserController instance;

	private CotaParlamentarUserDao cotaParlamentarDao;

	private CotaParlamentarUserController(Context context) {
		this.cotaParlamentarDao = CotaParlamentarUserDao.getInstance(context);
	}

	public static CotaParlamentarUserController getInstance(Context context) {
		if (instance == null) {
			instance = new CotaParlamentarUserController(context);
		}
		return instance;
	}

	public boolean persistCotasOnLocalDatabase(List<CotaParlamentar> cotas)
			throws NullCotaParlamentarException {
		if(cotas != null) {
			
			boolean result = cotaParlamentarDao.insertCotasOnFollowedParlamentar(cotas);
			return result;
		} else {
			throw new NullCotaParlamentarException();
		}
	}

	public boolean deleteCota(int idParlamentar) {
		return cotaParlamentarDao.deleteCotasFromParlamentar(idParlamentar);
	}

	public List<CotaParlamentar> getCotasByIdParlamentar(int idParlamentar){
		 return cotaParlamentarDao.getCotasByIdParlamentar(idParlamentar);
	}
}
