package com.OMM.application.user.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.util.Log;

import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JSONHelper {
	public static List<Parlamentar> listParlamentarFromJSON(
			String jsonParlamentar) throws NullParlamentarException {
		List<Parlamentar> listParlamentar;
		try {
			Gson gson = new Gson();
			listParlamentar = gson.fromJson(jsonParlamentar,
					new TypeToken<List<Parlamentar>>() {
					}.getType());
			
			Log.i("VALOR DA VARIAVEL jsonParlamentar", jsonParlamentar);

		} catch (NullPointerException e) {

			throw new NullParlamentarException();
		}
		return listParlamentar;
	}

	public static List<CotaParlamentar> listCotaParlamentarFromJSON(
			String jsonCotaParlamentar) throws NullCotaParlamentarException {
		List<CotaParlamentar> listCotaParlamentar;
		try {
			Gson gson = new Gson();
			listCotaParlamentar = gson.fromJson(jsonCotaParlamentar,
					new TypeToken<List<CotaParlamentar>>() {
					}.getType());

		} catch (NullPointerException npe) {

			throw new NullCotaParlamentarException();
		}
		if (listCotaParlamentar == null) {
			throw new NullCotaParlamentarException();

		}
		return listCotaParlamentar;
	}

	public static String newRequestUrl(String jsonNewUrl)
			throws NullPointerException {
		String newUrlServer = null;
		List<String> urls = new ArrayList<String>();

		try {
			Gson gson = new Gson();
			urls = gson.fromJson(jsonNewUrl, new TypeToken<List<String>>() {
			}.getType());

			Iterator<String> iterator = urls.iterator();
			while (iterator.hasNext()) {
				newUrlServer = iterator.next();
			}

		} catch (NullPointerException error) {

		}

		return newUrlServer;

	}

	public static int updateFromJSON(String jsonUpdate) {
		int codUpdates = 0;
		List<String> cods = new ArrayList<String>();
		try {

			Gson gson = new Gson();
			cods = gson.fromJson(jsonUpdate, new TypeToken<List<String>>() {
			}.getType());
			codUpdates = Integer.parseInt(cods.get(cods.size()-1));
		} catch (NullPointerException error) {
			
		}
		return codUpdates;
	}
}
