package com.OMM.application.user.view;

import com.OMM.application.user.R;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RankingListFragment extends Fragment {
	
	private static FragmentManager fragmentManager;

	private View majorRankingView = getActivity().findViewById(R.id.majorRanking_list);
	private View minorRankingView = getActivity().findViewById(R.id.minorRanking_list);

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_list_rankings,
				container, false);
		
		fragmentManager = this.getFragmentManager();
		
		majorRankingView.setOnClickListener(majorRankingListListener);
		minorRankingView.setOnClickListener(minorRankingListListener);

		return view;
	}
	
	View.OnClickListener majorRankingListListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {

			ParlamentarRankingListFragment parlamentarRankingListFragment = new ParlamentarRankingListFragment();
			
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			transaction.replace(R.id.fragment_container, parlamentarRankingListFragment);
			transaction.commit();
		}
	};
	
	View.OnClickListener minorRankingListListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
	}; 
}
