package iro.ift2905.monpremierbouton;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

class ExternalClickListener implements OnClickListener
{
	private Activity caller;
	
	public ExternalClickListener(Activity activity)
	{
		caller = activity;
	}
	
	private String toastText = "Exécution par implantation dans une classe externe!";
	@Override
	public void onClick(View v) {
		Toast.makeText(caller, toastText, Toast.LENGTH_SHORT).show();
		
		Intent i = new Intent(caller, AnonymousActivity.class);
		caller.startActivity(i);
	}
}
