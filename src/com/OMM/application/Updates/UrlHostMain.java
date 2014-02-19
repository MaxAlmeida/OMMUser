package com.OMM.application.Updates;

import android.content.Context;

import com.OMM.application.user.controller.UrlHostController;

public class UrlHostMain implements ObserverUpdates {

	private GetServerUpdates serverUpdatesUrl;
	private UrlHostController serverListener;
	

	public UrlHostMain(GetServerUpdates serverUpdates,Context context)
	{
		this.setServerUpdatesUrl(serverUpdates);
		serverUpdates.registerObserver(this);
		
		this.setServerListener(UrlHostController.getInstance(context));
	}


	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}


	public GetServerUpdates getServerUpdatesUrl() {
		return serverUpdatesUrl;
	}


	public void setServerUpdatesUrl(GetServerUpdates serverUpdatesUrl) {
		this.serverUpdatesUrl = serverUpdatesUrl;
	}


	public UrlHostController getServerListener() {
		return serverListener;
	}


	public void setServerListener(UrlHostController serverListener) {
		this.serverListener = serverListener;
	}
	
	
	
}
