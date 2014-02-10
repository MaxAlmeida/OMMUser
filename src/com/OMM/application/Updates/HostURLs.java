package com.OMM.application.Updates;

import java.util.ArrayList;

public class HostURLs implements GetServerUpdates{

	private ArrayList<ObserverUrlUpdates> observersList;
	private String newUrl;
	public HostURLs(ObserverUrlUpdates o)
	{
		this.observersList=new ArrayList<ObserverUrlUpdates>();
	}
	
	@Override
	public void registerObserver(ObserverUrlUpdates o) {
		
		observersList.add(o);
		
	}

	@Override
	public void removeObserver(ObserverUrlUpdates o) 
	{
		int i=observersList.indexOf(o);
		if(i>0) observersList.remove(i);
		
	}

	@Override
	public void notifyObservers() {
		ObserverUrlUpdates observer;
		if(!this.newUrl.isEmpty())
		{
			for(int i=0;i<observersList.size();i++)
			{
				observer=(ObserverUrlUpdates)observersList.get(i);
				observer.update(this.newUrl);
			
			}
		}
		
	}
	public void setNewUrl(String newUrl)
	{
		this.newUrl=newUrl;
	}
	public String getNewUrl()
	{
		return this.newUrl;
	}
	
	public void insertNewUrl(String newUrl)
	{
		notifyObservers();
	}

}
