package com.OMM.application.user.adapters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.client.ResponseHandler;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.OMM.application.user.R;
import com.OMM.application.user.alerts.AlertsFactory;
import com.OMM.application.user.controller.ParlamentarUserController;
import com.OMM.application.user.exceptions.ConnectionFailedException;
import com.OMM.application.user.exceptions.NullCotaParlamentarException;
import com.OMM.application.user.exceptions.NullParlamentarException;
import com.OMM.application.user.exceptions.RequestFailedException;
import com.OMM.application.user.model.Parlamentar;
import com.OMM.application.user.requests.HttpConnection;

public class ParlamentarSeguidoAdapter extends BaseAdapter {
	private Context context;
	private List<Parlamentar> parlamentares;
	private Parlamentar parlamentar;
	private LayoutInflater inflater = null;
	public ParlamentarUserController controller = null;

	public ParlamentarSeguidoAdapter(Context context, int textViewResourceId,
			List<Parlamentar> parlamentares) {
		super();

		this.context = context;
		this.parlamentares = parlamentares;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		parlamentar = parlamentares.get(position);
		final View view = inflater.inflate(R.layout.fragment_parlamentar, null);
		TextView textView = (TextView) view
				.findViewById(R.id.parlamentarlistfragment_txt_nome);
		textView.setText(parlamentar.getNome());

		final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
		checkBox.setTag(position);
		if (parlamentar.getIsSeguido() == 1) {
			ImageView imageView = (ImageView) view
					.findViewById(R.id.parlamentarlistfragment_img_arte);
			imageView
					.setImageResource(R.drawable.parlamentar_seguido_foto_mini);
			checkBox.setChecked(true);
		}
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				controller = ParlamentarUserController.getInstance(context);
				parlamentar = parlamentares.get((Integer) checkBox.getTag());
				controller.setParlamentar(parlamentar);

				if (isChecked) {
					ResponseHandler<String> responseHandler = HttpConnection
							.getResponseHandler();
					RequestTask task = new RequestTask();
					task.execute(responseHandler);
				} else {
					try {

						parlamentar = controller.getParlamentar();
						controller.unfollowedParlamentar();
						view.animate().alpha(0.0f);
						removeItem((Integer) checkBox.getTag());

					} catch (NullParlamentarException e) {
						AlertsFactory alertsFactory = AlertsFactory.getInstance(context);
						alertsFactory.createAlert(AlertsFactory.NULL_PARLAMENTARY_EXCEPTION, null, null);							
					} catch (NullCotaParlamentarException e) {
						AlertsFactory alertsFactory = AlertsFactory.getInstance(context);
						alertsFactory.createAlert(AlertsFactory.NULL_COTA_PARLAMENTAR_EXCEPTION, null, null);
					}
				}
			}
		});
		return view;
	}

	@Override
	public int getCount() {
		return parlamentares.size();
	}

	@Override
	public Object getItem(int position) {
		return parlamentares.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void clear() {
		parlamentares = new ArrayList<Parlamentar>();
	}

	public void addAll(List<Parlamentar> result) {
		Iterator<Parlamentar> i = result.iterator();
		while (i.hasNext()) {
			parlamentares.add(i.next());
		}
	}

	public void removeItem(int position) {
		parlamentares.remove(position);
		notifyDataSetChanged();
	}

	private class RequestTask extends AsyncTask<Object, Void, Integer> {

		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(context, "Aguarde...",
					"Buscando Dados");
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Integer doInBackground(Object... params) {

			Integer result = null;
			ParlamentarUserController parlamentarController = ParlamentarUserController
					.getInstance(context);

			ResponseHandler<String> rh = (ResponseHandler<String>) params[0];
			try {
				parlamentarController.doRequest(rh);
				result = AlertsFactory.NO_EXCEPTIONS;
			} catch (ConnectionFailedException cfe) {
				result = AlertsFactory.CONNECTION_FAILED_EXCEPTION;

			} catch (RequestFailedException rfe) {
				result = AlertsFactory.REQUEST_FAILED_EXCEPTION;

			} catch (NullParlamentarException npe) {
				result = AlertsFactory.NULL_PARLAMENTARY_EXCEPTION;

			} catch (NullCotaParlamentarException ncpe) {
				result = AlertsFactory.NULL_COTA_PARLAMENTAR_EXCEPTION;
			}
			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			progressDialog.dismiss();
			notifyDataSetChanged();
			if(result == AlertsFactory.NO_EXCEPTIONS){
				try {
					controller.followedParlamentar();
				} catch (NullCotaParlamentarException e) {
					AlertsFactory alertsFactory = AlertsFactory.getInstance(context);
					alertsFactory.createAlert(AlertsFactory.NULL_COTA_PARLAMENTAR_EXCEPTION, null, null);

				} catch (NullParlamentarException e) {
					AlertsFactory alertsFactory = AlertsFactory.getInstance(context);
					alertsFactory.createAlert(AlertsFactory.NULL_PARLAMENTARY_EXCEPTION, null, null);
				}
				Toast.makeText(context, "Seguido!", Toast.LENGTH_SHORT).show();
			}
			else{
				AlertsFactory alertsFactory = AlertsFactory.getInstance(context);
				alertsFactory.createAlert(result, null, null);
			}
		}
	}
}
