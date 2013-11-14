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
import com.OMM.application.user.dao.ParlamentarUserDao;
import com.OMM.application.user.model.Parlamentar;

public class ParlamentarSeguidoListFragment extends ListFragment {

	private OnParlamentarSeguidoSelectedListener listener;

	ParseTask parseTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		ParlamentarUserDao dao = new ParlamentarUserDao(getActivity());
		// TODO construir chamada dao parlamentares seguidos
		List<Parlamentar> list = dao.getAll();

		ParlamentarSeguidoAdapter adapter = new ParlamentarSeguidoAdapter(
				getActivity(), R.layout.fragment_parlamentar_seguido, list);

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

		private ParlamentarSeguidoListFragment fragment;

		public void setFragment(ParlamentarSeguidoListFragment fragment) {
			this.fragment = fragment;
		}

		@Override
		protected List<Parlamentar> doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			// Aqui vai aparecer a chamda da controller.
			return null;
		}

		@Override
		protected void onPostExecute(List<Parlamentar> result) {

			fragment.setListContent(result);

		}
	}

	public void updateListContent() {

		if (parseTask == null) {
			parseTask = new ParseTask();
			parseTask.setFragment(this);
		}
	}

	public void setListContent(List<Parlamentar> result) {

//		ArrayAdapter listAdapter = (ArrayAdapter) getListAdapter();
//		listAdapter.clear();
//		listAdapter.addAll(result);
//		parseTask.setFragment(null);

	}

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

	public void updateDetail(Parlamentar parlamentar) {

		listener.OnParlamentarSeguidoSelected(parlamentar);
	}

}
