package com.OMM.application.user.view;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

public abstract class Alerts
{
	
	public static void conectionFailedAlert( Context context ) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setTitle("Ops!");
		builder.setMessage("Falha na conexão.");
		builder.setNeutralButton("OK", null);
		builder.show();	
		
		Log.i("Alerts", "Exception ConnectionFailed");
	}
	
	public static void parlamentarFailedAlert( Context context ) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setTitle("Ops!");
		builder.setMessage("Falha na requisição com banco de dados.");
		builder.setNeutralButton("OK", null);
		builder.show();
		
		
		Log.i("Alerts", "Exception NullParlamentarException.");
	}
	
	public static void requestFailedAlert( Context context ) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setTitle("Ops!");
		builder.setMessage("Falha na conexão com servidor.");
		builder.setNeutralButton("OK", null);
		builder.show();
		
		Log.i("Alerts", "Exception RequestFailed.");
	}
	public static void unexpectedFailedAlert( Context context ) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setTitle("Ops!");
		builder.setMessage("Falha inesperada.");
		builder.setNeutralButton("OK", null);
		builder.show();
		
		Log.i("Alerts", "Exception.");
	}
	
	public static void cotaParlamentarFailedAlert( Context context ) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setTitle("Ops!");
		builder.setMessage("Falha na requisição com banco de dados.");
		builder.setNeutralButton("OK", null);
		builder.show();
		
		Log.i("Alerts", "Exception.");
	}
	
	
}
