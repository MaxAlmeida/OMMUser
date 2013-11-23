package com.OMM.application.user.view;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

import com.OMM.application.user.R;
import com.OMM.application.user.adapters.ParlamentarRankingAdapter;
import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.exceptions.NullParlamentarRankingMaioresException;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.requests.HttpConnection;


import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.widget.Toast;

import android.app.Activity;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;

public class ParlamentarRankingListFragment extends ListFragment {

	private OnParlamentarRankingSelectedListener listener;
	private static ParlamentarUserController controllerParlamentar;
	private ParseTask parseTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
			
			@Override
			public String handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {
				// TODO Auto-generated method stub
				return null;
			}
		};

		super.onCreate(savedInstanceState);

		controllerParlamentar = ParlamentarUserController
				.getInstance(getActivity());

		setHasOptionsMenu(true);

		ParlamentarUserController controllerParlamentar = ParlamentarUserController
				.getInstance(getActivity());

		List<Parlamentar> list = null;
		try {
			list = controllerParlamentar
					.doRequestMajorRanking(responseHandler);
		} catch (NullParlamentarRankingMaioresException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ParlamentarRankingAdapter adapter = new ParlamentarRankingAdapter(getActivity(),
				R.layout.fragment_ranking, list);

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

		private ParlamentarRankingListFragment fragment;

		public void setFragment(ParlamentarRankingListFragment fragment) {
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
	public interface OnParlamentarRankingSelectedListener {
		public void OnParlamentarSelected(Parlamentar parlamentar);
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

	public void updateDetail(Parlamentar parlamentar) {
		parlamentar = startRequest(parlamentar);
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
				parlamentar = (Parlamentar) parlamentarController.doRequestMajorRanking(rh);
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
