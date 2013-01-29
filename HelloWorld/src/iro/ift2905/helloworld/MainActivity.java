package iro.ift2905.helloworld;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * 
 * HelloWorld est le premier exemple d'un projet Android. Il contient
 * une activité de base et la mise en page par défaut. C'est le projet
 * qui est généré lors de la création initiale d'un projet à travers
 * Eclipse et le SDK Android.
 * 
 * Le code ci-dessous est exactement celui d'un projet Android vide.
 * Il utilise un layout, "activity_main.xml", se trouvant dans le
 * dossier res/layout/, pour afficher le traditionnel message
 * "Hello World!"
 * 
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
