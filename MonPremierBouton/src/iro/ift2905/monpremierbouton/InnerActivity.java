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

public class InnerActivity extends Activity {
	
	private String toastText = "Exécution par implantation dans une classe interne!";

	/*
	 * Cette implantation utilise
	 * une classe interne à la classe
	 * principale InnerActivity nommée
	 * InnerActivityClickHandler qui
	 * elle-même implante l'interface
	 * OnClickListener. Cette manière
	 * de procéder est donc une sorte
	 * d'hybride des méthodes par
	 * classe anonyme et par implantation
	 * directe. Au lieu de créer une
	 * nouvelle classe anonyme à chaque
	 * fois, on instancie une classe
	 * bien définie. On notera que,
	 * tout comme pour une classe anonyme,
	 * la classe interne a accès aux
	 * éléments de la classe principale.
	 * 
	 * Cette implantation est plus
	 * propre et concise que l'implantation
	 * par classe anonyme, mais reste plus lourde
	 * que l'implantation directe. Elle permet
	 * la réutilisation du code sans nécessiter
	 * l'évaluation de tous les événements d'un
	 * même type par une même méthode. On peut
	 * par exemple imaginer certains boutons
	 * ayant un comportement similaire comme étant
	 * gérés par la même classe interne, avec d'autres
	 * boutons gérés par d'autre classes internes.
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		((TextView)findViewById(R.id.activity_actuelle)).setText("Implantation : dans une classe interne");
		
		((Button)findViewById(R.id.bouton_test)).setOnClickListener(new InnerActivityClickHandler());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	private class InnerActivityClickHandler implements OnClickListener
	{
		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
			
			Intent i = new Intent(InnerActivity.this, ExternalActivity.class);
			startActivity(i);
		}
	}

}
