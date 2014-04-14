package com.OMM.application.user.alerts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;

public class RuntimeAlert {

	private static RuntimeAlert instance = null;
	private Context context;
	private OnClickListener positiveListener;

	private RuntimeAlert(Context context, OnClickListener positivelistener) {
		instance.context = context;
		instance.positiveListener = positivelistener;
	}

	protected static RuntimeAlert getInstance(Context context,
			OnClickListener positivelistener) {

		if (instance == null) {
			instance = new RuntimeAlert(context, positivelistener);
		}

		else {
			instance.context = context;
			instance.positiveListener = positivelistener;
		}

		return instance;
	}

	public AlertDialog createAlertDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Ops!");
		builder.setMessage("RunTimeException");
		builder.setNeutralButton("OK", positiveListener);
		Log.i("Alerts", "Exception RuntimeException");
		return builder.show(); 
	}

}
