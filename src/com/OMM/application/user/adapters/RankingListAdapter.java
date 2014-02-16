package com.OMM.application.user.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.OMM.application.user.R;
import com.OMM.application.user.model.Parlamentar;

public class RankingListAdapter extends ArrayAdapter<Parlamentar> {

	private Context context;
	private List<Parlamentar> parlamentares;

	public RankingListAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);

		this.context = context;
	}
}
