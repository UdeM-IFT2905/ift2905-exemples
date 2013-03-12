package com.seboid.meteodb;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

public class DBListActivity extends ListActivity {

	// type d'affichage (passé au début de l'activité)
	// "complet", "recent", ou "trie"
	String affichage;

	// Gestion DB
	DBHelperMeteo dbh;

	// DB ouvert (doit rester ouvert pendant l'affichage)
	SQLiteDatabase db;
	// le résultat d'un query est toujours dans un Cursor.
	// ce cursor est ensuite utilisé par l'adapter pour l'affichage
	Cursor cursor;

	SimpleCursorAdapter adapter;

	// les colonnes qui seront retournées dans le query SQL
	final String[] select = new String[] {
			DBHelperMeteo.C_ID,
			DBHelperMeteo.C_COND,
			DBHelperMeteo.C_HEURE,
			DBHelperMeteo.C_TEMP
	};

	// les colonnes à afficher dans les éléments du "to"
	static final String[] from = new String[] {
		DBHelperMeteo.C_COND,
		DBHelperMeteo.C_TEMP,
		DBHelperMeteo.C_HEURE,
		DBHelperMeteo.C_HEURE
	};
	// le Id des éléments assignés aux colonnes du "from"
	static final int[] to = new int[] {
		R.id.rcond,
		R.id.rtemp,
		R.id.rheure,
		R.id.rdepuis
	};


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// complet|recent|trie
		affichage=getIntent().getStringExtra("affichage");

		// acces au DB
		dbh=new DBHelperMeteo(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// on ouvre le DB quand l'activité (re-)démarre
		// on doit garder db ouvert pendant l'affichage
		db=dbh.getReadableDatabase();

		if( affichage==null || affichage.equals("complet")) {
			// on va chercher tous les éléments du DB
			cursor=db.query(DBHelperMeteo.TABLE,select,null, null, null, null, null);
		}else if( affichage.equals("recent")) {
			// si on voulait seulement les éléments dont l'heure est récente
			long maintenant=System.currentTimeMillis();
			long ilya1heure=maintenant-3600*1000; // il y a une heure, en millisecondes
			cursor=db.query(DBHelperMeteo.TABLE,select,DBHelperMeteo.C_HEURE+">"+ilya1heure, null, null, null, null);
		}else {
			// trié du plus récent au plus ancien
			cursor=db.query(DBHelperMeteo.TABLE,select,null, null, null,null, DBHelperMeteo.C_HEURE+" DESC");			
		}

		startManagingCursor(cursor);

		adapter = new SimpleCursorAdapter(this,R.layout.rangee,cursor,from,to);
		adapter.setViewBinder(VIEW_BINDER);

		setListAdapter(adapter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// on ferme l'acces au DB.
		// un nouveau cursor sera formé quand on repassera par resume()
		db.close();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	static final ViewBinder VIEW_BINDER = new ViewBinder() {
		@Override
		public boolean setViewValue(View v, Cursor c, int index) {
			switch( v.getId() ) {
			case R.id.rcond:
			case R.id.rtemp:
				return(false); // on laisse android afficher directement
			case R.id.rheure:
				((TextView)v).setText("(time:"+c.getLong(index)+")");
				return(true);
			case R.id.rdepuis:
				long heure=c.getLong(index);
				String depuis=android.text.format.DateUtils.getRelativeTimeSpanString(heure).toString();
				((TextView)v).setText(depuis);
				return(true);
			}
			return false;
		}	    	
	};


}

