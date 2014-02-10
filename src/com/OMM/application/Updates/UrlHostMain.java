package com.OMM.application.Updates;

import android.content.Context;

import com.OMM.application.user.controller.ServerListenerController;

public class UrlHostMain implements ObserverUrlUpdates {

	private GetServerUpdates serverUpdatesUrl;
	private ServerListenerController serverListener;
	

	public UrlHostMain(GetServerUpdates serverUpdates,Context context)
	{
		this.serverUpdatesUrl=serverUpdates;
		serverUpdates.registerObserver(this);
		
		this.serverListener=ServerListenerController.getInstance(context);
	}
	@Override
	public void update(String newUrl) 
	{
		
		serverListener.insertUrlServer(newUrl);
		
	}
	
	
}
