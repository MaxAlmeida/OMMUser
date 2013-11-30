package com.OMM.application.user.requests;

public abstract class MontaURL {
	
	public static final String IP = "192.168.1.3";
	
	public static String mountURLCota(int idParlamentar){
		 
		String urlCotaParlamentar = "http://" + IP + ":8080/OlhaMinhaMesada/cota?id=" + idParlamentar;
		
		return urlCotaParlamentar;
	}
	
	public static String mountURLParlamentar (int idParlamentar){
		 
		String urlParlamentar = "http://" + IP + ":8080/OlhaMinhaMesada/parlamentar?id=" + idParlamentar;
		
		return urlParlamentar;	
	}
	
	public static String mountUrlAll(){
		
		String urlAllParlamentares = "http://" + IP + ":8080/OlhaMinhaMesada/parlamentares";
		
		return urlAllParlamentares;
	}
	
	public static String mountUrlMajorRanking(){
		
		String urlRankingParlamentares = "http://" + IP + ":8080/OlhaMinhaMesada/rankingMaiores";
		
		return urlRankingParlamentares;
	}
}
