package com.OMM.application.user.view;

import java.util.List;

import org.apache.http.client.ResponseHandler;

import android.app.Activity;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.OMM.application.user.R;
import com.OMM.application.user.adapters.ParlamentarRankingAdapter;
import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.exceptions.ConnectionFailedException;
import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.RequestFailedException;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.requests.HttpConnection;

public class MajorRankingListFragment extends ListFragment {

	private OnParlamentarRankingSelectedListener listener;
	private static ParlamentarUserController controllerParlamentar;
	private ParseTask parseTask = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		controllerParlamentar = ParlamentarUserController
				.getInstance(getActivity());

		startRankingRequest();
		setHasOptionsMenu(true);

		super.onCreate(savedInstanceState);

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

		private MajorRankingListFragment fragment;

		private void setFragment(MajorRankingListFragment fragment) {
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setListContent(List result) {
		ArrayAdapter listAdapter = (ArrayAdapter) getListAdapter();
		
		listAdapter.clear();
		listAdapter.addAll(result);
		
		parseTask.setFragment(null);
		parseTask = null;
	}
	
	private void updateListContent(String inputText) {

		if (parseTask == null) {
			parseTask = new ParseTask();
			parseTask.setFragment(this);
		}
		try {
			parseTask.execute(inputText);
		}
		// TODO: tratar com a devida excessão lançada
		catch (IllegalStateException ise) {
			// IllegalStateException
		}
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

	private class RankingRequestTask extends AsyncTask<Object, Void, Integer> {
		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(getActivity(), "Aguarde...",
					"Buscando Dados");
		}

		@Override
		protected Integer doInBackground(Object... params) {

			Integer exception = Alerts.NO_EXCEPTIONS;
			
			@SuppressWarnings("unchecked")
			ResponseHandler<String> rh = (ResponseHandler<String>) params[0];
			try {
				controllerParlamentar.doRequestMajorRanking(rh);
				
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

		@SuppressWarnings({ "rawtypes", "unchecked" })
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

	private void startRankingRequest() {

		ResponseHandler<String> responseHandler = HttpConnection
				.getResponseHandler();
		
		RankingRequestTask task = new RankingRequestTask();
		task.execute(responseHandler);
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

				Alerts.conectionFailedAlert(getActivity());
				break;

			case Alerts.NULL_PARLAMENTAR_EXCEPTION:

				Alerts.parlamentarFailedAlert(getActivity());
				break;

			case Alerts.NULL_COTA_PARLAMENTAR_EXCEPTION:

				Alerts.cotaParlamentarFailedAlert(getActivity());
				break;

			case Alerts.REQUEST_FAILED_EXCEPTION:

				Alerts.requestFailedAlert(getActivity());
				break;

			case Alerts.UNEXPECTED_FAILED_EXCEPTION:

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
}
