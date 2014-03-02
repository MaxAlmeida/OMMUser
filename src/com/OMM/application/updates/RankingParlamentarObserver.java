package com.OMM.application.updates;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.model.Parlamentar;


public class RankingParlamentarObserver implements ObserverUpdatesParlamentar{

	private ParlamentarUserController parlamentarController;
	

	
	public RankingParlamentarObserver(
			GetServerUpdates subject,
			Context context)
	{	
		
		subject.registerObserver(this, null);
		parlamentarController = ParlamentarUserController.getInstance(context);
		
	}
	
	@Override
	public void update(List<Parlamentar> list)
	{
		while(!list.isEmpty())
		{
		   parlamentarController.updateParlamentar(list.get(0));
		   list.remove(0);
		}
		
	}

	public ParlamentarUserController getParlamentarController() {
		return parlamentarController;
	}

	public void setParlamentarController(
			ParlamentarUserController parlamentarController) {
		this.parlamentarController = parlamentarController;
	}

	

}
