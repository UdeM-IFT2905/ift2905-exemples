package com.seboid.meteodb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 *
 * On peut extraire la base de donnée de l'émulateur Android pour l'examiner directement.
 * Les DB Android sont des DB SQlite3.
 * 
 * Vous trouverez le DB de cet app dans l'espace fichier de l'app dans l'émulateur:
 * Dans eclipse, choisir la perspective (en haut à droite) "DDMS". Evidemment, vous devez avoir un émulateur activé.
 * 
 * Utilisez la View "File Explorer" et aller dans "data/data/com.seboid.meteodb.com/". C'est là que sont les fichiers de votre app.
 * Allez dans databases, sélectionnez "meteo.db", puis cliquez sur "Pull a file from the device" (en haut à droite du file explorer).
 * Vous aurez une copie local sur votre ordi de la base de donnée.
 * 
 * Pour manipuler ou simplement voir à quoi ressemble votre DB meteo.db, utilisez la commande "sqlite3".
 * 
 * sqlite3 meteo.db
 *  > .help  -> pour avoir la liste des commandes
 *  > .dump  -> pour voir la structure et tout le contenu du DB
 *  > .schema -> pour voir seulement la structure du DB
 *  > .quit ->  devinez à quoi ça sert...
 *
 */


public class DBHelperMeteo extends SQLiteOpenHelper {

	// La version doit être changée si on change quoi que ce soit
	// à la structure (ex: ajouter/enlever des colonnes, des tables, etc...)
	static final int VERSION=2;

	// nom de la table
	static final String TABLE = "meteo";

	//
	// Le nom des colonnes
	//
	// On doit avoir cette colonne pour le CursorAdapter d'Android
	static final String C_ID = "_id";
	static final String C_TEMP = "temperature";
	static final String C_COND = "conditions";
	static final String C_HEURE = "heure";

	// contexte pour afficher des toasts....
	Context context;	

	public DBHelperMeteo(Context context) {
		// le fichier DB est dans /data/data/com.seboid.meteodb/databases (voir DDMS)
		super(context, "meteo.db", null, VERSION);
		this.context=context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Toast.makeText(context, "Création DB",Toast.LENGTH_LONG).show();
		Log.d("DB meteo", "Création DB");
		String sql="create table "+TABLE+" ("
				+C_ID+" integer primary key, "
				+C_TEMP+ " text,"
				+C_COND+ " text,"
				+C_HEURE+ " long )";

		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int ancienneVersion, int nouvelleVersion) {
		// élimine l'ancienne table
		db.execSQL("drop table if exists "+TABLE);
		// création d'une nouvelle table
		onCreate(db);
	}

	//
	// ajout d'un élément dans la DB
	//
	public void AddNewEntry(String temperature, String conditions,long heure) {
		// ouvre la base de donnee
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues val = new ContentValues();
		val.clear();
		// Le ID doit être unique... le temps en milli devrait suffir.
		val.put(C_ID,System.currentTimeMillis());
		val.put(C_TEMP, temperature);
		val.put(C_COND, conditions);
		val.put(C_HEURE, heure);

		try {
			db.insertOrThrow(TABLE, null,val);
		} catch ( SQLException e ) {
			Log.d("DB meteo","Erreur DB: "+e.getMessage());
		}
		db.close();		
	}

	
	//
	// Combien y a-t-il de lignes dans le DB?
	//
	public int querySize() {
		final String[] select = new String[] {C_ID};
		SQLiteDatabase db=this.getReadableDatabase();

		Cursor c=db.query(TABLE,select,null, null, null, null, null);
		int size=c.getCount();
		db.close();
		return size;
	}


}
