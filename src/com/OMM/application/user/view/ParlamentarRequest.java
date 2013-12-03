package com.OMM.application.user.view;

import org.apache.http.client.ResponseHandler;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.exceptions.ConnectionFailedException;
import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.RequestFailedException;
import com.OMM.application.user.requests.HttpConnection;

public class ParlamentarRequest
{

	private Context context;
	
	public ParlamentarRequest(Context context){
		this.context = context;
		
	}

	private class RequestTask extends AsyncTask<Object, Void, Integer> {

		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(context, "Aguarde...",
					"Buscando Dados");
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Integer doInBackground(Object... params) {

			Integer result = null;
			ParlamentarUserController parlamentarController = ParlamentarUserController
					.getInstance(context);

			ResponseHandler<String> rh = (ResponseHandler<String>) params[0];
			try {
				parlamentarController.doRequest(rh);
				result = Alerts.NO_EXCEPTIONS;
			} catch (ConnectionFailedException cfe) {
				result = Alerts.CONNECTION_FAILED_EXCEPTION;

			} catch (RequestFailedException rfe) {
				result = Alerts.REQUEST_FAILED_EXCEPTION;

			} catch (NullParlamentarException npe) {
				result = Alerts.NULL_PARLAMENTAR_EXCEPTION;

			} catch (NullCotaParlamentarException ncpe) {
				result = Alerts.NULL_COTA_PARLAMENTAR_EXCEPTION;

			} catch (Exception e) {
				result = Alerts.UNEXPECTED_FAILED_EXCEPTION;

			}
			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			progressDialog.dismiss();

			switch ((Integer) result) {
			case 0:

				 ((GuiMain) context).OnParlamentarSelected();
				break;

			case Alerts.CONNECTION_FAILED_EXCEPTION:

				Alerts.conectionFailedAlert(context);
				break;

			case Alerts.NULL_PARLAMENTAR_EXCEPTION:

				Alerts.parlamentarFailedAlert(context);
				break;

			case Alerts.NULL_COTA_PARLAMENTAR_EXCEPTION:

				Alerts.cotaParlamentarFailedAlert(context);
				break;

			case Alerts.REQUEST_FAILED_EXCEPTION:

				Alerts.requestFailedAlert(context);
				break;

			case Alerts.UNEXPECTED_FAILED_EXCEPTION:

				Alerts.unexpectedFailedAlert(context);
				break;

			default:
				// Nothing should be done
			}
		}
	}

	public void startRequest() {

		ResponseHandler<String> responseHandler = HttpConnection
				.getResponseHandler();

		RequestTask task = new RequestTask();
		task.execute(responseHandler);

	}

	
}
