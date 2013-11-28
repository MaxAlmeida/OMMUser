package com.OMM.application.user.view;

import java.util.List;
import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.OMM.application.user.R;
import com.OMM.application.user.adapters.ParlamentarSeguidoAdapter;
import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.model.Parlamentar;

public class ParlamentarSeguidoListFragment extends ListFragment {

	private OnParlamentarSeguidoSelectedListener listener;
	private static ParlamentarUserController parlamentarController;
	//ParseTask parseTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		parlamentarController = ParlamentarUserController
				.getInstance(getActivity());
		List<Parlamentar> list = parlamentarController.getAllSelected();

		ParlamentarSeguidoAdapter adapter = new ParlamentarSeguidoAdapter(
				getActivity(), R.layout.fragment_parlamentar_seguido, list);

		setListAdapter(adapter);
		setRetainInstance(false);

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		parlamentarController.setParlamentar((Parlamentar) getListAdapter().getItem(
				position));
		parlamentarController.getSelected();
		updateDetail();
	}
	/*
	 * Responsavel por chamar uma activity, a natureza do fragment nao permite q
	 * ele chame activities, enta eh preciso criar uma interface para outra
	 * activity fazer a chamada, logo... a Main faz.
	 */
	public interface OnParlamentarSeguidoSelectedListener {
		public void OnParlamentarSeguidoSelected();
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

	private void updateDetail() {
//		RequestTask	request = new RequestTask();
//		request.execute();
		listener.OnParlamentarSeguidoSelected();
	}

}
