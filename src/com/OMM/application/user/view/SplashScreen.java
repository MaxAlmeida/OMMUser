package com.OMM.application.user.view;

import org.apache.http.client.ResponseHandler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.OMM.application.user.R;
import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.controller.UrlHostController;
import com.OMM.application.user.exceptions.ConnectionFailedException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.RequestFailedException;
import com.OMM.application.user.requests.HttpConnection;

public class SplashScreen extends Activity {

	private final int DURACAO_DA_TELA = 2860;
	private ParlamentarUserController parlamentarController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);

		MediaPlayer splashSound = MediaPlayer.create(this, R.raw.splash);
		splashSound.start();
		splashSound.seekTo(2000);

		parlamentarController = ParlamentarUserController
				.getInstance(getBaseContext());

		if (parlamentarController.checkEmptyDB() == true) {

			/*
			 * O problema pode estar aqui Olhar melhor a chamada do banco
			 */

			UrlHostController serverController = UrlHostController
					.getInstance(getBaseContext());
			serverController
					.insertUrlServer("192.168.1.106:8080/OlhaMinhaMesada");

			// so pra esperar mesmo
			for (int i = 0; i < 100000000; i++) {
			}
			startPopulateDB();
		} else {

			getUpdatesServer();

		}
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

		@SuppressWarnings("unchecked")
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

			} catch (Exception e) {
				exception = Alerts.UNEXPECTED_FAILED_EXCEPTION;

			}
			return exception;
		}

		protected void onPostExecute(Integer result) {

			progressDialog.dismiss();

			switch (result) {

			case Alerts.CONNECTION_FAILED_EXCEPTION:

				Alerts.conectionFailedAlert(SplashScreen.this);
				break;

			case Alerts.NULL_PARLAMENTAR_EXCEPTION:

				Alerts.parlamentarFailedAlert(SplashScreen.this);
				break;

			case Alerts.REQUEST_FAILED_EXCEPTION:

				Alerts.requestFailedAlert(SplashScreen.this);
				break;

			case Alerts.UNEXPECTED_FAILED_EXCEPTION:

				Alerts.unexpectedFailedAlert(SplashScreen.this);
				break;

			default:
				// Nothing should be done
			}
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {

					Intent myAction = new Intent(SplashScreen.this,
							GuiMain.class);
					SplashScreen.this.startActivity(myAction);
					SplashScreen.this.finish();
				}
			}, DURACAO_DA_TELA);

		}
	}

	private void startPopulateDB() {

		ResponseHandler<String> responseHandler = HttpConnection
				.getResponseHandler();

		initializeDBTask task = new initializeDBTask();
		task.execute(responseHandler);
	}

	// TODO
	/*
	 * Mudar esse metodo, se possivel apagar e substituir
	 */
	private void getUpdatesServer() {
		ResponseHandler<String> responseHandler = HttpConnection
				.getResponseHandler();
		requestServerUpdates task = new requestServerUpdates();
		task.execute(responseHandler);
	}

	// =============================================================================================================
	// TODO
	/*
	 * Mudar essa AsyncTask para atender o padrao Observer
	 */
	private class requestServerUpdates extends AsyncTask<Object, Void, Integer> {
		ProgressDialog progressDialog;
		Integer exception = Alerts.NO_EXCEPTIONS;
		ResponseHandler<String> responseHandler;

		@Override
		protected void onPreExecute() {
			// progressDialog =
			// ProgressDialog.show(GuiMain.this,"Atualizações","Atualizando informações por favor aguarde....");
		}

		@Override
		protected Integer doInBackground(Object... params) {
			UrlHostController serverController = UrlHostController
					.getInstance(getBaseContext());
			responseHandler = (ResponseHandler<String>) params[0];

			try {
				// if(serverController.getExistsUpdates(responseHandler)==1)
				// {
				// serverController.insertUrlServer(serverController.requestNewUrl(responseHandler,
				// 1));
				Toast.makeText(getBaseContext(), "OMM atualizado!",
						Toast.LENGTH_SHORT).show();
				// }else Toast.makeText(getBaseContext(),
				// "Não há atualizações disponivéis!",Toast.LENGTH_SHORT).show();

			} catch (Exception e) {
				// TODO: handle exception
			}
			return exception;
		}

	}
	// =============================================================================================================
}