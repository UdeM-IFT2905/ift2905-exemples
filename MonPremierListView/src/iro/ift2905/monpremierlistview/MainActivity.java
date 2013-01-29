package iro.ift2905.monpremierlistview;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * 
 * MonPremierListView introduit la puissante interface qu'est le ListView.
 * Trois variantes sont présentées :
 * - ListView depuis un tableau simple
 * - ListView depuis un HashMap
 * - ListView avec adapteur personnalisé
 * Les avantages et inconvénients de chaque variante sont soulignés.
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
