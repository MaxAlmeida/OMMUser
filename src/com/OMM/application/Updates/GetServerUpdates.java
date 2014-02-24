package com.OMM.application.Updates;

public interface GetServerUpdates {
	//TODO 
	/*
	 * mudar assinatura dos metodos para 
	 * outros observadores serem reconhecidos
	 */
	public void registerObserver(ObserverUpdatesParlamentar parlamentar,ObserverUpDatesCotas cotas);
	public void removeObserver(ObserverUpdatesParlamentar parlamentar,ObserverUpDatesCotas cotas);
	public void notifyObservers();

}
