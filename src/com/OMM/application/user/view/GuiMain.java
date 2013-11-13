package com.OMM.application.user.view;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.OMM.application.user.R;

public class GuiMain extends Activity implements
		ParlamentarSeguidoListFragment.OnParlamentarSeguidoSelectedListener,
		ParlamentarListFragment.OnParlamentarSelectedListener {

	private static final String SEGUIDOS = "Parlamentares Seguidos";
	private static final String PESQUISA = "Pesquisar Parlamentar";
	private static final String RANKINGS = "Rankings entre parlamentares";

	private static FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gui_main);
		fragmentManager = this.getFragmentManager();

		/*
		 * //inicializa o banco e cria se ele nao existir ParlamentarUserDao dao
		 * = new ParlamentarUserDao(getBaseContext()); ParlamentarPO po=new
		 * ParlamentarPO("001","Ramon Cruz da silva");
		 * po.setCod_parlamentar("002"); po.setNome_parlamentar("Tiririca");
		 * dao.insert(po); po.setCod_parlamentar("003");
		 * po.setNome_parlamentar("Romario brilha muito no curintias");
		 * dao.insert(po); po.setCod_parlamentar("004");
		 * po.setNome_parlamentar("Popo"); dao.insert(po);
		 * po.setCod_parlamentar("005"); po.setNome_parlamentar("Arruda");
		 * dao.insert(po);
		 */

		if (findViewById(R.id.fragment_container) != null) {

			/* cria a primeira lista */
			ParlamentarSeguidoListFragment fragment = new ParlamentarSeguidoListFragment();
			fragmentManager.beginTransaction()
					.replace(R.id.fragment_container, fragment).commit();
		}

		/*
		 * Criando um banco sqlite na forma mais simples sem as boas praticas de
		 * programa��o
		 * 
		 * SQLiteDatabase db=
		 * openOrCreateDatabase("devmedia.db",Context.MODE_PRIVATE,null);
		 * 
		 * /*esse banco criado fica na pasta databese da aplica��o e pode ser
		 * acessado usando o android Explorer do eclipse note que a cria��o do
		 * banco esta sendo manual. Detalhe: Se voce nao colocar o comando IF
		 * NOT EXISTS no inicio do comando CREATE TABLE o aplicativo executara
		 * apenas uma vez e dar� um erro na segunda execucao, isso acontence
		 * porque jah existe um banco de dados com a tabela no aparelho celular
		 * e nao eh necessario criar a tabela novamente, o problema deste medoto
		 * eh o acoplamento, desta forma vc acopla a interface direto com o
		 * banco de dados e isso n�o eh uma boa coisa , a manutencao fica muito
		 * complicado, entao vamos usar os padroes de projeto ..nesse caso
		 * usaremos o padrao DAO. comece criando uma classe para tratar do bando
		 * que extende da classe SQLiteOpenHelper
		 * 
		 * esse parte do c�digo foi substituida pela classe DB, ela que ficarah
		 * responsavel pelo banco de dados
		 * 
		 * 
		 * 
		 * StringBuilder strb= new StringBuilder();
		 * strb.append("CREATE TABLE  IF NOT EXISTS [clientes] (");
		 * strb.append("[ID_CLIENTE] INTEGER PRIMARY KEY AUTOINCREMENT,");
		 * strb.append("  [NOME] VARCHAR(30),");
		 * strb.append("[EMAIL] VARCHAR(40), ");
		 * strb.append(" [ENDERECO] VARCHAR(50),");
		 * strb.append("[NUMERO] VARCHAR(10));"); db.execSQL(strb.toString());
		 */

		/*
		 * come�ando a trabalhar com captura de eventos comece recuperando os
		 * elementos do layout como botoes campos de texto e etc.
		 */

		final Button btn_sobre_main = (Button) findViewById(R.id.btn_sobre_main);
		final Button btn_politico_main = (Button) findViewById(R.id.btn_politico_main);
		final Button btn_pesquisar_parlamentar = (Button) findViewById(R.id.btn_pesquisar_parlamentar);
		final Button btn_ranking_main = (Button) findViewById(R.id.btn_ranking);
		final Button btn_mostra_outros = (Button) findViewById(R.id.btn_ic_rolagem);
		// agora vc deve implementar os metodos de captura de eventos

		btn_sobre_main.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// esse comando chama outra activity
				startActivity(new Intent(getBaseContext(), GuiSobre.class));// corrigir
																			// a
																			// class
			}
		});

		btn_politico_main.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				/* Substitui a lista */
				ParlamentarSeguidoListFragment listFragment = new ParlamentarSeguidoListFragment();
				FragmentTransaction transaction = fragmentManager
						.beginTransaction();
				transaction.replace(R.id.fragment_container, listFragment);
				transaction.commit();
				Toast.makeText(getBaseContext(), SEGUIDOS, Toast.LENGTH_SHORT)
						.show();
			}
		});

		btn_pesquisar_parlamentar
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						/* Substitui a lista */
						ParlamentarListFragment listFragment = new ParlamentarListFragment();
						FragmentTransaction transaction = fragmentManager
								.beginTransaction();
						transaction.replace(R.id.fragment_container,
								listFragment);
						transaction.commit();
						Toast.makeText(getBaseContext(), PESQUISA,
								Toast.LENGTH_SHORT).show();
					}
				});
		btn_ranking_main.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ParlamentarSeguidoListFragment newFragment = new ParlamentarSeguidoListFragment();
				FragmentTransaction transaction = fragmentManager
						.beginTransaction();
				transaction.replace(R.id.fragment_container, newFragment);
				transaction.commit();
				Toast.makeText(getBaseContext(), RANKINGS, Toast.LENGTH_SHORT)
						.show();

			}
		});

		btn_mostra_outros.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				/* Modificando a visibilidade dos botoes */
				if (btn_pesquisar_parlamentar.getVisibility() == View.GONE) {
					btn_pesquisar_parlamentar.setVisibility(View.VISIBLE);
					btn_politico_main.setVisibility(View.VISIBLE);
					btn_ranking_main.setVisibility(View.VISIBLE);
					btn_sobre_main.setVisibility(View.VISIBLE);
					btn_mostra_outros.setScaleX(1.0f);
					btn_mostra_outros.setScaleY(1.0f);
				} else {
					btn_pesquisar_parlamentar.setVisibility(View.GONE);
					btn_politico_main.setVisibility(View.GONE);
					btn_ranking_main.setVisibility(View.GONE);
					btn_sobre_main.setVisibility(View.GONE);
					btn_mostra_outros.setScaleX(0.6f);
					btn_mostra_outros.setScaleY(0.6f);

				}
			}
		});

	}

	@Override
	public void OnParlamentarSeguidoSelected(String nome) {
		/* Substitui o detalhe */
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			ParlamentarDetailFragment detailFragment = new ParlamentarDetailFragment();
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();
			transaction.replace(R.id.fragment_container, detailFragment);
			// transaction.addToBackStack(null);
			transaction.commitAllowingStateLoss();
			getFragmentManager().executePendingTransactions();
			detailFragment.setText(nome);

		} else {
			ParlamentarDetailFragment detailFragment = new ParlamentarDetailFragment();
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();
			transaction.replace(R.id.detail_fragment_container, detailFragment);
			transaction.addToBackStack(null);
			transaction.commit();
			getFragmentManager().executePendingTransactions();
			detailFragment.setText(nome);
		}
	}

	@Override
	public void OnParlamentarSelected(String nome) {
		/* Substitui o detalhe */
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			ParlamentarDetailFragment detailFragment = new ParlamentarDetailFragment();
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();
			transaction.replace(R.id.fragment_container, detailFragment);
			// transaction.addToBackStack(null);
			transaction.commitAllowingStateLoss();
			getFragmentManager().executePendingTransactions();
			detailFragment.setText(nome);

		} else {
			ParlamentarDetailFragment detailFragment = new ParlamentarDetailFragment();
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();
			transaction.replace(R.id.detail_fragment_container, detailFragment);
			transaction.addToBackStack(null);
			transaction.commit();
			getFragmentManager().executePendingTransactions();
			detailFragment.setText(nome);
		}

	}

}
