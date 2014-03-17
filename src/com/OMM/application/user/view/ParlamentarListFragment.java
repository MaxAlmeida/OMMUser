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

		private void setFragment(ParlamentarListFragment fragment) {
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

	private void updateListContent(String inputText) {

		if (parseTask == null) {
			parseTask = new ParseTask();
			parseTask.setFragment(this);
		}
		try {
			parseTask.execute(inputText);
		}
		catch (IllegalStateException ise) {
			// IllegalStateException
		}
	}

	private void setListContent(List<Parlamentar> result) {
		ParlamentarAdapter listAdapter = (ParlamentarAdapter) getListAdapter();
		listAdapter.clear();
		listAdapter.addAll(result);
		listAdapter.notifyDataSetChanged();
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

	private void updateDetail() {
		if (controllerParlamentar.getParlamentar().getIsSeguido() == 1) {
			controllerParlamentar.getSelected();
			listener.OnParlamentarSelected();
		} else {
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
				query = filter(query, 0, query.length());
				updateListContent(query);
				hideKeyboard();
				return true;
			}

			@Override
			public boolean onQueryTextChange(String query) {
				query = filter(query, 0, query.length());
				updateListContent(query);

				return false;
			}
		});
	}

	private String filter(String source, int start, int end) {

		StringBuilder sb = new StringBuilder(end - start);
		for (int i = start; i < end; i++) {
			char c = source.charAt(i);
			if (isCharAllowed(c)) // put your condition here
				sb.append(c);
			else {
				// do nothing here.
			}
		}
		String sp = new String(sb);
		return sp;
	}

	private boolean isCharAllowed(char c) {
		return Character.isLetterOrDigit(c) || Character.isSpaceChar(c);
	}

	private class RequestTask extends AsyncTask<Object, Void, Integer> {

		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(getActivity(), "Aguarde...",
					"Buscando Dados");
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Integer doInBackground(Object... params) {

			Integer result = null;
			ParlamentarUserController parlamentarController = ParlamentarUserController
					.getInstance(getActivity());

			ResponseHandler<String> rh = (ResponseHandler<String>) params[0];
			try {
				parlamentarController.doRequest(rh);
				result = Alerts.NO_EXCEPTIONS;
			} catch (ConnectionFailedException cfe) {
				result = Alerts.CONNECTION_FAILED_EXCEPTION;

			} catch (RequestFailedException rfe) {
				result = Alerts.REQUEST_FAILED_EXCEPTION;

			} catch (NullParlamentarException npe) {
				result = Alerts.NULL_PARLAMENTAR_EXCEPTION;

			} catch (NullCotaParlamentarException ncpe) {
				result = Alerts.NULL_COTA_PARLAMENTAR_EXCEPTION;

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

			case Alerts.CONNECTION_FAILED_EXCEPTION:

				Alerts.conectionFailedAlert(getActivity(), null);
				break;

			case Alerts.NULL_PARLAMENTAR_EXCEPTION:

				Alerts.parlamentarFailedAlert(getActivity(), null);
				break;

			case Alerts.NULL_COTA_PARLAMENTAR_EXCEPTION:

				Alerts.cotaParlamentarFailedAlert(getActivity(), null);
				break;

			case Alerts.REQUEST_FAILED_EXCEPTION:

				Alerts.requestFailedAlert(getActivity(), null);
				break;

			// case Alerts.UNEXPECTED_FAILED_EXCEPTION:
			//
			// Alerts.unexpectedFailedAlert(getActivity(), null);
			// break;

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
