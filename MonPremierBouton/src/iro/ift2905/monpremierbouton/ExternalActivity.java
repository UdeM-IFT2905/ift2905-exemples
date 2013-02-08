package iro.ift2905.monpremierbouton;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

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

public class ExternalActivity extends Activity {

	/*
	 * Cette implantation utilise
	 * une classe externe qui
	 * n'a aucun lien avec la classe
	 * principale. Elle permet
	 * de gérer tous les événements
	 * de manière centralisée, même à
	 * travers plusieurs activités
	 * différentes. Cependant,
	 * elle nécessite alors des informations
	 * particulières, par exemple une référence
	 * à l'activité elle-même et à tout
	 * autre objet privé ou protégé qui pourrait
	 * être requis par le Listener.
	 * 
	 * Ce genre d'implantation est utile
	 * pour de très gros projets où les
	 * Listeners se ressemble beaucoup entre
	 * des activités différentes. On pourrait
	 * modifier l'implantation actuelle pour
	 * utiliser le Singleton et n'avoir donc
	 * qu'un seul objet Listener, ou passer
	 * en paramètres des détails plus spécifiques
	 * sur l'implantation nécessitée par le bouton
	 * en question. L'impossibilité d'accéder
	 * aux membres de la classe appelant le Listener
	 * reste néanmoins une importante restriction
	 * qui peut par exemple empêcher d'appeler
	 * des méthodes privées nécessaires à l'exécution
	 * du Listener.
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		((TextView)findViewById(R.id.activity_actuelle)).setText("Implantation : dans une classe externe");
		
		((Button)findViewById(R.id.bouton_test)).setOnClickListener(new ExternalClickListener(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
