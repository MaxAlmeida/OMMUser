package com.OMM.application.user.updates;

import com.OMM.application.user.exceptions.ConnectionFailedException;
import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.RequestFailedException;

public interface GetServerUpdates {
	//TODO 
	/*
	 * mudar assinatura dos metodos para 
	 * outros observadores serem reconhecidos
	 */
	public void registerObserver(ObserverUpdates parlamentar);
	public void removeObserver(ObserverUpdates parlamentar);
	public void notifyObservers(long idUpdate) throws ConnectionFailedException, RequestFailedException, NullParlamentarException, NullCotaParlamentarException;

}
