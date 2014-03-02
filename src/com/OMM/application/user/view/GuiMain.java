package com.OMM.application.user.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
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

public class GuiMain extends Activity implements
		ParlamentarSeguidoListFragment.OnParlamentarSeguidoSelectedListener,
		ParlamentarListFragment.OnParlamentarSelectedListener,
		ParlamentarMajorRankingListFragment.OnParlamentarRankingSelectedListener,
		ParlamentarMinorRankingListFragment.OnParlamentarMenorSelectedListener {

	private static final String MESSAGE_TO_PARLAMENTARES_FOLLOWED = "Parlamentares Seguidos";
	private static final String MESSAGE_TO_SEARCH_PARLAMENTAR = "Pesquisar Parlamentar";
	private static final String MESSAGE_TO_RANKINGS = "Rankings entre parlamentares";
	private static final int ANGLE_PARLAMENTARES_FOLLOWED_BUTTON = 30;
	private static final int ANGLE_LIST_ALL_PARLAMENTARES_BUTTON = 0;
	private static final int ANGLE_RANKING_BUTTON = -30;

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

		// //depuraçao
		//
		// UrlHostController serverControllerDebug =
		// UrlHostController.getInstance(getBaseContext());
		// Toast.makeText(getBaseContext(), serverControllerDebug.getUrl(),
		// Toast.LENGTH_LONG).show();

		btn_about_application_main
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						startActivity(new Intent(getBaseContext(),
								Activity_tests_apagar.class));

						/*
						 * startActivity(new Intent(getBaseContext(),
						 * GuiSobre.class));
						 */
					}
				});

		btn_parlamentar_main.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ParlamentarSeguidoListFragment listFragment = new ParlamentarSeguidoListFragment();
				loadFragment(listFragment);
				Toast.makeText(getBaseContext(),
						MESSAGE_TO_PARLAMENTARES_FOLLOWED, Toast.LENGTH_SHORT)
						.show();
				btn_show_all_parlamentares
						.setRotation(ANGLE_PARLAMENTARES_FOLLOWED_BUTTON);
			}
		});

		btn_search_parlamentar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ParlamentarListFragment listFragment = new ParlamentarListFragment();
				loadFragment(listFragment);
				Toast.makeText(getBaseContext(), MESSAGE_TO_SEARCH_PARLAMENTAR,
						Toast.LENGTH_SHORT).show();
				btn_show_all_parlamentares
						.setRotation(ANGLE_LIST_ALL_PARLAMENTARES_BUTTON);
			}
		});

		btn_ranking_main.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// ParlamentarRankingListFragment listFragment = new
				// ParlamentarRankingListFragment();
				// loadFragment(listFragment);
				AllRankingsFragment rankingListFragment = new AllRankingsFragment();
				updateFragment(R.id.fragment_container, rankingListFragment);

				Toast.makeText(getBaseContext(), MESSAGE_TO_RANKINGS,
						Toast.LENGTH_SHORT).show();
				btn_show_all_parlamentares.setRotation(ANGLE_RANKING_BUTTON);
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

	@Override
	protected void onResume() {
		super.onResume();
		// checkConnection();
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

	public void OnParlamentarRankingSelected() {
		
		ParlamentarDetailFragment parlamentarDetail = new ParlamentarDetailFragment();
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			updateFragment(R.id.fragment_container, parlamentarDetail);

		} else {
			updateFragment(R.id.detail_fragment_container, parlamentarDetail);
		}
	}
	
	 @Override
	public void OnParlamentarMenorSelected() {
		 
		ParlamentarDetailFragment detailFragment = new ParlamentarDetailFragment();
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			updateFragment(R.id.fragment_container, detailFragment);

		} else {
			updateFragment(R.id.detail_fragment_container, detailFragment);
		}
	}

	private void loadFragment(ListFragment listFragment) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.fragment_container, listFragment);
		// transaction.addToBackStack(null);
		transaction.commit();
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
// private void checkConnection()
// {
// ConnectivityManager con =(ConnectivityManager)
// getSystemService(Context.CONNECTIVITY_SERVICE);
//
// //3G
// if(con.getNetworkInfo(0).getState()==State.CONNECTED)
// {
// Toast.makeText(getBaseContext(),
// "Conexao 3G Ativa",Toast.LENGTH_SHORT).show();
//
// //Wifi
// }else if(con.getNetworkInfo(1).getState()==State.CONNECTED)
// {
// Toast.makeText(getBaseContext(), "Conexao Wifi",Toast.LENGTH_SHORT).show();
// }else
// {
// Toast.makeText(getBaseContext(),
// "Sem Conexao com internet",Toast.LENGTH_SHORT).show();
// }
// }
//

// =======
// package com.OMM.application.user.view;
//
// import org.apache.http.client.ResponseHandler;
//
// import android.app.Activity;
// import android.app.Fragment;
// import android.app.FragmentManager;
// import android.app.FragmentTransaction;
// import android.app.ListFragment;
// import android.app.ProgressDialog;
// import android.content.Intent;
// import android.content.res.Configuration;
// import android.os.AsyncTask;
// import android.os.Bundle;
// import android.view.Menu;
// import android.view.MenuInflater;
// import android.view.View;
// import android.widget.Button;
// import android.widget.Toast;
//
// import com.OMM.application.user.R;
// import com.OMM.application.user.controller.ParlamentarUserController;
// import com.OMM.application.user.exceptions.ConnectionFailedException;
// import com.OMM.application.user.exceptions.NullParlamentarException;
// import com.OMM.application.user.exceptions.RequestFailedException;
// import com.OMM.application.user.requests.HttpConnection;
//
// public class GuiMain extends Activity implements
// ParlamentarSeguidoListFragment.OnParlamentarSeguidoSelectedListener,
// ParlamentarListFragment.OnParlamentarSelectedListener,
// ParlamentarRankingListFragment.OnParlamentarRankingSelectedListener,
// ParlamentarMenorListFragment.OnParlamentarMenorSelectedListener {
//
// private static final String MESSAGE_TO_PARLAMENTARES_FOLLOWED =
// "Parlamentares Seguidos";
// private static final String MESSAGE_TO_SEARCH_PARLAMENTAR =
// "Pesquisar Parlamentar";
// private static final String MESSAGE_TO_RANKINGS =
// "Rankings entre parlamentares";
// private static final int ANGLE_PARLAMENTARES_FOLLOWED_BUTTON = 30;
// private static final int ANGLE_LIST_ALL_PARLAMENTARES_BUTTON = 0;
// private static final int ANGLE_RANKING_BUTTON = -30;
//
// private static ParlamentarUserController parlamentarController;
// private static FragmentManager fragmentManager;
//
// @Override
// protected void onCreate(Bundle savedInstanceState) {
// super.onCreate(savedInstanceState);
// setContentView(R.layout.gui_main);
// fragmentManager = this.getFragmentManager();
//
// if (findViewById(R.id.fragment_container) != null) {
// ParlamentarSeguidoListFragment fragment = new
// ParlamentarSeguidoListFragment();
// fragmentManager.beginTransaction()
// .replace(R.id.fragment_container, fragment).commit();
// }
//
// final Button btn_about_application_main = (Button)
// findViewById(R.id.btn_sobre_main);
// final Button btn_parlamentar_main = (Button)
// findViewById(R.id.btn_politico_main);
// final Button btn_search_parlamentar = (Button)
// findViewById(R.id.btn_pesquisar_parlamentar);
// final Button btn_ranking_main = (Button) findViewById(R.id.btn_ranking);
// final Button btn_show_all_parlamentares = (Button)
// findViewById(R.id.btn_ic_rolagem);
//
// btn_about_application_main
// .setOnClickListener(new View.OnClickListener() {
//
// @Override
// public void onClick(View v) {
// startActivity(new Intent(getBaseContext(),
// GuiSobre.class));
// }
// });
//
// btn_parlamentar_main.setOnClickListener(new View.OnClickListener() {
//
// @Override
// public void onClick(View v) {
// ParlamentarSeguidoListFragment listFragment = new
// ParlamentarSeguidoListFragment();
// loadFragment(listFragment);
// Toast.makeText(getBaseContext(),
// MESSAGE_TO_PARLAMENTARES_FOLLOWED, Toast.LENGTH_SHORT)
// .show();
// btn_show_all_parlamentares
// .setRotation(ANGLE_PARLAMENTARES_FOLLOWED_BUTTON);
// }
// });
//
// btn_search_parlamentar.setOnClickListener(new View.OnClickListener() {
//
// @Override
// public void onClick(View v) {
// ParlamentarListFragment listFragment = new ParlamentarListFragment();
// loadFragment(listFragment);
// Toast.makeText(getBaseContext(), MESSAGE_TO_SEARCH_PARLAMENTAR,
// Toast.LENGTH_SHORT).show();
// btn_show_all_parlamentares
// .setRotation(ANGLE_LIST_ALL_PARLAMENTARES_BUTTON);
// }
// });
//
// btn_ranking_main.setOnClickListener(new View.OnClickListener() {
//
// @Override
// public void onClick(View v) {
// // ParlamentarRankingListFragment listFragment = new
// ParlamentarRankingListFragment();
// // loadFragment(listFragment);
//
// RankingListFragment rankingListFragment = new RankingListFragment();
// updateFragment(R.id.fragment_container, rankingListFragment);
//
// Toast.makeText(getBaseContext(), MESSAGE_TO_RANKINGS,
// Toast.LENGTH_SHORT).show();
// btn_show_all_parlamentares.setRotation(ANGLE_RANKING_BUTTON);
// }
// });
//
// btn_show_all_parlamentares
// .setOnClickListener(new View.OnClickListener() {
//
// @Override
// public void onClick(View v) {
//
// // changing the buttons's visibility
// if (btn_search_parlamentar.getVisibility() == View.GONE) {
// btn_search_parlamentar.setVisibility(View.VISIBLE);
// btn_parlamentar_main.setVisibility(View.VISIBLE);
// btn_ranking_main.setVisibility(View.VISIBLE);
// btn_about_application_main
// .setVisibility(View.VISIBLE);
// btn_show_all_parlamentares.setScaleX(1.0f);
// btn_show_all_parlamentares.setScaleY(1.0f);
// btn_show_all_parlamentares.setAlpha(1.0f);
// } else {
// btn_search_parlamentar.setVisibility(View.GONE);
// btn_parlamentar_main.setVisibility(View.GONE);
// btn_ranking_main.setVisibility(View.GONE);
// btn_about_application_main.setVisibility(View.GONE);
// btn_show_all_parlamentares.setScaleX(0.6f);
// btn_show_all_parlamentares.setScaleY(0.6f);
// btn_show_all_parlamentares.setAlpha(0.5f);
// }
// }
// });
//
// parlamentarController = ParlamentarUserController
// .getInstance(getBaseContext());
//
// if (parlamentarController.checkEmptyDB() == true) {
// startPopulateDB();
// } else {
// // nothing should be done
// }
// }
//
// @Override
// public void OnParlamentarSeguidoSelected() {
//
// ParlamentarDetailFragment detailFragment = new ParlamentarDetailFragment();
//
// // Replaces the details according to the orientation of cell phone
// if (getResources().getConfiguration().orientation ==
// Configuration.ORIENTATION_PORTRAIT) {
// updateFragment(R.id.fragment_container, detailFragment);
//
// } else {
// updateFragment(R.id.detail_fragment_container, detailFragment);
// }
// }
//
// @Override
// public void OnParlamentarSelected() {
//
// ParlamentarDetailFragment detailFragment = new ParlamentarDetailFragment();
//
// // Replaces the details according to the orientation of cell phone
// if (getResources().getConfiguration().orientation ==
// Configuration.ORIENTATION_PORTRAIT) {
// updateFragment(R.id.fragment_container, detailFragment);
//
// } else {
// updateFragment(R.id.detail_fragment_container, detailFragment);
// }
// }
//
// public void OnParlamentarRankingSelected() {
// ParlamentarDetailFragment detailFragment = new ParlamentarDetailFragment();
//
// if (getResources().getConfiguration().orientation ==
// Configuration.ORIENTATION_PORTRAIT) {
// updateFragment(R.id.fragment_container, detailFragment);
//
// } else {
// updateFragment(R.id.detail_fragment_container, detailFragment);
// }
// }
//
//
// private void startPopulateDB() {
//
// ResponseHandler<String> responseHandler = HttpConnection
// .getResponseHandler();
//
// initializeDBTask task = new initializeDBTask();
// task.execute(responseHandler);
// }
//
// private void loadFragment(ListFragment listFragment) {
// FragmentTransaction transaction = fragmentManager.beginTransaction();
// transaction.replace(R.id.fragment_container, listFragment);
// transaction.commit();
// }
//
// @Override
// public boolean onCreateOptionsMenu(Menu menu) {
// MenuInflater inflater = getMenuInflater();
// inflater.inflate(R.menu.options_menu, menu);
// return true;
// }
//
//
// }