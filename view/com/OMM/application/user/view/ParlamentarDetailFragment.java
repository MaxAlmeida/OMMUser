package com.OMM.application.user.view;

import com.OMM.application.user.R;
import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.model.Parlamentar;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ParlamentarDetailFragment extends Fragment {

	private Parlamentar parlamentar;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gui_detalhe,
				container, false);
		
		final Button btn_detalhe_seguir = (Button) view.findViewById(R.id.btn_detalhe_seguir);
		final Button btn_detalhe_desseguir = (Button) view.findViewById(R.id.btn_detalhe_desseguir);
		btn_detalhe_desseguir.setVisibility(View.INVISIBLE);
	
		btn_detalhe_desseguir.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				ParlamentarUserController parlamentarController;
				parlamentarController=ParlamentarUserController.getInstance(getActivity());
				
				try{
					parlamentar.setSeguido(0);
				parlamentarController.unFollowedParlamentar(parlamentar);
				Toast.makeText(getActivity(), "Parlamentar DesSeguido", Toast.LENGTH_SHORT).show();
				btn_detalhe_desseguir.setVisibility(View.GONE);
				btn_detalhe_seguir.setVisibility(View.VISIBLE);

				}catch(NullParlamentarException nullEx)
				{
					Toast.makeText(getActivity(), "Deu pau,compre outro celular", Toast.LENGTH_SHORT).show();
					
				}
			}
		});
		
		
		btn_detalhe_seguir.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				ParlamentarUserController parlamentarController;
				parlamentarController=ParlamentarUserController.getInstance(getActivity());
				
				try{
					parlamentar.setSeguido(1);
				parlamentarController.followedParlamentar(parlamentar);
				Toast.makeText(getActivity(), "Parlamentar Seguido", Toast.LENGTH_SHORT).show();
				btn_detalhe_seguir.setVisibility(View.GONE);
				btn_detalhe_desseguir.setVisibility(View.VISIBLE);

				}catch(NullParlamentarException nullEx)
				{
					Toast.makeText(getActivity(), "Deu pau,compre outro celular", Toast.LENGTH_SHORT).show();
					
				}
			}
		});
		return view;
	}

	
	public void setBarras(Parlamentar parlamentar) {
		TextView view = (TextView) getView().findViewById(R.id.nome);
		view.setText(parlamentar.getNome());
		this.parlamentar=parlamentar;
		
		
	
	}
	
	
	
	
}