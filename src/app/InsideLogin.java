package app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.login2.R;

public class InsideLogin extends Activity{
	 private Button linkToMap;
	 private Button linkToMessage;
	 private Button linkToLogout;
	 
	 
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		
		
		linkToMap = (Button) findViewById(R.id.linkToMapScreen);
		linkToMessage = (Button) findViewById(R.id.linkToMessageScreen);
		linkToLogout = (Button) findViewById(R.id.linkToLogoutScreen);
		
		
		 // Link to Map Screen
		linkToMap.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(i);
              //  finish();
            }
        });
					
				
		 // Link to Message Screen
		linkToMessage.setOnClickListener(new View.OnClickListener() {
			 
           public void onClick(View view) {
               Intent i = new Intent(getApplicationContext(),
            		   CommunityActivity.class);
               startActivity(i);
             //  finish();
           }
       });
		
		 // Link to logout Screen
		linkToLogout.setOnClickListener(new View.OnClickListener() {
			 
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        loginPage.class);
                startActivity(i);
              //  finish();
            }
        });
	
	}

	
}
