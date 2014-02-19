package com.OMM.application.user.view;

import java.util.LinkedList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.OMM.application.Updates.RankingParlamentarObserver;
import com.OMM.application.Updates.ServerUpdatesSubject;
import com.OMM.application.user.R;
import com.OMM.application.user.model.Parlamentar;

public class Activity_tests_apagar extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_apagar);
		
		Button btn_testa= (Button)findViewById(R.id.btn_test);
		Button btn_testa2= (Button) findViewById(R.id.btn_teste2);
		
		btn_testa.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				LinkedList<Parlamentar> pilhaParlamentares = new LinkedList<Parlamentar>();
				ServerUpdatesSubject subject = new ServerUpdatesSubject();
				
				
				//{"id":7,"nome":"RAUL LIMA","partido":"PP","uf":"RR"}
				Parlamentar parlamentar = new Parlamentar();
				parlamentar.setId(7);
				parlamentar.setNome("RAUL LIMA");
				parlamentar.setPartido("PP");
				parlamentar.setUf("RR");
				parlamentar.setValor(10000000);
				parlamentar.setMajorRankingPos(1);
				
				//{"id":3,"nome":"CHICO DAS VERDURAS","partido":"PRP","uf":"RR"}
				Parlamentar parlamentar3= new Parlamentar();
				parlamentar3.setId(3);
				parlamentar3.setNome("CHICO DAS VERDURAS");
				parlamentar3.setPartido("PRP");
				parlamentar3.setUf("RR");
				parlamentar3.setValor(5000000);
				parlamentar3.setMajorRankingPos(3);
				
				pilhaParlamentares.add(parlamentar);
				
				pilhaParlamentares.add(parlamentar3);
				
				RankingParlamentarObserver observer=new RankingParlamentarObserver(subject, 
						pilhaParlamentares, getBaseContext());
				
				
				subject.notifyObservers();
				Toast.makeText(getBaseContext(), "Feito!",Toast.LENGTH_SHORT).show();
				
			}
		});
		
		btn_testa2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				LinkedList<Parlamentar> pilhaParlamentares = new LinkedList<Parlamentar>();
				ServerUpdatesSubject subject = new ServerUpdatesSubject();
				
				
				//{"id":7,"nome":"RAUL LIMA","partido":"PP","uf":"RR"}
				Parlamentar parlamentar = new Parlamentar();
				parlamentar.setId(7);
				parlamentar.setNome("RAUL LIMA");
				parlamentar.setPartido("PP");
				parlamentar.setUf("RR");
				parlamentar.setValor(443373.22);
				parlamentar.setMajorRankingPos(1);
				
				//{"id":3,"nome":"CHICO DAS VERDURAS","partido":"PRP","uf":"RR"}
				Parlamentar parlamentar3= new Parlamentar();
				parlamentar3.setId(3);
				parlamentar3.setNome("CHICO DAS VERDURAS");
				parlamentar3.setPartido("PRP");
				parlamentar3.setUf("RR");
				parlamentar3.setValor(414551.16);
				parlamentar3.setMajorRankingPos(3);
				
				pilhaParlamentares.add(parlamentar);
				
				pilhaParlamentares.add(parlamentar3);
				
				RankingParlamentarObserver observer=new RankingParlamentarObserver(subject, 
						pilhaParlamentares, getBaseContext());
				
				
				subject.notifyObservers();
				Toast.makeText(getBaseContext(), "Desfeito!",Toast.LENGTH_SHORT).show();
			}
		});
		
	}
	
	

}
