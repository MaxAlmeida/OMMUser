package com.OMM.application.user.alerts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;

public class RequestFailedAlert {

	private static RequestFailedAlert instance = null;
	private static AlertDialog alertInstance = null;
	private Context context;
	private OnClickListener positiveListener;

	private RequestFailedAlert(Context context, OnClickListener positivelistener) {
		instance.context = context;
		instance.positiveListener = positivelistener;
	}

	protected static RequestFailedAlert getInstance(Context context,
			OnClickListener positivelistener) {

		if (instance == null) {
			instance = new RequestFailedAlert(context, positivelistener);
		}

		else {
			instance.context = context;
			instance.positiveListener = positivelistener;
		}

		return instance;
	}

	public AlertDialog createAlertDialog() {
		if (alertInstance == null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle("Ops!");
			builder.setMessage("Falha na verificação do protocolo de envio. Desculpe pelo transtorno.");
			builder.setNeutralButton("OK", positiveListener);
			Log.i("Alerts", "Exception RequestFailed.");
			alertInstance = builder.show();
		}
		return alertInstance;
	}

}
