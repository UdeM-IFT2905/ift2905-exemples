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

public class AnonymousActivity extends Activity {
	
	private String toastText = "Exécution par classe anonyme!";

	/*
	 * Cette implantation utilise une classe dite anonyme
	 * pour réagir aux clics sur le bouton. Une classe
	 * anonyme est définie à même le code, donc sans
	 * nom ou structure prédéterminée. La classe
	 * va généralement implanter une interface en
	 * définissant toutes les méthodes de celle-ci
	 * à même le code.
	 * 
	 * Dans ce cas, la classe anonyme hérite de OnClickListener
	 * et redéfinit onClick pour réagir aux événements de clic
	 * du bouton.
	 * 
	 * On remarquera aussi que la classe anonyme, bien que
	 * techniquement dissociée de la classe principale
	 * AnonymousActivity, a accès aux variables et
	 * méthodes privées de celle-ci. Ceci est une particularité
	 * importante des classes anonymes. Pour avoir accès
	 * aux éléments de la classe anonyme elle-même,
	 * on doit explicitement utiliser le mot-clé this.
	 * 
	 * Pour accéder au mot-clé this de la classe
	 * principale, on doit alors utiliser
	 * <nom de la classe>.this.
	 * 
	 * Cette manière de procéder a l'avantage
	 * d'être assez simple et rapide, sans nécessiter
	 * de préparation ou de structure particulière. Pour
	 * des Listeners simples, c'est une possibilité
	 * intéressante.
	 * 
	 * Cependant, la syntaxe est assez lourde et
	 * la performance n'est pas idéale. De plus,
	 * il n'est pas possible de réutiliser le code
	 * aisément, et toute tentative de le faire
	 * risque de revenir au même que d'utiliser
	 * une autre méthode directement.
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		((TextView)findViewById(R.id.activity_actuelle)).setText("Implantation : classe anonyme");
		
		((Button)findViewById(R.id.bouton_test)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
				
				Intent i = new Intent(AnonymousActivity.this, ImplementsActivity.class);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
