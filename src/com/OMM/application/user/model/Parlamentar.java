package com.OMM.application.user.model;

import java.util.List;

public class Parlamentar {

	private int id;

	private String nome;

	private String partido;

	private String uf;

	private double valor;

	private int seguido;

	private byte[] foto;

	private List<CotaParlamentar> cotas;

	private int majorRankingPos;
	
	private int idUpdate;
	
	public Parlamentar() {
	}

	public Parlamentar(int id, String nome, String partido, String uf,
			int seguido) {
		super();
		this.id = id;
		this.nome = nome;
		this.partido = partido;
		this.uf = uf;
		this.seguido = seguido;
	}

	public int getIsSeguido() {
		return seguido;
	}

	public void setSeguido(int seguido) {
		this.seguido = seguido;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public List<CotaParlamentar> getCotas() {
		return cotas;
	}

	public void setCotas(List<CotaParlamentar> cotas) {
		this.cotas = cotas;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public int getMajorRankingPos() {
		return majorRankingPos;
	}

	public void setMajorRankingPos(int majorRankingPos) {
		this.majorRankingPos = majorRankingPos;
	}

	public int getIdUpdate() {
		return idUpdate;
	}

	public void setIdUpdate(int idUpdate) {
		this.idUpdate = idUpdate;
	}

}
