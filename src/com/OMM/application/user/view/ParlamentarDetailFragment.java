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
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.model.CotaParlamentar;

public class ParlamentarDetailFragment extends Fragment {
	
	private static final String EMPTY = "R$ 0,00";
	
	// Limits used to modify the bars of expenses
	private static final int UPPER_LIMIT_WHITE_BAR = 500; 
	private static final int  UPPER_LIMIT_GREEN_BAR = 1500;
	private static final int  UPPER_LIMIT_YELLOW_BAR = 3000;
	private static final int  UPPER_LIMIT_ORANGE_BAR = 5000;
	private static final int  LOWER_LIMIT_RED_BAR = 5000;
	
	//NumeroSubCota's values ​​coming from database
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
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		parlamentarController = ParlamentarUserController
				.getInstance(getActivity());
		View view = inflater.inflate(R.layout.gui_detalhe, container, false);

		final Button btn_detalhe_seguir = (Button) view
				.findViewById(R.id.btn_detalhe_seguir);
		final Button btn_detalhe_desseguir = (Button) view
				.findViewById(R.id.btn_detalhe_desseguir);

		if(parlamentarController.getParlamentar().getIsSeguido() == 1) {
		btn_detalhe_seguir.setVisibility(View.INVISIBLE);
		btn_detalhe_desseguir.setVisibility(View.VISIBLE);

		} else {
		 btn_detalhe_desseguir.setVisibility(View.INVISIBLE);
		 btn_detalhe_seguir.setVisibility(View.VISIBLE);
		}

		createButtons(view);

		btn_detalhe_desseguir.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					parlamentarController.getParlamentar().setSeguido(0);
					parlamentarController.unFollowedParlamentar();
					Toast.makeText(getActivity(), "Parlamentar DesSeguido",
							Toast.LENGTH_SHORT).show();
					btn_detalhe_desseguir.setVisibility(View.GONE);
					btn_detalhe_seguir.setVisibility(View.VISIBLE);
					
					ImageView imgView = (ImageView)getView().findViewById(R.id.foto);
					imgView.setImageResource(R.drawable.parlamentar_foto);
					
				} catch (NullParlamentarException nullEx) {
					Toast.makeText(getActivity(), "Erro na requisição",
							Toast.LENGTH_SHORT).show();

				}
			}
		});
		
		btn_detalhe_seguir.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					parlamentarController.getParlamentar().setSeguido(1);
					parlamentarController.followedParlamentar();
					Toast.makeText(getActivity(), "Parlamentar Seguido",
							Toast.LENGTH_SHORT).show();
					
					ImageView imgView = (ImageView)getView().findViewById(R.id.foto);
					imgView.setImageResource(R.drawable.parlamentar_seguido_foto);
					btn_detalhe_seguir.setVisibility(View.GONE);
					btn_detalhe_desseguir.setVisibility(View.VISIBLE);

				} catch (NullParlamentarException nullEx) {
					Toast.makeText(getActivity(), "Erro na requisição",
							Toast.LENGTH_SHORT).show();

				}
			}
		});
		return view;
	}

	public void setBarras() {

		DecimalFormat valorCotaDecimalFormat = new DecimalFormat("#,###.00");
		double totalValue = 0.0;
		resetBarras();
		
		TextView view = (TextView) getView().findViewById(R.id.nome);
		view.setText(parlamentarController.getParlamentar().getNome());
		view = (TextView) getView().findViewById(R.id.partido);
		view.setText(parlamentarController.getParlamentar().getPartido());
		view = (TextView) getView().findViewById(R.id.uf);
		view.setText(parlamentarController.getParlamentar().getUf());
		TextView textMes = (TextView) getView().findViewById(R.id.mes);
		textMes.setText("Valores do mês "+ selectedMes);
		
		if(parlamentarController.getParlamentar().getIsSeguido()==1){
			ImageView imgView = (ImageView)getView().findViewById(R.id.foto);
			imgView.setImageResource(R.drawable.parlamentar_seguido_foto);
		}
		else{
			//Nothing here.
		}
		
		double valorSubcotaRevista = 0;
		double valorSubcotaFrete = 0;
		Iterator<CotaParlamentar> iterator = parlamentarController
				.getParlamentar().getCotas().iterator();
		
		while (iterator.hasNext()) {
			
			CotaParlamentar cota = iterator.next();
			
			if (cota.getMes() == selectedMes) {
				double valorCota = cota.getValor();
				int numeroSubCota = cota.getNumeroSubCota();
				
				switch (numeroSubCota) {

				case ESCRITORIO:
					valorCota += valorSubcotaRevista;
					ImageView barEscritorio = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_escritorio);
					TextView textViewEscritorio = (TextView) getActivity()
							.findViewById(R.id.valor_cota_escritorio);
					textViewEscritorio.setText("R$ "
							+ valorCotaDecimalFormat.format(valorCota));
					valorSubcotaRevista = valorCota;
					
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

				case FRETE_AVIAO:
					valorCota += valorSubcotaFrete; 
					ImageView barAluguelAviao = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_aluguel_aviao);
					TextView textViewAluguelAviao = (TextView) getActivity()
							.findViewById(R.id.valor_cota_aluguel_aviao);
					textViewAluguelAviao.setText("R$ "
							+ valorCotaDecimalFormat.format(valorCota));
					valorSubcotaFrete = valorCota;
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
				
				case ASSINATURA_DE_PUBLICACOES:
					valorCota+=valorSubcotaRevista;
					ImageView barEscritorio2 = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_escritorio);
					TextView textViewEscritorio2 = (TextView) getActivity()
							.findViewById(R.id.valor_cota_escritorio);
					textViewEscritorio2.setText("R$ "
							+ valorCotaDecimalFormat.format(valorCota));
					valorSubcotaRevista = valorCota;
					sizeBar(barEscritorio2, valorCota);

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

				case LOCACAO_DE_VEICULOS:
					valorCota += valorSubcotaFrete; 
					ImageView barAluguelAviao2 = (ImageView) getActivity()
							.findViewById(R.id.barra_cota_aluguel_aviao);
					TextView textViewAluguelAviao2 = (TextView) getActivity()
							.findViewById(R.id.valor_cota_aluguel_aviao);
					textViewAluguelAviao2.setText("R$ "
							+ valorCotaDecimalFormat.format(valorCota));
					valorSubcotaFrete = valorCota;
					sizeBar(barAluguelAviao2, valorCota);

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

				default:

				}
				
				totalValue += valorCota;
				
			}

		}
		TextView total = (TextView) getActivity().findViewById(R.id.total);
		total.setText("Total: R$ "+valorCotaDecimalFormat.format(totalValue));

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
			
			//Nothing should be done
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
				Toast.makeText(getActivity(), "Alimentação", Toast.LENGTH_SHORT)
						.show();

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
						"Manutenção de Escritório de Apoio", Toast.LENGTH_SHORT)
						.show();

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
						Toast.makeText(getActivity(),
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
		@SuppressWarnings( "unused" )
		MenuItem Ano = menu.add(0,Menu.FIRST+12,0,"Ano");
		sub.add(0,Menu.FIRST,0,"Janeiro");
		sub.add(0,Menu.FIRST+1,0,"Fevereiro");
		sub.add(0,Menu.FIRST+2,0,"Março");
		sub.add(0,Menu.FIRST+3,0,"Abril");
		sub.add(0,Menu.FIRST+4,0,"Maio");
		sub.add(0,Menu.FIRST+5,0,"Junho");
		sub.add(0,Menu.FIRST+6,0,"Julho");
		sub.add(0,Menu.FIRST+7,0,"Agosto");
		sub.add(0,Menu.FIRST+8,0,"Setembro");
		sub.add(0,Menu.FIRST+9,0,"Outubro");
		sub.add(0,Menu.FIRST+10,0,"Novembro");
		sub.add(0,Menu.FIRST+11,0,"Dezembro");
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		case Menu.FIRST:
			selectedMes = 1;
			
			break;
		case Menu.FIRST+1: 
		    selectedMes = 2;
		
		    break;
		case Menu.FIRST+2: 
		    selectedMes = 3;
		
		    break;
		case Menu.FIRST+3: 
		    selectedMes = 4;
		
		    break;
		case Menu.FIRST+4: 
		    selectedMes = 5;
		
		    break;
		case Menu.FIRST+5: 
		    selectedMes = 6;
		
		    break;
		case Menu.FIRST+6: 
		    selectedMes = 7;
		
		    break;
		case Menu.FIRST+7: 
		    selectedMes = 8;
		
		    break;
		case Menu.FIRST+8: 
		    selectedMes = 9;
		
		    break;
		    
		case Menu.FIRST+9: 
		    selectedMes = 10;
		
		    break;   
		    
		case Menu.FIRST+10: 
		    selectedMes = 11;
		
		    break; 
		
		case Menu.FIRST+11: 
		    selectedMes = 12;
		
		    break;      
		    
		default:
			break;
		}
	setBarras();
	return true;
	}
	
	public void resetBarras(){
		double valorCota = 0;
		ImageView barEscritorio = (ImageView) getActivity()
				.findViewById(R.id.barra_cota_escritorio);
		TextView textViewEscritorio = (TextView) getActivity()
				.findViewById(R.id.valor_cota_escritorio);
		textViewEscritorio.setText(EMPTY);
		sizeBar(barEscritorio, valorCota);

		ImageView barCombustivel = (ImageView) getActivity()
				.findViewById(R.id.barra_cota_gasolina);
		TextView textViewCombustivel = (TextView) getActivity()
				.findViewById(R.id.valor_cota_gasolina);
		textViewCombustivel.setText(EMPTY);
		sizeBar(barCombustivel, valorCota);

		ImageView barTrabalhoTecnico = (ImageView) getActivity()
				.findViewById(R.id.barra_cota_trabalho_tecnico);
		TextView textViewTrabalhoTecnico = (TextView) getActivity()
				.findViewById(R.id.valor_cota_trabalho_tecnico);
		textViewTrabalhoTecnico.setText(EMPTY);
		sizeBar(barTrabalhoTecnico, valorCota);

		ImageView barDivulgacao = (ImageView) getActivity()
				.findViewById(R.id.barra_cota_divulgacao);
		TextView textViewDivulgacao = (TextView) getActivity()
				.findViewById(R.id.valor_cota_divulgacao);
		textViewDivulgacao.setText(EMPTY);
		sizeBar(barDivulgacao, valorCota);

		ImageView barSeguranca = (ImageView) getActivity()
				.findViewById(R.id.barra_cota_seguranca);
		TextView textViewSeguranca = (TextView) getActivity()
				.findViewById(R.id.valor_cota_seguranca);
		textViewSeguranca.setText(EMPTY);
		sizeBar(barSeguranca, valorCota);

		ImageView barAluguelAviao = (ImageView) getActivity()
				.findViewById(R.id.barra_cota_aluguel_aviao);
		TextView textViewAluguelAviao = (TextView) getActivity()
				.findViewById(R.id.valor_cota_aluguel_aviao);
		textViewAluguelAviao.setText(EMPTY);
		sizeBar(barAluguelAviao, valorCota);

		ImageView barTelefonia = (ImageView) getActivity()
				.findViewById(R.id.barra_cota_telefonia);
		TextView textViewTelefonia = (TextView) getActivity()
				.findViewById(R.id.valor_cota_telefonia);
		textViewTelefonia.setText(EMPTY);
		sizeBar(barTelefonia, valorCota);

		ImageView barCorreios = (ImageView) getActivity()
				.findViewById(R.id.barra_cota_correios);
		TextView textViewCorreios = (TextView) getActivity()
				.findViewById(R.id.valor_cota_correios);
		textViewCorreios.setText(EMPTY);
		sizeBar(barCorreios, valorCota);

		ImageView barAlimentacao = (ImageView) getActivity()
				.findViewById(R.id.barra_cota_alimentacao);
		TextView textViewAlimentacao = (TextView) getActivity()
				.findViewById(R.id.valor_cota_alimentacao);
		textViewAlimentacao.setText(EMPTY);
		sizeBar(barAlimentacao, valorCota);

		ImageView barHospedagem = (ImageView) getActivity()
				.findViewById(R.id.barra_cota_hoespedagem);
		TextView textViewHospedagam = (TextView) getActivity()
				.findViewById(R.id.valor_cota_hospedagem);
		textViewHospedagam.setText(EMPTY);
		sizeBar(barHospedagem, valorCota);

		ImageView barBilhetesAereos = (ImageView) getActivity()
				.findViewById(R.id.barra_cota_bilhetes_aereos);
		TextView textViewBilhetesAereos = (TextView) getActivity()
				.findViewById(R.id.valor_cota_bilhetes_aereos);
		textViewBilhetesAereos.setText(EMPTY);
		sizeBar(barBilhetesAereos, valorCota);
	}
}