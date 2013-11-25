package com.OMM.application.user.model;

public class CotaParlamentar
{

	private int cod;
	private int idParlamentar;
	private int mes;
	private int ano;
	private int numeroSubCota;
	private String descricao;
	private double valor;

	public CotaParlamentar()
	{
	}

	public int getCod( )
	{
		return cod;
	}

	public void setCod( int cod )
	{
		this.cod = cod;
	}

	public int getIdParlamentar( )
	{
		return idParlamentar;
	}

	public void setIdParlamentar( int idParlamentar )
	{
		this.idParlamentar = idParlamentar;
	}

	public int getMes( )
	{
		return mes;
	}

	public void setMes( int mes )
	{
		this.mes = mes;
	}

	public int getAno( )
	{
		return ano;
	}

	public void setAno( int ano )
	{
		this.ano = ano;
	}

	public int getNumeroSubCota( )
	{
		return numeroSubCota;
	}

	public void setNumeroSubCota( int numeroSubCota )
	{
		this.numeroSubCota = numeroSubCota;
	}

	public String getDescricao( )
	{
		return descricao;
	}

	public void setDescricao( String descricao )
	{
		this.descricao = descricao;
	}

	public double getValor( )
	{
		return valor;
	}

	public void setValor( double valor )
	{
		this.valor = valor;
	}



}
