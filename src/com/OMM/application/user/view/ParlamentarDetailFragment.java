package com.OMM.application.user.view;

import java.text.DecimalFormat;
import java.util.Iterator;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.OMM.application.user.R;
import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.model.CotaParlamentar;

public class ParlamentarDetailFragment extends Fragment {

	private static final String EMPTY = "R$ 0,00";

	// Limits used to modify the bars of expenses
	private static final int UPPER_LIMIT_WHITE_BAR = 500;
	private static final int UPPER_LIMIT_GREEN_BAR = 1500;
	private static final int UPPER_LIMIT_YELLOW_BAR = 3000;
	private static final int UPPER_LIMIT_ORANGE_BAR = 5000;
	private static final int LOWER_LIMIT_RED_BAR = 5000;

	// NumeroSubCota's values ​​coming from database
	private static final int ESCRITORIO = 1;
	private static final int COMBUSTIVEL = 3;
	private static final int TRABALHO_TECNICO_E_CONSULTORIA = 4;
	private static final int DIVULGACAO_ATIVIDADE_PARLAMENTAR = 5;
	private static final int SEGURANCA = 8;
	private static final int FRETE_AVIAO = 9;
	private static final int TELEFONIA = 10;
	private static final int SERVICOS_POSTAIS = 11;
	private static final int ASSINATURA_DE_PUBLICACOES = 12;
	private static final int ALIMENTACAO = 13;
	private static final int HOSPEDAGEM = 14;
	private static final int LOCACAO_DE_VEICULOS = 15;
	private static final int EMISSAO_BILHETES_AEREOS = 999;

	ParlamentarUserController parlamentarController;
	private int selectedMes = 1;
	private int selectedAno = 2013;
	private String mes = "Janeiro";
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		setHasOptionsMenu(true);
		parlamentarController = ParlamentarUserController
				.getInstance(getActivity());
		View view = inflater.inflate(R.layout.gui_detalhe, container, false);
		createButtons(view);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		if(this.isVisible()){
			setBarras();
		}
	}
	
	public void setBarras() {

		DecimalFormat valorCotaDecimalFormat = new DecimalFormat("#,###.00");
		double totalValue = 0;
		resetBarras();

		TextView view = (TextView) getView().findViewById(R.id.nome);
		view.setText(parlamentarController.getParlamentar().getNome());
		view = (TextView) getView().findViewById(R.id.partido);
		view.setText(parlamentarController.getParlamentar().getPartido());
		view = (TextView) getView().findViewById(R.id.uf);
		view.setText(parlamentarController.getParlamentar().getUf());
		view = (TextView) getView().findViewById(R.id.pos);
		formatRankingPos(view);
		TextView textMes = (TextView) getView().findViewById(R.id.mes_e_ano);
		textMes.setText( mes +"/"+ selectedAno);

		if (parlamentarController.getParlamentar().isSeguido() == 1) {
			ImageView imgView = (ImageView) getView().findViewById(R.id.foto);
			imgView.setImageResource(R.drawable.parlamentar_seguido_foto);
		} else {
			// Nothing here.
		}

		double valorSubcota = 0;
		boolean hasSubcota = false;
		Iterator<CotaParlamentar> iterator = parlamentarController
				.getParlamentar().getCotas().iterator();

		while (iterator.hasNext()) {

			CotaParlamentar cota = iterator.next();

			if ((cota.getMes() == selectedMes)
					&& (cota.getAno() == selectedAno)) {
				double valorCota = cota.getValor();
				int numeroSubCota = cota.getNumeroSubCota();
				switch (numeroSubCota) {

				case ASSINATURA_DE_PUBLICACOES:
					hasSubcota = true;
					valorSubcota = valorCota;

				case ESCRITORIO:
					valorCota += valorSubcota;
					if (hasSubcota == true && valorSubcota != 0) {
						valorCota -= valorSubcota;
						totalValue -= valorCota;
						valorSubcota = 0;
						hasSubcota = false;
					}
					ImageView barEscritorio = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_escritorio);
					TextView textViewEscritorio = (TextView) getActivity()
							.findViewById(R.id.valor_cota_escritorio);
					textViewEscritorio.setText("R$ "
							+ valorCotaDecimalFormat.format(valorCota));
					sizeBar(barEscritorio, valorCota);
					break;

				case COMBUSTIVEL:

					ImageView barCombustivel = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_gasolina);
					TextView textViewCombustivel = (TextView) getActivity()
							.findViewById(R.id.valor_cota_gasolina);
					textViewCombustivel.setText("R$ "
							+ valorCotaDecimalFormat.format(valorCota));
					sizeBar(barCombustivel, valorCota);
					break;

				case TRABALHO_TECNICO_E_CONSULTORIA:

					ImageView barTrabalhoTecnico = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_trabalho_tecnico);
					TextView textViewTrabalhoTecnico = (TextView) getActivity()
							.findViewById(R.id.valor_cota_trabalho_tecnico);
					textViewTrabalhoTecnico.setText("R$ "
							+ valorCotaDecimalFormat.format(valorCota));
					sizeBar(barTrabalhoTecnico, valorCota);

					break;

				case DIVULGACAO_ATIVIDADE_PARLAMENTAR:

					ImageView barDivulgacao = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_divulgacao);
					TextView textViewDivulgacao = (TextView) getActivity()
							.findViewById(R.id.valor_cota_divulgacao);
					textViewDivulgacao.setText("R$ "
							+ valorCotaDecimalFormat.format(valorCota));
					sizeBar(barDivulgacao, valorCota);

					break;

				case SEGURANCA:

					ImageView barSeguranca = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_seguranca);
					TextView textViewSeguranca = (TextView) getActivity()
							.findViewById(R.id.valor_cota_seguranca);
					textViewSeguranca.setText("R$ "
							+ valorCotaDecimalFormat.format(valorCota));

					sizeBar(barSeguranca, valorCota);
					break;

				case LOCACAO_DE_VEICULOS:
					hasSubcota = true;
					valorSubcota = valorCota;

				case FRETE_AVIAO:
					valorCota += valorSubcota;
					if (hasSubcota == true && valorSubcota != 0) {
						valorCota -= valorSubcota;
						totalValue -= valorCota;
						valorSubcota = 0;
						hasSubcota = false;
					}
					ImageView barAluguelAviao = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_aluguel_aviao);
					TextView textViewAluguelAviao = (TextView) getActivity()
							.findViewById(R.id.valor_cota_aluguel_aviao);
					textViewAluguelAviao.setText("R$ "
							+ valorCotaDecimalFormat.format(valorCota));
					sizeBar(barAluguelAviao, valorCota);
					
					break;

				case TELEFONIA:

					ImageView barTelefonia = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_telefonia);
					TextView textViewTelefonia = (TextView) getActivity()
							.findViewById(R.id.valor_cota_telefonia);
					textViewTelefonia.setText("R$ "
							+ valorCotaDecimalFormat.format(valorCota));
					sizeBar(barTelefonia, valorCota);

					break;

				case SERVICOS_POSTAIS:

					ImageView barCorreios = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_correios);
					TextView textViewCorreios = (TextView) getActivity()
							.findViewById(R.id.valor_cota_correios);
					textViewCorreios.setText("R$ "
							+ valorCotaDecimalFormat.format(valorCota));
					sizeBar(barCorreios, valorCota);

					break;

				case ALIMENTACAO:

					ImageView barAlimentacao = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_alimentacao);
					TextView textViewAlimentacao = (TextView) getActivity()
							.findViewById(R.id.valor_cota_alimentacao);
					textViewAlimentacao.setText("R$ "
							+ valorCotaDecimalFormat.format(valorCota));
					sizeBar(barAlimentacao, valorCota);

					break;

				case HOSPEDAGEM:

					ImageView barHospedagem = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_hoespedagem);
					TextView textViewHospedagam = (TextView) getActivity()
							.findViewById(R.id.valor_cota_hospedagem);
					textViewHospedagam.setText("R$ "
							+ valorCotaDecimalFormat.format(valorCota));
					sizeBar(barHospedagem, valorCota);

					break;

				case EMISSAO_BILHETES_AEREOS:

					ImageView barBilhetesAereos = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_bilhetes_aereos);
					TextView textViewBilhetesAereos = (TextView) getActivity()
							.findViewById(R.id.valor_cota_bilhetes_aereos);
					textViewBilhetesAereos.setText("R$ "
							+ valorCotaDecimalFormat.format(valorCota));
					sizeBar(barBilhetesAereos, valorCota);

					break;

				}
				totalValue += valorCota;

			}

		}
		TextView total = (TextView) getActivity().findViewById(R.id.total);
		total.setText("Total: R$ " + valorCotaDecimalFormat.format(totalValue));

	}

	private void formatRankingPos(TextView view) {
		int pos = parlamentarController.getParlamentar().getMajorRankingPos();
		if (pos < 10) {
			view.setText("00"
					+ parlamentarController.getParlamentar()
							.getMajorRankingPos());
		} else if (pos < 100) {
			view.setText("0"
					+ parlamentarController.getParlamentar()
							.getMajorRankingPos());
		} else {
			view.setText(""
					+ parlamentarController.getParlamentar()
							.getMajorRankingPos());
		}
	}

	public void sizeBar(ImageView bar, double valorCota) {

		if (valorCota <= UPPER_LIMIT_WHITE_BAR) {

			bar.setImageResource(R.drawable.barra_branca);

		} else if (valorCota <= UPPER_LIMIT_GREEN_BAR) {

			bar.setImageResource(R.drawable.barra_verde);

		} else if (valorCota <= UPPER_LIMIT_YELLOW_BAR) {

			bar.setImageResource(R.drawable.barra_amarela);

		} else if (valorCota <= UPPER_LIMIT_ORANGE_BAR) {

			bar.setImageResource(R.drawable.barra_laranja);

		} else if (valorCota > LOWER_LIMIT_RED_BAR) {

			bar.setImageResource(R.drawable.barra_vermelha);

		} else {

			// Nothing should be done
		}

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

		btn_cota_divulgacao.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(),
						"Divulgação da Atividade Parlamentar",
						Toast.LENGTH_SHORT).show();

			}
		});

		btn_cota_escritorio.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(),
						"Manutenção de Escritório de Apoio",
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
				Toast.makeText(getActivity(), "Hospedagem", Toast.LENGTH_SHORT)
						.show();

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
				Toast.makeText(getActivity(), "Telefonia", Toast.LENGTH_SHORT)
						.show();

			}
		});

		btn_cota_trabalho_tecnico
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Toast.makeText(
								getActivity(),
								"Consultorias, Pesquisas e Trabalhos Técnicos",
								Toast.LENGTH_SHORT).show();

					}
				});

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

		super.onCreateOptionsMenu(menu, inflater);
		menu.clear();
		SubMenu sub = menu.addSubMenu("Mês");
		SubMenu ano = menu.addSubMenu("Ano");
		//if (parlamentarController.getParlamentar().getIsSeguido() == 1) {
			MenuItem esquecer = menu.add(0,Menu.FIRST + 12, 0, "Esquecer");
			esquecer.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		//} else {
			MenuItem seguir = menu.add(0, Menu.FIRST + 13, 0, "Seguir");
			seguir.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
			if(parlamentarController.getParlamentar().isSeguido() == 1){
				seguir.setVisible(false);
			}
			else {
				esquecer.setVisible(false);
			}
		//}
		sub.add(0, Menu.FIRST, 0, "Janeiro");
		sub.add(0, Menu.FIRST + 1, 0, "Fevereiro");
		sub.add(0, Menu.FIRST + 2, 0, "Março");
		sub.add(0, Menu.FIRST + 3, 0, "Abril");
		sub.add(0, Menu.FIRST + 4, 0, "Maio");
		sub.add(0, Menu.FIRST + 5, 0, "Junho");
		sub.add(0, Menu.FIRST + 6, 0, "Julho");
		sub.add(0, Menu.FIRST + 7, 0, "Agosto");
		sub.add(0, Menu.FIRST + 8, 0, "Setembro");
		sub.add(0, Menu.FIRST + 9, 0, "Outubro");
		sub.add(0, Menu.FIRST + 10, 0, "Novembro");
		sub.add(0, Menu.FIRST + 11, 0, "Dezembro");

		Iterator<CotaParlamentar> iterator = parlamentarController
				.getParlamentar().getCotas().iterator();

		int anoMaior = 2010;
		while (iterator.hasNext()) {
			
			CotaParlamentar cota = iterator.next();

			if (anoMaior < cota.getAno()){
				anoMaior = cota.getAno();

			ano.add(0, anoMaior, 0, Integer.toString(anoMaior));
			
			}
		}
	}

	public boolean onOptionsItemSelected(MenuItem item) {
        
        
		switch (item.getItemId()) {
		case 0:
		case Menu.FIRST:
			selectedMes = 1;
			mes = "Janeiro";
			break;

		case Menu.FIRST + 1:
			selectedMes = 2;
			mes = "Fevereiro";
			break;

		case Menu.FIRST + 2:
			selectedMes = 3;
			mes = "Março";
			break;

		case Menu.FIRST + 3:
			selectedMes = 4;
			mes = "Abril";
			break;

		case Menu.FIRST + 4:
			selectedMes = 5;
			mes = "Maio";
			break;

		case Menu.FIRST + 5:
			selectedMes = 6;
			mes = "Junho";
			break;

		case Menu.FIRST + 6:
			selectedMes = 7;
			mes = "Julho";
			break;

		case Menu.FIRST + 7:
			selectedMes = 8;
			mes = "Agosto";
			break;

		case Menu.FIRST + 8:
			selectedMes = 9;
			mes = "Setembro";
			break;

		case Menu.FIRST + 9:
			selectedMes = 10;
			mes = "Outubro";
			break;

		case Menu.FIRST + 10:
			selectedMes = 11;
			mes = "Novembro";
			break;

		case Menu.FIRST + 11:
			selectedMes = 12;
			mes = "Dezembro";
			break;
			
		case Menu.FIRST +12:
			try {
				parlamentarController.unfollowedParlamentar();
				Toast.makeText(getActivity(), "Parlamentar desseguido",
						Toast.LENGTH_SHORT).show();
				
				ImageView imgView = (ImageView) getView().findViewById(
						R.id.foto);
				imgView.setImageResource(R.drawable.parlamentar_foto);
				
				getActivity().invalidateOptionsMenu();
				
			} catch (NullParlamentarException nullEx) {
				Toast.makeText(getActivity(), "Erro na requisição",
						Toast.LENGTH_SHORT).show();
			} catch (NullCotaParlamentarException e) {
				Toast.makeText(getActivity(), "Erro na requisição",
						Toast.LENGTH_SHORT).show();
			}
          break;
          
		case Menu.FIRST + 13:
			try {
				parlamentarController.followedParlamentar();
				Toast.makeText(getActivity(), "Parlamentar Seguido",
						Toast.LENGTH_SHORT).show();

				ImageView imgView = (ImageView) getView().findViewById(
						R.id.foto);
				imgView.setImageResource(R.drawable.parlamentar_seguido_foto);
				
				getActivity().invalidateOptionsMenu();

			} catch (NullParlamentarException nullEx) {
				Toast.makeText(getActivity(), "Erro na requisição",
						Toast.LENGTH_SHORT).show();
			} catch (NullCotaParlamentarException e) {
				Toast.makeText(getActivity(), "Erro na requisição",
						Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			
			selectedAno = item.getItemId();
		    break;
		}

		setBarras();
		return true;
	}

	public void resetBarras() {

		double valorCota = 0;

		ImageView barEscritorio = (ImageView) getActivity().findViewById(
				R.id.barra_cota_escritorio);
		TextView textViewEscritorio = (TextView) getActivity().findViewById(
				R.id.valor_cota_escritorio);
		textViewEscritorio.setText(EMPTY);
		sizeBar(barEscritorio, valorCota);

		ImageView barCombustivel = (ImageView) getActivity().findViewById(
				R.id.barra_cota_gasolina);
		TextView textViewCombustivel = (TextView) getActivity().findViewById(
				R.id.valor_cota_gasolina);
		textViewCombustivel.setText(EMPTY);
		sizeBar(barCombustivel, valorCota);

		ImageView barTrabalhoTecnico = (ImageView) getActivity().findViewById(
				R.id.barra_cota_trabalho_tecnico);
		TextView textViewTrabalhoTecnico = (TextView) getActivity()
				.findViewById(R.id.valor_cota_trabalho_tecnico);
		textViewTrabalhoTecnico.setText(EMPTY);
		sizeBar(barTrabalhoTecnico, valorCota);

		ImageView barDivulgacao = (ImageView) getActivity().findViewById(
				R.id.barra_cota_divulgacao);
		TextView textViewDivulgacao = (TextView) getActivity().findViewById(
				R.id.valor_cota_divulgacao);
		textViewDivulgacao.setText(EMPTY);
		sizeBar(barDivulgacao, valorCota);

		ImageView barSeguranca = (ImageView) getActivity().findViewById(
				R.id.barra_cota_seguranca);
		TextView textViewSeguranca = (TextView) getActivity().findViewById(
				R.id.valor_cota_seguranca);
		textViewSeguranca.setText(EMPTY);
		sizeBar(barSeguranca, valorCota);

		ImageView barAluguelAviao = (ImageView) getActivity().findViewById(
				R.id.barra_cota_aluguel_aviao);
		TextView textViewAluguelAviao = (TextView) getActivity().findViewById(
				R.id.valor_cota_aluguel_aviao);
		textViewAluguelAviao.setText(EMPTY);
		sizeBar(barAluguelAviao, valorCota);

		ImageView barTelefonia = (ImageView) getActivity().findViewById(
				R.id.barra_cota_telefonia);
		TextView textViewTelefonia = (TextView) getActivity().findViewById(
				R.id.valor_cota_telefonia);
		textViewTelefonia.setText(EMPTY);
		sizeBar(barTelefonia, valorCota);

		ImageView barCorreios = (ImageView) getActivity().findViewById(
				R.id.barra_cota_correios);
		TextView textViewCorreios = (TextView) getActivity().findViewById(
				R.id.valor_cota_correios);
		textViewCorreios.setText(EMPTY);
		sizeBar(barCorreios, valorCota);

		ImageView barAlimentacao = (ImageView) getActivity().findViewById(
				R.id.barra_cota_alimentacao);
		TextView textViewAlimentacao = (TextView) getActivity().findViewById(
				R.id.valor_cota_alimentacao);
		textViewAlimentacao.setText(EMPTY);
		sizeBar(barAlimentacao, valorCota);

		ImageView barHospedagem = (ImageView) getActivity().findViewById(
				R.id.barra_cota_hoespedagem);
		TextView textViewHospedagam = (TextView) getActivity().findViewById(
				R.id.valor_cota_hospedagem);
		textViewHospedagam.setText(EMPTY);
		sizeBar(barHospedagem, valorCota);

		ImageView barBilhetesAereos = (ImageView) getActivity().findViewById(
				R.id.barra_cota_bilhetes_aereos);
		TextView textViewBilhetesAereos = (TextView) getActivity()
				.findViewById(R.id.valor_cota_bilhetes_aereos);
		textViewBilhetesAereos.setText(EMPTY);
		sizeBar(barBilhetesAereos, valorCota);
	}
}