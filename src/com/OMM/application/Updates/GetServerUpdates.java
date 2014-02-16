package com.OMM.application.Updates;

public interface GetServerUpdates {
	
	public void registerObserver(ObserverUpdates o);
	public void removeObserver(ObserverUpdates o);
	public void notifyObservers();

}
