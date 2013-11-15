package com.OMM.application.user.view;

import com.OMM.application.user.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ParlamentarDetailFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gui_detalhe,
				container, false);
		return view;
	}


	public void setText(String nome) {
		TextView view = (TextView) getView().findViewById(R.id.nome);
		view.setText(nome);
	}
	
}