package com.OMM.test.user.dao;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import android.content.Context;
import android.test.AndroidTestCase;
import com.OMM.application.user.dao.CotaParlamentarUserDao;
import com.OMM.application.user.model.CotaParlamentar;

public class CotaParlamentarUserDaoTest extends AndroidTestCase
{

	Context context;
	private CotaParlamentarUserDao cotaDao;
	private CotaParlamentar cotaParlamentar = new CotaParlamentar();

	public void setUp()
	{
		context = getContext();
		this.cotaDao = CotaParlamentarUserDao.getInstance(context);
		cotaParlamentar.setIdParlamentar(0);
		cotaParlamentar.setValor(330);
	}

	public void testGetInstance()
	{

		CotaParlamentarUserDao parlamentarDao1 = CotaParlamentarUserDao
				.getInstance(context);
		CotaParlamentarUserDao parlamentarDao2 = CotaParlamentarUserDao
				.getInstance(context);
		assertSame(parlamentarDao1, parlamentarDao2);
	}

	public void testInsertFollowed()
	{
		List<CotaParlamentar> list = new ArrayList<CotaParlamentar>();
		list.add(cotaParlamentar);
		assertEquals(true, cotaDao.insertCotasOnFollowedParlamentar(list));
		cotaDao.deleteCotasFromParlamentar(cotaParlamentar.getIdParlamentar());
	}

	public void testDeleteParlamentar()
	{

		List<CotaParlamentar> list = new ArrayList<CotaParlamentar>();
		list.add(cotaParlamentar);
		cotaDao.insertCotasOnFollowedParlamentar(list);
		Assert.assertEquals(true, cotaDao
				.deleteCotasFromParlamentar(cotaParlamentar.getIdParlamentar()));
	}

	public void testGetCotasByIdParlamentar()
	{

		List<CotaParlamentar> list = new ArrayList<CotaParlamentar>();
		list.add(cotaParlamentar);
		cotaDao.insertCotasOnFollowedParlamentar(list);
		List<CotaParlamentar> listDao = cotaDao.getCotasByIdParlamentar(0);
		assertEquals(330.0, listDao.get(0).getValor(), .0f);
		cotaDao.deleteCotasFromParlamentar(cotaParlamentar.getIdParlamentar());
	}

}
