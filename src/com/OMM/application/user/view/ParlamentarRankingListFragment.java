package com.OMM.application.user.view;

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

public class ParlamentarRankingListFragment extends ListFragment {

	private OnParlamentarRankingSelectedListener listener;
	private static ParlamentarUserController controllerParlamentar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		controllerParlamentar = ParlamentarUserController
				.getInstance(getActivity());

		startRankingRequest();

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

	public interface OnParlamentarRankingSelectedListener {
		public void OnParlamentarSelected();
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
		ParlamentarRequest parlamentarRequest = new ParlamentarRequest(getActivity());
		parlamentarRequest.startRequest();
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
}
