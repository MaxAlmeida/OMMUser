package com.OMM.test.user.requests;

import junit.framework.Assert;

import com.OMM.application.user.requests.MountURL;

import android.test.AndroidTestCase;

public class MountURLTest extends AndroidTestCase {

	private String IP;
	private MountURL url=null;
	public void setUp() {
		url=MountURL.getIsntance(getContext());
		

	}

	public void testMountUTL() {

		MountURL mountURL = MountURL.getIsntance(getContext());
		assertNotNull(mountURL);

	}

	public void testMountURLCota() {

		String urlParlamentar = "http://" + IP
				+ "/cota?id=373";

		Assert.assertEquals(urlParlamentar, url.mountURLCota(373));
	}

	public void testMountURLParlamentar() {

		String urlParlamentar = "http://" + IP
				+ "/parlamentar?id=373";

		Assert.assertEquals(urlParlamentar, url.mountURLParlamentar(373));
	}

	public void testMountUrlAll() {

		String urlAll = "http://" + IP + "/parlamentares";

		Assert.assertEquals(urlAll, url.mountUrlAll());

	}

	public void testMountUrlMajorRanking() {

		String urlMajorRanking = "http://" + IP
				+ "/rankingMaiores";

		Assert.assertEquals(urlMajorRanking, url.mountUrlMajorRanking());

	}

}