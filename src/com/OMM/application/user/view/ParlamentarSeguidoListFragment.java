package com.OMM.application.user.view;

import java.util.List;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.OMM.application.user.R;
import com.OMM.application.user.adapters.ParlamentarAdapter;
import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.model.Parlamentar;

public class ParlamentarSeguidoListFragment extends ListFragment {

	private OnParlamentarSeguidoSelectedListener listener;
	private static ParlamentarUserController parlamentarController;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		parlamentarController = ParlamentarUserController
				.getInstance(getActivity());
		List<Parlamentar> list = parlamentarController.getAllSelected();

		ParlamentarAdapter adapter = new ParlamentarAdapter(
				getActivity(), R.layout.fragment_parlamentar_seguido, list);

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

	public interface OnParlamentarSeguidoSelectedListener {
		public void OnParlamentarSeguidoSelected();
	}

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
		listener.OnParlamentarSeguidoSelected();
	}

}
