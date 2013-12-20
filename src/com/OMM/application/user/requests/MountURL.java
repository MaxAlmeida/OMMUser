package com.OMM.application.user.requests;

import com.OMM.application.user.controller.ServerListenerController;

import android.content.Context;

public class MountURL {

//	public static final String IP = "env-6198716.jelastic.websolute.net.br";
	
	private String IP = null;
	private static MountURL instance=null;
	private ServerListenerController controller=null;
	private MountURL(Context context)
	{
		controller = ServerListenerController.getInstance(context);
		IP=controller.getUrl();
	}
	public static MountURL getIsntance(Context context)
	{
		return (instance==null ? new MountURL(context):instance);
	}
	
	
	
	public   String mountURLCota(int idParlamentar) {

		String urlCotaParlamentar = "http://" + IP
				+ "/cota?id=" + idParlamentar;

		return urlCotaParlamentar;
	}

	public   String mountURLParlamentar(int idParlamentar) {

		String urlParlamentar = "http://" + IP
				+ "/parlamentar?id=" + idParlamentar;

		return urlParlamentar;
	}

	public   String mountUrlAll() {

		String urlAllParlamentares = "http://" + IP
				+ "/parlamentares";

		return urlAllParlamentares;
	}

	public  String mountUrlMajorRanking() {

		String urlRankingParlamentares = "http://" + IP
				+ "/rankingMaiores";

		return urlRankingParlamentares;
	}
	public String mountUrlServerListener()
	{
		return ("http://"+IP+"/urlServer");
	}
	public String mountUrlRequestUpdates(int cod)
	{
		return ("http://"+IP+"/urlServer?cod="+cod);
	}
	public String getIP()
	{
		return this.IP;
	}
	public String mountUrlExistsUpdate()
	{
		return ("http://"+IP+"/upDateCode");
	}
	
}
