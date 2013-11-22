package com.OMM.application.user.model;

public class ParlamentarRankingMaiores
{
	private double valorTotalAnual;
	
	private String nomeParlamentar;
	
	public ParlamentarRankingMaiores() {
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
}
