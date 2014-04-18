package com.OMM.application.user.updates;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ResponseHandler;

import com.OMM.application.user.controller.CotaParlamentarUserController;
import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.controller.ServerUpdatesController;
import com.OMM.application.user.exceptions.ConnectionFailedException;
import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.RequestFailedException;
import com.OMM.application.user.helper.JSONHelper;
import com.OMM.application.user.model.CotaParlamentar;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.requests.HttpConnection;
import com.OMM.application.user.requests.MountURL;

import android.content.Context;

public class ServerUpdatesSubject implements GetServerUpdates {

	
	private ArrayList<ObserverUpdatesParlamentar> observersParlamentarList;
	private ArrayList<ObserverUpDatesCotas> observersCotasList;
	private Context context;
	private ParlamentarUserController parlamentarController;
	private CotaParlamentarUserController cotaParlamentarController;
	private ServerUpdatesController urlHostController;
	private List<Parlamentar> listParlamentares;
	private List<CotaParlamentar> listCotas;
	
	
	
	public ServerUpdatesSubject(Context context)
	{
		this.observersParlamentarList=new ArrayList<ObserverUpdatesParlamentar>();
		this.observersCotasList=new ArrayList<ObserverUpDatesCotas>();
		this.context=context;
		this.listParlamentares=new ArrayList<Parlamentar>();
		this.listCotas=new ArrayList<CotaParlamentar>();
		
		urlHostController = ServerUpdatesController.getInstance(context);
		parlamentarController = ParlamentarUserController.getInstance(context);
		cotaParlamentarController = CotaParlamentarUserController
				.getInstance(context);
	}
	/*public  ServerUpdatesSubject()
	{
		this.observersParlamentarList=new ArrayList<ObserverUpdatesParlamentar>();
		this.observersCotasList=new ArrayList<ObserverUpDatesCotas>();
	}*/
	@Override
	public void registerObserver(ObserverUpdatesParlamentar parlamentar,ObserverUpDatesCotas cotas) {
		
		this.observersParlamentarList.add(parlamentar);
		this.observersCotasList.add(cotas);
		
	}

	@Override
	public void removeObserver(ObserverUpdatesParlamentar parlamentar,ObserverUpDatesCotas cotas) 
	{
		int i=observersParlamentarList.indexOf(parlamentar);
		if(i>0) observersParlamentarList.remove(i);
		
		i=observersCotasList.indexOf(cotas);
		if(i>0)observersCotasList.remove(i);
	}

	@Override
	public void notifyObservers() {
		ObserverUpdatesParlamentar observerParlamentar;
		ObserverUpDatesCotas observerCotas;
		
		if(!this.listParlamentares.isEmpty())
		{
			if(observersParlamentarList.size()>0)
			{
				 
				for(int i=0;i<observersParlamentarList.size();i++)
				{
					observerParlamentar=(ObserverUpdatesParlamentar)observersParlamentarList.get(i);
					observerParlamentar.update(listParlamentares);
				
				}
			}
		}else if(!this.listCotas.isEmpty())
		{
			if(observersCotasList.size()>0)
			{
				for(int i=0;i<observersCotasList.size();i++)
				{
					observerCotas=(ObserverUpDatesCotas)observersCotasList.get(i);
					observerCotas.update(listCotas);
				
				}
			}
		}
		
		
	}
	
	public void doRequestParlamentar(
			ResponseHandler<String> responseHandler)
			throws ConnectionFailedException,
			RequestFailedException, NullParlamentarException {

		
		if (responseHandler != null) {
			String url;
			int idUpdate = parlamentarController.getIdUpdateParlamentar();
			url = MountURL.getIsntance(context, urlHostController)
					.mountUrlParlamentarUpdate(idUpdate);
			String jsonParlamentarUpdate = HttpConnection.request(
					responseHandler, url);

			this.listParlamentares = JSONHelper
					.listParlamentarFromJSON(jsonParlamentarUpdate);
			//TODO Verificar necessidade desse metodo
			
			parlamentarController.setParlamentares(this.listParlamentares);
			
			notifyObservers();
		}  
		
		
	}
	public void doRequestCota(
			ResponseHandler<String> responseHandler)
			throws ConnectionFailedException, RequestFailedException,
			NullParlamentarException, NullCotaParlamentarException {

		
		
		if (responseHandler != null) {
			String url;

			ArrayList<Integer> parlamentaresIds = (ArrayList<Integer>) parlamentarController
					.getAllSelectedIds();
			int idUpdate = parlamentarController.getLastIdUpdate();
			url = MountURL.getIsntance(context, urlHostController)
					.mountUrlCotaParlamentarUpdate(idUpdate, parlamentaresIds);
			String jsonCotaParlamentarUpdate = HttpConnection.request(
					responseHandler, url);

			listCotas = JSONHelper
					.listCotaParlamentarFromJSON(jsonCotaParlamentarUpdate);
		
			//TODO 
			/*
			 * essa responsabilidade vai para o observador
			 */
			if (listCotas.size() != 0) {
				cotaParlamentarController
						.persistCotasOnLocalDatabase(listCotas);
			} else {
				// nothing here
			}
			notifyObservers();
		}
		 
	}

	/*
	 * Verifica se existe alguma atualização no servidor
	 */
	public boolean doRequestUpdateVerify(ResponseHandler<String> responseHandler)
			throws ConnectionFailedException, RequestFailedException {

		int idUpdate = parlamentarController.getLastIdUpdate();

		String url = MountURL.getIsntance(context, urlHostController)
				.mountUrlExistsUpdate();
		String jsonUpdateVerify = HttpConnection.request(responseHandler, url);
		int serverIdUpdate = JSONHelper.updateFromJSON(jsonUpdateVerify);

		if (serverIdUpdate > idUpdate) {
			return true;
		} else {
			return false;
		}
	}
	//TODO
	//Metodo criado para testar a classe ... APAGAR 
	public void setListParlamentares(ArrayList<Parlamentar>list)
	{
		this.listParlamentares=list;
	}
	
	
	public ArrayList<ObserverUpdatesParlamentar> getObserversParlamentarList() {
		return observersParlamentarList;
	}
	public ArrayList<ObserverUpDatesCotas> getObserversCotasList() {
		return observersCotasList;
	}
	public ParlamentarUserController getParlamentarController() {
		return parlamentarController;
	}
	public CotaParlamentarUserController getCotaParlamentarController() {
		return cotaParlamentarController;
	}
	public ServerUpdatesController getUrlHostController() {
		return urlHostController;
	}
	public List<Parlamentar> getListParlamentares() {
		return listParlamentares;
	}
	public List<CotaParlamentar> getListCotas() {
		return listCotas;
	}
	public void setObserversParlamentarList(
			ArrayList<ObserverUpdatesParlamentar> observersParlamentarList) {
		this.observersParlamentarList = observersParlamentarList;
	}
	public void setObserversCotasList(
			ArrayList<ObserverUpDatesCotas> observersCotasList) {
		this.observersCotasList = observersCotasList;
	}
	public void setParlamentarController(
			ParlamentarUserController parlamentarController) {
		this.parlamentarController = parlamentarController;
	}
	public void setCotaParlamentarController(
			CotaParlamentarUserController cotaParlamentarController) {
		this.cotaParlamentarController = cotaParlamentarController;
	}
	public void setUrlHostController(ServerUpdatesController urlHostController) {
		this.urlHostController = urlHostController;
	}
	public void setListParlamentares(List<Parlamentar> listParlamentares) {
		this.listParlamentares = listParlamentares;
	}
	public void setListCotas(List<CotaParlamentar> listCotas) {
		this.listCotas = listCotas;
	}
	
	
}
