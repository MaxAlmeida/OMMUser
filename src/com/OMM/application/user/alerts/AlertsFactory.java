package com.OMM.application.user.alerts;

import android.content.Context;
import android.content.DialogInterface.OnClickListener;

public class AlertsFactory {

	public static final int NO_EXCEPTIONS = 0;	
	public static final int CONNECTION_FAILED_EXCEPTION = 1;
	public static final int NULL_PARLAMENTARY_EXCEPTION = 2;
	public static final int REQUEST_FAILED_EXCEPTION = 3;
	public static final int NULL_COTA_PARLAMENTAR_EXCEPTION = 5;
	public static final int RUNTIME_EXCEPTION = 6;

	private static AlertsFactory instance = null;
	private Context context;

	private AlertsFactory(Context context) {
		this.context = context;
	}

	public static AlertsFactory getInstance(Context context) {
		if (instance == null) {
			instance = new AlertsFactory(context);
		} else {
			instance.setContext(context);
		}

		return instance;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public void createAlert(int type, OnClickListener positivelistener,
			OnClickListener negativelistener) {

		switch (type) {
		case CONNECTION_FAILED_EXCEPTION:
			ConnectionFailedAlert.getInstance(context, positivelistener).createAlertDialog();
			break;	
		case NULL_PARLAMENTARY_EXCEPTION:
			NullParlamentaryAlert.getInstance(context, positivelistener).createAlertDialog();
			break;
		case REQUEST_FAILED_EXCEPTION:
			RequestFailedAlert.getInstance(context, positivelistener).createAlertDialog();
			break;
		case NULL_COTA_PARLAMENTAR_EXCEPTION:
			NullCotaParlamentarAlert.getInstance(context, positivelistener).createAlertDialog();
			break;
		case RUNTIME_EXCEPTION:
			RuntimeAlert.getInstance(context, positivelistener).createAlertDialog();
			break;
		default:			
			break;
		}
	}

}