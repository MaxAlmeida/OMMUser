package com.OMM.application.user.requests;

public class MountURL {

	public static final String IP = "env-6198716.jelastic.websolute.net.br/";

	public static String mountURLCota(int idParlamentar) {

		String urlCotaParlamentar = "http://" + IP
				+ "cota?id=" + idParlamentar;

		return urlCotaParlamentar;
	}

	public static String mountURLParlamentar(int idParlamentar) {

		String urlParlamentar = "http://" + IP
				+ "parlamentar?id=" + idParlamentar;

		return urlParlamentar;
	}

	public static String mountUrlAll() {

		String urlAllParlamentares = "http://" + IP
				+ "parlamentares";

		return urlAllParlamentares;
	}

	public static String mountUrlMajorRanking() {

		String urlRankingParlamentares = "http://" + IP
				+ "rankingMaiores";

		return urlRankingParlamentares;
	}
}
