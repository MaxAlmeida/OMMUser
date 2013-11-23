package com.OMM.application.user.adapters;

import android.widget.ArrayAdapter;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.OMM.application.user.R;
import com.OMM.application.user.model.Parlamentar;

public class ParlamentarRankingAdapter extends ArrayAdapter<Parlamentar> {

	
	private Context context;
	private List<Parlamentar> parlamentares;
	
	public ParlamentarRankingAdapter(Context context, int textViewResourceId,
			List<Parlamentar> parlamentares) {
		super(context, textViewResourceId, parlamentares);

		this.context = context;
		this.parlamentares = parlamentares;
		
		
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View view = inflater.inflate(R.layout.fragment_ranking, null);
		TextView textView = (TextView) view.findViewById(R.id.parlamentarlistfragment_txt_nome);
		textView.setText(parlamentares.get(position).getNome());
		return view;
	}
}

	
	

}
