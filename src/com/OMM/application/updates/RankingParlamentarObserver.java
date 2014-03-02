package com.OMM.application.updates;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.model.Parlamentar;

public class RankingParlamentarObserver implements ObserverUpdatesParlamentar{

	private ParlamentarUserController parlamentarController;
	private ArrayList<Parlamentar> pilhaParlamentares;

	
	public RankingParlamentarObserver(
			GetServerUpdates s,
			ArrayList<Parlamentar> pilhaParlamentares,
			Context context)
	{	
		this.pilhaParlamentares=pilhaParlamentares;
		s.registerObserver(this,null);
		parlamentarController = ParlamentarUserController.getInstance(context);
		
	}
	
	@Override
	public void update(List<Parlamentar> list)
	{
		while(!list.isEmpty())
		{
		   parlamentarController.updateParlamentar(pilhaParlamentares.get(0));
		   list.remove(0);
		}
		
	}

}
