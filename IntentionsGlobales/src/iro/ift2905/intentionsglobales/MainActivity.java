package iro.ift2905.intentionsglobales;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * 
 * IntentionsGlobales montre comment utiliser les intentions pour
 * interagir avec certaines composantes du système Android, par
 * exemple la caméra ou le GPS. L'utilisation des intentions
 * pour retourner des données d'une activité appelée vers une
 * activité appelante est montrée.
 * 
 */

public class MainActivity extends Activity {
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

	EditText url, streetAddress;
	ImageButton cameraButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		url = (EditText)findViewById(R.id.url);
		((Button)findViewById(R.id.urlSearch)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Uri searchAddress = Uri.parse(url.getText().toString());
				Intent search = new Intent(android.content.Intent.ACTION_VIEW, searchAddress);
				
				try
				{
					startActivity(search);
				} catch(Exception e)
				{
					Toast.makeText(getApplicationContext(), "Impossible de lancer un navigateur!", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		streetAddress = (EditText)findViewById(R.id.streetAddress);
		((Button)findViewById(R.id.addressSearch)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String address = streetAddress.getText().toString().replace(' ', '+');
				Uri searchAddress = Uri.parse("geo:0,0?q=" + address);
				Intent search = new Intent(android.content.Intent.ACTION_VIEW, searchAddress);
				
				try
				{
					startActivity(search);
				} catch(Exception e)
				{
					Toast.makeText(getApplicationContext(), "Impossible de lancer une carte!", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		cameraButton = ((ImageButton)findViewById(R.id.cameraButton));
		
		cameraButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

				try
				{
					startActivityForResult(camera, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
				} catch(Exception e)
				{
					Toast.makeText(getApplicationContext(), "Impossible d'accéder à la caméra!", Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
		{
			if(resultCode == RESULT_OK)
			{
				Bundle extras = data.getExtras();
				cameraButton.setImageBitmap((Bitmap)extras.get("data"));
			}
		}
	}

}
