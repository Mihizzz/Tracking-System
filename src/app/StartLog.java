package app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.login2.R;

public class StartLog extends Activity {
	
	
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.startlog);

		
			if (new ConnectionDetector(this).isConnectingToInternet()) {
				
			} 
					
				

		/* start up the splash screen and main menu in a time delayed thread */

		new Handler().postDelayed(new Thread() {
			@Override
			public void run() {
				Intent mainMenu = new Intent(StartLog.this,loginPage.class);
				StartLog.this.startActivity(mainMenu);
				StartLog.this.finish();
				overridePendingTransition(R.drawable.fadein, R.drawable.fadeout);

			}
		}, 1000);
	}

	
}