package com.OMM.application.user.model;

public class CotaParlamentar {

	private int id;
	
	private int idParlamentar;

	private int ano;

	private int numeroSubCota;

	private double valorJaneiro;

	private double valorFevereiro;

	private double valorMarco;

	private double valorAbril;

	private double valorMaio;

	private double valorJunho;

	private double valorJulho;

	private double valorAgosto;

	private double valorSetembro;

	private double valorOutubro;

	private double valorNovembro;

	private double valorDezembro;

	private String descricao;

	private String especificacao;

	public CotaParlamentar() {
	}

	public CotaParlamentar(int id, int idParlamentar, int ano,
			int numeroSubCota, double valorJaneiro, double valorFevereiro,
			double valorMarco, double valorAbril, double valorMaio,
			double valorJunho, double valorJulho, double valorAgosto,
			double valorSetembro, double valorOutubro, double valorNovembro,
			double valorDezembro, String descricao, String especificacao) {
		super();
		this.id = id;
		this.idParlamentar = idParlamentar;
		this.ano = ano;
		this.numeroSubCota = numeroSubCota;
		this.valorJaneiro = valorJaneiro;
		this.valorFevereiro = valorFevereiro;
		this.valorMarco = valorMarco;
		this.valorAbril = valorAbril;
		this.valorMaio = valorMaio;
		this.valorJunho = valorJunho;
		this.valorJulho = valorJulho;
		this.valorAgosto = valorAgosto;
		this.valorSetembro = valorSetembro;
		this.valorOutubro = valorOutubro;
		this.valorNovembro = valorNovembro;
		this.valorDezembro = valorDezembro;
		this.descricao = descricao;
		this.especificacao = especificacao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdParlamentar() {
		return idParlamentar;
	}

	public void setIdParlamentar(int idParlamentar) {
		this.idParlamentar = idParlamentar;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getNumeroSubCota() {
		return numeroSubCota;
	}

	public void setNumeroSubCota(int numeroSubCota) {
		this.numeroSubCota = numeroSubCota;
	}

	public double getValorJaneiro() {
		return valorJaneiro;
	}

	public void setValorJaneiro(double valorJaneiro) {
		this.valorJaneiro = valorJaneiro;
	}

	public double getValorFevereiro() {
		return valorFevereiro;
	}

	public void setValorFevereiro(double valorFevereiro) {
		this.valorFevereiro = valorFevereiro;
	}

	public double getValorMarco() {
		return valorMarco;
	}

	public void setValorMarco(double valorMarco) {
		this.valorMarco = valorMarco;
	}

	public double getValorAbril() {
		return valorAbril;
	}

	public void setValorAbril(double valorAbril) {
		this.valorAbril = valorAbril;
	}

	public double getValorMaio() {
		return valorMaio;
	}

	public void setValorMaio(double valorMaio) {
		this.valorMaio = valorMaio;
	}

	public double getValorJunho() {
		return valorJunho;
	}

	public void setValorJunho(double valorJunho) {
		this.valorJunho = valorJunho;
	}

	public double getValorJulho() {
		return valorJulho;
	}

	public void setValorJulho(double valorJulho) {
		this.valorJulho = valorJulho;
	}

	public double getValorAgosto() {
		return valorAgosto;
	}

	public void setValorAgosto(double valorAgosto) {
		this.valorAgosto = valorAgosto;
	}

	public double getValorSetembro() {
		return valorSetembro;
	}

	public void setValorSetembro(double valorSetembro) {
		this.valorSetembro = valorSetembro;
	}

	public double getValorOutubro() {
		return valorOutubro;
	}

	public void setValorOutubro(double valorOutubro) {
		this.valorOutubro = valorOutubro;
	}

	public double getValorNovembro() {
		return valorNovembro;
	}

	public void setValorNovembro(double valorNovembro) {
		this.valorNovembro = valorNovembro;
	}

	public double getValorDezembro() {
		return valorDezembro;
	}

	public void setValorDezembro(double valorDezembro) {
		this.valorDezembro = valorDezembro;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getEspecificacao() {
		return especificacao;
	}

	public void setEspecificacao(String especificacao) {
		this.especificacao = especificacao;
	}
	
	public boolean equals(CotaParlamentar cota){
		//Completar com os outros atributos..
		if (this.id==cota.getId() && (this.idParlamentar==cota.getIdParlamentar()))
			return true;
		return false;
		
	}


}
