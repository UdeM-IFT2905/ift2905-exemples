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
 * des boutons. Différentes variations sont implantées, entre
 * autres texte seul, texte et image, case à cocher, etc.
 * 
 */

public class JolisBoutonsActivity extends Activity implements OnClickListener, OnCheckedChangeListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ////////////////////////////////////////////////////
        ////////////////////////////////////////////////////
        ////////////////////////////////////////////////////
        //
        // le bouton 1 est du texte simple.
        //
        Button b1 = (Button)findViewById(R.id.button1);
        b1.setOnClickListener(this);
        
        ////////////////////////////////////////////////////
        ////////////////////////////////////////////////////
        ////////////////////////////////////////////////////
        //
        // le bouton 2 est un texte spécial...
        // c'est du HTML simple
        // ce genre de texte doit être défini dynamiquement
        //
        
        Button b2 = (Button)findViewById(R.id.button2);
        b2.setOnClickListener(this);

        // on peut définir le texte comme suit...
        b2.setText("Trop simple");

        // on peut utilise du HTML directement
        b2.setText(Html.fromHtml("<b>Bonjour</b><br>C'est <i>moi</i><br><img src=\"http://www.umontreal.ca/images/contenu/capsules/udem.png\">."));

        // on peut aussi utiliser du HTML déposé dans une string (dans string.xml)
        b2.setText(Html.fromHtml(getString(R.string.b2)));
        
        //
        // les tags qui sont utilisables sont documentés ici:
        // http://commonsware.com/blog/Android/2010/05/26/html-tags-supported-by-textview.html
        // (non, les images ne fonctionnent pas...)
        //

        ////////////////////////////////////////////////////
        ////////////////////////////////////////////////////
        ////////////////////////////////////////////////////
        //
        // Le bouton 3 est plus complexe (et plus flexible)
        //
        // >> Utilisez un ListView plutôt qu'un Button.
        // >> Placez tous le composants (les TextView et autres) dans ce ListView
        //
        // >> Propriétés du ListView:  Clickable = True , Focusable = True
        //

        LinearLayout ly3 = (LinearLayout)findViewById(R.id.bouton3);
        ly3.setOnClickListener(this);
        
        ////////////////////////////////////////////////////
        ////////////////////////////////////////////////////
        ////////////////////////////////////////////////////
        //
        // Le bouton 4 est comme le bouton 3, mais avec des OnClick différents
        // pour certains éléments (le checkbox, essentiellement)
        //
        
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

	// pour notre checkbox
	@Override
	public void onCheckedChanged(CompoundButton b, boolean val) {
		Toast.makeText(JolisBoutonsActivity.this,"CHECK = "+val,Toast.LENGTH_SHORT).show();
	}
	
}