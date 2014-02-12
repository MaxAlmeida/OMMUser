package com.OMM.application.user.view;

import org.apache.http.client.ResponseHandler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.OMM.application.user.R;
import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.controller.UrlHostController;
import com.OMM.application.user.exceptions.ConnectionFailedException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.RequestFailedException;
import com.OMM.application.user.requests.HttpConnection;

@SuppressLint("ShowToast")
public class GuiMain extends Activity implements
		ParlamentarSeguidoListFragment.OnParlamentarSeguidoSelectedListener,
		ParlamentarListFragment.OnParlamentarSelectedListener,
		ParlamentarRankingListFragment.OnParlamentarRankingSelectedListener {

	private static final String MESSAGE_TO_PARLAMENTARES_FOLLOWED = "Parlamentares Seguidos";
	private static final String MESSAGE_TO_SEARCH_PARLAMENTAR = "Pesquisar Parlamentar";
	private static final String MESSAGE_TO_RANKINGS = "Rankings entre parlamentares";
	private static final int ANGLE_PARLAMENTARES_FOLLOWED_BUTTON = 30;
	private static final int ANGLE_LIST_ALL_PARLAMENTARES_BUTTON = 0;
	private static final int ANGLE_RANKING_BUTTON = -30;

	private static ParlamentarUserController parlamentarController;
	private static FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gui_main);
		fragmentManager = this.getFragmentManager();

		if (findViewById(R.id.fragment_container) != null) {
			ParlamentarSeguidoListFragment fragment = new ParlamentarSeguidoListFragment();
			fragmentManager.beginTransaction()
					.replace(R.id.fragment_container, fragment).commit();
		}

		final Button btn_about_application_main = (Button) findViewById(R.id.btn_sobre_main);
		final Button btn_parlamentar_main = (Button) findViewById(R.id.btn_politico_main);
		final Button btn_search_parlamentar = (Button) findViewById(R.id.btn_pesquisar_parlamentar);
		final Button btn_ranking_main = (Button) findViewById(R.id.btn_ranking);
		final Button btn_show_all_parlamentares = (Button) findViewById(R.id.btn_ic_rolagem);

		
		//depuraçao 
		
		UrlHostController serverControllerDebug = UrlHostController.getInstance(getBaseContext());
		Toast.makeText(getBaseContext(), serverControllerDebug.getUrl(), Toast.LENGTH_LONG).show();
		
		
		
		btn_about_application_main
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						startActivity(new Intent(getBaseContext(),
								GuiSobre.class));
					}
				});

		btn_parlamentar_main.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ParlamentarSeguidoListFragment listFragment = new ParlamentarSeguidoListFragment();
				loadFragment(listFragment);
				Toast.makeText(getBaseContext(),
						MESSAGE_TO_PARLAMENTARES_FOLLOWED, Toast.LENGTH_SHORT)
						.show();
				btn_show_all_parlamentares
						.setRotation(ANGLE_PARLAMENTARES_FOLLOWED_BUTTON);
			}
		});

		btn_search_parlamentar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ParlamentarListFragment listFragment = new ParlamentarListFragment();
				loadFragment(listFragment);
				Toast.makeText(getBaseContext(), MESSAGE_TO_SEARCH_PARLAMENTAR,
						Toast.LENGTH_SHORT).show();
				btn_show_all_parlamentares
						.setRotation(ANGLE_LIST_ALL_PARLAMENTARES_BUTTON);
			}
		});

		btn_ranking_main.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ParlamentarRankingListFragment listFragment = new ParlamentarRankingListFragment();
				loadFragment(listFragment);
				Toast.makeText(getBaseContext(), MESSAGE_TO_RANKINGS,
						Toast.LENGTH_SHORT).show();
				btn_show_all_parlamentares.setRotation(ANGLE_RANKING_BUTTON);
			}
		});

		btn_show_all_parlamentares
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						// changing the buttons's visibility
						if (btn_search_parlamentar.getAlpha() == 0.0f) {
							btn_search_parlamentar.animate().alpha(1.0f);
							btn_parlamentar_main.animate().alpha(1.0f);
							btn_ranking_main.animate().alpha(1.0f);
							btn_about_application_main.
							animate().alpha(1.0f);
							btn_show_all_parlamentares.animate().scaleX(1.0f);
							btn_show_all_parlamentares.animate().scaleY(1.0f);
							btn_show_all_parlamentares.animate().alpha(1.0f);
						} else {
							btn_search_parlamentar.animate().alpha(0.0f);
							btn_parlamentar_main.animate().alpha(0.0f);
							btn_ranking_main.animate().alpha(0.0f);
							btn_about_application_main.animate().alpha(0.0f);
							btn_show_all_parlamentares.animate().scaleX(0.6f);
							btn_show_all_parlamentares.animate().scaleY(0.6f);
							btn_show_all_parlamentares.animate().alpha(0.5f);
						}
					}
				});

		parlamentarController = ParlamentarUserController
				.getInstance(getBaseContext());

		if (parlamentarController.checkEmptyDB() == true) {
			
			/*
			 * O problema pode estar aqui 
			 * Olhar melhor a chamada do banco 
			 */
				
			UrlHostController serverController = UrlHostController.getInstance(getBaseContext());
			serverController.insertUrlServer("env-6198716.jelastic.websolute.net.br");
			
			//so pra esperar mesmo 
			for(int i=0;i<100000000;i++);
			
			startPopulateDB();
		} else {

		getUpdatesServer();
			
			
		}
	}

	private void updateFragment(int viewId) {
		ParlamentarDetailFragment detailFragment = new ParlamentarDetailFragment();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(viewId, detailFragment);
		//transaction.addToBackStack(null);
		transaction.commit();
		getFragmentManager().executePendingTransactions();
		detailFragment.setBarras();
	}

	@Override
	public void OnParlamentarSeguidoSelected() {

		// Replaces the details according to the orientation of cell phone
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			updateFragment(R.id.fragment_container);

		} else {
			updateFragment(R.id.detail_fragment_container);
		}
	}

	@Override
	public void OnParlamentarSelected() {

		// Replaces the details according to the orientation of cell phone
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			updateFragment(R.id.fragment_container);

		} else {
			updateFragment(R.id.detail_fragment_container);
		}
	}

	public void OnParlamentarRankingSelected() {
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			updateFragment(R.id.fragment_container);

		} else {
			updateFragment(R.id.detail_fragment_container);
		}
	}

	//=============================================================================================================
	//TODO
	/*
	 * Mudar essa AsyncTask para atender o padrao Observer 
	 */
	private class requestServerUpdates extends AsyncTask<Object, Void,Integer>
	{
		ProgressDialog progressDialog;
		Integer exception= Alerts.NO_EXCEPTIONS;
		ResponseHandler<String> responseHandler;
		@Override
		protected void onPreExecute()
		{
		//	progressDialog = ProgressDialog.show(GuiMain.this,"Atualizações","Atualizando informações por favor aguarde....");
		}
		@Override
		protected Integer doInBackground(Object... params) {
			UrlHostController serverController = UrlHostController.getInstance(getBaseContext());
			responseHandler= (ResponseHandler<String>) params[0];
			
			try
			{
 		//		if(serverController.getExistsUpdates(responseHandler)==1)
 		//		{
 		//			serverController.insertUrlServer(serverController.requestNewUrl(responseHandler, 1));
					Toast.makeText(getBaseContext(), "OMM atualizado!",Toast.LENGTH_SHORT).show();
 		//		}else Toast.makeText(getBaseContext(), "Não há atualizações disponivéis!",Toast.LENGTH_SHORT).show();
				
			}catch (Exception e) {
				// TODO: handle exception
			}
			return exception;
		}
		
	}
	//=============================================================================================================
	
	
	private class initializeDBTask extends AsyncTask<Object, Void, Integer> {
		ProgressDialog progressDialog;

		Integer exception = Alerts.NO_EXCEPTIONS;
		
		@Override
		protected void onPreExecute() {
			
			progressDialog = ProgressDialog.show(GuiMain.this,
					"Instalando Banco de Dados...",
					"Isso pode demorar alguns minutos ");
		
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Integer doInBackground(Object... params) {
			

			
			ParlamentarUserController parlamentarController = ParlamentarUserController
					.getInstance(getBaseContext());

			ResponseHandler<String> responseHandler = (ResponseHandler<String>) params[0];
			

			try {
				
				parlamentarController.insertAll(responseHandler);
				
				
			} catch (ConnectionFailedException cfe) {
				exception = Alerts.CONNECTION_FAILED_EXCEPTION;

			} catch (NullParlamentarException cpe) {
				exception = Alerts.NULL_PARLAMENTAR_EXCEPTION;

			} catch (RequestFailedException rfe) {
				exception = Alerts.REQUEST_FAILED_EXCEPTION;
				

			} catch (Exception e) {
				exception = Alerts.UNEXPECTED_FAILED_EXCEPTION;

			}
			return exception;
		}

		protected void onPostExecute(Integer result) {

			progressDialog.dismiss();

			switch (result) {

			case Alerts.CONNECTION_FAILED_EXCEPTION:

				Alerts.conectionFailedAlert(GuiMain.this);
				break;

			case Alerts.NULL_PARLAMENTAR_EXCEPTION:

				Alerts.parlamentarFailedAlert(GuiMain.this);
				break;

			case Alerts.REQUEST_FAILED_EXCEPTION:

				Alerts.requestFailedAlert(GuiMain.this);
				break;

			case Alerts.UNEXPECTED_FAILED_EXCEPTION:

				Alerts.unexpectedFailedAlert(GuiMain.this);
				break;

			default:
				// Nothing should be done
			}
		}
	}

	private void startPopulateDB() {

		ResponseHandler<String> responseHandler = HttpConnection
				.getResponseHandler();

		
		
		initializeDBTask task = new initializeDBTask();
		task.execute(responseHandler);
	}
	
	//TODO 
	/*Mudar esse metodo, se possivel apagar e substituir 
	 * 
	 */
	private void getUpdatesServer()
	{
		ResponseHandler<String> responseHandler = HttpConnection.getResponseHandler();
		requestServerUpdates task= new requestServerUpdates();
		task.execute(responseHandler);
	}

	private void loadFragment(ListFragment listFragment) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.fragment_container, listFragment);
		//transaction.addToBackStack(null);
		transaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {
	
		new AlertDialog.Builder(this)
	        .setTitle("Sair")
	        .setMessage("Tem certeza que deseja sair?")
	        .setNegativeButton("Cancelar", null)
	        .setPositiveButton("Ok", new OnClickListener() {

	            public void onClick(DialogInterface arg0, int arg1) {
	                GuiMain.super.onBackPressed();
	            }
	        }).create().show();
	}
}