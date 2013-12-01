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

	private CotaParlamentarUserController(Context context) {
		this.cotaParlamentarDao = CotaParlamentarUserDao.getInstance(context);
	}

	public static CotaParlamentarUserController getInstance(Context context) {
		if (instance == null) {
			instance = new CotaParlamentarUserController(context);
		}
		return instance;
	}

	public boolean persistCotasOnLocalDatabase(Parlamentar parlamentar)
			throws NullParlamentarException {
		if(parlamentar != null) {

			List<CotaParlamentar> cotas = parlamentar.getCotas();	
			boolean result = cotaParlamentarDao.insertCotasOnFollowedParlamentar(cotas);
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
