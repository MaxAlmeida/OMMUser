package com.OMM.application.user.view;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;

import com.OMM.application.user.R;

public class ParlamentarDetailActivity extends Activity {
  
  public static final String nome = "nome";
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
      finish();
      return;
    }
    setContentView(R.layout.detail_parlamentar_port);
    Bundle extras = getIntent().getExtras();
    if (extras != null) {
      String s = (String)extras.get(nome);
      TextView view = (TextView) findViewById(R.id.detailsText);
      view.setText(s);
    }
  }
} 