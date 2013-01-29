package iro.ift2905.monpremierbouton;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * 
 * MonPremierBouton est un exemple centré principalement sur
 * l'interaction avec les boutons. Quatre méthodes de réaction
 * aux événements sont utilisées dans quatre variantes différentes :
 * - Réaction par classe anonyme
 * - Réaction par héritage du Listener approprié dans l'Activity
 * - Réaction par utilisation d'une sous-classe implantant le Listener
 * - Réaction par une classe extern à l'activité
 * Les avantages et désavantages de chaque méthode sont détaillés
 * dans la version appropriée.
 * 
 */

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
