package com.OMM.application.Updates;

import java.util.ArrayList;

public class ServerUpdatesSubject implements GetServerUpdates {

	private ArrayList<ObserverUpdates> observersList;
	
	
	public  ServerUpdatesSubject()
	{
		this.observersList=new ArrayList<ObserverUpdates>();
	}
	@Override
	public void registerObserver(ObserverUpdates o) {
		
		observersList.add(o);
		
	}

	@Override
	public void removeObserver(ObserverUpdates o) 
	{
		int i=observersList.indexOf(o);
		if(i>0) observersList.remove(i);
		
	}

	@Override
	public void notifyObservers() {
		ObserverUpdates observer;
		if(observersList.size()>0)
		{
			for(int i=0;i<observersList.size();i++)
			{
				observer=(ObserverUpdates)observersList.get(i);
				observer.update();
			
			}
		}
		
	}
}
