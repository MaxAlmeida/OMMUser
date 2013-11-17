package com.OMM.application.user.requests;

public abstract class MontaURL {
	
	private static final String IP = "192.168.0.100";
	
	public  MontaURL(){
		// Empty Constructor
	}
		
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
}
