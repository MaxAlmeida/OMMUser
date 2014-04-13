package com.OMM.application.user.requests;

import java.util.Iterator;
import java.util.List;

import android.content.Context;

import com.OMM.application.user.controller.ServerUpdatesController;

public class MountURL {

	// public static final String IP = "env-6198716.jelastic.websolute.net.br";

	private String IP = null;
	private static MountURL instance = null;

	private MountURL(Context context, ServerUpdatesController controller) {
		IP = controller.getUrl();
	}

	public static MountURL getIsntance(Context context,
			ServerUpdatesController controller) {
		if (instance == null)
			instance = new MountURL(context, controller);

		return instance;
	}

	public String mountURLCota(int idParlamentar) {

		String urlCotaParlamentar = "http://" + IP

		+ "/cota?id=" + idParlamentar;
		return urlCotaParlamentar;
	}

	public String mountURLParlamentar(int idParlamentar) {

		String urlParlamentar = "http://" + IP

		+ "/parlamentar?id=" + idParlamentar;

		return urlParlamentar;
	}

	public String mountUrlAll() {

		String urlRankingParlamentares = "http://" + IP

		+ "/parlamentares";

		return urlRankingParlamentares;
	}

	public String mountUrlRequestUpdates(int cod) {
		return ("http://" + IP + "/urlServer?cod=" + cod);
	}

	public String getIP() {
		return this.IP;
	}

	public String mountUrlExistsUpdate() {
		return ("http://" + IP + "/verifyUpdate");
	}

	public String mountUrlParlamentarUpdate(int idUpdate) {
		return ("http://" + IP + "/parlamentarUpdate?idUpdate=" + idUpdate);
	}
	
	public String mountUrlCotaParlamentarUpdate(int idUpdate, List<Integer> parlamentaresIds) {
		String returns = ("http://" + IP + "/cotaUpdate?idUpdate=" + idUpdate);
		
		Iterator<Integer> i = parlamentaresIds.iterator();
		
		while(i.hasNext()){
			returns+="&idParlamentar[]="+i.next();
		}
		
		return returns;	
	}
	
}
