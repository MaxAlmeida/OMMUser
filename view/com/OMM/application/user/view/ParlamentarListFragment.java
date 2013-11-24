package com.OMM.application.user.view;

import java.util.List;

import org.apache.http.client.ResponseHandler;

import android.app.Activity;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.OMM.application.user.R;
import com.OMM.application.user.adapters.ParlamentarAdapter;
import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.requests.HttpConnection;

public class ParlamentarListFragment extends ListFragment {

	private OnParlamentarSelectedListener listener;
	private static ParlamentarUserController controllerParlamentar;
	private ParseTask parseTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		controllerParlamentar = ParlamentarUserController
				.getInstance(getActivity());

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
		protected List<Parlamentar> doInBackground(String... params) {
			List<Parlamentar> result = controllerParlamentar
					.getSelected(params[0]);
			return result;
		}

		@Override
		protected void onPostExecute(List<Parlamentar> result) {

			fragment.setListContent(result);

		}
	}

	public void updateListContent(String inputText) {

		if (parseTask == null) {
			parseTask = new ParseTask();
			parseTask.setFragment(this);
		}
		try {
			parseTask.execute(inputText);

		}
		// TODO tratar com a devida excessão lançada.
		catch (IllegalStateException e) {
			// IllegalStateException
		}
	}

	public void setListContent(List<Parlamentar> result) {

		ArrayAdapter<Parlamentar> listAdapter = (ArrayAdapter<Parlamentar>) getListAdapter();
		listAdapter.clear();
		listAdapter.addAll(result);
		parseTask.setFragment(null);
		parseTask = null;

	}

	public interface OnParlamentarSelectedListener {
		public void OnParlamentarSelected(Parlamentar parlamentar);
	}

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
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

		super.onCreateOptionsMenu(menu, inflater);
		MenuItem search = menu.add("Pesquisa");
		final SearchView sv = new SearchView(getActivity());
		search.setActionView(sv);
		search.setIcon(R.drawable.ic_search);
		search.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		sv.setOnQueryTextListener(new OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				updateListContent(query);
				hideKeyboard();
				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				updateListContent(newText);
				return false;
			}
		});
	}

	private class RequestTask extends AsyncTask<Object, Void, Parlamentar> {
		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(getActivity(), "Aguarde...",
					"Buscando Dados");
		}

		@Override
		protected Parlamentar doInBackground(Object... params) {

			ParlamentarUserController parlamentarController = ParlamentarUserController
					.getInstance(getActivity());
			ResponseHandler<String> rh = (ResponseHandler<String>) params[0];
			Parlamentar parlamentar = (Parlamentar) params[1];
			try {
				parlamentar = parlamentarController.doRequest(rh,
						parlamentar.getId());
			}
			// TODO corrigir tratamento de excessão, deve ser lançado pela
			// controller.
			catch (Exception e) {
				progressDialog.dismiss();
			}
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
		RequestTask task = new RequestTask();
		task.execute(responseHandler, parlamentar);

		return parlamentar;
	}

	private void hideKeyboard() {
		InputMethodManager inputManager = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus()
				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
