package iro.ift2905.monpremiertoast;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

/**
 * 
 * MonPremierToast est un exemple de base qui donne une vue d'ensemble
 * sur le cycle de vie d'une activité Android, notamment les différents
 * états et événements liés aux changements entre ces états. Les "toasts"
 * et l'utilisation des logs sont aussi introduits.
 * 
 * À noter que seules onCreate et onCreateOptionsMenu sont généralement
 * considérées comme nécessaires au bon fonctionnement de l'activité.
 * Toutes les autres méthodes surchargées sont optionnelles et ne devraient
 * être définies que si nécessaire.
 * 
 * @see <a href="http://developer.android.com/training/basics/activity-lifecycle/starting.html">Starting an Activity</a>
 * @see <a href="http://developer.android.com/reference/android/app/Activity.html">Activity</a>
 * 
 * L'application est aussi un exemple d'utilisation des classes Toast
 * et Log.
 * 
 * Un "toast" est une notification d'Android apparaissant
 * au bas de l'écran. Elle nécessite un contexte, habituellement
 * celui de l'application qui crée le toast, ainsi qu'un message
 * et une durée (LENGTH_SHORT ou LENGTH_LONG). À noter
 * que la méthode Toast.makeText crée un objet Toast,
 * mais ne l'affiche pas. Il faut appeler la méthode Toast.show
 * sur cet objet pour l'afficher! Ce comportement permet
 * de réutiliser le même objet Toast si désiré.
 * 
 * La classe Log est une classe utilitaire purement statique
 * permettant à une application d'envoyer des messages
 * vers un ordinateur connecté. On va généralement utiliser
 * Eclipse et le SDK Android pour voir ces messages grâce à
 * l'onglet LogCat. La classe Log donne accès à une variété de
 * fonctions, tout dépendant du type de message désiré. Les
 * plus communes seront d (débogage), i (information),
 * w (avertissement, "warn") et e (erreur). Toutes les fonctions
 * nécessitent au minimum un "tag" et un message, le tag servant
 * à classifier le message de manière plus spécifique que le type.
 * Par exemple, chaque activité dans une grande application
 * pourrait avoir un tag unique, en plus de la sévérité de l'erreur.
 * 
 */
public class MainActivity extends Activity {

	/* Appelée lors du démarrage initial de l'activité.
	 * Une fois exécutée, l'activité se trouve dans l'état
	 * "créé" (created), un état transitoire qui appelle
	 * rapidement la méthode onStart.
	 * 
	 * L'activité n'est pas encore visible à cette étape.
	 * 
	 * Contrairement aux autres méthodes du cycle de vie, la méthode
	 * onCreate est nécessaire à la majorité des activités;
	 * c'est là que le layout est initialisé et c'est aussi le
	 * meilleur endroit où effectuer la majorité de l'initialisation
	 * de l'activité.
	 * 
	 * Il est absolument obligatoire d'appeler la méthode
	 * super.onCreate pour que l'activité s'exécute correctement.
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * @see iro.ift2905.monpremiertoast.MainActivity#onStart
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Toast.makeText(getApplicationContext(), "onCreate", Toast.LENGTH_SHORT).show();
		Log.d("MonPremierToast", "onCreate appelé!");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	/* Appelée après onCreate ou onRestart, tout dépendant de la situation.
	 * À partir de cette méthode, l'activité est dite "visible", mais n'est
	 * pas nécessairement en avant-plan et prête à recevoir des entrées utilisateur.
	 * 
	 * C'est l'endroit idéal pour initialiser les objets qui sont liés à l'interaction
	 * avec l'utilisateur.
	 * 
	 * Il est absolument obligatoire d'appeler la méthode
	 * super.onStart pour que l'activité s'exécute correctement.
	 * 
	 * @see android.app.Activity#onStart()
	 */
	@Override
	public void onStart()
	{
		super.onStart();
		Toast.makeText(getApplicationContext(), "onStart", Toast.LENGTH_SHORT).show();
		Log.d("MonPremierToast", "onStart appelé!");
	}
	
	/* Appelée après onRestart ou onPause, l'activité est alors prête à
	 * interagir avec l'utilisateur. L'activité est alors dans l'état
	 * "reprise" (resumed).
	 * 
	 * C'est l'endroit idéal pour activer des animations ou pour prendre
	 * le contrôle de fonctionalités à accès exclusif, car l'utilisateur
	 * est garanti d'être focusé sur l'activité dès cet instant.
	 * 
	 * Il faut cependant noter que onResume n'est pas un indicateur
	 * parfait de la visibilité de l'activité, car elle peut être
	 * appelée alors qu'une fenêtre système (par exemple l'écran de
	 * verrouillage) est active et cache l'activité.
	 * 
	 * Il est absolument obligatoire d'appeler la méthode
	 * super.onResume pour que l'activité s'exécute correctement.
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	public void onResume()
	{
		super.onResume();
		Toast.makeText(getApplicationContext(), "onResume", Toast.LENGTH_SHORT).show();
		Log.d("MonPremierToast", "onResume appelé!");
	}
	
	/* Appelée lorsqu'une autre activité est affichée. L'activité n'est
	 * pas nécessairement arrête ou détruite, mais perd la possibilité d'interagir
	 * avec l'utilisateur. Elle est encore visible à l'utilisateur, mais se trouve
	 * en arrière-plan.
	 * 
	 * C'est ici que les animations devraient être arrêtées, ainsi que tout accès
	 * exclusif à une fonctionalité libéré. À noter que la nouvelle activité
	 * à afficher ne sera pas créée tant que onPause ne s'est pas terminée; aussi,
	 * il est important de ne pas exécuter de longues actions dans celle-ci.
	 * 
	 * C'est aussi l'emplacement où tout état persistant modifié par l'activité
	 * devrait être sauvegardé, par exemple des entrées effectuées par l'utilisateur.
	 * Il peut en effet arriver que l'activité soit détruite pour libérer de
	 * l'espace mémoire et ce sans appeler d'autres méthodes du cycle de vie.
	 * 
	 * Généralement, onPause sera suivie par onStop. Cependant, si la nouvelle
	 * activité ne cache pas entièrement l'activité existante, il est
	 * possible que onStop ne soit pas appelée.
	 * 
	 * Il est absolument obligatoire d'appeler la méthode
	 * super.onPause pour que l'activité s'exécute correctement.
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	public void onPause()
	{
		super.onPause();
		Toast.makeText(getApplicationContext(), "onPause", Toast.LENGTH_SHORT).show();
		Log.d("MonPremierToast", "onPause appelé!");
	}
	
	/* Appelée lorsque l'activité n'est plus visible du tout. L'activité
	 * est alors dans l'état "caché" (hidden) et peut rester dans cet état
	 * pendant une durée prolongée, soit jusqu'à sa fermeture totale ou
	 * sa réouverture par l'utilisateur.
	 * 
	 * Il n'est pas garanti que onStop sera appelée lors de la fermeture
	 * de l'activité, aussi aucune action critique à la sauvegarde des
	 * données éditées par l'activité ne devrait être exécutée ici.
	 * 
	 * Il est absolument obligatoire d'appeler la méthode
	 * super.onStop pour que l'activité s'exécute correctement.
	 * 
	 * @see android.app.Activity#onStop()
	 */
	@Override
	public void onStop()
	{
		Toast.makeText(getApplicationContext(), "onStop", Toast.LENGTH_SHORT).show();
		Log.d("MonPremierToast", "onStop appelé!");
		super.onStop();
	}
	
	/* Appelée après onStop et avant onStart, c'est l'endroit utilisé
	 * pour réinitialiser des objets créés dans onCreate et arrêtés
	 * dans onStop. L'utilité de cette méthode est assez limitée.
	 * 
	 * Il est absolument obligatoire d'appeler la méthode
	 * super.onRestart pour que l'activité s'exécute correctement.
	 * 
	 * @see android.app.Activity#onRestart()
	 */
	@Override
	public void onRestart()
	{
		super.onRestart();
		Toast.makeText(getApplicationContext(), "onRestart", Toast.LENGTH_SHORT).show();
		Log.d("MonPremierToast", "onRestart appelé!");
	}
	
	/* Appelée lorsque l'activité est détruite. C'est l'endroit où
	 * les ressources utilisées par l'activité peuvent être détruites,
	 * par exemple dans le cadre d'une application contenant plusieurs
	 * activités. Il est alors possible que l'activité détruite utilise
	 * des ressources qui ne sont plus nécessaires (par exemple des threads).
	 * Détruire ces ressources permet de libérer de l'espace mémoire
	 * pour le reste de l'application.
	 * 
	 * Tout comme onStop, onDestroy n'est pas garantie d'être exécutée!
	 * Toute sauvegarde de données devrait se faire dans onPause.
	 * 
	 * Il est absolument obligatoire d'appeler la méthode
	 * super.onDestroy pour que l'activité s'exécute correctement.
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	public void onDestroy()
	{
		Toast.makeText(getApplicationContext(), "onDestroy", Toast.LENGTH_SHORT).show();
		Log.d("MonPremierToast", "onDestroy appelé!");
		super.onDestroy();
	}
}
