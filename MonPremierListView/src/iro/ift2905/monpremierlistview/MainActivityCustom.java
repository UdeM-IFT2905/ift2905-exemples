package iro.ift2905.monpremierlistview;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

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

public class MainActivityCustom extends Activity {
	
	/*
	 * Le Listener utilisé reste le même pour toutes les variantes.
	 * Il permet de réagir lorsqu'un clic est effectué sur un
	 * des éléments de la liste. À noter que ceci est un exemple
	 * d'événement qui n'est pas disponible sous forme de
	 * propriété XML : il faut utiliser l'une des méthodes vues
	 * dans MonPremierBouton. Dans ce cas, on a choisi une classe
	 * interne pour simplifier les choses.
	 * 
	 * La réaction à l'événement est ici très simple :
	 * on affiche la position de l'élément cliqué, sauf pour
	 * les éléments 30 et 31, qui sont spécialement
	 * créés pour passer aux autres variantes.
	 * 
	 */
	
	private class MainListOnItemClick implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
			switch(position)
			{
			case 30:
			{
				Intent i = new Intent(MainActivityCustom.this, MainActivityArray.class);
				startActivity(i);
				break;
			}
			case 31:
			{
				Intent i = new Intent(MainActivityCustom.this, MainActivityHashMap.class);
				startActivity(i);
				break;
			}
			default:
				Toast.makeText(getApplicationContext(), "Position " + position, Toast.LENGTH_SHORT).show();
					
			}
		}
	}
	
	private ListView mainList;
	private WeatherAdapter mainAdapter;
	
	/*
	 * Cette variante utilise un adapteur
	 * personnalisé. Cette méthode est
	 * incroyablement plus flexible que
	 * toutes les autres méthodes montrées
	 * jusqu'à présent. Dans cet exemple
	 * on va même jusqu'à définir
	 * une classe spéciale pour stocker
	 * les valeurs de chaque élément de la
	 * liste, donnant un maximum de flexibilité.
	 * 
	 * On remarquera d'ailleurs que le code
	 * s'en trouve allégé, puisqu'on ne fait
	 * que stocker les entiers aléatoires
	 * utilisés pour stocker le type de météo
	 * et la température ainsi qu'un objet
	 * de type Date stockant la date de la
	 * journée affichée dans l'élément.
	 * 
	 * Pour lier le ListView au tableau,
	 * on fait appel à un un WeatherAdapter,
	 * notre adapteur personnalisé. Celui-ci
	 * nécessite une liste de WeatherData,
	 * notre structure de stockage d'information
	 * pour chaque élément. Les détails d'implantation
	 * se trouvent dans la classe elle-même. On
	 * notera une fois de plus la simplicité du code
	 * et la dissociation entre la logique de haut niveau
	 * (stockage et génération des données) et celle
	 * de plus bas niveau (sélection du layout,
	 * affichage des données).
	 * 
	 * Une fois l'adapteur initialisé, il suffit
	 * de le relier à la liste et de définir
	 * un Listener pour les clics sur les éléments.
	 * 
	 * Cette approche possède une flexibilité
	 * sans pareil. On peut sauvegarder exactement
	 * les données nécessaires sans devoir faire
	 * de conversion. On peut aller plus loin
	 * en définissant des structures spécifiques
	 * adaptées à notre application. Ici, par exemple,
	 * on veut pouvoir afficher une liste de prédictions
	 * météo, mais aussi parfois du texte seul (pour les
	 * liens vers les autres versions). On peut simplement
	 * définir notre structure de stockage de telle sorte
	 * que ce genre d'usage est simple et le tour est joué!
	 * 
	 * Le désavantage de cette méthode est qu'elle nécessite
	 * la création d'un adapteur personnalisé. Ceci n'est
	 * pas extrêmement complexe, mais cela demande plus
	 * de travail que les méthodes précédentes.
	 * 
	 */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		GregorianCalendar gc = new GregorianCalendar();
		
		Random r = new Random();
		
		ArrayList<WeatherAdapter.WeatherData> weatherData = new ArrayList<WeatherAdapter.WeatherData>();
		for(int a = 0; a < 30; a++)
		{			
			int random_int = r.nextInt(4);
			
			int temp_max = r.nextInt(51) - 20;
			int temp_min = temp_max - r.nextInt(20);
			
			Date d = gc.getTime();
			
			gc.add(GregorianCalendar.DAY_OF_MONTH, 1);
			
			weatherData.add(new WeatherAdapter.WeatherData(temp_min, temp_max, random_int, d));
		}

		weatherData.add(new WeatherAdapter.WeatherData((String)getText(R.string.array_link)));
		weatherData.add(new WeatherAdapter.WeatherData((String)getText(R.string.hash_link)));
		
		mainAdapter = new WeatherAdapter(getApplicationContext(), weatherData);
		
		mainList = (ListView)findViewById(R.id.mainList);
		mainList.setAdapter(mainAdapter);
		mainList.setOnItemClickListener(new MainListOnItemClick());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
