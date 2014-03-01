package com.OMM.application.user.view;

import org.apache.http.client.ResponseHandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.AsyncTask;
import android.os.Bundle;

import com.OMM.application.Updates.*;
import com.OMM.application.user.R;
import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.exceptions.ConnectionFailedException;
import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.RequestFailedException;
import com.OMM.application.user.requests.HttpConnection;

@SuppressWarnings("unchecked")
public class SplashScreen extends Activity {

	private static final int NO_CONNECTIVITY = 0;
	private static final int CONNECTIVIVTY_3G = 1;
	private static final int CONNECTIVITY_WIFI = 2;

	private ParlamentarUserController parlamentarController;
	private boolean isEmpty = true;
	private boolean needsUpdate = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);

		parlamentarController = ParlamentarUserController
				.getInstance(getBaseContext());
		isEmpty = parlamentarController.checkEmptyDB();

	}

	@Override
	protected void onResume() {
		super.onResume();

		switch (checkConnection()) {
		case NO_CONNECTIVITY:
			NoDataConnection noDataConnection = new NoDataConnection();
			noDataConnection.execute();
			break;
		case CONNECTIVIVTY_3G:
			InitBy3G initBy3G = new InitBy3G();
			initBy3G.execute();
			break;
		case CONNECTIVITY_WIFI:
			if (isEmpty) {
				startPopulateDB();
			} else {
				startUpdateDB();
			}
			break;
		default:
			// nothing here
			break;
		}
		if (!isEmpty) {
			startApplication();
		}

	}

	private int checkConnection() {
		ConnectivityManager con = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		try {
			// 3G
			if (con.getNetworkInfo(0).getState() == State.CONNECTED) {
				return 1;
			}
			// Wifi
			else if (con.getNetworkInfo(1).getState() == State.CONNECTED) {
				return 2;
			} else {
				return 0;
			}
		} catch (Exception e) {
			return 0;
		}
	}

	private void startApplication() {
		Intent myAction = new Intent(SplashScreen.this, GuiMain.class);
		SplashScreen.this.startActivity(myAction);
		SplashScreen.this.finish();
	}

	private void startUpdateDB() {
		ResponseHandler<String> responseHandler = HttpConnection
				.getResponseHandler();
		UpdateDBTask task = new UpdateDBTask();
		task.execute(responseHandler);
	}

	private void startPopulateDB() {

		ResponseHandler<String> responseHandler = HttpConnection
				.getResponseHandler();
		initializeDBTask task = new initializeDBTask();
		task.execute(responseHandler);
	}

	private class InitBy3G extends AsyncTask<Object, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (isEmpty) {
				AlertDialog.Builder builder = new Builder(SplashScreen.this);
				builder.setTitle("Instalação do Banco de Dados");
				builder.setMessage("Esse procedimento pode gerar gastos adicionais devido ao uso de dados. Deseja continuar?");
				builder.setPositiveButton("Sim!", positiveListener);
				builder.setNegativeButton("Não.", null);
				builder.show();
			} else {
				AlertDialog.Builder builder = new Builder(SplashScreen.this);
				builder.setTitle("Atualização de Dados");
				builder.setMessage("Esse procedimento pode gerar gastos adicionais devido ao uso de dados. Deseja continuar?");
				builder.setPositiveButton("Sim!", positiveListener);
				builder.setNegativeButton("Não.", null);
				builder.show();
			}

		}

		@Override
		protected Boolean doInBackground(Object... params) {
			return true;
		}

		OnClickListener positiveListener = new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (isEmpty)
					startPopulateDB();
				else {
					startUpdateDB();
				}
			}
		};
	}

	private class NoDataConnection extends AsyncTask<Object, Void, Boolean> {

		AlertDialog.Builder builder = new Builder(SplashScreen.this);
		boolean okToinitDataBase = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			builder.setTitle("Conexão de Dados");
			builder.setMessage("Não foi detectada nenhuma conexão de dados, por isso"
					+ " as informações dos parlamentares podem estar desatualizadas."
					+ " Atualize seu aplicativo com frequência.");
			builder.setNeutralButton("Ok", listener);
			builder.show();
		}

		@Override
		protected Boolean doInBackground(Object... params) {

			return okToinitDataBase;
		}

		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				startApplication();
			}
		};
	}

	private class initializeDBTask extends AsyncTask<Object, Void, Integer> {
		ProgressDialog progressDialog;

		Integer exception = Alerts.NO_EXCEPTIONS;

		@Override
		protected void onPreExecute() {

			progressDialog = ProgressDialog.show(SplashScreen.this,
					"Instalando Banco de Dados...",
					"Isso pode demorar alguns minutos ");

		}

		@Override
		protected Integer doInBackground(Object... params) {

			ParlamentarUserController parlamentarController = ParlamentarUserController
					.getInstance(getBaseContext());

			ResponseHandler<String> dataResponseHandler = (ResponseHandler<String>) params[0];

			try {

				parlamentarController.insertAll(dataResponseHandler);

			} catch (ConnectionFailedException cfe) {
				exception = Alerts.CONNECTION_FAILED_EXCEPTION;

			} catch (NullParlamentarException cpe) {
				exception = Alerts.NULL_PARLAMENTAR_EXCEPTION;

			} catch (RequestFailedException rfe) {
				exception = Alerts.REQUEST_FAILED_EXCEPTION;
				
				// } catch (Exception e) {
				// exception = Alerts.UNEXPECTED_FAILED_EXCEPTION;
				//
			}
			return exception;
		}

		protected void onPostExecute(Integer result) {

			progressDialog.dismiss();

			switch (result) {

			case Alerts.CONNECTION_FAILED_EXCEPTION:

				Alerts.conectionFailedAlert(SplashScreen.this, positiveListener);
				break;

			case Alerts.NULL_PARLAMENTAR_EXCEPTION:

				Alerts.parlamentarFailedAlert(SplashScreen.this, positiveListener);
				break;

			case Alerts.REQUEST_FAILED_EXCEPTION:

				Alerts.requestFailedAlert(SplashScreen.this, positiveListener);
				break;
			case Alerts.RUNTIME_EXCEPTION:
				Alerts.runtimeException(SplashScreen.this, positiveListener);
				break;

			default:

				Alerts.unexpectedFailedAlert(SplashScreen.this, positiveListener);
				break;
			}

		}
	}

	private class UpdateDBTask extends AsyncTask<Object, Void, Integer> {
		DataUpdate dataUpdate;
		ServerUpdatesSubject subject;
		ProgressDialog progressDialog;
		Integer exception = Alerts.NO_EXCEPTIONS;
		ResponseHandler<String> responseHandlerVerify;
		ResponseHandler<String> responseHandlerCota;
		ResponseHandler<String> responseHandlerParlamentar;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(SplashScreen.this,
					"Atualizando Dados...", "Isso pode demorar alguns minutos");
		}

		@Override
		protected Integer doInBackground(Object... params) {

			subject = new ServerUpdatesSubject(SplashScreen.this);

			dataUpdate = new DataUpdate(SplashScreen.this);
			responseHandlerVerify = (ResponseHandler<String>) params[0];
			try {
				needsUpdate = dataUpdate
						.doRequestUpdateVerify(responseHandlerVerify);
				if (needsUpdate) {
					dataUpdate.doRequestParlamentar(responseHandlerParlamentar);
					dataUpdate.doRequestCota(responseHandlerCota);
				}

			} catch (ConnectionFailedException cfe) {
				exception = Alerts.CONNECTION_FAILED_EXCEPTION;

			} catch (RequestFailedException rfe) {
				exception = Alerts.REQUEST_FAILED_EXCEPTION;

			} catch (NullParlamentarException npe) {
				exception = Alerts.NULL_PARLAMENTAR_EXCEPTION;

			} catch (NullCotaParlamentarException ncpe) {
				exception = Alerts.NULL_COTA_PARLAMENTAR_EXCEPTION;
			} catch (RuntimeException e) {
				exception = Alerts.RUNTIME_EXCEPTION;
			}

			return exception;
		}

		protected void onPostExecute(Integer result) {

			progressDialog.dismiss();

			switch (result) {

			case Alerts.CONNECTION_FAILED_EXCEPTION:

				Alerts.conectionFailedAlert(SplashScreen.this,positiveListener);
				break;

			case Alerts.NULL_PARLAMENTAR_EXCEPTION:

				Alerts.parlamentarFailedAlert(SplashScreen.this,positiveListener);
				break;

			case Alerts.REQUEST_FAILED_EXCEPTION:

				Alerts.requestFailedAlert(SplashScreen.this,positiveListener);
				break;

			default:

				Alerts.unexpectedFailedAlert(SplashScreen.this,positiveListener);
				break;
			}

		}
	}

	OnClickListener positiveListener = new OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			startApplication();
		}
	};

}