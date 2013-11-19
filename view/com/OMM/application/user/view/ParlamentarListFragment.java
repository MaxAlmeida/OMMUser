package com.OMM.application.user.view;

import java.util.List;

import org.apache.http.client.ResponseHandler;

import android.app.Activity;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.OMM.application.user.R;
import com.OMM.application.user.adapters.ParlamentarAdapter;
import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.requests.HttpConnection;

public class ParlamentarListFragment extends ListFragment {

	private OnParlamentarSelectedListener listener;

	ParseTask parseTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		/**
		 * criando um botão ou campo de pesquisa na action bar da activity.
		 * */
		setHasOptionsMenu(true);

		ParlamentarUserController controllerParlamentar = ParlamentarUserController
				.getInstance(getActivity());
		List<Parlamentar> list = controllerParlamentar.getAll();

		ParlamentarAdapter adapter = new ParlamentarAdapter(getActivity(),
				R.layout.fragment_parlamentar, list);

		setListAdapter(adapter);
		setRetainInstance(false);

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Parlamentar parlamentar = (Parlamentar) getListAdapter().getItem(
				position);
		Toast.makeText(getActivity(), "toquei!", Toast.LENGTH_SHORT).show();
		updateDetail(parlamentar);

	}

	private static class ParseTask extends
			AsyncTask<String, Void, List<Parlamentar>> {

		private ParlamentarListFragment fragment;

		public void setFragment(ParlamentarListFragment fragment) {
			this.fragment = fragment;
		}

		@Override
		protected List<Parlamentar> doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			// Aqui vai aparecer a chamda da controller.
			return null;
		}

		@Override
		protected void onPostExecute(List<Parlamentar> result) {

			fragment.setListContent(result);

		}
	}

	public void updateListContent() {

		if (parseTask == null) {
			parseTask = new ParseTask();
			parseTask.setFragment(this);
		}
	}

	public void setListContent(List<Parlamentar> result) {

		// ArrayAdapter listAdapter = (ArrayAdapter) getListAdapter();
		// listAdapter.clear();
		// listAdapter.addAll(result);
		// parseTask.setFragment(null);

	}

	/*
	 * Responsavel por chamar uma activity, a natureza do fragment nao permite q
	 * ele chame activities, enta eh preciso criar uma interface para outra
	 * activity fazer a chamada, logo... a Main faz.
	 */
	public interface OnParlamentarSelectedListener {
		public void OnParlamentarSelected(Parlamentar parlamentar);
	}

	/*
	 * Faz a chamada para concatenar activities esse metodo so vai funcionar se
	 * a interface anterior for implementada
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof OnParlamentarSelectedListener) {
			listener = (OnParlamentarSelectedListener) activity;
		} else {
			throw new ClassCastException(
					activity.toString()
							+ "must implement ParlamentarListFragment.OnParlamentarSelectedListner");
		}
	}

	public void updateDetail(Parlamentar parlamentar) {

		parlamentar = startRequest(parlamentar);

		// listener.OnParlamentarSelected(startRequest(parlamentar));
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Do something that differs the Activity's menu here
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.search:
			// Not implemented here
			return false;
		default:
			break;
		}

		return false;
	}

	private class BuscaTask extends AsyncTask<Object, Void, Parlamentar> {
		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(getActivity(), "Aguarde...",
					"Buscando Dados");
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Parlamentar doInBackground(Object... params) {

			ParlamentarUserController parlamentarController = ParlamentarUserController
					.getInstance(getActivity());

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
			listener.OnParlamentarSelected(result);
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
