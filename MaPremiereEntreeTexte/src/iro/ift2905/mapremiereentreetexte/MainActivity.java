package iro.ift2905.mapremiereentreetexte;

import iro.ift2905.mapremiereentreetexte.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * 
 * MaPremièreEntréeTexte introduit les champs de texte. Plusieurs
 * variantes sont implantées et les distinctions sont clairement
 * expliquées. L'interaction entre les types de champ et l'apparence
 * du clavier virtuel est souligné. Les événements uniques aux
 * champs de texte sont aussi introduits.
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
