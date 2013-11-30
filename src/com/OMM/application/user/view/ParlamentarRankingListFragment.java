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
import com.OMM.application.user.adapters.ParlamentarRankingAdapter;
import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.exceptions.ConnectionFailedException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.RequestFailedException;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.requests.HttpConnection;

public class ParlamentarRankingListFragment extends ListFragment {

	private OnParlamentarRankingSelectedListener listener;
	private static ParlamentarUserController controllerParlamentar;
	private ParseTask parseTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		controllerParlamentar = ParlamentarUserController
				.getInstance(getActivity());
		@SuppressWarnings( "unused" )
		ResponseHandler<String> responseHandler = HttpConnection
				.getResponseHandler();

		startRequest();

		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
		ParlamentarRankingAdapter adapter = new ParlamentarRankingAdapter(
				getActivity(), R.layout.fragment_ranking,
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

		private ParlamentarRankingListFragment fragment;

		public void setFragment(ParlamentarRankingListFragment fragment) {
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

	public interface OnParlamentarRankingSelectedListener {
		public void OnParlamentarRankingSelected();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof OnParlamentarRankingSelectedListener) {
			listener = (OnParlamentarRankingSelectedListener) activity;
		} else {
			throw new ClassCastException(
					activity.toString()
							+ "must implement ParlamentarRankingListFragment.OnParlamentarSelectedListner");
		}
	}

	public void updateDetail() {
		startRequest();
	}

	private class RequestTask extends AsyncTask<Object, Void, Integer> {
		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(getActivity(), "Aguarde...",
					"Buscando Dados");
		}

		@Override
		protected Integer doInBackground(Object... params) {
			
			Integer exception = Alerts.NO_EXCEPTIONS;
			@SuppressWarnings( "unchecked" )
			ResponseHandler<String> rh = (ResponseHandler<String>) params[0];
			try {
				controllerParlamentar.doRequestMajorRanking(rh);
			}catch (ConnectionFailedException cfe) {
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

		@Override
		protected void onPostExecute(Integer result) {

			progressDialog.dismiss();
			switch (result) {

				case Alerts.CONNECTION_FAILED_EXCEPTION:

					Alerts.conectionFailedAlert(getActivity());
					break;

				case Alerts.NULL_PARLAMENTAR_EXCEPTION:

					Alerts.parlamentarFailedAlert(getActivity());
					break;

				case Alerts.REQUEST_FAILED_EXCEPTION:

					Alerts.requestFailedAlert(getActivity());
					break;

				case Alerts.UNEXPECTED_FAILED_EXCEPTION:

					Alerts.unexpectedFailedAlert(getActivity());
					break;

				default:
					ArrayAdapter listAdapter = (ArrayAdapter) getListAdapter();
					listAdapter.clear();
					listAdapter.addAll(controllerParlamentar.getParlamentares());
				}
			
		}
	}

	private void startRequest() {

		ResponseHandler<String> responseHandler = HttpConnection
				.getResponseHandler();
		RequestTask task = new RequestTask();
		task.execute(responseHandler);
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

	private void hideKeyboard() {
		InputMethodManager inputManager = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus()
				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
