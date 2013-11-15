package com.OMM.application.user.model;

import java.util.List;

public class Parlamentar {

	int id;

	String nome;

	String partido;

	String uf;

	int seguido;

	byte[] foto;

	List<CotaParlamentar> cotas;

	public Parlamentar() {
	}

	public Parlamentar(int id, String nome, String partido, String uf,
			int seguido, byte[] foto) {
		super();
		this.id = id;
		this.nome = nome;
		this.partido = partido;
		this.uf = uf;
		this.seguido = seguido;
		this.foto = foto;
	}

	public int isSeguido() {
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

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public List<CotaParlamentar> getCotas() {
		return cotas;
	}

	public void setCotas(List<CotaParlamentar> cotas) {
		this.cotas = cotas;
	}

}
