package com.OMM.application.user.alerts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;

public class ConnectionFailedAlert {

	private static ConnectionFailedAlert instance = null;
	private static AlertDialog alertInstance = null;
	private Context context;
	private OnClickListener positiveListener;

	private ConnectionFailedAlert(Context context,
			OnClickListener positivelistener) {
		this.context = context;
		this.positiveListener = positivelistener;
	}

	protected static ConnectionFailedAlert getInstance(Context context,
			OnClickListener positivelistener) {

		if (instance == null) {
			instance = new ConnectionFailedAlert(context, positivelistener);
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
			builder.setMessage("O servidor não está respondendo. Desculpe pelo transtorno.");
			builder.setNeutralButton("OK", this.positiveListener);
			Log.i("Alerts", "Exception ConnectionFailed");
			alertInstance = builder.show();
		}
		return alertInstance;
	}

}
