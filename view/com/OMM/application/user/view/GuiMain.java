package com.OMM.application.user.view;

import org.apache.http.client.ResponseHandler;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.OMM.application.user.R;
import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.dao.ParlamentarUserDao;
import com.OMM.application.user.exceptions.ConnectionFailedException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.RequestFailedException;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.requests.HttpConnection;
import com.OMM.application.user.view.ParlamentarRankingListFragment.OnParlamentarRankingSelectedListener;

public class GuiMain extends Activity implements
		ParlamentarSeguidoListFragment.OnParlamentarSeguidoSelectedListener,
		ParlamentarListFragment.OnParlamentarSelectedListener,
		ParlamentarRankingListFragment.OnParlamentarRankingSelectedListener{

	private static final String SEGUIDOS = "Parlamentares Seguidos";
	private static final String PESQUISA = "Pesquisar Parlamentar";
	private static final String RANKINGS = "Rankings entre parlamentares";
	private static ParlamentarUserController parlamentarController;
	private static FragmentManager fragmentManager;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gui_main);
		fragmentManager = this.getFragmentManager();

		if (findViewById(R.id.fragment_container) != null)
		{
			ParlamentarSeguidoListFragment fragment = new ParlamentarSeguidoListFragment();
			fragmentManager.beginTransaction()
					.replace(R.id.fragment_container, fragment).commit();
		}


		final Button btn_sobre_main = ( Button ) findViewById(R.id.btn_sobre_main);
		final Button btn_politico_main = ( Button ) findViewById(R.id.btn_politico_main);
		final Button btn_pesquisar_parlamentar = ( Button ) findViewById(R.id.btn_pesquisar_parlamentar);
		final Button btn_ranking_main = ( Button ) findViewById(R.id.btn_ranking);
		final Button btn_mostra_outros = ( Button ) findViewById(R.id.btn_ic_rolagem);

		btn_sobre_main.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick( View v )
			{
				startActivity(new Intent(getBaseContext(), GuiSobre.class));
			}
		});

		btn_politico_main.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick( View v )
			{
				/* Substitui a lista */
				ParlamentarSeguidoListFragment listFragment = new ParlamentarSeguidoListFragment();
				loadFragment(listFragment);
				Toast.makeText(getBaseContext(), SEGUIDOS, Toast.LENGTH_SHORT)
						.show();
			}
		});

		btn_pesquisar_parlamentar.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick( View v )
			{
				ParlamentarListFragment listFragment = new ParlamentarListFragment();
				loadFragment(listFragment);
				Toast.makeText(getBaseContext(), PESQUISA, Toast.LENGTH_SHORT)
						.show();
			}
		});

		btn_ranking_main.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick( View v )
			{
				ParlamentarSeguidoListFragment listFragment = new ParlamentarSeguidoListFragment();
				loadFragment(listFragment);
				Toast.makeText(getBaseContext(), RANKINGS, Toast.LENGTH_SHORT)
						.show();
			}

		});

		btn_mostra_outros.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick( View v )
			{
				/* Modificando a visibilidade dos botoes */
				if (btn_pesquisar_parlamentar.getVisibility() == View.GONE)
				{
					btn_pesquisar_parlamentar.setVisibility(View.VISIBLE);
					btn_politico_main.setVisibility(View.VISIBLE);
					btn_ranking_main.setVisibility(View.VISIBLE);
					btn_sobre_main.setVisibility(View.VISIBLE);
					btn_mostra_outros.setScaleX(1.0f);
					btn_mostra_outros.setScaleY(1.0f);
				} else
				{
					btn_pesquisar_parlamentar.setVisibility(View.GONE);
					btn_politico_main.setVisibility(View.GONE);
					btn_ranking_main.setVisibility(View.GONE);
					btn_sobre_main.setVisibility(View.GONE);
					btn_mostra_outros.setScaleX(0.6f);
					btn_mostra_outros.setScaleY(0.6f);
				}
			}
		});

		parlamentarController = ParlamentarUserController
				.getInstance(getBaseContext());

		if (parlamentarController.checkEmptyDB() == true)
		{

			startPopulateDB();

		} else
		{
			// nothing should be done
		}
	}

	private void updateFragment( int viewId ){
		ParlamentarDetailFragment detailFragment = new ParlamentarDetailFragment();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(viewId, detailFragment);
		transaction.addToBackStack(null);
		transaction.commit();
		getFragmentManager().executePendingTransactions();
		detailFragment.setBarras();

	}

	@Override
	public void OnParlamentarSeguidoSelected()	{
		/* Substitui o detalhe */
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
		{
			updateFragment(R.id.fragment_container);

		} else
		{
			updateFragment(R.id.detail_fragment_container);
		}
	}

	@Override
	public void OnParlamentarSelected()	{

		/* Substitui o detalhe */
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
		{
			updateFragment(R.id.fragment_container);

		} else
		{
			updateFragment(R.id.detail_fragment_container);
		}
	}

	public void OnParlamentarRankingSelected( )
	{
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
		{
			updateFragment(R.id.fragment_container);

		} else
		{
			updateFragment(R.id.detail_fragment_container);
		}
	}

	private class initializeDBTask extends AsyncTask<Object, Void, Integer>
	{
		ProgressDialog progressDialog;

		Integer exception = 0;

		@Override
		protected void onPreExecute( )
		{
			progressDialog = ProgressDialog.show(GuiMain.this,
					"Instalando Banco de Dados...",
					"Isso pode demorar alguns minutos");
		}

		@SuppressWarnings( "unchecked" )
		@Override
		protected Integer doInBackground(Object... params) {

			ParlamentarUserController parlamentarController = ParlamentarUserController
					.getInstance(getBaseContext());

			ResponseHandler<String> responseHandler = ( ResponseHandler<String> ) params[ 0 ];

			boolean result = false;

			try
			{
				result = parlamentarController.insertAll(responseHandler);
			} catch (ConnectionFailedException cfe)
			{

				// TODO: Fazer constantes para retirar números mágicos
				exception = 1;

			} catch (NullParlamentarException cpe)
			{

				exception = 2;
			} catch (RequestFailedException rfe)
			{

				exception = 3;

			} catch (Exception e)
			{
				exception = 4;

			}

			return exception;
		}

		protected void onPostExecute( Integer result )
		{

			progressDialog.dismiss();

			switch (result)
			{

				case 1:

					Alerts.conectionFailedAlert(GuiMain.this);
					break;

				case 2:

					Alerts.parlamentarFailedAlert(GuiMain.this);
					break;

				case 3:

					Alerts.requestFailedAlert(GuiMain.this);
					break;

				case 4:

					Alerts.unexpectedFailedAlert(GuiMain.this);
					break;

				default:
			}
		}
	}

	private void startPopulateDB( )
	{
		ResponseHandler<String> responseHandler = HttpConnection
				.getResponseHandler();
		initializeDBTask task = new initializeDBTask();
		task.execute(responseHandler);
	}

	private void loadFragment( ListFragment listFragment )
	{
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.fragment_container, listFragment);
		transaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu )
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;
	}

}
