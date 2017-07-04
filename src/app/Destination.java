package app;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.login2.R;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Destination extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_previous_raids);
	 
	
	    AsyncTaskRunner asynctask = new AsyncTaskRunner();
	    asynctask.execute();
	    
	    
	}

	private class AsyncTaskRunner extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... Params) {
			
	   
			
		String result ="";
		InputStream isr=null;
		try{
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost= new HttpPost("http://www.phyrates.mwmalith.com/My/setLocations.php");
			HttpResponse response =httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			isr = entity.getContent();
			
		}
		catch(Exception e)
		{
			Log.e("log_tag", " error in http connection"+ e.toString());
			return "couldnt connect to data base";
			
		}
		
		// response to string
		
		
		try
		{
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
			StringBuilder sb= new StringBuilder();
			String line =null;
			while((line=reader.readLine())!=null)
			{
				sb.append(line + "\n");
			}
			isr.close();
			
			result=sb.toString();
		
		}
		catch(Exception e)
		{
		Log.e("log_log", "Error converting" +e.toString());	
		}
		
		

		String s="";
		
													// parse JSON code
		try
		{
		
		JSONArray jArray = new JSONArray(result);

		
		 for(int i=0;i<jArray.length();i++)
		 {
			JSONObject json = jArray.getJSONObject(i);	
			s=s+ " "+"No:"+json.getInt("Number")+" "+ json.getString("First_Address")+" , "+ 
					json.getString("Second_Address") +"\n\n";
			
		 }	
		} 
		
		catch(Exception e)
		{
			Log.e("log_tag", "error parsing data" +e.toString());
			}
		return s;
		}
		
		
		
@Override
protected void onPostExecute(String result) {
	
	TextView resultView=(TextView) findViewById(R.id.results);
	resultView.setText(result);
	
}


			}	
	}
