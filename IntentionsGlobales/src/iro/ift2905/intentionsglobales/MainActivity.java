package iro.ift2905.intentionsglobales;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * 
 * IntentionsGlobales montre comment utiliser les intentions pour
 * interagir avec certaines composantes du système Android, par
 * exemple la caméra ou le GPS. L'utilisation des intentions
 * pour retourner des données d'une activité appelée vers une
 * activité appelante est montrée.
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
