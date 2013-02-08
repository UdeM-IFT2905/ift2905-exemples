package iro.ift2905.monpremieraffichage;

import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.widget.ImageView;;

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
	
	private int conditionStrings[] = {
			R.string.cloudy,
			R.string.bad,
			R.string.freezing_rain,
			R.string.sunny
		};
	private int conditionIcons[] = {
			R.drawable.cloudy,
			R.drawable.fire,
			R.drawable.ice,
			R.drawable.sunny
		};
	private int conditionDescriptionStrings[] = {
			R.string.cloudy_desc,
			R.string.bad_desc,
			R.string.freezing_rain_desc,
			R.string.sunny_desc
		};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	/* 
	 * La seule fonctionalité interactive du
	 * code est la génération, à chaque ouverture
	 * de l'application, d'une nouvelle "prédiction"
	 * purement aléatoire. Ceci est fait à l'aide de
	 * la classe Random standard et de quelques
	 * fonctionnalités Android.
	 * 
	 * findViewById est une méthode de View et
	 * Activity permettant d'obtenir un objet
	 * de type View depuis un ID XML. Il est
	 * important de noter que la méthode
	 * échouera si l'élément ciblé ne fait
	 * pas partie de l'ensemble des éléments
	 * enfants de l'activité ou de l'objet View.
	 * 
	 * Une fois l'objet View obtenu, on doit
	 * faire un "cast" sur celui-ci pour
	 * avoir accès aux méthodes particulières
	 * à l'implantation recherchée, ici
	 * TextView et ImageView pour changer
	 * le texte et l'image, respectivement.
	 * 
	 * setText et setImageResource peuvent
	 * prendre plusieurs valeurs, y compris
	 * des valeurs statiques définies à
	 * même le code. Il est cependant beaucoup
	 * plus intéressant d'utiliser les ressources
	 * du projet pour le faire.
	 * 
	 * Cette méthode fait aussi usage de quelques
	 * classes et méthodes liées aux dates. Il
	 * n'est pas nécessaire de connaître l'usage
	 * exact de celles-ci, mais la documentation
	 * Android couvre chacune si la curiosité vous prend.
	 * 
	 * NB : le layout de l'activité utilise include
	 * pour le même sous-layout deux fois, ce qui duplique
	 * tous les éléments. On notera que findViewById se contente
	 * de retourner la première instance de l'objet ayant l'ID
	 * demandé. Cette entorse à l'utilisation correcte des
	 * layouts est utilisée pour montrer les problèmes pouvant
	 * être encourus.
	 * 
	 * Si on cherche à réutiliser un layout plusieurs fois
	 * en changeant les données contenues dans celui-ci,
	 * on privilégiera plutôt un ListView.
	 * 
	 */
	@Override
	protected void onResume() {
		Random r = new Random();
		
		int today = r.nextInt(conditionStrings.length);
		int tomorrow = r.nextInt(conditionStrings.length);

		((TextView)findViewById(R.id.dayConditionNow)).setText(conditionStrings[today]);
		((ImageView)findViewById(R.id.dayIconNow)).setImageResource(conditionIcons[today]);
		((TextView)findViewById(R.id.dayMoreNow)).setText(conditionDescriptionStrings[today]);

		((TextView)findViewById(R.id.dayCondition)).setText(conditionStrings[tomorrow]);
		((ImageView)findViewById(R.id.dayIcon)).setImageResource(conditionIcons[tomorrow]);
		
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
		GregorianCalendar gc = new GregorianCalendar();

		((TextView)findViewById(R.id.dayDateNow)).setText(df.format(gc.getTime()));
		
		gc.add(GregorianCalendar.DAY_OF_MONTH, 1);
		
		((TextView)findViewById(R.id.dayDate)).setText(df.format(gc.getTime()));

		int today_max = r.nextInt(51) - 20;
		int today_min = today_max - r.nextInt(20);
		int tomorrow_max = r.nextInt(51) - 20;
		int tomorrow_min = tomorrow_max - r.nextInt(20);
		
		((TextView)findViewById(R.id.dayTemperaturesNow)).setText(String.format(getResources().getString(R.string.temp), today_min, today_max));
		((TextView)findViewById(R.id.dayTemperatures)).setText(String.format(getResources().getString(R.string.temp), tomorrow_min, tomorrow_max));
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
