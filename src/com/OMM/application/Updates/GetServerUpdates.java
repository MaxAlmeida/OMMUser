package com.OMM.application.Updates;

public interface GetServerUpdates {
	
	public void registerObserver(ObserverUrlUpdates o);
	public void removeObserver(ObserverUrlUpdates o);
	public void notifyObservers();

}
