package com.seboid.meteodemo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.drawable.Drawable;

//
// une classe qui charge une page web et parse le contenu JSON
// NOTE: cette classe NE DOIT PAS toucher à l'interface
//

public class MeteoWebAPI {
	// les informations intéressantes
	String temperature;
	String conditions;
	String ville;
	CharSequence depuis;
	Drawable icone;

	// null si pas d'erreur
	String erreur;

	MeteoWebAPI() {
		erreur=null;
		
		String url="http://api.wunderground.com/api/14d1606932624c49/conditions/lang:FR/q/zmw:00000.1.71627.json";
		
		try {
			// lire la page web
			HttpEntity page = getHttp(url);
			
			// JSON format
			JSONObject js = new JSONObject(EntityUtils.toString(page,HTTP.UTF_8));
			// extraire les informations courantes
			JSONObject obs = js.getJSONObject("current_observation");

			temperature=obs.getString("temp_c")+"c";
			conditions=obs.getString("weather");
			ville = obs.getJSONObject("display_location").getString("full");
			
			long epoch= Long.parseLong(obs.getString("observation_epoch"));
			depuis=android.text.format.DateUtils.getRelativeTimeSpanString(epoch*1000);
			
			String iconName = obs.getString("icon");
			if( iconName!=null ) {
				Calendar cal= Calendar.getInstance();
				int h=cal.get(Calendar.HOUR_OF_DAY);
				// on devrait comparer a astronomy:sunset et sunrise
				icone=loadHttpImage("http://icons.wxug.com/i/c/a/"+((h>16 || h<7)?"nt_":"")+iconName+".gif");
			}
		} catch (ClientProtocolException e) {
			erreur="erreur http(protocol):"+e.getMessage();
		} catch (IOException e) {
			erreur="erreur http(IO):"+e.getMessage();
		} catch (ParseException e) {
			erreur="erreur JSON(parse):"+e.getMessage();
		} catch (JSONException e) {
			erreur="erreur JSON:"+e.getMessage();
		}
	}

	//
	// lire une page web et retourner le contenu
	//
	private HttpEntity getHttp(String url) throws ClientProtocolException, IOException 
	{
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet http = new HttpGet(url);
		HttpResponse response = httpClient.execute(http);
		return response.getEntity();    		
	}
		
	
	//
	// lire une image avec un URL
	//
	private Drawable loadHttpImage(String url) throws ClientProtocolException, IOException {
		InputStream is = getHttp(url).getContent();
		Drawable d = Drawable.createFromStream(is, "src");
		return d;
	}


}
