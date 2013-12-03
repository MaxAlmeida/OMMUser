package com.OMM.application.user.exceptions;

import android.annotation.TargetApi;
import java.util.Arrays;

@TargetApi(18)
public class NullParlamentarException extends Exception {

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "NullParlamentarException [fillInStackTrace()="
				+ fillInStackTrace() + ", getMessage()=" + getMessage()
				+ ", getLocalizedMessage()=" + getLocalizedMessage()
				+ ", getStackTrace()=" + Arrays.toString(getStackTrace())
				+ ", toString()=" + super.toString() + ", getCause()="
				+ getCause() + ", getSuppressed()=" + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
