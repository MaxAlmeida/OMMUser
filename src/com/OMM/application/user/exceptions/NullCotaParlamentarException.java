package com.OMM.application.user.exceptions;

import java.util.Arrays;

public class NullCotaParlamentarException extends Exception {

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "NullCotaParlamentarException [fillInStackTrace()="
				+ fillInStackTrace() + ", getMessage()=" + getMessage()
				+ ", getLocalizedMessage()=" + getLocalizedMessage()
				+ ", getStackTrace()=" + Arrays.toString(getStackTrace())
				+ ", toString()=" + super.toString() + ", getCause()="
				+ getCause() + ", getSuppressed()=" + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
