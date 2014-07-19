package com.OMM.application.user.updates;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ResponseHandler;

import android.content.Context;

import com.OMM.application.user.controller.CotaParlamentarUserController;
import com.OMM.application.user.controller.ServerUpdatesController;
import com.OMM.application.user.exceptions.ConnectionFailedException;
import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.RequestFailedException;
import com.OMM.application.user.helper.JSONHelper;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.requests.HttpConnection;
import com.OMM.application.user.requests.MountURL;

public class ServerUpdatesSubject implements GetServerUpdates {

	
	private ArrayList<ObserverUpdates> observersParlamentarList;
	private Context context;
	private CotaParlamentarUserController cotaParlamentarController;
	private ServerUpdatesController serverUpdatesController;
	private List<Object> listParlamentares;
	private List<CotaParlamentar> listCotas;
	private static ServerUpdatesSubject instance = null;
	
	
	private ServerUpdatesSubject(Context context)
	{
		this.observersParlamentarList=new ArrayList<ObserverUpdates>();
		this.context=context;
		this.listParlamentares=new ArrayList<Object>();
		this.listCotas=new ArrayList<CotaParlamentar>();
		
		serverUpdatesController = ServerUpdatesController.getInstance(context);
		
		cotaParlamentarController = CotaParlamentarUserController
				.getInstance(context);
	}
	
	public static ServerUpdatesSubject getInstance(Context context){
		if(instance == null){
			instance = new ServerUpdatesSubject(context);
		}
		return instance;
	}
	
	@Override
	public void registerObserver(ObserverUpdates parlamentar) {
		
		this.observersParlamentarList.add(parlamentar);
		
	}

	@Override
	public void removeObserver(ObserverUpdates parlamentar) 
	{
		int i=observersParlamentarList.indexOf(parlamentar);
		if(i>0) observersParlamentarList.remove(i);
	}

	@Override
	public void notifyObservers(long idUpdate) throws ConnectionFailedException, RequestFailedException, NullParlamentarException, NullCotaParlamentarException {
		ObserverUpdates observerParlamentar;
		
		if(!this.listParlamentares.isEmpty())
		{
			if(observersParlamentarList.size()>0)
			{
				 
				for(int i=0;i<observersParlamentarList.size();i++)
				{
					observerParlamentar=(ObserverUpdates)observersParlamentarList.get(i);
					observerParlamentar.update(idUpdate);
				}
			}
		}
	}
	
//	


	/*
	 * Verifica se existe alguma atualização no servidor
	 */
	public void doRequestUpdateVerify(ResponseHandler<String> responseHandler)
			throws ConnectionFailedException, RequestFailedException, NullParlamentarException, NullCotaParlamentarException {

		long idUpdate = serverUpdatesController.getLastIdUpdate();

		String url = MountURL.getIsntance(context, serverUpdatesController)
				.mountUrlExistsUpdate();
		String jsonUpdateVerify = HttpConnection.request(responseHandler, url);
		long serverIdUpdate = JSONHelper.updateFromJSON(jsonUpdateVerify);

		if (serverIdUpdate != idUpdate) {
			notifyObservers(serverIdUpdate);
		} 
	}
	
	public ArrayList<ObserverUpdates> getObserversParlamentarList() {
		return observersParlamentarList;
	}

	public CotaParlamentarUserController getCotaParlamentarController() {
		return cotaParlamentarController;
	}
	public ServerUpdatesController getUrlHostController() {
		return serverUpdatesController;
	}
	public List<CotaParlamentar> getListCotas() {
		return listCotas;
	}
	public void setObserversParlamentarList(
			ArrayList<ObserverUpdates> observersParlamentarList) {
		this.observersParlamentarList = observersParlamentarList;
	}
	public void setCotaParlamentarController(
			CotaParlamentarUserController cotaParlamentarController) {
		this.cotaParlamentarController = cotaParlamentarController;
	}
	public void setUrlHostController(ServerUpdatesController urlHostController) {
		this.serverUpdatesController = urlHostController;
	}
	public void setListCotas(List<CotaParlamentar> listCotas) {
		this.listCotas = listCotas;
	}
}
