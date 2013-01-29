package iro.ift2905.intentionslocales;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * 
 * IntentionsLocales explique l'utilisation d'intentions (Intents)
 * dans Android pour facilement passer d'une activité à une autre
 * tout en restant dans la même application. L'utilisation d'intentions
 * pour passer des paramètres ou des données est aussi montrée.
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
