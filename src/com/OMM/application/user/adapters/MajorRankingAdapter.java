package com.OMM.application.user.adapters;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.OMM.application.user.R;
import com.OMM.application.user.model.Parlamentar;

public class MajorRankingAdapter extends ArrayAdapter<Parlamentar> {

	private Context context;
	private List<Parlamentar> parlamentares;

	public MajorRankingAdapter(Context context, int textViewResourceId,
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
		TextView textViewNome = (TextView) view
				.findViewById(R.id.parlamentarlistfragment_txt_nome);
		textViewNome.setText(parlamentares.get(position).getNome());
		TextView textViewPartido = (TextView) view
				.findViewById(R.id.parlamentarRankinglistfragment_txt_partido);
		textViewPartido.setText(parlamentares.get(position).getPartido());
		TextView textViewUF = (TextView) view
				.findViewById(R.id.parlamentarRankinglistfragment_txt_uf);
		textViewUF.setText(parlamentares.get(position).getUf());
		TextView textViewValor = (TextView) view
				.findViewById(R.id.parlamentarRankinglistfragment_txt_valor);
		DecimalFormat valor = new DecimalFormat("#,###.00");
		textViewValor.setText(""
				+ valor.format(parlamentares.get(position).getValor()));
		TextView textViewPosicao = (TextView) view
				.findViewById(R.id.parlamentarlistfragment_txt_posicao);
		textViewPosicao.setText("" + (parlamentares.get(position).getRanking() + 1));

		return view;
	}
}
