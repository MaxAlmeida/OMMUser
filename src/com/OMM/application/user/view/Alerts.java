package com.OMM.application.user.view;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

public abstract class Alerts {
	public static final int NO_EXCEPTIONS = 0;
	public static final int CONNECTION_FAILED_EXCEPTION = 1;
	public static final int NULL_PARLAMENTAR_EXCEPTION = 2;
	public static final int REQUEST_FAILED_EXCEPTION = 3;
	public static final int UNEXPECTED_FAILED_EXCEPTION = 4;
	public static final int NULL_COTA_PARLAMENTAR_EXCEPTION = 5;

	public static void conectionFailedAlert(Context context) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setTitle("Ops!");
		builder.setMessage("Falha na conexão, verifique sua conexão com a internet.");
		builder.setNeutralButton("OK", null);
		builder.show();

		Log.i("Alerts", "Exception ConnectionFailed");
	}
	
	public static void parlamentarFailedAlert(Context context) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setTitle("Ops!");
		builder.setMessage("Falha na requisição com banco de dados.");
		builder.setNeutralButton("OK", null);
		builder.show();

		Log.i("Alerts", "Exception NullParlamentarException.");
	}

	public static void requestFailedAlert(Context context) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setTitle("Ops!");
		builder.setMessage("Falha na conexão com o servidor.");
		builder.setNeutralButton("OK", null);
		builder.show();

		Log.i("Alerts", "Exception RequestFailed.");
	}

	public static void unexpectedFailedAlert(Context context) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setTitle("Ops!");
		builder.setMessage("Falha na requisição.");
		builder.setNeutralButton("OK", null);
		builder.show();

		Log.i("Alerts", "Exception.");
	}

	public static void cotaParlamentarFailedAlert(Context context) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setTitle("Ops!");
		builder.setMessage("Falha na requisição com banco de dados.");
		builder.setNeutralButton("OK", null);
		builder.show();

		Log.i("Alerts", "Exception NullCotaParlamentar");
	}

}
