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
	private static final int OFFICE = 1;
	private static final int FUEL = 3;
	private static final int TECHNICAL_WORK_AND_CONSULTORY = 4;
	private static final int DISCLOSURE_OF_PARLIAMENTARY_ACTIVITY = 5;
	private static final int SECURITY = 8;
	private static final int CARGO_AIRCRAFT = 9;
	private static final int PHONE = 10;
	private static final int POST_SERVICES = 11;
	private static final int PUBLICATIONS_SIGNATURE = 12;
	private static final int FEED = 13;
	private static final int LODGING = 14;
	private static final int LEASE_OF_VEHICLES = 15;
	private static final int ISSUANCE_OF_AIR_TICKETS = 999;

	ParlamentarUserController parlamentarController;
	private int selectedMonth = 1;
	private int selectedYear = 2013;
	private String month = "Janeiro";
	

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
			setBars();
		}
	}
	
	public void setBars() {

		DecimalFormat valorCotaDecimalFormat = new DecimalFormat("#,###.00");
		double totalValue = 0;
		resetBars();

		TextView view = (TextView) getView().findViewById(R.id.nome);
		view.setText(parlamentarController.getParlamentar().getNome());
		view = (TextView) getView().findViewById(R.id.partido);
		view.setText(parlamentarController.getParlamentar().getPartido());
		view = (TextView) getView().findViewById(R.id.uf);
		view.setText(parlamentarController.getParlamentar().getUf());
		view = (TextView) getView().findViewById(R.id.pos);
		formatRankingPos(view);
		TextView textMes = (TextView) getView().findViewById(R.id.mes_e_ano);
		textMes.setText( month +"/"+ selectedYear);

		if (parlamentarController.getParlamentar().getIsSeguido() == 1) {
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

			if ((cota.getMes() == selectedMonth)
					&& (cota.getAno() == selectedYear)) {
				double valorCota = cota.getValor();
				int numeroSubCota = cota.getNumeroSubCota();
				switch (numeroSubCota) {

				case PUBLICATIONS_SIGNATURE:
					hasSubcota = true;
					valorSubcota = valorCota;

				case OFFICE:
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

				case FUEL:

					ImageView barCombustivel = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_gasolina);
					TextView textViewCombustivel = (TextView) getActivity()
							.findViewById(R.id.valor_cota_gasolina);
					textViewCombustivel.setText("R$ "
							+ valorCotaDecimalFormat.format(valorCota));
					sizeBar(barCombustivel, valorCota);
					break;

				case TECHNICAL_WORK_AND_CONSULTORY:

					ImageView barTrabalhoTecnico = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_trabalho_tecnico);
					TextView textViewTrabalhoTecnico = (TextView) getActivity()
							.findViewById(R.id.valor_cota_trabalho_tecnico);
					textViewTrabalhoTecnico.setText("R$ "
							+ valorCotaDecimalFormat.format(valorCota));
					sizeBar(barTrabalhoTecnico, valorCota);

					break;

				case DISCLOSURE_OF_PARLIAMENTARY_ACTIVITY:

					ImageView barDivulgacao = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_divulgacao);
					TextView textViewDivulgacao = (TextView) getActivity()
							.findViewById(R.id.valor_cota_divulgacao);
					textViewDivulgacao.setText("R$ "
							+ valorCotaDecimalFormat.format(valorCota));
					sizeBar(barDivulgacao, valorCota);

					break;

				case SECURITY:

					ImageView barSeguranca = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_seguranca);
					TextView textViewSeguranca = (TextView) getActivity()
							.findViewById(R.id.valor_cota_seguranca);
					textViewSeguranca.setText("R$ "
							+ valorCotaDecimalFormat.format(valorCota));

					sizeBar(barSeguranca, valorCota);
					break;

				case LEASE_OF_VEHICLES:
					hasSubcota = true;
					valorSubcota = valorCota;

				case CARGO_AIRCRAFT:
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

				case PHONE:

					ImageView barTelefonia = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_telefonia);
					TextView textViewTelefonia = (TextView) getActivity()
							.findViewById(R.id.valor_cota_telefonia);
					textViewTelefonia.setText("R$ "
							+ valorCotaDecimalFormat.format(valorCota));
					sizeBar(barTelefonia, valorCota);

					break;

				case POST_SERVICES:

					ImageView barCorreios = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_correios);
					TextView textViewCorreios = (TextView) getActivity()
							.findViewById(R.id.valor_cota_correios);
					textViewCorreios.setText("R$ "
							+ valorCotaDecimalFormat.format(valorCota));
					sizeBar(barCorreios, valorCota);

					break;

				case FEED:

					ImageView barAlimentacao = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_alimentacao);
					TextView textViewAlimentacao = (TextView) getActivity()
							.findViewById(R.id.valor_cota_alimentacao);
					textViewAlimentacao.setText("R$ "
							+ valorCotaDecimalFormat.format(valorCota));
					sizeBar(barAlimentacao, valorCota);

					break;

				case LODGING:

					ImageView barHospedagem = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_hoespedagem);
					TextView textViewHospedagam = (TextView) getActivity()
							.findViewById(R.id.valor_cota_hospedagem);
					textViewHospedagam.setText("R$ "
							+ valorCotaDecimalFormat.format(valorCota));
					sizeBar(barHospedagem, valorCota);

					break;

				case ISSUANCE_OF_AIR_TICKETS:

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

	public void sizeBar(ImageView bar, double cotaValue) {

		if (cotaValue <= UPPER_LIMIT_WHITE_BAR) {

			bar.setImageResource(R.drawable.barra_branca);

		} else if (cotaValue <= UPPER_LIMIT_GREEN_BAR) {

			bar.setImageResource(R.drawable.barra_verde);

		} else if (cotaValue <= UPPER_LIMIT_YELLOW_BAR) {

			bar.setImageResource(R.drawable.barra_amarela);

		} else if (cotaValue <= UPPER_LIMIT_ORANGE_BAR) {

			bar.setImageResource(R.drawable.barra_laranja);

		} else if (cotaValue > LOWER_LIMIT_RED_BAR) {

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
			selectedMonth = 1;
			month = "Janeiro";
			break;

		case Menu.FIRST + 1:
			selectedMonth = 2;
			month = "Fevereiro";
			break;

		case Menu.FIRST + 2:
			selectedMonth = 3;
			month = "Março";
			break;

		case Menu.FIRST + 3:
			selectedMonth = 4;
			month = "Abril";
			break;

		case Menu.FIRST + 4:
			selectedMonth = 5;
			month = "Maio";
			break;

		case Menu.FIRST + 5:
			selectedMonth = 6;
			month = "Junho";
			break;

		case Menu.FIRST + 6:
			selectedMonth = 7;
			month = "Julho";
			break;

		case Menu.FIRST + 7:
			selectedMonth = 8;
			month = "Agosto";
			break;

		case Menu.FIRST + 8:
			selectedMonth = 9;
			month = "Setembro";
			break;

		case Menu.FIRST + 9:
			selectedMonth = 10;
			month = "Outubro";
			break;

		case Menu.FIRST + 10:
			selectedMonth = 11;
			month = "Novembro";
			break;

		case Menu.FIRST + 11:
			selectedMonth = 12;
			month = "Dezembro";
			break;

		default:
			
			selectedYear = item.getItemId();
		    break;
		}

		setBars();
		return true;
	}

	public void resetBars() {

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