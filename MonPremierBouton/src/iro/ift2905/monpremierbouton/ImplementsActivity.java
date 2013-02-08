package iro.ift2905.monpremierbouton;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
 * Notons aussi que cet exemple est le premier qui utilise la notion
 * d'Intent. Un Intent est une manière de signifier au système
 * d'exploitation Android que l'activité cherche à interagir
 * avec quelque chose d'externe à celle-ci. Dans notre cas,
 * on souhaite simplement appeler une autre activité
 * de notre application, mais il est possibile d'utiliser le
 * système d'Intents pour demander accès à la caméra,
 * pour afficher des images dans la gallerie et bien d'autres.
 * Le système d'Intents est l'une des forces d'Android et
 * est extrêmement puissant lorsque bien compris.
 * 
 * De même, l'utilisation de plusieurs activités entraîne
 * aussi la première utilisation non-triviale du bouton
 * Back! N'hésitez pas à utiliser celui-ci pour parcourir
 * non-séquentiellement les quatres implantations.
 * 
 */

public class ImplementsActivity extends Activity implements OnClickListener {
	
	private String toastText = "Exécution par implantation dans l'activité!";

	/*
	 * Cette implantation utilise directement
	 * la classe principale ImplementsActivity
	 * et implante l'interface OnClickListener.
	 * 
	 * Cette manière de procéder permet un
	 * accès direct et logique aux éléments
	 * de la classe principale et est très propre
	 * au niveau du code, le tout sans nécessiter
	 * une structure particulière.
	 * 
	 * Le problème principal est que si la classe
	 * doit gérer plusieurs boutons différents,
	 * la même méthode va être appelée à chaque fois,
	 * forçant le code à faire une validation de
	 * l'objet View passé en paramètre pour déterminer
	 * quel code exécuter lors de l'appel de la
	 * méthode.
	 * 
	 * Cette technique est donc bien adaptée à
	 * des Listeners de toute sorte tant que
	 * le nombre d'éléments réutilisant le même
	 * événement est assez restreint.
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		((TextView)findViewById(R.id.activity_actuelle)).setText("Implantation : dans l'activité");
		
		((Button)findViewById(R.id.bouton_test)).setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
		
		Intent i = new Intent(this, InnerActivity.class);
		startActivity(i);
	}

}
