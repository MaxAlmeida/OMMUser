package com.OMM.application.user.view;

import java.util.List;

import com.OMM.application.user.R;
import com.OMM.application.user.adapters.ParlamentarRankingAdapter;
import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.view.ParlamentarSeguidoListFragment.OnParlamentarSeguidoSelectedListener;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class ParlamentarMenorListFragment extends ListFragment {

	private OnParlamentarMenorSelectedListener listener;
	private static ParlamentarUserController parlamentarController;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		parlamentarController = ParlamentarUserController
				.getInstance(getActivity());
		List<Parlamentar> list = parlamentarController.getMinor();

		ParlamentarRankingAdapter adapter = new ParlamentarRankingAdapter(
				getActivity(), R.layout.fragment_ranking, list);

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

	public interface OnParlamentarMenorSelectedListener {
		public void OnParlamentarMenorSelected();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof OnParlamentarMenorSelectedListener) {
			listener = (OnParlamentarMenorSelectedListener) activity;
		} else {
			throw new ClassCastException(
					activity.toString()
							+ "must implement ParlamentarMenorListFragment.OnParlamentarSelectedListner");
		}
	}

	private void updateDetail() {
		listener.OnParlamentarMenorSelected();
	}

}
