package com.OMM.application.user.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.OMM.application.user.R;

public class AllRankingsFragment extends Fragment {

	private static FragmentManager fragmentManager;

	private View majorRankingView;
	private View minorRankingView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_list_rankings,
				container, false);

		fragmentManager = this.getFragmentManager();

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		majorRankingView = getActivity().findViewById(R.id.majorRanking_list);
		minorRankingView = getActivity().findViewById(R.id.minorRanking_list);

		majorRankingView.setClickable(true);
		majorRankingView.setOnClickListener(majorRankingListListener);
		minorRankingView.setClickable(true);
		minorRankingView.setOnClickListener(minorRankingListListener);
	}

	View.OnClickListener majorRankingListListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			
			ParlamentarMajorRankingListFragment parlamentarRankingListFragment = new ParlamentarMajorRankingListFragment();

			parlamentarRankingListFragment.setOrderRanking("major");
			
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();
			transaction.replace(R.id.fragment_container,
					parlamentarRankingListFragment);
			transaction.commit();
		}
	};

	View.OnClickListener minorRankingListListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			ParlamentarMajorRankingListFragment parlamentarMinor = new ParlamentarMajorRankingListFragment();
	
			parlamentarMinor.setOrderRanking("minor");
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();
			transaction.replace(R.id.fragment_container, parlamentarMinor);
			transaction.commit();

		}
	};
}
