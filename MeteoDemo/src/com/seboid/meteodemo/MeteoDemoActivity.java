package com.seboid.meteodemo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MeteoDemoActivity extends Activity implements OnClickListener {

	//
	// les composants d'interface
	//
	Button actualiser;
	TextView temperature;
	TextView conditions;
	TextView ville;
	TextView depuis;
	ImageView icone;
	MeteoWebAPI web;

	//
	// Appelle lorsque l'activite est creee
	//
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// pour une barre de progres rotative
		// (Note: a faire avant le setContentView)
		getWindow().requestFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		// afficher l'interface
		setContentView(R.layout.main);

		// trouver les composants d'interface
		actualiser=(Button)findViewById(R.id.actualiser);
		conditions=(TextView)findViewById(R.id.conditions);
		temperature=(TextView)findViewById(R.id.temperature);
		ville=(TextView)findViewById(R.id.ville);
		depuis=(TextView)findViewById(R.id.heure);
		icone=(ImageView)findViewById(R.id.icone);

		// associer une action au bouton
		actualiser.setOnClickListener(this);					
	}

	@Override
	public void onClick(View arg0) {
		//
		// plutot que de connecter ici avec l'internet,
		// on démarre une tache séparée qui va faire le travail.
		//
		new DownloadLoginTask().execute();
	}

	//
	// Lecture de conditions actuelles
	// <params, progress, results>	
	//
	private class DownloadLoginTask extends AsyncTask<String, String, MeteoWebAPI> {
		protected void onPreExecute() {
			// éxécute en premier, dans le thread interface.
			setProgressBarIndeterminateVisibility(true);
		}
		protected MeteoWebAPI doInBackground(String... params) {
			// éxécute en arrière-plan, dans un thread non-interface
			// va chercher les conditions sur le web...
			MeteoWebAPI web=new MeteoWebAPI();
			return web;
		}
		protected void onProgressUpdate(String... s) {
			// éxécute dans le thread interface, si le thread non-interface
			// appelle publishProgress à l'intérieur de doInBackground
		}
		protected void onPostExecute(MeteoWebAPI web) {
			// on a fini de charger les informations
			setProgressBarIndeterminateVisibility(false);
			if( web==null ) return;
			if( web.erreur!=null ) {
				Toast.makeText(MeteoDemoActivity.this,web.erreur,Toast.LENGTH_SHORT).show();
				return;
			}
			// ok! on affiche les informations!
			temperature.setText(web.temperature);
			conditions.setText(web.conditions);
			ville.setText(web.ville);
			depuis.setText(web.depuis);
			icone.setImageDrawable(web.icone);
		}

	}	
	

}