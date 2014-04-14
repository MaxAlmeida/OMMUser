package com.OMM.application.user.alerts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;

public class UnknownExceptionAlert {

	private static UnknownExceptionAlert instance = null;
	private static AlertDialog alertInstance = null;
	private Context context;
	private OnClickListener positiveListener;

	private UnknownExceptionAlert(Context context,
			OnClickListener positivelistener) {
		instance.context = context;
		instance.positiveListener = positivelistener;
	}

	protected static UnknownExceptionAlert getInstance(Context context,
			OnClickListener positivelistener) {

		if (instance == null) {
			instance = new UnknownExceptionAlert(context, positivelistener);
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
			builder.setMessage("Falha desconhecida.");
			builder.setNeutralButton("OK", positiveListener);
			Log.i("Alerts", "Exception UnknownException");
			alertInstance = builder.show();
		}
		return alertInstance;
	}

}
