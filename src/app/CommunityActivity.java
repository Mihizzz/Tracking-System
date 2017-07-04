 package app;

//import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.login2.R;

public class CommunityActivity extends Activity {
	
	 private String finalUrl = "";
	    private HandleCommPlaylistXML obj;
	    private EditText title, link, description;
	    AlertDialog.Builder builder;
	    TextView myMsg;
	    String finalURL;
	    EditText comuntiyurl;
	
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	    	 super.onCreate(savedInstanceState);
	    	 setContentView(R.layout.communityplaylist);
	    	 
	    	 builder = new AlertDialog.Builder(this);

	         // Creates textview for centre title
	         myMsg = new TextView(this);
	         myMsg.setText("Destinations!");
	         myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
	         myMsg.setTextSize(35);
	         myMsg.setTextColor(Color.BLACK);
	    }
	    
	    
	    
	    /* public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/


   public void fetch(View view) {





       
       finalUrl = "http://www.phyrates.mwmalith.com/test/?tag=getMessages&username=mihirani&password=123";

       obj = new HandleCommPlaylistXML(finalUrl);
       obj.fetchXML();
       while (obj.parsingComplete) ;
       builder.setCustomTitle(myMsg);

       builder.setMessage(obj.getTitle());
       builder.setPositiveButton("OK", null);

       


       AlertDialog dialog = builder.show();









   }
	

}
