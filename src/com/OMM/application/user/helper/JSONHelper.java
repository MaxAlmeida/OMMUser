package com.OMM.application.user.helper;

import java.util.List;

import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JSONHelper {
	public static List<Parlamentar> listParlamentarFromJSON(String jsonParlamentar) {
		
		Gson gson = new Gson();
		List<Parlamentar> listParlamentar = gson.fromJson(jsonParlamentar,
				new TypeToken<List<Parlamentar>>() {
				}.getType());
		
		return listParlamentar;
	}

	public static List<CotaParlamentar> listCotaParlamentarFromJSON(String jsonCotaParlamentar) {
		
		Gson gson = new Gson();
		List<CotaParlamentar> listCotaParlamentar = gson.fromJson(jsonCotaParlamentar,
				new TypeToken<List<CotaParlamentar>>() {
				}.getType());
		
		return listCotaParlamentar;
	}
}
