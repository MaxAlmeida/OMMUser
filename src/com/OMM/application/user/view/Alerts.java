package com.OMM.application.user.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;

public abstract class Alerts {
	public static final int NO_EXCEPTIONS = 0;
	public static final int CONNECTION_FAILED_EXCEPTION = 1;
	public static final int NULL_PARLAMENTAR_EXCEPTION = 2;
	public static final int REQUEST_FAILED_EXCEPTION = 3;
	public static final int NULL_COTA_PARLAMENTAR_EXCEPTION = 5;
	public static final int RUNTIME_EXCEPTION = 6;

	public static void conectionFailedAlert(Context context,
			OnClickListener positivelistener) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setTitle("Ops!");
		builder.setMessage("O servidor não está respondendo. Desculpe pelo transtorno.");
		builder.setNeutralButton("OK", positivelistener);
		builder.show();

		Log.i("Alerts", "Exception ConnectionFailed");
	}

	public static void parlamentarFailedAlert(Context context,
			OnClickListener positivelistener) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setTitle("Ops!");
		builder.setMessage("Falha na requisição com o servidor. Dados insuficientes recebidos.");
		builder.setNeutralButton("OK", positivelistener);
		builder.show();

		Log.i("Alerts", "Exception NullParlamentarException.");
	}

	public static void requestFailedAlert(Context context,
			OnClickListener positivelistener) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setTitle("Ops!");
		builder.setMessage("Falha na verificação do protocolo de envio. Desculpe pelo transtorno.");
		builder.setNeutralButton("OK", positivelistener);
		builder.show();

		Log.i("Alerts", "Exception RequestFailed.");
	}

	public static void cotaParlamentarFailedAlert(Context context,
			OnClickListener positivelistener) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setTitle("Ops!");
		builder.setMessage("Falha na requisição com o servidor. Dados insuficientes recebidos.");
		builder.setNeutralButton("OK", positivelistener);
		builder.show();

		Log.i("Alerts", "Exception NullCotaParlamentar");
	}

	public static void runtimeException(Context context,
			OnClickListener positivelistener) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setTitle("Ops!");
		builder.setMessage("RunTimeException");
		builder.setNeutralButton("OK", positivelistener);
		builder.show();

		Log.i("Alerts", "Exception RuntimeException");
	}
}
