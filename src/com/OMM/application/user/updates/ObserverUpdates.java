package com.OMM.application.user.updates;

import com.OMM.application.user.exceptions.ConnectionFailedException;
import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.RequestFailedException;


public interface ObserverUpdates {
	public void update(long idUpdate) throws ConnectionFailedException,
			RequestFailedException, NullParlamentarException,
			NullCotaParlamentarException;

	
}
