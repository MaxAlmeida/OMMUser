package com.OMM.application.user.exceptions;

public class RequestFailedException extends Exception{

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "RequestFailedException [getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	

}
