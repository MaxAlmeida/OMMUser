package com.OMM.application.user.view;

import java.util.List;

import android.app.Activity;
import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.OMM.application.user.R;
import com.OMM.application.user.adapters.ParlamentarSeguidoAdapter;
import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.model.Parlamentar;

public class ParlamentarSeguidoListFragment extends ListFragment {

	private OnParlamentarSeguidoSelectedListener listener;
	private static ParlamentarUserController controllerParlamentar;
	//ParseTask parseTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		controllerParlamentar = ParlamentarUserController
				.getInstance(getActivity());
		List<Parlamentar> list = controllerParlamentar.getAllSelected();

		ParlamentarSeguidoAdapter adapter = new ParlamentarSeguidoAdapter(
				getActivity(), R.layout.fragment_parlamentar_seguido, list);

		setListAdapter(adapter);
		setRetainInstance(false);

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Parlamentar parlamentar = (Parlamentar) getListAdapter().getItem(
				position);
		Toast.makeText(getActivity(), "Toquei!", Toast.LENGTH_SHORT).show();
		updateDetail(parlamentar);

	}
	
	//TODO corigir chamada da controller e bug NullPointer Exception
//	private static class ParseTask extends
//			AsyncTask<String, Void, List<Parlamentar>> {
//
//		private ParlamentarSeguidoListFragment fragment;
//
//		public void setFragment(ParlamentarSeguidoListFragment fragment) {
//			this.fragment = fragment;
//		}
//
//		@Override
//		protected List<Parlamentar> doInBackground(String... params) {
//			List<Parlamentar> result = controllerParlamentar.getSelected(params[0]);
//			return result;
//		}
//
//		@Override
//		protected void onPostExecute(List<Parlamentar> result) {
//
//			fragment.setListContent(result);
//
//		}
//	}
//
//	public void updateListContent(String nome) {
//
//		if (parseTask == null) {
//			parseTask = new ParseTask();
//			parseTask.setFragment(this);
//			parseTask.execute(nome);
//		}
//	}
//
//	public void setListContent(List<Parlamentar> result) {
//
//		// ArrayAdapter listAdapter = (ArrayAdapter) getListAdapter();
//		// listAdapter.clear();
//		// listAdapter.addAll(result);
//		// parseTask.setFragment(null);
//
//	}

	/*
	 * Responsavel por chamar uma activity, a natureza do fragment nao permite q
	 * ele chame activities, enta eh preciso criar uma interface para outra
	 * activity fazer a chamada, logo... a Main faz.
	 */
	public interface OnParlamentarSeguidoSelectedListener {
		public void OnParlamentarSeguidoSelected(Parlamentar parlamentar);
	}

	/*
	 * Faz a chamada para concatenar activities esse metodo so vai funcionar se
	 * a interface anterior for implementada
	 */

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof OnParlamentarSeguidoSelectedListener) {
			listener = (OnParlamentarSeguidoSelectedListener) activity;
		} else {
			throw new ClassCastException(
					activity.toString()
							+ "must implement ParlamentarSeguidoListFragment.OnParlamentarSelectedListner");
		}
	}

	private void updateDetail(Parlamentar parlamentar) {

		listener.OnParlamentarSeguidoSelected(parlamentar);
	}

}
