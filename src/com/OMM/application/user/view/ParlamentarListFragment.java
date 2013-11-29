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
import com.OMM.application.user.R;
import com.OMM.application.user.adapters.ParlamentarAdapter;
import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.exceptions.ConnectionFailedException;
import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.RequestFailedException;
import com.OMM.application.user.view.Alerts;
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
		controllerParlamentar.getAll();
		setHasOptionsMenu(true);
		ParlamentarAdapter adapter = new ParlamentarAdapter(getActivity(),
				R.layout.fragment_parlamentar,
				controllerParlamentar.getParlamentares());
		setListAdapter(adapter);
		setRetainInstance(false);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		controllerParlamentar.setParlamentar((Parlamentar) getListAdapter()
				.getItem(position));
		updateDetail();
	}

	private static class ParseTask extends AsyncTask<String, Void, Void> {

		private ParlamentarListFragment fragment;

		public void setFragment(ParlamentarListFragment fragment) {
			this.fragment = fragment;
		}

		@Override
		protected Void doInBackground(String... params) {
			controllerParlamentar.getByName(params[0]);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			fragment.setListContent(controllerParlamentar.getParlamentares());
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

	public void setListContent(List result) {
		ArrayAdapter listAdapter = (ArrayAdapter) getListAdapter();
		listAdapter.clear();
		listAdapter.addAll(result);
		parseTask.setFragment(null);
		parseTask = null;
	}

	public interface OnParlamentarSelectedListener {
		public void OnParlamentarSelected();
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

	public void updateDetail() {
		if(controllerParlamentar.getParlamentar().getIsSeguido()==1){
			controllerParlamentar.getSelected();
			listener.OnParlamentarSelected();
		}
		else{
			startRequest();
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

		super.onCreateOptionsMenu(menu, inflater);
		menu.clear();
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

	private class RequestTask extends AsyncTask<Object, Void, Integer> {
		ProgressDialog progressDialog;
		Integer exception = 0;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(getActivity(), "Aguarde...",
					"Buscando Dados");
		}

		@Override
		protected Integer doInBackground(Object... params) {

			Integer result = null;
			ParlamentarUserController parlamentarController = ParlamentarUserController
					.getInstance(getActivity());
			ResponseHandler<String> rh = (ResponseHandler<String>) params[0];
			try {
				parlamentarController.doRequest(rh);
				result = 0;
			} catch (ConnectionFailedException cfe) {

				// TODO: Fazer constantes para retirar números mágicos
				exception = 1;
				result = exception;

			} catch (NullParlamentarException npe) {

				exception = 2;
				result = exception;
			} catch (NullCotaParlamentarException ncpe) {

				exception = 3;
				result = exception;
			} catch (RequestFailedException rfe) {

				exception = 4;
				result = exception;

			} catch (Exception e) {
				exception = 5;
				result = exception;

			}
			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			progressDialog.dismiss();

			switch ((Integer) result) {
			case 0:

				listener.OnParlamentarSelected();
				break;

			case 1:

				Alerts.conectionFailedAlert(getActivity());
				break;

			case 2:

				Alerts.parlamentarFailedAlert(getActivity());
				break;

			case 3:

				Alerts.cotaParlamentarFailedAlert(getActivity());
				break;

			case 4:

				Alerts.requestFailedAlert(getActivity());
				break;

			case 5:

				Alerts.unexpectedFailedAlert(getActivity());
				break;

			default:

				// Nothing should be done
			}
		}
	}

	private void startRequest() {

		ResponseHandler<String> responseHandler = HttpConnection
				.getResponseHandler();
		RequestTask task = new RequestTask();
		task.execute(responseHandler);

	}

	private void hideKeyboard() {
		InputMethodManager inputManager = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus()
				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
