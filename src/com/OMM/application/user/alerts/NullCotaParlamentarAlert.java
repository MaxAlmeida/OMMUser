package com.OMM.application.user.alerts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;

public class NullCotaParlamentarAlert {

	private static NullCotaParlamentarAlert instance = null;
	private static AlertDialog alertInstance = null;
	private Context context;
	private OnClickListener positiveListener;

	private NullCotaParlamentarAlert(Context context,
			OnClickListener positivelistener) {
		this.context = context;
		this.positiveListener = positivelistener;
	}

	protected static NullCotaParlamentarAlert getInstance(Context context,
			OnClickListener positivelistener) {

		if (instance == null) {
			instance = new NullCotaParlamentarAlert(context, positivelistener);
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
			builder.setMessage("Falha na requisição com o servidor. Dados insuficientes recebidos.");
			builder.setNeutralButton("OK", positiveListener);
			Log.i("Alerts", "Exception NullParlamentary");
			alertInstance = builder.show();
		}
		return alertInstance;
	}

}
