package com.OMM.application.user.controller;

import org.apache.http.client.ResponseHandler;

import android.content.Context;

import com.OMM.application.user.dao.ServerListenerDao;


public class ServerListenerController {

	private static ServerListenerController  instance= null;
	private ServerListenerDao serverListenerDao=null;
	
	private ServerListenerController(Context context) {
		serverListenerDao=ServerListenerDao.getInstance(context);
		
	}
	public static ServerListenerController getInstance(Context context)
	{
		return (instance==null?new ServerListenerController(context):instance);
	}
	
	public String getUrl()
	{
		return serverListenerDao.getUrlServer();
	}
	
	public boolean insertUrlServer(String url_server)
	{
		return serverListenerDao.insertUrlServer(url_server);
	}
	public int getCodUpdateRequest(ResponseHandler<String> responseHandler) {
		
		
		return 0;
	}


}
