package iro.ift2905.jolisboutons;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 
 * JolisBoutons est une introduction à la modification visuelle
 * des boutons. Quatre variantes sont implantées :
 * - Bouton avec texte simple
 * - Bouton formaté en HTML
 * - Bouton formé par un LinearLayout avec des TextView simples
 * - Bouton formé par un LinearLayout avec des éléments avancés (image, texte, case à cocher)
 * 
 */

public class JolisBoutonsActivity extends Activity implements OnClickListener, OnCheckedChangeListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /*
         * Le bouton 1 est le plus simple. On se sert de la classe
         * Button normale et le texte est directement défini dans
         * le layout.
         */
        Button b1 = (Button)findViewById(R.id.button1);
        b1.setOnClickListener(this);
        
        
        
        /*
         * Le bouton 2 est légèrement plus complexe. On ignore
         * le texte du bouton tel que défini dans le layout
         * et on génère à la place une séquence de texte
         * dite "riche" (avec formatage) depuis du HTML
         * simple.
         */
        Button b2 = (Button)findViewById(R.id.button2);
        b2.setOnClickListener(this);
        
        

        /*
         * La manière la plus simple d'insérer du texte
         * par l'entremise de l'activité.
         */
        b2.setText("Trop simple");

        /*
         * On peut directement insérer du code HTML
         * à l'aide de Html.fromHtml. C'est ici qu'on
         * peut remarquer que setText prend, non pas
         * un objet String, mais bien un objet
         * CharSequence, dont String n'est qu'une variante
         * possible. À noter que les images ne sont
         * pas immédiatement fonctionnelles et
         * demandent à être remplacées ultérieurement,
         * aussi est-il déconseillé de les utiliser
         * de cette manière. Les balises HTML utilisables
         * sont documentées ici :
         * http://commonsware.com/blog/Android/2010/05/26/html-tags-supported-by-textview.html
         */
        b2.setText(Html.fromHtml("<b>Bonjour</b><br>C'est <i>moi</i><br><img src=\"http://www.umontreal.ca/images/contenu/capsules/udem.png\">."));

        /*
         * Finalement, la méthode standard pour gérer
         * du texte en Android, les ressources. On peut
         * stocker du code HTML dans une ressource
         * à l'aide d'une syntaxe particulière
         * (voir strings.xml).
         */
        b2.setText(Html.fromHtml(getString(R.string.b2)));
        
        
        
        /*
         * Le bouton 3 est en vérité un LinearLayout
         * avec les propriétés Clickable et Focusable
         * réglées à "true", ce qui en fait effectivement
         * un bouton. En effet, on pourra remarquer que
         * la méthode setOnClickListener, et donc toute la
         * fonctionalité entourant le clic, fait partie
         * de la classe View, la classe de base pour tous
         * les éléments d'interface. Il est donc possible
         * de transformer n'importe quel élément en bouton
         * si désiré.
         * 
         * Dans ce cas-ci, on se contente de créer un
         * LinearLayout contenant des TextViews et de l'utiliser
         * comme un seul bouton monolithique.
         */
        LinearLayout ly3 = (LinearLayout)findViewById(R.id.bouton3);
        ly3.setOnClickListener(this);
        
        
        
        /*
         * Le bouton 4 est une variante légèrement plus avancée
         * du bouton 3 où on a utilisé des éléments enfants
         * plus complexes, par exemple une image ou une
         * case à cocher. Tous ces éléments étant définis
         * dans le layout principal, ils restent accessibles
         * directement avec findViewById. On utilise cette
         * propriété pour ajouter un événement sur la
         * case à cocher.
         */
        LinearLayout ly4 = (LinearLayout)findViewById(R.id.bouton4);
        ly4.setOnClickListener(this);
        
        CheckBox cb4=(CheckBox)findViewById(R.id.checkBox1);
        cb4.setOnCheckedChangeListener(this);
        
    }

	@Override
	public void onClick(View v) {
		String info="???";
		switch( v.getId() ) {
		case R.id.button1:
			info="Bouton 1!!";
			break;
		case R.id.button2:
			info="Bouton 2!!";
			break;
		case R.id.bouton3:
			info="Bouton 3!!";
			break;
		case R.id.bouton4:
			info="Bouton 4!!";
			break;
		}
		Toast.makeText(this,info,Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onCheckedChanged(CompoundButton b, boolean val) {
		Toast.makeText(JolisBoutonsActivity.this,"CHECK = "+val,Toast.LENGTH_SHORT).show();
	}
	
}