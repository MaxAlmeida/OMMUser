package com.OMM.application.user.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.OMM.application.user.R;
import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.model.Parlamentar;

public class ParlamentarDetailFragment extends Fragment {

	private Parlamentar parlamentar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gui_detalhe, container, false);

		final Button btn_detalhe_seguir = (Button) view
				.findViewById(R.id.btn_detalhe_seguir);
		final Button btn_detalhe_desseguir = (Button) view
				.findViewById(R.id.btn_detalhe_desseguir);
		btn_detalhe_desseguir.setVisibility(View.INVISIBLE);
		
		createButtons(view);




		btn_detalhe_desseguir.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				ParlamentarUserController parlamentarController;
				parlamentarController = ParlamentarUserController
						.getInstance(getActivity());

				try {
					parlamentar.setSeguido(0);
					parlamentarController.unFollowedParlamentar(parlamentar);
					Toast.makeText(getActivity(), "Parlamentar DesSeguido",
							Toast.LENGTH_SHORT).show();
					btn_detalhe_desseguir.setVisibility(View.GONE);
					btn_detalhe_seguir.setVisibility(View.VISIBLE);

				} catch (NullParlamentarException nullEx) {
					Toast.makeText(getActivity(),
							"Deu pau,compre outro celular", Toast.LENGTH_SHORT)
							.show();

				}
			}
		});

		btn_detalhe_seguir.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				ParlamentarUserController parlamentarController;
				parlamentarController = ParlamentarUserController
						.getInstance(getActivity());

				try {
					parlamentar.setSeguido(1);
					parlamentarController.followedParlamentar(parlamentar);
					Toast.makeText(getActivity(), "Parlamentar Seguido",
							Toast.LENGTH_SHORT).show();
					btn_detalhe_seguir.setVisibility(View.GONE);
					btn_detalhe_desseguir.setVisibility(View.VISIBLE);

				} catch (NullParlamentarException nullEx) {
					Toast.makeText(getActivity(),
							"Deu pau,compre outro celular", Toast.LENGTH_SHORT)
							.show();

				}
			}
		});
		return view;
	}

	public void setBarras(Parlamentar parlamentar) {
		TextView view = (TextView) getView().findViewById(R.id.nome);
		view.setText(parlamentar.getNome());
		this.parlamentar = parlamentar;



	}

	public void createButtons(View view) {
		
		 final Button btn_cota_alimentacao = (Button) view
		 .findViewById(R.id.btn_cota_alimentacao);
		 final Button btn_cota_aluguel_aviao = (Button) view
		 .findViewById(R.id.btn_cota_aluguel_aviao);
		 final Button btn_cota_bilhetes_aereos = (Button) view
		 .findViewById(R.id.btn_cota_bilhetes_aereos);
		 final Button btn_cota_correios = (Button) view
		 .findViewById(R.id.btn_cota_correios);
		 final Button btn_cota_divulgacao = (Button) view
		 .findViewById(R.id.btn_cota_divulgacao);
		 final Button btn_cota_escritorio = (Button) view
		 .findViewById(R.id.btn_cota_escritorio);
		 final Button btn_cota_gasolina = (Button) view
		 .findViewById(R.id.btn_cota_gasolina);
		 final Button btn_cota_hospedagem = (Button) view
		 .findViewById(R.id.btn_cota_hospedagem);
		 final Button btn_cota_seguranca = (Button) view
		 .findViewById(R.id.btn_cota_seguranca);
		 final Button btn_cota_telefone = (Button) view
		 .findViewById(R.id.btn_cota_telefone);
		 final Button btn_cota_trabalho_tecnico = (Button) view
		 .findViewById(R.id.btn_cota_trabalho_tecnico);
		
		
		btn_cota_alimentacao.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Alimentação",
						Toast.LENGTH_SHORT).show();

			}
		});
		
		btn_cota_aluguel_aviao.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Fretamento de Aeronaves",
						Toast.LENGTH_SHORT).show();

			}
		});
		
		btn_cota_bilhetes_aereos.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Bilhetes Aéreos",
						Toast.LENGTH_SHORT).show();

			}
		});
		
		btn_cota_correios.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Serviços Postais",
						Toast.LENGTH_SHORT).show();

			}
		});
		
		btn_cota_correios.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Serviços Postais",
						Toast.LENGTH_SHORT).show();

			}
		});
		
		btn_cota_divulgacao.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Divulgação da Atividade Parlamentar",
						Toast.LENGTH_SHORT).show();

			}
		});
		
		btn_cota_escritorio.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Manutenção de Escritório de Apoio",
						Toast.LENGTH_SHORT).show();

			}
		});
		
		btn_cota_gasolina.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Combustíveis e Lubrificantes",
						Toast.LENGTH_SHORT).show();

			}
		});
		
		btn_cota_hospedagem.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Hospedagem",
						Toast.LENGTH_SHORT).show();

			}
		});
		
		btn_cota_seguranca.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Serviço de Segurança",
						Toast.LENGTH_SHORT).show();

			}
		});
		
		btn_cota_telefone.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Telefonia",
						Toast.LENGTH_SHORT).show();

			}
		});
		
		btn_cota_trabalho_tecnico.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Consultorias, Pesquisas e Trabalhos Técnicos",
						Toast.LENGTH_SHORT).show();

			}
		});
		
		
	
	}

}