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
import com.OMM.application.user.adapters.MajorRankingAdapter;
import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.exceptions.ConnectionFailedException;
import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.RequestFailedException;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.requests.HttpConnection;

public class ParlamentarMajorRankingListFragment extends ListFragment {

	private OnParlamentarRankingSelectedListener listener;
	private static ParlamentarUserController parlamentarController;
	ParseTask parseTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		parlamentarController = ParlamentarUserController
				.getInstance(getActivity());

		parlamentarController.getAll();

		super.onCreate(savedInstanceState);

		MajorRankingAdapter adapter = new MajorRankingAdapter(
				getActivity(), R.layout.fragment_ranking,
				parlamentarController.getParlamentares());

		setListAdapter(adapter);
		setRetainInstance(false);

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		parlamentarController.setParlamentar((Parlamentar) getListAdapter()
				.getItem(position));
		updateDetail();

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

	private void updateDetail() {
		startRequest();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setListContent(List result) {
		ArrayAdapter listAdapter = (ArrayAdapter) getListAdapter();
		listAdapter.clear();
		listAdapter.addAll(result);
		parseTask.setFragment(null);
		parseTask = null;
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

	
	private class RequestTask extends AsyncTask<Object, Void, Integer> {

		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(getActivity(), "Aguarde...",
					"Buscando Dados ");
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

			} catch (Exception e) {
				result = Alerts.UNEXPECTED_FAILED_EXCEPTION;

			}
			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			progressDialog.dismiss();

			switch ((Integer) result) {
			case 0:

				listener.OnParlamentarRankingSelected();
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

//			case Alerts.UNEXPECTED_FAILED_EXCEPTION:
//
//				Alerts.unexpectedFailedAlert(getActivity(), null);
//				break;

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

	private void hideKeyboard() {
		InputMethodManager inputManager = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus()
				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	
}
