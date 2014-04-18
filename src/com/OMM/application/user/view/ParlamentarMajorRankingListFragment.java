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
import android.widget.Toast;

import com.OMM.application.user.R;
import com.OMM.application.user.adapters.MajorRankingAdapter;
import com.OMM.application.user.alerts.AlertsFactory;
import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.exceptions.ConnectionFailedException;
import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.RequestFailedException;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.requests.HttpConnection;

public class ParlamentarMajorRankingListFragment extends ListFragment {

	private OnParlamentarSelectedListener listener;
	private static ParlamentarUserController parlamentarController;
	ParseTask parseTask;

	// TODO
	/*
	 * Criar aqui um atributo que vai decidir se a lista da linha 48 vai ser
	 * crescente ou decrescente, esse atributo vai ser add pela classe
	 * AllRankingsFragments .. Na main nao muda nada
	 */
	private String orderRanking = "major";

	public String getOrderRanking() {
		return orderRanking;
	}

	public void setOrderRanking(String orderRanking) {
		this.orderRanking = orderRanking;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		parlamentarController = ParlamentarUserController
				.getInstance(getActivity());

		// Mudar aqui
		List<Parlamentar> list;
		if (orderRanking.equals("major")) {
			list = parlamentarController.getAll();
		} else if (orderRanking.equals("minor")) {
			list = parlamentarController.getMinor();
		} else {
			list = null;
		}

		setHasOptionsMenu(true);
		MajorRankingAdapter adapter = new MajorRankingAdapter(getActivity(),
				R.layout.fragment_ranking, list);
		// TODO
		/*
		 * Apagar
		 */
		Toast.makeText(getActivity(), orderRanking, Toast.LENGTH_SHORT).show();

		setListAdapter(adapter);
		setRetainInstance(false);

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		parlamentarController.setParlamentar((Parlamentar) getListAdapter()
				.getItem(position));
		parlamentarController.getSelected();
		updateDetail();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof OnParlamentarSelectedListener) {
			listener = (OnParlamentarSelectedListener) activity;
		} else {
			throw new ClassCastException(
					activity.toString()
							+ "must implement ParlamentarMajorRankingListFragment.OnParlamentarSelectedListner");
		}
	}

	private void updateDetail() {
		if (parlamentarController.getParlamentar().isSeguido() == 1) {
			parlamentarController.getSelected();
			listener.onParlamentarSelected();
		} else {
			startRequest();
		}
	}

	private static class ParseTask extends AsyncTask<String, Void, Void> {

		private ParlamentarMajorRankingListFragment fragment;

		private void setFragment(ParlamentarMajorRankingListFragment fragment) {
			this.fragment = fragment;
		}

		@Override
		protected Void doInBackground(String... params) {
			parlamentarController.getByName(params[0]);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			fragment.setListContent(parlamentarController.getParlamentares());
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
		// TODO tratar com a devida excess�o lan�ada.
		catch (IllegalStateException ise) {
			// IllegalStateException
		}
	}

	private void setListContent(List<Parlamentar> result) {
		MajorRankingAdapter listAdapter = (MajorRankingAdapter) getListAdapter();
		listAdapter.clear();
		listAdapter.addAll(result);
		parseTask.setFragment(null);
		parseTask = null;
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
			if (isCharAllowed(c)) {
				sb.append(c);
			} else {
				// do nothing here.
			}
		}
		String sp = new String(sb);
		return sp;
	}

	private boolean isCharAllowed(char c) {
		return Character.isLetterOrDigit(c) || Character.isSpaceChar(c);
	}

	private void hideKeyboard() {
		InputMethodManager inputManager = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus()
				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
				result = AlertsFactory.NO_EXCEPTIONS;
			} catch (ConnectionFailedException cfe) {
				result = AlertsFactory.CONNECTION_FAILED_EXCEPTION;

			} catch (RequestFailedException rfe) {
				result = AlertsFactory.REQUEST_FAILED_EXCEPTION;

			} catch (NullParlamentarException npe) {
				result = AlertsFactory.NULL_PARLAMENTARY_EXCEPTION;

			} catch (NullCotaParlamentarException ncpe) {
				result = AlertsFactory.NULL_COTA_PARLAMENTAR_EXCEPTION;

			}

			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			progressDialog.dismiss();

			if (result == AlertsFactory.NO_EXCEPTIONS) {
				listener.onParlamentarSelected();
			} else {
				AlertsFactory alertsFactory = AlertsFactory
						.getInstance(getActivity());
				alertsFactory.createAlert(result, null, null);
			}
		}
	}

	private void startRequest() {

		ResponseHandler<String> responseHandler = HttpConnection
				.getResponseHandler();

		RequestTask task = new RequestTask();
		task.execute(responseHandler);

	}
}
