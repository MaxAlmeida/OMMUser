package com.OMM.application.user.helper;

import java.util.List;

import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.TransmissionException;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class JSONHelper
{
	public static List<Parlamentar> listParlamentarFromJSON(
			String jsonParlamentar) {

		Gson gson = new Gson();
		List<Parlamentar> listParlamentar = gson.fromJson(jsonParlamentar,
				new TypeToken<List<Parlamentar>>() {
				}.getType());

		return listParlamentar;
	}

	public static List<CotaParlamentar> listCotaParlamentarFromJSON(
			String jsonCotaParlamentar) throws NullCotaParlamentarException, TransmissionException {
		List<CotaParlamentar> listCotaParlamentar;
		try{
			Gson gson = new Gson();
			listCotaParlamentar = gson.fromJson(
					jsonCotaParlamentar, new TypeToken<List<CotaParlamentar>>() {
					}.getType());

		} catch (NullPointerException npe) {
			
			throw new NullCotaParlamentarException();
		} catch (JsonSyntaxException jse) {
			
			throw new TransmissionException();
		}
		
		if(listCotaParlamentar == null) {
			throw new NullCotaParlamentarException();
			
		}
		return listCotaParlamentar;
	}

	public static List<Parlamentar> listParlamentarRankingMaioresFromJSON(
			String jsonParlamentarRankingMaiores) {
		
		Gson gson = new Gson();
		List<Parlamentar> listParlamentarRankingMaiores = gson.fromJson(
				jsonParlamentarRankingMaiores, new TypeToken<List<Parlamentar>>() {
				}.getType());
		
		return listParlamentarRankingMaiores;

	}
}
