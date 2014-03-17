package com.OMM.application.user.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.OMM.application.user.R;

public class GuiMain extends Activity
		implements
		ParlamentarSeguidoListFragment.OnParlamentarSeguidoSelectedListener,
		ParlamentarListFragment.OnParlamentarSelectedListener,
		ParlamentarMajorRankingListFragment.OnParlamentarMajorSelectedListener,
		ParlamentarMinorRankingListFragment.OnParlamentarMinorSelectedListener {

	private static final String PARLAMENTARES_FOLLOWED_MESSAGE = "Parlamentares Seguidos";
	private static final String SEARCH_PARLAMENTAR_MESSAGE = "Pesquisar Parlamentar";
	private static final String RANKINGS_MESSAGE = "Rankings entre parlamentares";
	private static final int FOLLOWED_PARLAMENTARES_BUTTON_ANGLE = 30;
	private static final int LIST_ALL_PARLAMENTARES_BUTTON_ANGLE = 0;
	private static final int RANKINGS_BUTTON_ANGLE = -30;

	
	private static FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gui_main);
		fragmentManager = this.getFragmentManager();

		if (findViewById(R.id.fragment_container) != null) {
			ParlamentarSeguidoListFragment fragment = new ParlamentarSeguidoListFragment();
			fragmentManager.beginTransaction()
					.replace(R.id.fragment_container, fragment).commit();
		}

		final Button btn_about_application_main = (Button) findViewById(R.id.btn_sobre_main);
		final Button btn_parlamentar_main = (Button) findViewById(R.id.btn_politico_main);
		final Button btn_search_parlamentar = (Button) findViewById(R.id.btn_pesquisar_parlamentar);
		final Button btn_ranking_main = (Button) findViewById(R.id.btn_ranking);
		final Button btn_show_all_parlamentares = (Button) findViewById(R.id.btn_ic_rolagem);

		btn_about_application_main
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						startActivity(new Intent(getBaseContext(),
								GuiAbout.class));
					}
				});

		btn_parlamentar_main.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ParlamentarSeguidoListFragment listFragment = new ParlamentarSeguidoListFragment();
				updateFragment(R.id.fragment_container,listFragment);
				Toast.makeText(getBaseContext(),
						PARLAMENTARES_FOLLOWED_MESSAGE, Toast.LENGTH_SHORT)
						.show();
				btn_show_all_parlamentares
						.setRotation(FOLLOWED_PARLAMENTARES_BUTTON_ANGLE);
			}
		});

		btn_search_parlamentar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ParlamentarListFragment listFragment = new ParlamentarListFragment();
				updateFragment(R.id.fragment_container,listFragment);
				Toast.makeText(getBaseContext(), SEARCH_PARLAMENTAR_MESSAGE,
						Toast.LENGTH_SHORT).show();
				btn_show_all_parlamentares
						.setRotation(LIST_ALL_PARLAMENTARES_BUTTON_ANGLE);
			}
		});

		btn_ranking_main.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				AllRankingsFragment rankingListFragment = new AllRankingsFragment();
				updateFragment(R.id.fragment_container, rankingListFragment);

				Toast.makeText(getBaseContext(), RANKINGS_MESSAGE,
						Toast.LENGTH_SHORT).show();
				btn_show_all_parlamentares.setRotation(RANKINGS_BUTTON_ANGLE);
			}
		});

		btn_show_all_parlamentares
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						ImageView img_esconder = (ImageView) findViewById(R.id.icon_esconder);
						// changing the buttons's visibility
						if (btn_search_parlamentar.getAlpha() == 0.0f) {

							btn_search_parlamentar.animate().alpha(1.0f);
							btn_parlamentar_main.animate().alpha(1.0f);
							btn_ranking_main.animate().alpha(1.0f);
							btn_about_application_main.animate().alpha(1.0f);

							btn_show_all_parlamentares.animate().scaleX(1.0f);
							btn_show_all_parlamentares.animate().scaleY(1.0f);
							btn_show_all_parlamentares.animate().alpha(1.0f);

							img_esconder.animate().alpha(1.0f);
							img_esconder.animate().scaleX(1.0f);
							img_esconder.animate().scaleY(1.0f);
						} else {

							btn_search_parlamentar.animate().alpha(0.0f);
							btn_parlamentar_main.animate().alpha(0.0f);
							btn_ranking_main.animate().alpha(0.0f);
							btn_about_application_main.animate().alpha(0.0f);

							btn_show_all_parlamentares.animate().scaleX(0.6f);
							btn_show_all_parlamentares.animate().scaleY(0.6f);
							btn_show_all_parlamentares.animate().alpha(0.5f);

							img_esconder.animate().scaleX(0.6f);
							img_esconder.animate().scaleY(0.6f);
							img_esconder.animate().alpha(0.0f);
						}
					}
				});
	}

	private void updateFragment(int viewId, Fragment fragment) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(viewId, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
		getFragmentManager().executePendingTransactions();
	}

	@Override
	public void OnParlamentarSeguidoSelected() {

		ParlamentarDetailFragment parlamentarDetail = new ParlamentarDetailFragment();
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

			updateFragment(R.id.fragment_container, parlamentarDetail);

		} else {
			updateFragment(R.id.detail_fragment_container, parlamentarDetail);
		}
	}

	@Override
	public void OnParlamentarSelected() {

		ParlamentarDetailFragment parlamentarDetail = new ParlamentarDetailFragment();
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			updateFragment(R.id.fragment_container, parlamentarDetail);

		} else {
			updateFragment(R.id.detail_fragment_container, parlamentarDetail);
		}
	}
	
	@Override
	public void OnParlamentarMajorSelected() {

		ParlamentarDetailFragment parlamentarDetail = new ParlamentarDetailFragment();
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			updateFragment(R.id.fragment_container, parlamentarDetail);

		} else {
			updateFragment(R.id.detail_fragment_container, parlamentarDetail);
		}
	}

	@Override
	public void OnParlamentarMinorSelected() {

		ParlamentarDetailFragment detailFragment = new ParlamentarDetailFragment();
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			updateFragment(R.id.fragment_container, detailFragment);

		} else {
			updateFragment(R.id.detail_fragment_container, detailFragment);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;
	}

	@Override
	public void onBackPressed() {

		new AlertDialog.Builder(this).setTitle("Sair")
				.setMessage("Tem certeza que deseja sair?")
				.setNegativeButton("Cancelar", null)
				.setPositiveButton("Ok", new OnClickListener() {

					public void onClick(DialogInterface arg0, int arg1) {
						GuiMain.super.onBackPressed();
					}
				}).create().show();
	}
}