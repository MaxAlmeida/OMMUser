package com.OMM.application.user.model;

public class ParlamentarRankingMaiores {
	
	private int id;
	
	private double valorTotalAnual;
	
	private String nomeParlamentar;
	
	private String partido;
	
	private String uf;
	
	public ParlamentarRankingMaiores() {
		// Empty Constructor
	}

	public double getValor() {
		
		return valorTotalAnual;
	}

	public void setValor(double valor) {
		
		this.valorTotalAnual = valor;
	}

	public String getNomeParlamentar() {
		
		return nomeParlamentar;
	}

	public void setNomeParlamentar(String nomeParlamentar) {
		
		this.nomeParlamentar = nomeParlamentar;
	}

	public int getId() {
		
		return id;
	}

	public void setId(int id) {
		
		this.id = id;
	}

	public double getValorTotalAnual() {
		
		return valorTotalAnual;
	}

	public void setValorTotalAnual(double valorTotalAnual)  {
		
		this.valorTotalAnual = valorTotalAnual;
	}

	public String getPartido() {
		
		return partido;
	}

	public void setPartido(String partido) {
		
		this.partido = partido;
	}

	public String getUf() {
		
		return uf;
	}

	public void setUf(String uf) {
		
		this.uf = uf;
	}
}
