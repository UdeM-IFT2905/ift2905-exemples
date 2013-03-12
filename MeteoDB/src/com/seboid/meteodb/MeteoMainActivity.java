package com.seboid.meteodb;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//
// Ici on refactorise le code:
// 

public class MeteoMainActivity extends Activity implements OnClickListener {

	// les composants d'interface
	Button actualiser;
	TextView temperature;
	TextView conditions;
	TextView ville;
	TextView depuis;
	ImageView icone;

	TextView infodb;
	
	Button listedb1,listedb2,listedb3;


	// pour acceder aux information meteo (Web JSON)
	WebAPI web;

	// acces a notre DB
	DBHelperMeteo dbh;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// pour le progress bar rotatif
		// (faire avant setContentView )
		getWindow().requestFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		setContentView(R.layout.meteomain);

		// les composants d'interface
		actualiser=(Button)findViewById(R.id.actualiser);
		conditions=(TextView)findViewById(R.id.conditions);
		temperature=(TextView)findViewById(R.id.temperature);
		ville=(TextView)findViewById(R.id.ville);
		depuis=(TextView)findViewById(R.id.heure);
		icone=(ImageView)findViewById(R.id.icone);

		infodb=(TextView)findViewById(R.id.infodb);
		
		listedb1=(Button)findViewById(R.id.listedb1);
		listedb2=(Button)findViewById(R.id.listedb2);
		listedb3=(Button)findViewById(R.id.listedb3);


		actualiser.setOnClickListener(this);
		listedb1.setOnClickListener(this);
		listedb2.setOnClickListener(this);
		listedb3.setOnClickListener(this);

		// Acces au DB
		dbh = new DBHelperMeteo(this);

		// un peu d'info sur la db
		infodb.setText("( "+dbh.querySize()+" éléments )");

	}


	@Override
	public void onClick(View v) {
		Intent in;
		switch( v.getId() ) {
		case R.id.actualiser:
			// mettre a jour la meteo (et ajoute une entree DB)
			new ReadMeteoTask().execute();
			break;
		case R.id.listedb1:
			// afficher le contenu DB au complet
			in=new Intent(this,DBListActivity.class);
			in.putExtra("affichage","complet");
			startActivity(in);
			break;
		case R.id.listedb2:
			// afficher le contenu DB de la derniere heure
			in=new Intent(this,DBListActivity.class);
			in.putExtra("affichage","recent");
			startActivity(in);
			break;
		case R.id.listedb3:
			// afficher le contenu DB trié
			in=new Intent(this,DBListActivity.class);
			in.putExtra("affichage","trie");
			startActivity(in);
			break;
		}
	}


	// lecture de conditions meteo actuelles
	// les parametres sont <startparams, progress, results>	
	private class ReadMeteoTask extends AsyncTask<String, String, WebAPI> {
		protected void onPreExecute() {
			setProgressBarIndeterminateVisibility(true);
		}
		protected WebAPI doInBackground(String... params) {
			WebAPI web=new WebAPI("http://api.wunderground.com/api/14d1606932624c49/conditions/q/zmw:00000.1.71627.json");
			return web;
		}
		//			protected void onProgressUpdate(String... s) {
		//				//Toast.makeText(MeteoSimpleActivity3.this, s[0], Toast.LENGTH_SHORT).show();
		//			}
		protected void onPostExecute(WebAPI web) {
			setProgressBarIndeterminateVisibility(false);

			if( web==null ) return;
			if( web.erreur!=null ) {
				Toast.makeText(MeteoMainActivity.this,web.erreur,Toast.LENGTH_SHORT).show();
				return;
			}
			// ok! on affiche les informations!
			MeteoMainActivity.this.temperature.setText(web.temperature);
			MeteoMainActivity.this.conditions.setText(web.conditions);
			MeteoMainActivity.this.ville.setText(web.ville);
			MeteoMainActivity.this.depuis.setText(web.depuis);
			MeteoMainActivity.this.icone.setImageDrawable(web.icone);

			// ajoute cette information dans le DB
			MeteoMainActivity.this.dbh.AddNewEntry(web.temperature,web.conditions,web.heure);	
			
			// un peu d'info sur la db (mise a jour)
			MeteoMainActivity.this.infodb.setText("( "+MeteoMainActivity.this.dbh.querySize()+" éléments )");
		}

	}	









}