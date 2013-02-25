package iro.ift2905.monpremierlistview;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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

public class MainActivityHashMap extends Activity {
	
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
				Intent i = new Intent(MainActivityHashMap.this, MainActivityArray.class);
				startActivity(i);
				break;
			}
			case 31:
			{
				Intent i = new Intent(MainActivityHashMap.this, MainActivityCustom.class);
				startActivity(i);
				break;
			}
			default:
				Toast.makeText(getApplicationContext(), "Position " + position, Toast.LENGTH_SHORT).show();
					
			}
		}
	}

	private int conditionStrings[] = {
			R.string.cloudy,
			R.string.bad,
			R.string.freezing_rain,
			R.string.sunny
		};
	
	private ListView mainList;
	private SimpleAdapter mainAdapter;
	
	/*
	 * Cette variante utilise une liste de HashMap,
	 * un type de structure de données
	 * souvent utilisé pour stocker des valeurs
	 * de type "paire" (une valeur liée à un
	 * indice). On initialise ici chaque
	 * élément avec des données de météo de
	 * façon similaire à celle utilisée
	 * dans l'exemple MonPremierAffichage.
	 * 
	 * L'intérêt de cette structure est de
	 * permettre d'avoir plusieurs valeurs
	 * différentes pour chaque élément de la liste.
	 * En effet, chaque élément possède son propre
	 * HashMap, qui relie des indices (un String)
	 * à une valeur correspondante (qui peut être
	 * n'importe quel type, mais est généralement
	 * un int ou String). De plus, le ArrayList
	 * est une forme plus rapide de liste chaînée,
	 * permettant ainsi une taille de liste
	 * arbitraire et variable.
	 * 
	 * Pour lier le ListView au tableau,
	 * on fait appel à un SimpleAdapter. Cet adapteur
	 * s'attend à une structure bien spécifique, telle
	 * que montrée dans cet exemple. Il lui faut une
	 * liste de tables de hachage où les clés sont
	 * des textes. Ceci lui permet alors de relier
	 * une clé à une View correspondante. Pour définir
	 * ce lien, on utilise dans le constructeur deux
	 * tableaux de taille égale, de telle sorte
	 * que la clé à l'indice x du premier tablea
	 * va être utilisée pour remplire le View dont
	 * l'ID est donné à l'indice x du second tableau.
	 * 
	 * Une fois l'adapteur initialisé, il suffit
	 * de le relier à la liste et de définir
	 * un Listener pour les clics sur les éléments.
	 * 
	 * Cette technique a l'avantage de rester assez
	 * simple, ne demandant pas de nouvelle classe ou
	 * quoi que ce soit du genre, tout en permettant
	 * de stocker des données beaucoup plus complexes.
	 * Il n'y a aussi plus de limite quant à la taille
	 * de la liste et le Layout utilisé peut être bien
	 * plus avancé, comportant plusieurs éléments.
	 * 
	 * Néanmoins, il reste difficile de créer des
	 * éléments de liste ayant plusieurs types différents,
	 * par exemple des images et du texte. Il serait aussi
	 * très complexe de varier l'apparence des éléments
	 * selon des critères arbitraires, par exemple
	 * alterner les couleurs de fond pour mieux distinguer
	 * les rangées.
	 * 
	 */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
		GregorianCalendar gc = new GregorianCalendar();
		
		Random r = new Random();
		
		ArrayList<HashMap<String,String>> items = new ArrayList<HashMap<String,String>>();
		for(int a = 0; a < 30; a++)
		{
			HashMap<String, String> element = new HashMap<String, String>();
			element.put("day", df.format(gc.getTime()));
			
			int random_int = r.nextInt(4);
			
			int temp_max = r.nextInt(51) - 20;
			int temp_min = temp_max - r.nextInt(20);
			
			element.put("condition", (String) getText(conditionStrings[random_int]));
			element.put("temperature", String.format(getResources().getString(R.string.temp), temp_min, temp_max));
			
			gc.add(GregorianCalendar.DAY_OF_MONTH, 1);
			
			items.add(element);
		}
		
		HashMap<String, String> goto_array = new HashMap<String, String>();
		goto_array.put("condition", (String) getText(R.string.array_link));
		items.add(goto_array);
		

		HashMap<String, String> goto_custom = new HashMap<String, String>();
		goto_custom.put("condition", (String) getText(R.string.custom_link));
		items.add(goto_custom);
		
		mainAdapter = new SimpleAdapter(getApplicationContext(), items, R.layout.jour_simple,
				new String[] { "day", "condition", "temperature" },
				new int[]  { R.id.dayDate, R.id.dayCondition, R.id.dayTemperatures });
		
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
