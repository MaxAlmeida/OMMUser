package com.OMM.application.user.controller;

import java.util.Iterator;
import java.util.List;

import org.apache.http.client.ResponseHandler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.OMM.application.user.dao.CotaParlamentarUserDao;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.helper.DB;
import com.OMM.application.user.helper.JSONHelper;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.requests.HttpConnection;
import com.OMM.application.user.requests.MontaURL;

public class CeapUserController {

	private static CeapUserController instance;

	private CotaParlamentarUserDao cotaDao;
	private Context context;

	private CeapUserController(Context context) {
		this.cotaDao = CotaParlamentarUserDao.getInstance(context);
		this.context=context;
	}

	public static CeapUserController getInstance(Context context) {

		if (instance == null) {

			instance = new CeapUserController(context);
		}

		return instance;
	}

	private List<CotaParlamentar> convertJsonToCotaParlamentar(String jsonCota)
			throws NullParlamentarException {
		try {

			List<CotaParlamentar> cotas = JSONHelper
					.listCotaParlamentarFromJSON(jsonCota);

			return cotas;

		} catch (NullPointerException npe) {

			throw new NullParlamentarException();
		}
	}

	public boolean persistCotaDB(Parlamentar parlamentar)
			throws NullParlamentarException {

		boolean result = true;

		List<CotaParlamentar> cotas = parlamentar.getCotas();
		Iterator<CotaParlamentar> iterator = cotas.iterator();

		while (iterator.hasNext()) {

			boolean temporary = cotaDao.insertFollowed(parlamentar,
					iterator.next());

			result = result & temporary;
		}

		return result;
	}

	public boolean deleteCota(Parlamentar parlamentar) {

		CotaParlamentarUserDao cota = CotaParlamentarUserDao.getInstance(context);

		return cota.deleteParlamentar(parlamentar);
	}

}
