package com.seboid.unpremiertoast;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

// yo!  

public class UnPremierToastActivity extends Activity {
    @Override
	protected void onDestroy() {
		super.onDestroy();
		Toast.makeText(this,"Destroy!",Toast.LENGTH_SHORT).show();
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		Toast.makeText(this,"Pause!",Toast.LENGTH_SHORT).show();

	}

	@Override
	protected void onResume() {
		super.onResume();
		Toast.makeText(this,"Resume!",Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onStart() {
		super.onStart();
		Toast.makeText(this,"Start!",Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onStop() {
		super.onStop();
		Toast.makeText(this,"Stop!",Toast.LENGTH_SHORT).show();
	}

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		Toast.makeText(this,"Create!",Toast.LENGTH_SHORT).show();        
    }
}