package com.seboid.meteodb;

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
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.drawable.Drawable;
import android.util.Log;

//
// une classe qui charge une page web et parse le contenu JSON
// NOTE: cette classe NE PEUT PAS toucher à l'interface
//

public class WebAPI {
	// les informations intéressantes
	String temperature;
	String conditions;
	String ville;
	CharSequence depuis;
	long heure;
	Drawable icone;

	// null si pas d'erreur, sinon c'est null.
	String erreur;

	WebAPI(String url) {
		Log.d("meteo2","loading "+url);		
		erreur=null;
		
		try {
			// juste pour faire semblant que c'est long... ;-)
			Thread.sleep(1000);
			
			// lire la page web
			HttpEntity page = getHttp(url);
			
			// JSON format
			JSONObject js = new JSONObject(EntityUtils.toString(page,HTTP.UTF_8));
			// extraire les informations
			JSONObject obs = js.getJSONObject("current_observation");
			
			temperature=obs.getString("temp_c")+"c";
			conditions=obs.getString("weather");
			ville = obs.getJSONObject("display_location").getString("full");
			
			long epoch= Long.parseLong(obs.getString("observation_epoch"));
			Log.d("meteo2","epoch is "+epoch);
			heure=epoch*1000; // heure en millisecondes
			depuis=android.text.format.DateUtils.getRelativeTimeSpanString(epoch*1000);
			
			String iconName = js.getJSONObject("current_observation").getString("icon");
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
        } catch (InterruptedException e) {
			erreur="erreur JSON:"+e.getMessage();
		}
	}

	private HttpEntity getHttp(String url) throws ClientProtocolException, IOException
	{
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setContentCharset(params, "utf-8");

		HttpClient httpClient = new DefaultHttpClient(params);
		//HttpClient httpClient = new DefaultHttpClient();
		HttpGet http = new HttpGet(url);
		HttpResponse response = httpClient.execute(http);
		return response.getEntity();
	}

	private Drawable loadHttpImage(String url) throws ClientProtocolException, IOException {
		InputStream is = getHttp(url).getContent();
		Drawable d = Drawable.createFromStream(is, "src");
		return d;
	}


}
