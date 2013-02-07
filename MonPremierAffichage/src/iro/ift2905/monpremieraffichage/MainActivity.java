package iro.ift2905.monpremieraffichage;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * 
 * MonPremierAffichage est une introduction à la mise en page
 * et aux propriétés visuelles de composantes de base de l'interface
 * d'Android. L'utilisation et la configuration de boîtes de texte statique
 * et d'images sont montrées. Quelques propriétés de base des éléments
 * ainsi que les "layouts" et certains concepts tels le "anchoring"
 * sont introduits.
 * 
 * L'exemple est thématiquement centré sur une application météo.
 * 
 * 
 * Les icônes sont une gracieuseté d'Antonis Theodorakis sous
 * license CC BY-NC-ND 3.0.
 * @see <a href="http://bit.ly/fR3wKk">Page Behance des icônes</a>
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
