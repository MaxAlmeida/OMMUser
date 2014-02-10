package com.OMM.application.user.controller;

import java.util.Iterator;
import java.util.List;

import org.apache.http.client.ResponseHandler;

import android.content.Context;
import android.util.MonthDisplayHelper;

import com.OMM.application.user.dao.UrlHostDao;
import com.OMM.application.user.exceptions.ConnectionFailedException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.RequestFailedException;
import com.OMM.application.user.exceptions.TransmissionException;
import com.OMM.application.user.helper.JSONHelper;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.requests.HttpConnection;
import com.OMM.application.user.requests.MountURL;


public class UrlHostController {

	private static UrlHostController  instance= null;
	private UrlHostDao urlHostDao=null;
	private static MountURL url=null;
	
	private UrlHostController(Context context) {
		urlHostDao=UrlHostDao.getInstance(context);
		
		
	}
	public static UrlHostController getInstance(Context context)
	{
		if (instance ==null) 
			instance =new UrlHostController(context);
		
		url=MountURL.getIsntance(context,instance);
		return instance;
		 
	}
	
	public String getUrl()
	{
		return urlHostDao.getUrlServer();
	}
	
	public boolean insertUrlServer(String url_server)
	{
		return urlHostDao.insertUrlServer(url_server);
	}
	
	public int getExistsUpdates(ResponseHandler<String> response) 
			throws  ConnectionFailedException,
					RequestFailedException, TransmissionException 
	{
		
		
		String url= this.url.mountUrlExistsUpdate();
		String jsonCodUpdate =HttpConnection.request(response, url);
		
		return JSONHelper.codUpdates(jsonCodUpdate);
		
	}
	public String requestNewUrl(ResponseHandler<String> response,int cod) throws NullPointerException,ConnectionFailedException,
	RequestFailedException,TransmissionException
	{
		String url=this.url.mountUrlRequestUpdates(cod);
		String jsonNewUrlServer = HttpConnection.request(response, url);
		
		return JSONHelper.newRequestUrl(jsonNewUrlServer);
	}
}
