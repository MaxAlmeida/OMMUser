package com.OMM.application.user.view;



import com.OMM.application.user.adapters.ParlamentarAdapter;
import com.OMM.application.user.dao.ParlamentarUserDao;
import com.OMM.application.user.pojo.ParlamentarPO;


import android.R;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;


public class GuiBuscarParlamentar extends ListActivity  {
	
	private SearchView mSearchView;
	
	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		ParlamentarUserDao dao = new ParlamentarUserDao(getBaseContext());
		setListAdapter(new ParlamentarAdapter(getBaseContext(), dao.getAll()));
		
		
				
	}
	@Override
	 protected void onListItemClick(ListView l, View v, int position, long id){
		/*
		 * Dessa forma voce consegue fazer passagens de paramentros dentro da aplicacao
		 * 
		 */
		ParlamentarPO po= (ParlamentarPO)l.getAdapter().getItem(position);// aqui eu digo em que item usuario clicou
		Toast.makeText(getBaseContext(),"Cod do parlamentar:"+po.getCod_parlamentar().toString(), Toast.LENGTH_SHORT).show();
		
		
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		/*pega todos os menus criados
		 * criados no xml itensmenu e tras 
		 * para tela do usuario  
		 */
		MenuInflater menuInflater=getMenuInflater();
		menuInflater.inflate(com.OMM.application.user.R.menu.menu_opcoes, menu);
	 /*
	  * ate esse momento os menus somentes s�o expostos na 
	  * tela, nada de captura de eventos agora 
	  */
	
		return true;
	}
	
	
	
	 
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId()) {
		case com.OMM.application.user.R.id.filtro:startActivity(new Intent(getBaseContext(),Filtro_parlamentar.class));break;
			default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	/*
	 * Esse metodo sempre vai ser executado
	 * entao o usarei para atualizar a listagem
	 * 
	 */
	@Override
	public void onResume()
	{
		super.onResume();
		
		
	}

	/*
	 * agora eu preciso retornar um paremetro 
	 */
}
