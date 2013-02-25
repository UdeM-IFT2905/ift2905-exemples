package iro.ift2905.monpremierlistview;

import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class MainActivityArray extends Activity {
	
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
				Intent i = new Intent(MainActivityArray.this, MainActivityHashMap.class);
				startActivity(i);
				break;
			}
			case 31:
			{
				Intent i = new Intent(MainActivityArray.this, MainActivityCustom.class);
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
	private ArrayAdapter<String> mainAdapter;
	
	/*
	 * Cette variante utilise un simple tableau
	 * de Strings, donc de texte, pour remplir
	 * le ListView. C'est la manière la plus
	 * directe (et la plus limitée) d'utiliser
	 * un ListView. On initialise ici chaque
	 * élément avec des données de météo de
	 * façon similaire à celle utilisée
	 * dans l'exemple MonPremierAffichage.
	 * 
	 * Nous utilisons aussi une fonctionalité
	 * pratique d'Android sous la forme
	 * de layouts génériques. Si, au lieu d'utiliser
	 * R.*, on utilise android.R.*, on a accès
	 * aux layouts, strings, ids, etc. du système
	 * lui-même, nous permettant de réutiliser
	 * plusieurs fonctions simples. Ici, le
	 * layout simple_list_item_1 est un
	 * layout conçu pour être utilisé dans
	 * un ListView, donnant accès à un seul
	 * TextView.
	 * 
	 * Pour lier le ListView au tableau,
	 * on fait appel à un ArrayAdapter. Cette
	 * classe est dite générique, c'est-à-dire
	 * qu'elle peut être paramétrée selon
	 * un type particulier. En l'occurrence,
	 * le ArrayAdapter nécessite le type
	 * du tableau qui va être utilisé pour
	 * peupler le ListView.
	 * 
	 * Une fois l'adapteur initialisé, il suffit
	 * de le relier à la liste et de définir
	 * un Listener pour les clics sur les éléments.
	 * 
	 * L'avantage indéniable de cette approche est
	 * sa simplicité. Il suffit de quelques lignes
	 * pour peupler une liste fonctionnelle.
	 * 
	 * Le désavantage tout aussi indéniable est
	 * le manque de flexibilité de l'approche. On ne peut
	 * pas définir plus d'une valeur par élément de la liste,
	 * les possibilités de layouts sont très limitées et
	 * la liste doit avoir une taille fixe.
	 * 
	 */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
		GregorianCalendar gc = new GregorianCalendar();
		
		Random r = new Random();
		
		String[] items = new String[32];
		for(int a = 0; a < 30; a++)
		{
			items[a] = df.format(gc.getTime()) + " : " + (String) getText(conditionStrings[r.nextInt(4)]);
			gc.add(GregorianCalendar.DAY_OF_MONTH, 1);
		}
		items[30] = (String) getText(R.string.hash_link);
		items[31] = (String) getText(R.string.custom_link);
		
		mainAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, items);
		
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
