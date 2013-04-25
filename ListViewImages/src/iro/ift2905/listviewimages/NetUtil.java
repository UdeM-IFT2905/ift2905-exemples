package iro.ift2905.listviewimages;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

public class NetUtil {

	//
	// lire une image avec un URL
	//
	public static Bitmap loadHttpImage(String url) throws MalformedURLException, IOException {
		URL urlobj = new URL(url);
		HttpURLConnection urlConnection = (HttpURLConnection) urlobj.openConnection();
		
		InputStream is = null;
		Bitmap b = null;
		try
		{
			is = new BufferedInputStream(urlConnection.getInputStream());
			b = BitmapFactory.decodeStream(is);
		}
		finally
		{
			urlConnection.disconnect();
		}
		
		return b;
	}

	//
	// lire une image avec un URL
	//
	public static Drawable loadHttpImageDrawable(String url) throws MalformedURLException, IOException {
		URL urlobj = new URL(url);
		HttpURLConnection urlConnection = (HttpURLConnection) urlobj.openConnection();
		
		InputStream is = null;
		Drawable d = null;
		try
		{
			is = new BufferedInputStream(urlConnection.getInputStream());
			d = Drawable.createFromStream(is, "src");
		}
		finally
		{
			urlConnection.disconnect();
		}
		
		return d;
	}

}
