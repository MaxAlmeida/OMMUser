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
		/*
		 * TODO Retirar esse código após primaira instalação paraEvitar a
		 * criação de muitos parlamentares locaiscódigo usado apenas para teste,
		 * quebra o esquemaarquitetural do MVC.
		 */
		// inicializa o banco e cria se ele nao existir
		// ParlamentarUserDao dao = new ParlamentarUserDao(getBaseContext());
		// Parlamentar po = new Parlamentar();
		// po.setId(54373);
		// po.setNome("Tiririca");
		// po.setPartido("pcdob");
		// po.setSeguido(false);
		// po.setUf("DF");
		// dao.insert(po);

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
						/* Substitui a lista */
						ParlamentarListFragment listFragment = new ParlamentarListFragment();
						FragmentTransaction transaction = fragmentManager
								.beginTransaction();
						transaction.replace(R.id.fragment_container,
								listFragment);
						transaction.commit();
						Toast.makeText(getBaseContext(), PESQUISA,
								Toast.LENGTH_SHORT).show();
					}
				});
		btn_ranking_main.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ParlamentarSeguidoListFragment newFragment = new ParlamentarSeguidoListFragment();
				FragmentTransaction transaction = fragmentManager
						.beginTransaction();
				transaction.replace(R.id.fragment_container, newFragment);
				transaction.commit();
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

	private class BuscaTask extends AsyncTask<Object, Void, String> {
		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(GuiMain.this, "Aguarde...",
					"Buscando Dados");
		}

		@SuppressWarnings("unchecked")
		@Override
		protected String doInBackground(Object... params) {

			ParlamentarUserController parlamentarController = ParlamentarUserController
					.getInstance();

			ResponseHandler<String> rh = (ResponseHandler<String>) params[0];

			Parlamentar parlamentar = (Parlamentar) params[1];
			try {
				parlamentar = parlamentarController.fazerRequisicao(rh,
						parlamentar.getId());
			} catch (Exception e) {
				progressDialog.dismiss();
			}
			Log.i("LOGS", "Parlamentar:" + parlamentar.getNome());

			String objeto = parlamentar.toString();

			return objeto;
		}

		@Override
		protected void onPostExecute(final String result) {

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

}