package com.OMM.test.user.dao;

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
		Assert.assertEquals(true, cotaDao.insertFollowed(cotaParlamentar));
		cotaDao.deleteCotasFromParlamentar(cotaParlamentar.getIdParlamentar());
	}

	public void testDeleteParlamentar()
	{

		cotaDao.insertFollowed(cotaParlamentar);
		Assert.assertEquals(true, cotaDao
				.deleteCotasFromParlamentar(cotaParlamentar.getIdParlamentar()));
	}

	public void testGetCotasByIdParlamentar()
	{
		cotaDao.insertFollowed(cotaParlamentar);
		List<CotaParlamentar> list = cotaDao.getCotasByIdParlamentar(0);
		assertEquals(330.0, list.get(0).getValor(), .0f);
		cotaDao.deleteCotasFromParlamentar(cotaParlamentar.getIdParlamentar());
	}

}
