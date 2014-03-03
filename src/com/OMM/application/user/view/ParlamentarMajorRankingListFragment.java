package com.OMM.application.user.view;

import java.util.List;

import com.OMM.application.user.R;
import com.OMM.application.user.adapters.MajorRankingAdapter;
import com.OMM.application.user.adapters.ParlamentarMinorAdapter;
import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.model.Parlamentar;

import android.app.Activity;
import android.app.ListFragment;
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

public class ParlamentarMajorRankingListFragment extends ListFragment {

	private OnParlamentarMajorSelectedListener listener;
	private static ParlamentarUserController parlamentarController;
	ParseTask parseTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		parlamentarController = ParlamentarUserController
				.getInstance(getActivity());
		List<Parlamentar> list = parlamentarController.getAll();
		setHasOptionsMenu(true);
		MajorRankingAdapter adapter = new MajorRankingAdapter(
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

	public interface OnParlamentarMajorSelectedListener {
		public void OnParlamentarMajorSelected();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof OnParlamentarMajorSelectedListener) {
			listener = (OnParlamentarMajorSelectedListener) activity;
		} else {
			throw new ClassCastException(
					activity.toString()
							+ "must implement ParlamentarMajorRankingListFragment.OnParlamentarSelectedListner");
		}
	}

	private void updateDetail() {
		listener.OnParlamentarMajorSelected();
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
