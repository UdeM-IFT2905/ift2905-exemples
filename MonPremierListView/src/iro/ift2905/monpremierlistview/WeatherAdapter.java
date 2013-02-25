package iro.ift2905.monpremierlistview;

import java.text.DateFormat;
import java.util.List;
import java.util.Date;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * La classe WeatherAdapter est conçue spécifiquement
 * pour relier une ListView avec une liste de
 * WeatherData, permettant d'afficher les données
 * voulues exactement comme désiré. Elle hérite
 * de BaseAdapter, qui est généralement la classe
 * la plus logique à dériver lorsqu'on cherche
 * à créer un adapteur personnalisé.
 * 
 * Dans ce cas, on a aussi choisi de transférer
 * l'interprétation des valeurs numériques
 * de l'activité vers l'adapteur, ce qui
 * dissocie la génération des données avec
 * son interprétation, généralement une bonne
 * pratique. On sauve aussi en espace mémoire
 * en ne gardant pas dans la liste de coûteux
 * Strings, au prix de devoir les regénérer
 * lorsque l'élément de la liste correspondant
 * est reconstruit.
 *
 */

public class WeatherAdapter extends BaseAdapter {
	
	/**
	 * 
	 * Simple classe de stockage, elle permet deux
	 * types de données : l'une stockant des informations
	 * météo, et l'autre ne stockant que du texte, le
	 * "override" qui, en quelque sorte, "surcharge" l'élément
	 * pour n'afficher que du texte.
	 * 
	 * On remarquera ici l'utilisation de propriétés
	 * publiques finales, simplifiant de beaucoup
	 * la classe (il n'est alors pas nécessaire de créer
	 * des méthodes get, car les variables sont automatiquement
	 * invariables une fois le constructeur exécuté).
	 * 
	 */
	public static class WeatherData
	{
		public final int temperatureMin, temperatureMax, conditionID;
		public final Date date;
		public final String override;
		public final boolean isOverriden;
		
		public WeatherData(int tmin, int tmax, int condition, Date d)
		{
			temperatureMax = tmax;
			temperatureMin = tmin;
			conditionID = condition;
			date = d;
			override = "";
			isOverriden = false;
		}
		
		public WeatherData(String override)
		{
			this.override = override;
			this.temperatureMax = this.temperatureMin = this.conditionID = 0;
			this.date = null;
			this.isOverriden = true;
		}
	}

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
	
	private Context _context;
	private List<WeatherData> _data;
	private DateFormat _df;

	/*
	 * Cette méthode est utilisée pour
	 * déterminer la taille actuelle de
	 * la liste. N'oubliez pas de vérifier
	 * que la liste est bien définie
	 * avant de retourner sa taille!
	 * 
	 */
	@Override
	public int getCount() {
		if(_data != null)
			return _data.size();
		else
			return 0;
	}

	/*
	 * Cette méthode retourne l'élément stocké
	 * à l'indice donné. Il n'est pas absolument nécessaire
	 * de vérifier si l'indice est bien dans la liste
	 * (les appels sont généralement bien formulés), mais
	 * cela reste une bonne pratique.
	 * 
	 */
	@Override
	public Object getItem(int at) {
		if(_data != null && at >= 0 && at < _data.size())
			return _data.get(at);
		else
			return null;
	}

	/*
	 * Cette méthode retourne un numéro d'identification
	 * (ID) pour l'élément à l'indice donné. Cet ID
	 * devrait généralement être unique pour l'élément
	 * donné, en plus d'être relié à celui-ci. Dans notre
	 * exemple, on se contente de retourner l'indice, mais
	 * on pourrait par exemple imaginer une liste triable
	 * où l'on voudrait pouvoir retracer un élément
	 * particulier une fois le tri effectué et où
	 * la position n'est plus un ID approprié. On va
	 * souvent utiliser une forme de hachage des données
	 * pour générer cet ID.
	 * 
	 */
	@Override
	public long getItemId(int at) {
		return at;
	}

	/*
	 * Cette méthode est la plus importante à
	 * surcharger dans tout l'adapteur. C'est là
	 * que la logique reliant les données avec
	 * la liste se trouve.
	 * 
	 * Premièrement, on détermine les données se trouvant
	 * à la position demandée. Puis, on génère le View
	 * requis. C'est ici que les choses se compliquent, car il
	 * faut introduire le principe de "recyclage".
	 * 
	 * Lorsque Android crée les éléments de la liste, plusieurs
	 * méthodes sont exécutées. Celles-ci sont assez coûteuses pour
	 * qu'on veuille éviter de les effectuer autant que possible. Ainsi,
	 * lorsque l'usager parcourt la liste, Android va prendre les éléments
	 * de la liste qui ne sont plus visibles (disons les éléments du haut
	 * de la liste pendant que l'usager descend) et les réutiliser au bas
	 * de la liste, évitant d'en créer de nouveaux.
	 * 
	 * C'est pour cette raison que getView reçoit un View en paramètre.
	 * Si celui-ci est nul, c'est que l'élément de la liste doit être
	 * créé de zéro. On va alors utiliser un LayoutInflater pour
	 * créer un nouvel objet View depuis le layout voulu. Si le
	 * View n'est pas nul, c'est qu'on veut réutiliser un View
	 * existant. Il suffit alors de mettre à jour les données
	 * affichées dans le View, évitant de devoir créer un nouvel
	 * objet depuis un layout.
	 * 
	 * À noter que dans le cas spécifique d'une ListView, on doit
	 * utiliser le troisième paramètre de LayoutInflater.inflate et
	 * le mettre à false, sans quoi il sera impossible de créer le View.
	 * 
	 * Une fois le View créé (si nécessaire), on peut définir son
	 * contenu. Ici, on a deux variantes : une prévision météo ou
	 * du texte simple. Dans chaque cas, on prend les Views appropriés,
	 * on les transforme en ImageView ou TextView et on définit les
	 * valeurs comme à l'habitude. Cependant, pour afficher deux
	 * versions différentes à l'aide d'un même layout, on va
	 * cacher un ensemble d'éléments et en montrer un autre, selon
	 * le cas. On pourrait aussi utiliser deux layouts différents,
	 * mais cela devient beaucoup plus complexe et peut réduire
	 * les performances lors du défilement.
	 * 
	 * Pour simplifier le processus, on a défini tous les éléments
	 * de la prévision météo dans un même RelativeLayout. Il suffit
	 * alors de changer la visibilité de celui-ci pour cacher ou montrer
	 * l'ensemble des éléments.
	 * 
	 * À la toute fin du processus, on ajoute une petite touche en
	 * alternant la couleur du fond. Pour ce faire, on vérifie si l'élément
	 * est à un indice pair (% est l'opérateur modulo) et on définit le
	 * fond tout dépendant du résultat de ce test.
	 * 
	 */
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		WeatherData data = _data.get(position);
		
		if(view == null)
		{
			LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.jour_complet, parent, false);
		}
		
		if(_data.get(position).isOverriden)
		{
			TextView mainText = (TextView)view.findViewById(R.id.overrideText);
			mainText.setText(data.override);
			
			mainText.setVisibility(View.VISIBLE);
			view.findViewById(R.id.jourCompletInner).setVisibility(View.INVISIBLE);
		}
		else
		{
			TextView condition = (TextView)view.findViewById(R.id.dayCondition);
			TextView date = (TextView)view.findViewById(R.id.dayDate);
			TextView temperatures = (TextView)view.findViewById(R.id.dayTemperatures);
			ImageView icon = (ImageView)view.findViewById(R.id.dayIcon);

			condition.setText(conditionStrings[data.conditionID]);
			date.setText(_df.format(data.date));
			temperatures.setText(String.format(_context.getResources().getString(R.string.temp), data.temperatureMin, data.temperatureMax));
			icon.setImageResource(conditionIcons[data.conditionID]);
			
			view.findViewById(R.id.overrideText).setVisibility(View.INVISIBLE);
			view.findViewById(R.id.jourCompletInner).setVisibility(View.VISIBLE);
		}
		
		if(position % 2 == 0)
			view.setBackgroundColor(Color.argb(255, 20, 20, 20));
		else
			view.setBackgroundColor(Color.BLACK);
		
		return view;
	}
	
	/**
	 * 
	 * Constructeur de l'adapteur. On notera
	 * l'utilisation de List au lieu de ArrayList;
	 * ceci est une bonne pratique, car List est
	 * une interface générique alors que
	 * ArrayList est une implantation de cette
	 * interface. L'adapteur n'a pas besoin d'une
	 * implantation particulière, tout ce qu'il lui
	 * faut, c'est une liste. Ceci permet donc au programme
	 * utilisant l'adapteur d'utiliser n'importe quelle
	 * implantation de List.
	 * 
	 * De plus, il faut toujours demander un Context,
	 * car ceci est utilisé entre autres pour obtenir
	 * le LayoutInflater nécessaire à l'initialisation
	 * des Views dans getView.
	 * 
	 */
	
	public WeatherAdapter(Context context, List<WeatherData> data)
	{
		_df = DateFormat.getDateInstance(DateFormat.LONG);
		_context = context;
		_data = data;
	}

}
