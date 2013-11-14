package com.OMM.application.user.view;



import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.OMM.application.user.dao.ParlamentarUserDao;
import com.OMM.application.user.model.Parlamentar;


public class GuiBuscarParlamentar extends ListActivity  {
	
	
	private int RETORNO_FILTRO=1;
	private String nomeParlamentar=null;
	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		//ParlamentarUserDao dao = new ParlamentarUserDao(getBaseContext());
		
		//setListAdapter(new ParlamentarAdapter(getBaseContext(), dao.getAll()));
		
		
				
	}
	@Override
	 protected void onListItemClick(ListView l, View v, int position, long id){
		/*
		 * Dessa forma voce consegue fazer passagens de paramentros dentro da aplicacao
		 * 
		 */
		Parlamentar po= (Parlamentar)l.getAdapter().getItem(position);// aqui eu digo em que item usuario clicou
		Toast.makeText(getBaseContext(),"Cod do parlamentar:"+po.getId(), Toast.LENGTH_SHORT).show();
		
		
		
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
	  * ate esse momento os menus somentes são expostos na 
	  * tela, nada de captura de eventos agora 
	  */
	
		return true;
	}
	
	
	
	 
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId()) {
		case com.OMM.application.user.R.id.filtro:
								{							
									startActivityForResult(new Intent(getBaseContext(),Filtro_parlamentar.class), RETORNO_FILTRO);break;
								}
			default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onActivityResult (int requestCode, int resultCode, Intent data)
	{
		if(RETORNO_FILTRO==requestCode)
		{
			if(resultCode==RESULT_OK)
			{
				
				 nomeParlamentar=data.getStringExtra("txt").toString();
				 
			}
		}
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

		if(nomeParlamentar!=null)
		{
			ParlamentarUserDao dao = new ParlamentarUserDao(getBaseContext());
			if(dao.getSelected(nomeParlamentar).size()>=1){
				//setListAdapter(new ParlamentarAdapter(getBaseContext(), dao.getSelected(nomeParlamentar)));
			}else
			{
				Toast.makeText(getBaseContext(),"O parlamentar "+nomeParlamentar+" não foi encontrado!", Toast.LENGTH_LONG).show();
			}
			
		}
		
		
		
	}

	/*
	 * agora eu preciso retornar um paremetro 
	 */
}
