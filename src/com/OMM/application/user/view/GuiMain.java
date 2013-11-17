package com.OMM.application.user.view;

import org.apache.http.client.ResponseHandler;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.OMM.application.user.R;
import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.requests.HttpConnection;

public class GuiMain extends Activity implements
		ParlamentarSeguidoListFragment.OnParlamentarSeguidoSelectedListener,
		ParlamentarListFragment.OnParlamentarSelectedListener {

	private static final String SEGUIDOS = "Parlamentares Seguidos";
	private static final String PESQUISA = "Pesquisar Parlamentar";
	private static final String RANKINGS = "Rankings entre parlamentares";

	private static FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gui_main);
		fragmentManager = this.getFragmentManager();

		if (findViewById(R.id.fragment_container) != null) {

			/* cria a primeira lista */
			ParlamentarSeguidoListFragment fragment = new ParlamentarSeguidoListFragment();
			fragmentManager.beginTransaction()
					.replace(R.id.fragment_container, fragment).commit();
		}

		final Button btn_sobre_main = (Button) findViewById(R.id.btn_sobre_main);
		final Button btn_politico_main = (Button) findViewById(R.id.btn_politico_main);
		final Button btn_pesquisar_parlamentar = (Button) findViewById(R.id.btn_pesquisar_parlamentar);
		final Button btn_ranking_main = (Button) findViewById(R.id.btn_ranking);
		final Button btn_mostra_outros = (Button) findViewById(R.id.btn_ic_rolagem);

		// agora vc deve implementar os metodos de captura de eventos

		btn_sobre_main.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// esse comando chama outra activity

				startActivity(new Intent(getBaseContext(), GuiSobre.class));// corrigir
																			// //
																			// a
																			// classe

			}
		});

		btn_politico_main.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				/* Substitui a lista */
				ParlamentarSeguidoListFragment listFragment = new ParlamentarSeguidoListFragment();
				FragmentTransaction transaction = fragmentManager
						.beginTransaction();
				transaction.replace(R.id.fragment_container, listFragment);
				transaction.commit();
				Toast.makeText(getBaseContext(), SEGUIDOS, Toast.LENGTH_SHORT)
						.show();
			}
		});

		btn_pesquisar_parlamentar
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						View view = findViewById(R.id.search);
						view.performClick();
						/* Substitui a lista */
						loadParlamentarFragment();
						Toast.makeText(getBaseContext(), PESQUISA,
								Toast.LENGTH_SHORT).show();
					}
				});

		btn_ranking_main.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				loadPSeguidoFragment();
				Toast.makeText(getBaseContext(), RANKINGS, Toast.LENGTH_SHORT)
						.show();
			}

		});

		btn_mostra_outros.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				/* Modificando a visibilidade dos botoes */
				if (btn_pesquisar_parlamentar.getVisibility() == View.GONE) {
					btn_pesquisar_parlamentar.setVisibility(View.VISIBLE);
					btn_politico_main.setVisibility(View.VISIBLE);
					btn_ranking_main.setVisibility(View.VISIBLE);
					btn_sobre_main.setVisibility(View.VISIBLE);
					btn_mostra_outros.setScaleX(1.0f);
					btn_mostra_outros.setScaleY(1.0f);
				} else {
					btn_pesquisar_parlamentar.setVisibility(View.GONE);
					btn_politico_main.setVisibility(View.GONE);
					btn_ranking_main.setVisibility(View.GONE);
					btn_sobre_main.setVisibility(View.GONE);
					btn_mostra_outros.setScaleX(0.6f);
					btn_mostra_outros.setScaleY(0.6f);
				}
			}
		});

		ParlamentarUserController parlamentarController = ParlamentarUserController
				.getInstance(getBaseContext());

		if (parlamentarController.checkEmptyDB() == true) {

			startPopulateDB();

		} else {
			// nothing should be done
		}
	}

	@Override
	public void OnParlamentarSeguidoSelected(Parlamentar parlamentar) {
		/* Substitui o detalhe */
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			ParlamentarDetailFragment detailFragment = new ParlamentarDetailFragment();
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();
			transaction.replace(R.id.fragment_container, detailFragment);
			// transaction.addToBackStack(null);
			transaction.commitAllowingStateLoss();
			getFragmentManager().executePendingTransactions();
			detailFragment.setText(parlamentar.getNome());

		} else {
			ParlamentarDetailFragment detailFragment = new ParlamentarDetailFragment();
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();
			transaction.replace(R.id.detail_fragment_container, detailFragment);
			transaction.addToBackStack(null);
			transaction.commit();
			getFragmentManager().executePendingTransactions();
			detailFragment.setText(parlamentar.getNome());
		}
	}

	@Override
	public void OnParlamentarSelected(Parlamentar parlamentar) {

		parlamentar = startRequest(parlamentar);
		if (parlamentar != null) {
			// 1. Instantiate an AlertDialog.Builder with its constructor

		}
		/* Substitui o detalhe */
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			ParlamentarDetailFragment detailFragment = new ParlamentarDetailFragment();
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();
			transaction.replace(R.id.fragment_container, detailFragment);
			// transaction.addToBackStack(null);
			transaction.commitAllowingStateLoss();
			getFragmentManager().executePendingTransactions();
			detailFragment.setText(parlamentar.getNome());

		} else {
			ParlamentarDetailFragment detailFragment = new ParlamentarDetailFragment();
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();
			transaction.replace(R.id.detail_fragment_container, detailFragment);
			transaction.addToBackStack(null);
			transaction.commit();
			getFragmentManager().executePendingTransactions();
			detailFragment.setText(parlamentar.getNome());
		}
	}

	private class initializeDBTask extends AsyncTask<Object, Void, Void> {
		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(GuiMain.this, "Instalando Banco de Dados...",
					"Isso pode demorar alguns minutos");
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Void doInBackground(Object... params) {

			ParlamentarUserController parlamentarController = ParlamentarUserController
					.getInstance(getBaseContext());

			ResponseHandler<String> responseHandler = (ResponseHandler<String>) params[0];

			boolean result = false;

			try {

				result = parlamentarController.insertAll(responseHandler);

				if (result == true) {

					progressDialog.setMessage("Dados recebidos com sucesso!");

				} else {

					progressDialog.setMessage("Falha na requisição");
				}

			} catch (Exception e) {

				progressDialog.dismiss();
			}

			Log.i("LOGS", "Sucesso da inicialização: " + result);

			return null;
		}

		protected void onPostExecute(Void result) {
			progressDialog.dismiss();
		}
	}

	private void startPopulateDB() {

		ResponseHandler<String> responseHandler = HttpConnection
				.getResponseHandler();
		initializeDBTask task = new initializeDBTask();
		task.execute(responseHandler);
	}

	private class BuscaTask extends AsyncTask<Object, Void, Parlamentar> {
		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(GuiMain.this, "Aguarde...",
					"Buscando Dados");
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Parlamentar doInBackground(Object... params) {

			ParlamentarUserController parlamentarController = ParlamentarUserController
					.getInstance(getBaseContext());

			ResponseHandler<String> rh = (ResponseHandler<String>) params[0];

			Parlamentar parlamentar = (Parlamentar) params[1];
			try {
				parlamentar = parlamentarController.doRequest(rh,
						parlamentar.getId());
			} catch (Exception e) {
				progressDialog.dismiss();
			}
			Log.i("LOGS", "Parlamentar:" + parlamentar.getNome());

			return parlamentar;
		}

		@Override
		protected void onPostExecute(final Parlamentar result) {

			progressDialog.dismiss();
		}
	}

	private Parlamentar startRequest(Parlamentar parlamentar) {

		ResponseHandler<String> responseHandler = HttpConnection
				.getResponseHandler();
		BuscaTask task = new BuscaTask();
		task.execute(responseHandler, parlamentar);

		return parlamentar;
	}

	private void loadPSeguidoFragment() {
		ParlamentarSeguidoListFragment newFragment = new ParlamentarSeguidoListFragment();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.fragment_container, newFragment);
		transaction.commit();
	}

	private void loadParlamentarFragment() {
		ParlamentarListFragment listFragment = new ParlamentarListFragment();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.fragment_container, listFragment);
		transaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.search:
			Button b = (Button) findViewById(R.id.btn_ic_rolagem);
			b.performClick();
			// b.setClickable(false);
			return true;
		default:
			break;
		}

		return false;
	}

}