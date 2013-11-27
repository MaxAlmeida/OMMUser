package com.OMM.application.user.view;

import android.app.AlertDialog;
import android.content.Context;

public abstract class Alerts
{
	
	public static void conectionFailed( Context context ) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setTitle("Ops!");
		builder.setMessage("Falha na conexão.");
		builder.setNeutralButton("OK", null);
		builder.show();	
	}
}
