package com.OMM.application.Updates;

import java.util.LinkedList;

import android.content.Context;

import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.model.Parlamentar;

public class RankingParlamentarObserver implements ObserverUpdates{

	private ParlamentarUserController parlamentarController;
	private LinkedList<Parlamentar> pilhaParlamentares;
	
	public RankingParlamentarObserver(
			GetServerUpdates s,
			LinkedList<Parlamentar> pilhaParlamentares,
			Context context)
	{	
		this.pilhaParlamentares=pilhaParlamentares;
		s.registerObserver(this);
		parlamentarController = ParlamentarUserController.getInstance(context);
		
	}
	
	@Override
	public void update()
	{
		while(!pilhaParlamentares.isEmpty())
		{
		   parlamentarController.updateParlamentar(pilhaParlamentares.getLast());
		   pilhaParlamentares.removeLast();
		}
		
	}

}
